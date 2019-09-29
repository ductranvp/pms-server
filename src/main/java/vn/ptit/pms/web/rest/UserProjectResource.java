package vn.ptit.pms.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.pms.domain.Project;
import vn.ptit.pms.service.ProjectService;
import vn.ptit.pms.service.UserProjectService;
import vn.ptit.pms.service.UserService;
import vn.ptit.pms.service.dto.TotalUserProjectDto;
import vn.ptit.pms.service.dto.UserProjectDto;

import java.util.List;

@RestController
@RequestMapping("/api/user-project")
public class UserProjectResource {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    UserProjectService userProjectService;

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<UserProjectDto>> getUserProjectByProjectId(@PathVariable Long projectId) {
        return ResponseEntity.ok(userProjectService.getUserProjectByProjectId(projectId));
    }

    @GetMapping("/current-user/overview")
    public ResponseEntity<TotalUserProjectDto> getOverviewByCurrentUser() {
        return ResponseEntity.ok(userProjectService.getOverviewByCurrentUser());
    }

    @GetMapping("/current-user")
    public ResponseEntity<List<Project>> getProjectByCurrentUser() {
        return ResponseEntity.ok(userProjectService.getProjectByCurrentUser());
    }

    /*Get project that current user and query user have in common*/
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Project>> getProjectByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userProjectService.getProjectByUsername(username));
    }

    @GetMapping("/current-user/role-manager")
    public ResponseEntity<List<Project>> getProjectByCurrentUserWithRoleManager() {
        return ResponseEntity.ok(userProjectService.getProjectByCurrentUserWithRoleManager());
    }

    @GetMapping("/current-user/role-member")
    public ResponseEntity<List<Project>> getProjectByCurrentUserWithRoleMember() {
        return ResponseEntity.ok(userProjectService.getProjectByCurrentUserWithRoleMember());
    }

    @GetMapping("/closed")
    public ResponseEntity<List<Project>> getByCloseStatus() {
        return ResponseEntity.ok(userProjectService.getByCloseStatus());
    }
}
