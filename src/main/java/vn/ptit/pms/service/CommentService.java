package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.pms.domain.Attachment;
import vn.ptit.pms.domain.Comment;
import vn.ptit.pms.exception.AppException;
import vn.ptit.pms.repository.CommentRepository;
import vn.ptit.pms.service.dto.CommentDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final String ENTITY_NAME = "Comment";
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AttachmentService attachmentService;

    public CommentDto create(CommentDto dto, List<MultipartFile> files) {
        CommentDto result = new CommentDto();
        Comment savedComment = commentRepository.save(dto.convertToEntity());
        result.updateAttrFromEntity(savedComment);
        List<Attachment> attachments = new ArrayList<>();
        for (MultipartFile file : files) {
            attachments.add(attachmentService.save(null, result.getId(), file));
        }
        result.setAttachments(attachments);
        return result;
    }

    public List<CommentDto> getTaskComments(Long taskId) {
        List<Comment> comments = commentRepository.findByTaskId(taskId);
        return comments.stream().map(comment -> CommentDto.valueOf(comment)).collect(Collectors.toList());
    }

    public CommentDto update(CommentDto dto, List<MultipartFile> files) {
        dto.setEdited(true);
        commentRepository.save(dto.convertToEntity());
        for (Attachment att : dto.getRemoveAttachments()) {
            attachmentService.delete(att.getId());
        }
        for (MultipartFile file : files) {
            attachmentService.save(null, dto.getId(), file);
        }
        dto.setRemoveAttachments(new ArrayList<>());
        dto.setAttachments(attachmentService.getByCommentId(dto.getId()));
        return dto;
    }

    public void delete(Long id) {
        try {
            Comment comment = commentRepository.findById(id).get();
            comment.setDeleted(true);
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new AppException(ENTITY_NAME + " " + id + " could not be found");
        }
    }
}
