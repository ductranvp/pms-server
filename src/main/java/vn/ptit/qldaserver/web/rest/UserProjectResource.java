package vn.ptit.qldaserver.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.qldaserver.domain.Invitation;
import vn.ptit.qldaserver.domain.User;
import vn.ptit.qldaserver.domain.UserInvitation;
import vn.ptit.qldaserver.domain.enumeration.InvitationStatus;
import vn.ptit.qldaserver.repository.UserRepository;
import vn.ptit.qldaserver.security.SecurityUtils;
import vn.ptit.qldaserver.service.*;
import vn.ptit.qldaserver.service.dto.InvitationRequestDto;

import java.util.Optional;
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
        return null;
    }
}
