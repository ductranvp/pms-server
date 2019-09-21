package vn.ptit.pms.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.pms.domain.Task;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.service.UserTaskService;
import vn.ptit.pms.service.dto.AssignTaskDto;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserTaskResource {
    @Autowired
    private UserTaskService userTaskService;

    @PostMapping("/user-task/add")
    @Transactional
    public ResponseEntity<Void> addTaskAssignment(@RequestBody AssignTaskDto dto) {
        userTaskService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user-task/remove")
    @Transactional
    public ResponseEntity<Void> removeTaskAssignment(@RequestBody AssignTaskDto dto) {
        userTaskService.delete(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user-task/get-tasks/{userId}")
    public ResponseEntity<List<Task>> getListTaskOfUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userTaskService.getListTaskOfUser(userId));
    }

    @GetMapping("/user-task/get-users/{taskId}")
    public ResponseEntity<List<User>> getListUserOfTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(userTaskService.getListUserOfTask(taskId));
    }
}
