package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.pms.domain.Invitation;
import vn.ptit.pms.service.InvitationService;
import vn.ptit.pms.service.dto.core.ErrorEntity;

import java.util.List;

@RestController
@RequestMapping("/api/invitation")
public class InvitationResource {
    private final Logger log = LoggerFactory.getLogger(InvitationResource.class);
    private final String ENTITY_NAME = "Invitation";

    @Autowired
    private InvitationService service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Invitation invitation) {
        log.info("REST request to create {}", ENTITY_NAME);
        if (invitation.getId() != null) {
            return new ResponseEntity<>(ErrorEntity.badRequest("A new " + ENTITY_NAME + " cannot already have an ID"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(service.save(invitation));
    }

    @GetMapping("/all")
    public ResponseEntity<List<?>> getAll() {
        log.info("REST request to get all {}", ENTITY_NAME);
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        log.info("REST request to get {}", ENTITY_NAME);
        return ResponseEntity.ok(service.getOneById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Invitation invitation) {
        log.info("REST request to update {}", ENTITY_NAME);
        if (invitation.getId() == null) {
            return new ResponseEntity<>(ErrorEntity.badRequest(ENTITY_NAME + " must have and ID"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(service.save(invitation));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("REST request to delete {}", ENTITY_NAME);
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
