package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.pms.domain.UserInvitation;
import vn.ptit.pms.domain.key.UserInvitationKey;
import vn.ptit.pms.repository.UserRepository;
import vn.ptit.pms.service.InvitationService;
import vn.ptit.pms.service.UserInvitationService;
import vn.ptit.pms.service.dto.InvitationRequestDto;
import vn.ptit.pms.service.dto.InvitationResponseDto;
import vn.ptit.pms.service.dto.UserInvitationDto;
import vn.ptit.pms.service.dto.core.ErrorEntity;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user-invitation")
public class UserInvitationResource {
    private final Logger log = LoggerFactory.getLogger(UserInvitationResource.class);
    private final String ENTITY_NAME = "User Invitation";
    @Autowired
    InvitationService invitationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserInvitationService userInvitationService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserInvitation entity) {
        log.info("REST request to create {}", ENTITY_NAME);
        if (entity.getId() != null) {
            return new ResponseEntity<>(ErrorEntity.badRequest("A new " + ENTITY_NAME + " cannot already have an ID"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userInvitationService.save(entity));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserInvitation>> getAll() {
        log.info("REST request to get all {}", ENTITY_NAME);
        return ResponseEntity.ok(userInvitationService.getAll());
    }

    @GetMapping("/get")
    public ResponseEntity<UserInvitation> get(@Valid UserInvitationKey id) {
        log.info("REST request to get {}", ENTITY_NAME);
        return ResponseEntity.ok(userInvitationService.getOneById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserInvitation>> getByUserId(@PathVariable Long userId) {
        log.info("REST request to get list {} by user id: {}", ENTITY_NAME, userId);
        return ResponseEntity.ok(userInvitationService.getByUserId(userId));
    }

    @GetMapping("/current-user")
    public ResponseEntity<List<UserInvitationDto>> getByCurrentUser() {
        log.info("REST request to get list {} by current user", ENTITY_NAME);
        return ResponseEntity.ok(userInvitationService.getByCurrentUser());
    }

    @GetMapping("/invitation/{invitationId}")
    public ResponseEntity<List<UserInvitation>> getByInvitationId(@PathVariable Long invitationId) {
        log.info("REST request to get list {} by invitation id: {}", ENTITY_NAME, invitationId);
        return ResponseEntity.ok(userInvitationService.getByInvitationId(invitationId));
    }

    @PutMapping("/user-invitation")
    public ResponseEntity<?> update(@RequestBody UserInvitation entity) {
        log.info("REST request to save {}", ENTITY_NAME);
        if (entity.getId() == null) {
            return new ResponseEntity<>(ErrorEntity.badRequest(ENTITY_NAME + " must have and ID"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userInvitationService.save(entity));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@Valid UserInvitationKey id) {
        log.info("REST request to delete {}: {}", ENTITY_NAME, id);
        userInvitationService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/request")
    @Transactional
    public ResponseEntity<?> sendInvitationRequest(@RequestBody InvitationRequestDto dto) {
        log.info("REST request to send {}", ENTITY_NAME);
        userInvitationService.sendInvitationRequest(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/response")
    public ResponseEntity<?> responseToInvitation(@RequestBody InvitationResponseDto dto) {
        log.info("REST request to response to {}", ENTITY_NAME);
        userInvitationService.responseToInvitation(dto);
        return ResponseEntity.ok().build();
    }
}
