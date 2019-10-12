package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.pms.domain.Notification;
import vn.ptit.pms.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationResource {
//    private final Logger log = LoggerFactory.getLogger(NotificationResource.class);
//    private final String ENTITY_NAME = "Notification";
//
//    @Autowired
//    private NotificationService notificationService;
//
//    @PostMapping("/create")
//    public ResponseEntity<Notification> create(@RequestBody Notification notification) {
//        log.info("REST request to create {}: {}", ENTITY_NAME, notification);
//        if (notification.getId() != null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok(notificationService.save(notification));
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<Notification>> getAll() {
//        log.info("REST request to get all {}", ENTITY_NAME);
//        return ResponseEntity.ok(notificationService.getAll());
//    }
//
//    @GetMapping("/get/{id}")
//    public ResponseEntity<Notification> get(@PathVariable Long id) {
//        log.info("REST request to get {}: {}", ENTITY_NAME, id);
//        return ResponseEntity.ok(notificationService.getOneById(id));
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<Notification> update(@RequestBody Notification notification) {
//        log.info("REST request to update {}: {}", ENTITY_NAME, notification);
//        if (notification.getId() == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok(notificationService.save(notification));
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        log.info("REST request to delete {}: {}", ENTITY_NAME, id);
//        notificationService.delete(id);
//        return ResponseEntity.ok().build();
//    }
}
