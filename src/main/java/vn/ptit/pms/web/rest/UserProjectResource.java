package vn.ptit.pms.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.pms.service.ProjectService;
import vn.ptit.pms.service.dto.TotalUserProjectDto;
import vn.ptit.pms.service.UserProjectService;
import vn.ptit.pms.service.UserService;
import vn.ptit.pms.service.dto.UserProjectDto;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserProjectResource {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    UserProjectService userProjectService;

    @GetMapping("/user-project/{id}")
    public ResponseEntity<List<UserProjectDto>> getUsersInProject(@PathVariable Long id) {
        return ResponseEntity.ok(userProjectService.getUsersInProject(id));
    }

    @GetMapping("/user-project/overview")
    public ResponseEntity<TotalUserProjectDto> getCurrentUserAllProject() {
        return ResponseEntity.ok(userProjectService.getCurrentUserAllProject());
    }

    @GetMapping("/user-project/current-user")
    public ResponseEntity<List<?>> getCurrentUserProject() {
        return ResponseEntity.ok(userProjectService.getCurrentUserProject());
    }

    /*Get project that current user and query user have in common*/
    @GetMapping("/user-project/get/{username}")
    public ResponseEntity<List<?>> getProjectByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userProjectService.getProjectByUsername(username));
    }

    @GetMapping("/user-project/role-manager")
    public ResponseEntity<List<?>> getUserProjectWithRoleManager() {
        return ResponseEntity.ok(userProjectService.getUserProjectWithRoleManager());
    }

    @GetMapping("/user-project/role-member")
    public ResponseEntity<List<?>> getUserProjectWithRoleMember() {
        return ResponseEntity.ok(userProjectService.getUserProjectWithRoleMember());
    }

    @GetMapping("/user-project/closed")
    public ResponseEntity<List<?>> getUserClosedProject() {
        return ResponseEntity.ok(userProjectService.getUserClosedProject());
    }
}
