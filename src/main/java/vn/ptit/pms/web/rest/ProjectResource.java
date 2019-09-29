package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vn.ptit.pms.domain.Project;
import vn.ptit.pms.security.UserPrincipal;
import vn.ptit.pms.security.annotation.CurrentUser;
import vn.ptit.pms.service.ProjectService;
import vn.ptit.pms.service.UserProjectService;
import vn.ptit.pms.service.UserService;
import vn.ptit.pms.service.dto.core.ErrorEntity;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectResource {
    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);
    private final String ENTITY_NAME = "Project";
    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    UserProjectService userProjectService;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<?> create(@RequestBody Project project, @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        log.info("REST request to create {}", ENTITY_NAME);
        if (project.getId() != null) {
            return new ResponseEntity<>(ErrorEntity.badRequest("A new " + ENTITY_NAME + " cannot already have an ID"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(projectService.save(project, userPrincipal));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Project project, @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        log.info("REST request to update {} : {}", ENTITY_NAME, project);
        if (project.getId() == null) {
            return new ResponseEntity<>(ErrorEntity.badRequest(ENTITY_NAME + " must have id"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(projectService.save(project, userPrincipal));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAll() {
        log.info("REST request to get all {}", ENTITY_NAME);
        return ResponseEntity.ok(projectService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        log.info("REST request to get {} : {}", ENTITY_NAME, id);
        return ResponseEntity.ok(projectService.getOneById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("REST request to delete {} : {}", ENTITY_NAME, id);
        projectService.delete(id);
        return ResponseEntity.ok().build();
    }
}
