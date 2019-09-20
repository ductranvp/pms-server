package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.pms.domain.Project;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.domain.UserProject;
import vn.ptit.pms.domain.enumeration.ProjectRole;
import vn.ptit.pms.domain.key.UserProjectKey;
import vn.ptit.pms.service.ProjectService;
import vn.ptit.pms.service.UserProjectService;
import vn.ptit.pms.service.UserService;
import vn.ptit.pms.service.dto.core.ErrorEntity;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectResource {
    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);
    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    UserProjectService userProjectService;

    @PostMapping("/projects")
    @Transactional
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        log.info("REST request to save Project : {}", project);
        if (project.getId() != null) {
            return new ResponseEntity<>(ErrorEntity.badRequest("A new project cannot already have an ID"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(projectService.save(project));
    }

    @PutMapping("/projects")
    public ResponseEntity<?> updateProject(@RequestBody Project project) {
        log.info("REST request to update Project : {}", project);
        if (project.getId() == null) {
            return new ResponseEntity<>(ErrorEntity.badRequest("Project must have id"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(projectService.save(project));
    }

    @GetMapping("/projects")
    public ResponseEntity<?> getAllProjects() {
        log.info("REST request to get all Projects");
        List<Project> projects = projectService.findAll();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<?> getProject(@PathVariable Long id) {
        log.info("REST request to get Project : {}", id);
        return ResponseEntity.ok(projectService.findOne(id));
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        log.info("REST request to delete Project : {}", id);
        projectService.delete(id);
        return ResponseEntity.ok().build();
    }
}
