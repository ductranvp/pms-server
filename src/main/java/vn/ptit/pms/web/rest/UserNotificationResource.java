package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.pms.domain.UserNotification;
import vn.ptit.pms.service.UserNotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/user-notification")
public class UserNotificationResource {
    private final Logger log = LoggerFactory.getLogger(UserNotificationResource.class);
    private final String ENTITY_NAME = "User Notification";

    @Autowired
    private UserNotificationService userNotificationService;

    @PostMapping("/create")
    public ResponseEntity<UserNotification> create(@RequestBody UserNotification userNotification) {
        log.info("REST request to create {}", ENTITY_NAME);
        if (userNotification.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userNotificationService.save(userNotification));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserNotification>> getAll() {
        log.info("REST request to get all {}", ENTITY_NAME);
        return ResponseEntity.ok(userNotificationService.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<UserNotification> update(@RequestBody UserNotification userNotification) {
        log.info("REST request to update {}", ENTITY_NAME);
        if (userNotification.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userNotificationService.save(userNotification));
    }
}
