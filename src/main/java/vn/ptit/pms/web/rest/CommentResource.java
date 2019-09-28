package vn.ptit.pms.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.pms.service.CommentService;
import vn.ptit.pms.service.dto.CommentDto;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentResource {
    @Autowired
    CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<CommentDto> createComment(@RequestPart("entity") CommentDto dto,
                                                    @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(commentService.create(dto, files));
    }

    @GetMapping("/comment/{taskId}")
    public ResponseEntity<List<CommentDto>> getTaskComments(@PathVariable Long taskId) {
        return ResponseEntity.ok(commentService.getTaskComments(taskId));
    }

    @PutMapping("/comment")
    public ResponseEntity<CommentDto> updateComment(@RequestPart("entity") CommentDto dto,
                                                    @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        if (dto.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(commentService.update(dto, files));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }
}
