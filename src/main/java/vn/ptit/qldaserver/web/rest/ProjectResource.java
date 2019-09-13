package vn.ptit.qldaserver.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.qldaserver.domain.Project;
import vn.ptit.qldaserver.service.ProjectService;
import vn.ptit.qldaserver.service.dto.ApiResponseDto;
import vn.ptit.qldaserver.service.dto.ProjectDto;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectResource {
    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);
    @Autowired
    ProjectService projectService;

    @PostMapping("/projects")
    public ResponseEntity<?> createProject(@RequestBody ProjectDto projectDto) {
        log.info("REST request to save Project : {}", projectDto);
        if (projectDto.getId() != null) {
            return new ResponseEntity<>(
                    new ApiResponseDto(false, "A new project cannot already have an ID"), HttpStatus.BAD_REQUEST);
        }
        Project result = projectService.save(projectDto);
        return new ResponseEntity<>(new ApiResponseDto(true, "Create project successfully", result), HttpStatus.OK);
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
        Project project = projectService.findOne(id);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/projects")
    public ResponseEntity<?> updateProject(@RequestBody ProjectDto projectDto) {
        log.info("REST request to update Project : {}", projectDto);
        Project project = projectService.save(projectDto);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        log.info("REST request to delete Project : {}", id);
        projectService.delete(id);
        return ResponseEntity.ok().build();
    }
}
