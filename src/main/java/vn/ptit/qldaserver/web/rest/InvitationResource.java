package vn.ptit.qldaserver.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.qldaserver.domain.Invitation;
import vn.ptit.qldaserver.service.InvitationService;
import vn.ptit.qldaserver.service.dto.core.ErrorEntity;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvitationResource {
    private final Logger log = LoggerFactory.getLogger(InvitationResource.class);
    private final String ENTITY_NAME = "Invitation";

    @Autowired
    private InvitationService service;

    @PostMapping("/invitation")
    public ResponseEntity<?> create(@RequestBody Invitation invitation) {
        log.info("REST request to save {}", ENTITY_NAME);
        if (invitation.getId() != null) {
            return new ResponseEntity<>(ErrorEntity.badRequest("A new " + ENTITY_NAME + " cannot already have an ID"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(service.save(invitation));
    }

    @GetMapping("/invitation")
    public ResponseEntity<List<?>> getAll() {
        log.info("REST request to get all {}", ENTITY_NAME);
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/invitation/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        log.info("REST request to get {}", ENTITY_NAME);
        return ResponseEntity.ok(service.findOne(id));
    }

    @PutMapping("/invitation")
    public ResponseEntity<?> update(@RequestBody Invitation invitation) {
        log.info("REST request to save {}", ENTITY_NAME);
        if (invitation.getId() == null) {
            return new ResponseEntity<>(ErrorEntity.badRequest(ENTITY_NAME + " must have and ID"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(service.save(invitation));
    }

    @DeleteMapping("/invitation/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("REST request to delete {}", ENTITY_NAME);
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
