package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.pms.domain.Task;
import vn.ptit.pms.exception.BadRequestException;
import vn.ptit.pms.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskResource {
    private final Logger log = LoggerFactory.getLogger(TaskResource.class);
    private final String ENTITY_NAME = "Task";

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<Task> create(@RequestBody Task task) {
        log.info("REST request to create {}: {}", ENTITY_NAME, task);
        if (task.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return ResponseEntity.ok(taskService.create(task));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Task>> list() {
        log.info("REST request to get all {}", ENTITY_NAME);
        return ResponseEntity.ok(taskService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Task> get(@PathVariable Long id) {
        log.info("REST request to get {}: {}", ENTITY_NAME, id);
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody Task task) {
        log.info("REST request to update {}: {}", ENTITY_NAME, task);
        if (task.getId() == null) {
            throw new BadRequestException(ENTITY_NAME + " must have an ID");
        }
        return ResponseEntity.ok(taskService.update(task));
    }

    @PutMapping("/update-list")
    public ResponseEntity<Task> updateList(@RequestBody List<Task> list) {
        log.info("REST request to update list {}: {}", ENTITY_NAME, list);
        taskService.updateList(list);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST request to delete {}: {}", ENTITY_NAME, id);
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}
