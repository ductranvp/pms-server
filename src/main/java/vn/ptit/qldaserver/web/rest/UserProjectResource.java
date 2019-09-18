package vn.ptit.qldaserver.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.qldaserver.domain.User;
import vn.ptit.qldaserver.service.ProjectInvitationDto;
import vn.ptit.qldaserver.service.ProjectService;
import vn.ptit.qldaserver.service.UserService;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserProjectResource {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @GetMapping("/user-project/{id}")
    public ResponseEntity<Set<User>> getUsersByProjectId(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getUsersByProjectId(id));
    }

    @PostMapping("/user-project/invite")
    public ResponseEntity<Void> inviteUsersToProject(@RequestBody ProjectInvitationDto dto){
        return null;
    }
}
