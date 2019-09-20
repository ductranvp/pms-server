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
@RequestMapping("/api")
public class TaskResource {
    private final Logger log = LoggerFactory.getLogger(TaskResource.class);
    private final String ENTITY_NAME = "Task";

    @Autowired
    private TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        log.info("REST request to save {}: {}", ENTITY_NAME, task);
        if (task.getId() != null) {
            throw new BadRequestException("A new task cannot already have an ID");
        }
        return ResponseEntity.ok(taskService.save(task));
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTask() {
        log.info("REST request to get all {}", ENTITY_NAME);
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        log.info("REST request to get {}: {}", ENTITY_NAME, id);
        return ResponseEntity.ok(taskService.findOne(id));
    }

    @PutMapping("/tasks")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        log.info("REST request to update {}: {}", ENTITY_NAME, task);
        if (task.getId() == null) {
            throw new BadRequestException("Task must have an ID");
        }
        return ResponseEntity.ok(taskService.save(task));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.info("REST request to save {}: {}", ENTITY_NAME, id);
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}
