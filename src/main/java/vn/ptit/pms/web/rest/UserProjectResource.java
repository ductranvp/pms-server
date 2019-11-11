package vn.ptit.pms.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vn.ptit.pms.domain.Project;
import vn.ptit.pms.domain.key.UserProjectKey;
import vn.ptit.pms.security.UserPrincipal;
import vn.ptit.pms.security.annotation.CurrentUser;
import vn.ptit.pms.service.ProjectService;
import vn.ptit.pms.service.UserProjectService;
import vn.ptit.pms.service.UserService;
import vn.ptit.pms.service.dto.TotalUserProjectDto;
import vn.ptit.pms.service.dto.UserProjectDto;
import vn.ptit.pms.service.dto.core.ErrorEntity;

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
    public ResponseEntity<?> getUserProjectByProjectId(@PathVariable Long projectId,
                                                       @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        if (!userProjectService.isUserInProject(userId, projectId))
            return new ResponseEntity<>(ErrorEntity.notFound("Not found"), HttpStatus.NOT_FOUND);
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

    @PutMapping("/update")
    public ResponseEntity<UserProjectDto> update(@RequestBody UserProjectDto dto) {
        return ResponseEntity.ok(userProjectService.updateUserProject(dto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("userId") Long userId, @RequestParam("projectId") Long projectId) {
        if (userProjectService.isLastAdmin(new UserProjectKey(userId, projectId)))
            return new ResponseEntity<>(ErrorEntity.badRequest("You can't leave project because you are the last project manager"), HttpStatus.FORBIDDEN);
        userProjectService.delete(new UserProjectKey(userId, projectId));
        return ResponseEntity.ok().build();
    }
}
