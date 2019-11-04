package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import vn.ptit.pms.domain.Task;
import vn.ptit.pms.domain.enumeration.ProjectRole;
import vn.ptit.pms.domain.enumeration.TaskStatus;
import vn.ptit.pms.security.UserPrincipal;
import vn.ptit.pms.security.annotation.CurrentUser;
import vn.ptit.pms.service.CategoryService;
import vn.ptit.pms.service.TaskService;
import vn.ptit.pms.service.UserProjectService;
import vn.ptit.pms.service.dto.TaskDto;
import vn.ptit.pms.service.dto.core.ErrorEntity;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskResource {
    private final Logger log = LoggerFactory.getLogger(TaskResource.class);
    private final String ENTITY_NAME = "Task";

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserProjectService userProjectService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Task task, @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        log.info("REST request to create {}: {}", ENTITY_NAME, task);

        Long projectId = categoryService.getProjectIdByCategoryId(task.getCategoryId());
        Long userId = userPrincipal.getId();
        if (!userProjectService.isUserProjectInRole(userId, projectId, ProjectRole.ROLE_MANAGER))
            return new ResponseEntity<>(ErrorEntity.forbidden("Access denied"), HttpStatus.FORBIDDEN);

        return ResponseEntity.ok(taskService.create(task));
    }

    @GetMapping("/get/{taskId}")
    public ResponseEntity<?> get(@PathVariable Long taskId, @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        log.info("REST request to get {}: {}", ENTITY_NAME, taskId);

        Long projectId = taskService.getProjectIdByTaskId(taskId);
        Long userId = userPrincipal.getId();
        if (!userProjectService.isUserInProject(userId, projectId))
            return new ResponseEntity<>(ErrorEntity.notFound("Not found"), HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(taskService.getDtoById(taskId));
    }

    /* Only for Project Manager */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestPart("dto") TaskDto dto,
                                    @RequestPart("files") List<MultipartFile> files,
                                    @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        log.info("REST request to update {}: {}", ENTITY_NAME, dto);

        Long projectId = taskService.getProjectIdByTaskId(dto.getId());
        Long userId = userPrincipal.getId();
        if (!userProjectService.isUserInProject(userId, projectId))
            return new ResponseEntity<>(ErrorEntity.notFound("Not found"), HttpStatus.NOT_FOUND);
        if (!userProjectService.isUserProjectInRole(userId, projectId, ProjectRole.ROLE_MANAGER))
            return new ResponseEntity<>(ErrorEntity.forbidden("Access denied"), HttpStatus.FORBIDDEN);

        taskService.update(dto, files);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-progress")
    public ResponseEntity<?> updateProgress(@RequestBody Task task,
                                            @ApiIgnore @CurrentUser UserPrincipal userPrincipal){
        log.info("REST request to update progress {}: {}", ENTITY_NAME, task);

        Long projectId = taskService.getProjectIdByTaskId(task.getId());
        Long userId = userPrincipal.getId();
        if (!userProjectService.isUserInProject(userId, projectId))
            return new ResponseEntity<>(ErrorEntity.notFound("Not found"), HttpStatus.NOT_FOUND);
        taskService.updateProgress(task.getId(), task.getProgress());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-position")
    public ResponseEntity<?> updatePosition(@RequestBody Task task,
                                            @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        log.info("REST request to update position {}: {}", ENTITY_NAME, task);

        Long projectId = taskService.getProjectIdByTaskId(task.getId());
        Long userId = userPrincipal.getId();
        if (!userProjectService.isUserInProject(userId, projectId))
            return new ResponseEntity<>(ErrorEntity.notFound("Not found"), HttpStatus.NOT_FOUND);
        if (task.getStatus().equals(TaskStatus.VERIFIED) && !userProjectService.isUserProjectInRole(userId, projectId, ProjectRole.ROLE_MANAGER))
            return new ResponseEntity<>(ErrorEntity.forbidden("Access denied"), HttpStatus.FORBIDDEN);

        taskService.updatePosition(task);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-list-position")
    public ResponseEntity<Task> updateListPosition(@RequestBody List<Task> list) {
        log.info("REST request to update list {}: {}", ENTITY_NAME, list);
        taskService.updateListPosition(list);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        log.info("REST request to delete {}: {}", ENTITY_NAME, id);

        Long projectId = taskService.getProjectIdByTaskId(id);
        Long userId = userPrincipal.getId();
        if (!userProjectService.isUserProjectInRole(userId, projectId, ProjectRole.ROLE_MANAGER))
            return new ResponseEntity<>(ErrorEntity.forbidden("Access denied"), HttpStatus.FORBIDDEN);

        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}
