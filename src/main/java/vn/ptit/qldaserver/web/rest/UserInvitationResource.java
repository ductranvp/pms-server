package vn.ptit.qldaserver.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.qldaserver.domain.Invitation;
import vn.ptit.qldaserver.domain.Project;
import vn.ptit.qldaserver.domain.User;
import vn.ptit.qldaserver.domain.UserInvitation;
import vn.ptit.qldaserver.domain.enumeration.InvitationStatus;
import vn.ptit.qldaserver.domain.key.UserInvitationKey;
import vn.ptit.qldaserver.repository.ProjectRepository;
import vn.ptit.qldaserver.repository.UserInvitationRepository;
import vn.ptit.qldaserver.repository.UserRepository;
import vn.ptit.qldaserver.security.SecurityUtils;
import vn.ptit.qldaserver.service.*;
import vn.ptit.qldaserver.service.dto.InvitationRequestDto;
import vn.ptit.qldaserver.service.dto.InvitationResponseDto;
import vn.ptit.qldaserver.service.dto.core.ErrorEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserInvitationResource {
    private final Logger log = LoggerFactory.getLogger(UserInvitationResource.class);
    private final String ENTITY_NAME = "User Invitation";
    @Autowired
    InvitationService invitationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailService mailService;
    @Autowired
    private UserInvitationService userInvitationService;
    @Autowired
    private UserInvitationRepository userInvitationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectService projectService;

    /* BASE CRUD */
    @PostMapping("/user-invitation")
    public ResponseEntity<?> create(@RequestBody UserInvitation entity) {
        log.info("REST request to save {}", ENTITY_NAME);
        if (entity.getId() != null) {
            return new ResponseEntity<>(ErrorEntity.badRequest("A new " + ENTITY_NAME + " cannot already have an ID"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userInvitationService.save(entity));
    }

    @GetMapping("/user-invitation")
    public ResponseEntity<List<?>> getAll() {
        log.info("REST request to get all {}", ENTITY_NAME);
        return ResponseEntity.ok(userInvitationService.findAll());
    }

    @GetMapping("/user-invitation/get-one")
    public ResponseEntity<?> get(@Valid UserInvitationKey id) {
        log.info("REST request to get {}", ENTITY_NAME);
        return ResponseEntity.ok(userInvitationService.findOne(id));
    }

    @GetMapping("/user-invitation/by-user/{id}")
    public ResponseEntity<List<?>> getByUser(@PathVariable Long id) {
        log.info("REST request to get {}", ENTITY_NAME);
        return ResponseEntity.ok(userInvitationService.findByUserId(id));
    }

    @GetMapping("/user-invitation/by-invitation/{id}")
    public ResponseEntity<List<?>> getByInvitation(@PathVariable Long id) {
        log.info("REST request to get {}", ENTITY_NAME);
        return ResponseEntity.ok(userInvitationService.findByInvitationId(id));
    }

    @PutMapping("/user-invitation")
    public ResponseEntity<?> update(@RequestBody UserInvitation entity) {
        log.info("REST request to save {}", ENTITY_NAME);
        if (entity.getId() == null) {
            return new ResponseEntity<>(ErrorEntity.badRequest(ENTITY_NAME + " must have and ID"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userInvitationService.save(entity));
    }

    @DeleteMapping("/user-invitation/delete")
    public ResponseEntity<?> delete(@Valid UserInvitationKey id) {
        log.info("REST request to delete {}", ENTITY_NAME);
        userInvitationService.delete(id);
        return ResponseEntity.ok().build();
    }

    /* LOGIC */
    @PostMapping("/user-invitation/request")
    public ResponseEntity<Void> inviteUsersToProject(@RequestBody InvitationRequestDto dto) {
        /*Create invitation first*/
        Invitation invitation = new Invitation(dto.getContent(), dto.getProjectId());
        Invitation saved = invitationService.save(invitation);
        dto.getListEmail().forEach(email -> {
            UserInvitation userInvitation = new UserInvitation();
            userInvitation.setInvitation(saved);

            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (!user.getUsername().equalsIgnoreCase(SecurityUtils.getCurrentUserLogin())) {
                    userInvitation.setUser(user);
                    userInvitationService.save(userInvitation);
                    mailService.sendEmailFromTemplate(user, "projectInvitationEmail", "email.invitation.title");
                }
            }
        });
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user-invitation/response")
    public ResponseEntity<Void> responseToInvitation(@RequestBody InvitationResponseDto dto) {
        User current = userService.getCurrentUser();
        if (current == null) return ResponseEntity.badRequest().build();
        UserInvitation userInvitation = userInvitationService.findOne(
                new UserInvitationKey(current.getId(), dto.getInvitationId()));
        if (dto.isAccept()) {
            userInvitation.setStatus(InvitationStatus.ACCEPTED);
            Long projectId = userInvitation.getInvitation().getProject().getId();
            Project project = projectService.findOne(projectId);
//            Set<User> users = project.getUsers();
//            users.add(new User(current.getId()));
            projectRepository.save(project);
        } else {
            userInvitation.setStatus(InvitationStatus.REJECTED);
        }
        userInvitationRepository.save(userInvitation);
        return ResponseEntity.ok().build();
    }
}
