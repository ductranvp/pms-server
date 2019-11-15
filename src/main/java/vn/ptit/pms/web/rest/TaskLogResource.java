package vn.ptit.pms.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vn.ptit.pms.domain.Project;
import vn.ptit.pms.security.UserPrincipal;
import vn.ptit.pms.security.annotation.CurrentUser;
import vn.ptit.pms.service.TaskLogService;
import vn.ptit.pms.service.dto.TaskDrawerDto;
import vn.ptit.pms.service.dto.TaskLogDto;

import java.util.List;

@RestController
@RequestMapping("/api/task-log")
public class TaskLogResource {
    @Autowired
    TaskLogService taskLogService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveLog(@RequestBody Long taskId, @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        taskLogService.saveLog(taskId, userPrincipal.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-recent-task")
    public ResponseEntity<List<TaskLogDto>> getRecentTask(@ApiIgnore @CurrentUser UserPrincipal userPrincipal){
        return ResponseEntity.ok(taskLogService.getRecentTask(userPrincipal.getId()));
    }
}
