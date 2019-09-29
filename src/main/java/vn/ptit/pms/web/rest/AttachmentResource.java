package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.pms.domain.Attachment;
import vn.ptit.pms.service.AttachmentService;

import java.util.List;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentResource {
    private final Logger log = LoggerFactory.getLogger(AttachmentResource.class);
    private final String ENTITY_NAME = "Attachment";

    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/create")
    public ResponseEntity<Attachment> create(@RequestPart(value = "taskId", required = false) Long taskId,
                                             @RequestPart(value = "commentId", required = false) Long commentId,
                                             @RequestPart("file") MultipartFile file) {
        log.info("REST request to create {}", ENTITY_NAME);
        if (taskId == null && commentId == null || taskId != null && commentId != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(attachmentService.save(taskId, commentId, file));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Attachment>> getTaskAttachments(@PathVariable Long taskId) {
        log.info("REST request to get list {} by task id: {}", ENTITY_NAME, taskId);
        return ResponseEntity.ok(attachmentService.getByTaskId(taskId));
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<Attachment>> getCommentAttachments(@PathVariable Long commentId) {
        log.info("REST request to get list {} by comment id: {}", ENTITY_NAME, commentId);
        return ResponseEntity.ok(attachmentService.getByCommentId(commentId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST request to delete {}: {}", ENTITY_NAME, id);
        attachmentService.delete(id);
        return ResponseEntity.ok().build();
    }
}
