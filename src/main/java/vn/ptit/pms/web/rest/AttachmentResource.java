package vn.ptit.pms.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.pms.domain.Attachment;
import vn.ptit.pms.service.AttachmentService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AttachmentResource {
    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/attachment")
    public ResponseEntity<Attachment> createAttachment(@RequestPart(value = "taskId", required = false) Long taskId,
                                                       @RequestPart(value = "commentId", required = false) Long commentId,
                                                       @RequestPart("file") MultipartFile file) {
        if (taskId == null && commentId == null || taskId != null && commentId != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(attachmentService.save(taskId, commentId, file));
    }

    @GetMapping("/attachment/task/{taskId}")
    public ResponseEntity<List<Attachment>> getTaskAttachments(@PathVariable Long taskId) {
        return ResponseEntity.ok(attachmentService.findByTaskId(taskId));
    }

    @GetMapping("/attachment/comment/{commentId}")
    public ResponseEntity<List<Attachment>> getCommentAttachment(@PathVariable Long commentId) {
        return ResponseEntity.ok(attachmentService.findByCommentId(commentId));
    }

    @DeleteMapping("/attachment/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        attachmentService.delete(id);
        return ResponseEntity.ok().build();
    }
}
