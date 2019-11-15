package vn.ptit.pms.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vn.ptit.pms.domain.Project;
import vn.ptit.pms.security.UserPrincipal;
import vn.ptit.pms.security.annotation.CurrentUser;
import vn.ptit.pms.service.ProjectLogService;

import java.util.List;

@RestController
@RequestMapping("/api/project-log")
public class ProjectLogResource {
    @Autowired
    ProjectLogService projectLogService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveLog(@RequestBody Long projectId, @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        projectLogService.saveLog(projectId, userPrincipal.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-recent-project")
    public ResponseEntity<List<Project>> getRecentProject(@ApiIgnore @CurrentUser UserPrincipal userPrincipal){
        return ResponseEntity.ok(projectLogService.getRecentProject(userPrincipal.getId()));
    }
}
