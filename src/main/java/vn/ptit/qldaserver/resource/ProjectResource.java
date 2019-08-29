package vn.ptit.qldaserver.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.qldaserver.payload.ApiResponse;
import vn.ptit.qldaserver.model.Project;
import vn.ptit.qldaserver.repository.ProjectRepository;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectResource {
    @Autowired
    ProjectRepository projectRepository;

    @GetMapping
    public ResponseEntity<?> getAllProjects(){
        List<Project> projects = projectRepository.findAll();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProject(@PathVariable Long projectId){
        Project project = projectRepository.findById(projectId).get();
        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        Project entity = new Project();
        entity.setName(project.getName());
        entity.setDescription(project.getDescription());
        Project result = projectRepository.save(entity);
        return ResponseEntity.ok(new ApiResponse(true, "Create project successfully", result));
    }

    @PutMapping
    public ResponseEntity<?> updateProject(@RequestBody Project project) {
        Project result = projectRepository.save(project);
        return ResponseEntity.ok(new ApiResponse(true, "Update project successfully", result));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        Project project = projectRepository.findById(projectId).get();
        project.setDeleted(true);
        projectRepository.save(project);
        return ResponseEntity.ok(new ApiResponse(true, "Delete project successfully", null));
    }
}
