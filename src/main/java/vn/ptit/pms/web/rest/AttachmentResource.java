package vn.ptit.pms.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.pms.domain.Attachment;
import vn.ptit.pms.service.AttachmentService;

@RestController
@RequestMapping("/api")
public class AttachmentResource {
    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/attachment")
    public ResponseEntity<Attachment> createAttachment(@RequestPart(value = "taskId", required = false) Long taskId,
                                                       @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(attachmentService.save(taskId, file));
    }

    @DeleteMapping("/attachment/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        attachmentService.delete(id);
        return ResponseEntity.ok().build();
    }
}
