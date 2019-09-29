package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.pms.service.CommentService;
import vn.ptit.pms.service.dto.CommentDto;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentResource {
    private final Logger log = LoggerFactory.getLogger(CommentResource.class);
    private final String ENTITY_NAME = "Comment";
    @Autowired
    CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDto> create(@RequestPart("entity") CommentDto dto,
                                             @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        log.info("REST request to create {}", ENTITY_NAME);
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(commentService.create(dto, files));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<CommentDto>> getTaskComments(@PathVariable Long taskId) {
        log.info("REST request to get list {} by task id: {}", ENTITY_NAME, taskId);
        return ResponseEntity.ok(commentService.getTaskComments(taskId));
    }

    @PutMapping("/update")
    public ResponseEntity<CommentDto> update(@RequestPart("entity") CommentDto dto,
                                             @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        log.info("REST request to update {}", ENTITY_NAME);
        if (dto.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(commentService.update(dto, files));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST request to delete {}: {}", ENTITY_NAME, id);
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }
}
