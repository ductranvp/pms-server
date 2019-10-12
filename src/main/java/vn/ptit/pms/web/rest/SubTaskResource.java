package vn.ptit.pms.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.pms.domain.SubTask;
import vn.ptit.pms.service.SubTaskService;

import java.util.List;

@RestController
@RequestMapping("/api/sub-task")
public class SubTaskResource {
    @Autowired
    SubTaskService subTaskService;

    @PostMapping("/create")
    public ResponseEntity<SubTask> create(@RequestBody SubTask subTask) {
        return ResponseEntity.ok(subTaskService.create(subTask));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SubTask> getOneById(@PathVariable Long id) {
        return ResponseEntity.ok(subTaskService.getOneById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubTask>> getAll() {
        return ResponseEntity.ok(subTaskService.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<SubTask> update(@RequestBody SubTask subTask) {
        return ResponseEntity.ok(subTaskService.update(subTask));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subTaskService.delete(id);
        return ResponseEntity.ok().build();
    }
}
