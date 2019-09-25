package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.pms.domain.Attachment;
import vn.ptit.pms.exception.AppException;
import vn.ptit.pms.repository.AttachmentRepository;
import vn.ptit.pms.util.CommonConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AttachmentService {
    private final String ENTITY_NAME = "Attachment";

    @Value("${server.port}")
    private Long serverPort;

    @Autowired
    AttachmentRepository attachmentRepository;

    public Attachment save(Long taskId, MultipartFile file) {
        String attachmentDir = "http://localhost:" + serverPort + "/attachment/";
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setType(file.getContentType());
        attachment.setTaskId(taskId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsszz");
        String fileName = sdf.format(new Date()) + getExtensionFile(file.getOriginalFilename());
        attachment.setUrl(attachmentDir + fileName);
        try {
            Files.createDirectories(Paths.get(CommonConstants.SAVED_ATTACHMENT_PATH));
            Files.copy(file.getInputStream(), Paths.get(CommonConstants.SAVED_ATTACHMENT_PATH, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attachmentRepository.save(attachment);
    }

    private String getExtensionFile(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public Attachment findOne(Long id) {
        try {
            return attachmentRepository.findById(id).get();
        } catch (Exception e) {
            throw new AppException(ENTITY_NAME + " " + id + " could not be found");
        }
    }

    public List<Attachment> findAll() {
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
}
