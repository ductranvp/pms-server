package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.pms.domain.Attachment;
import vn.ptit.pms.domain.Notification;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.domain.UserNotification;
import vn.ptit.pms.domain.enumeration.AttachmentType;
import vn.ptit.pms.domain.key.UserNotificationKey;
import vn.ptit.pms.exception.AppException;
import vn.ptit.pms.repository.AttachmentRepository;
import vn.ptit.pms.service.dto.AttachmentDto;
import vn.ptit.pms.service.dto.NotificationDto;
import vn.ptit.pms.service.dto.UserDto;
import vn.ptit.pms.util.CommonConstants;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentService {
    private final String ENTITY_NAME = "Attachment";
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    UserService userService;
    @Value("${server.port}")
    private Long serverPort;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserNotificationService userNotificationService;
    @Autowired
    private UserTaskService userTaskService;

    public Attachment save(Long projectId, Long taskId, Long commentId, boolean isTaskReport, String description, MultipartFile file) {
        String attachmentDir = "http://localhost:" + serverPort + "/attachment/";
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setType(file.getContentType());
        attachment.setDescription(description);
        if (projectId != null) {
            attachment.setTargetType(AttachmentType.PROJECT);
            attachment.setTargetId(projectId);
        }

        if (taskId != null){
            if (isTaskReport) attachment.setTargetType(AttachmentType.TASK_REPORT);
            else attachment.setTargetType(AttachmentType.TASK);
            attachment.setTargetId(taskId);
        }

        if (commentId != null){
            attachment.setTargetType(AttachmentType.COMMENT);
            attachment.setTargetId(commentId);
        }

        String fileName = UUID.randomUUID().toString() + getExtensionFile(file.getOriginalFilename());
        attachment.setUrl(attachmentDir + fileName);
        try {
            Files.createDirectories(Paths.get(CommonConstants.SAVED_ATTACHMENT_PATH));
            Files.copy(file.getInputStream(), Paths.get(CommonConstants.SAVED_ATTACHMENT_PATH, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (taskId != null) {
            User currentUser = userService.getCurrentUser();
            Notification savedNotification = notificationService.save(NotificationDto.attachment(taskId));
            List<User> users = userTaskService.getListUserOfTask(taskId);
            users.forEach(user -> {
                if (user.getId() != currentUser.getId()) {
                    /* Create notification for user */
                    UserNotificationKey key = new UserNotificationKey(user.getId(), savedNotification.getId());
                    userNotificationService.save(new UserNotification(key));
                }
            });
        }
        return attachmentRepository.save(attachment);
    }

    @Transactional
    public void saveProjectAttachment(Long projectId, String description, List<MultipartFile> files){
        files.forEach(file -> {
            save(projectId, null, null, false, description, file);
        });
    }

    public List<AttachmentDto> getProjectAttachment(Long projectId){
        List<AttachmentDto> result = new ArrayList<>();
        attachmentRepository.findByTargetTypeAndTargetId(AttachmentType.PROJECT, projectId).forEach(att -> {
            AttachmentDto dto = new AttachmentDto(att);
            dto.setCreatedBy(new UserDto(userService.getUserById(att.getCreatedBy())));
            result.add(dto);
        });
        return result;
    }

    private String getExtensionFile(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public Attachment getOneById(Long id) {
        try {
            return attachmentRepository.findById(id).get();
        } catch (Exception e) {
            throw new AppException(ENTITY_NAME + " " + id + " could not be found");
        }
    }

    public List<Attachment> getByTaskId(Long taskId) {
        return attachmentRepository.findByTargetTypeAndTargetId(AttachmentType.TASK, taskId);
    }

    public List<AttachmentDto> getTaskReport(Long taskId) {
        List<AttachmentDto> result = new ArrayList<>();
        attachmentRepository.findByTargetTypeAndTargetId(AttachmentType.TASK_REPORT, taskId).forEach(att -> {
            AttachmentDto dto = new AttachmentDto(att);
            dto.setCreatedBy(new UserDto(userService.getUserById(att.getCreatedBy())));
            result.add(dto);
        });
        return result;
    }

    public List<Attachment> getByCommentId(Long commentId) {
        return attachmentRepository.findByTargetTypeAndTargetId(AttachmentType.COMMENT, commentId);
    }

    public List<Attachment> getAll() {
        return attachmentRepository.findAll();
    }

    public void delete(Long id) {
        try {
            Attachment attachment = attachmentRepository.findById(id).get();
            attachment.setDeleted(true);
            attachmentRepository.save(attachment);
        } catch (Exception e) {
            throw new AppException(ENTITY_NAME + " " + id + " could not be found");
        }
    }

    public List<Attachment> getByProjectId(Long projectId) {
        return attachmentRepository.findByTargetTypeAndTargetId(AttachmentType.PROJECT, projectId);
    }
}
