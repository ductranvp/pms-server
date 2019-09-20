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
import vn.ptit.pms.service.dto.core.ErrorEntity;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

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
    private UserInvitationService userInvitationService;

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

    @GetMapping("/user-invitation/current-user")
    public ResponseEntity<List<?>> getByCurrentUser() {
        log.info("REST request to get {}", ENTITY_NAME);
        return ResponseEntity.ok(userInvitationService.findByCurrentUser());
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

    @PostMapping("/user-invitation/request")
    @Transactional
    public ResponseEntity<?> sendInvitationRequest(@RequestBody InvitationRequestDto dto) {
        log.info("REST request to send {}", ENTITY_NAME);
        userInvitationService.sendInvitationRequest(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user-invitation/response")
    public ResponseEntity<?> responseToInvitation(@RequestBody InvitationResponseDto dto) {
        log.info("REST request to response to {}", ENTITY_NAME);
        userInvitationService.responseToInvitation(dto);
        return ResponseEntity.ok().build();
    }
}
