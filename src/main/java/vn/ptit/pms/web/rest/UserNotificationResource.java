package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vn.ptit.pms.domain.UserNotification;
import vn.ptit.pms.security.UserPrincipal;
import vn.ptit.pms.security.annotation.CurrentUser;
import vn.ptit.pms.service.UserNotificationService;
import vn.ptit.pms.service.dto.UserNotificationDto;

import java.util.List;

@RestController
@RequestMapping("/api/user-notification")
public class UserNotificationResource {
    private final Logger log = LoggerFactory.getLogger(UserNotificationResource.class);
    private final String ENTITY_NAME = "User Notification";

    @Autowired
    private UserNotificationService userNotificationService;

//    @PostMapping("/create")
//    public ResponseEntity<UserNotification> create(@RequestBody UserNotification userNotification) {
//        log.info("REST request to create {}", ENTITY_NAME);
//        if (userNotification.getId() != null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok(userNotificationService.save(userNotification));
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<UserNotification>> getAll() {
//        log.info("REST request to get all {}", ENTITY_NAME);
//        return ResponseEntity.ok(userNotificationService.getAll());
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<UserNotification> update(@RequestBody UserNotification userNotification) {
//        log.info("REST request to update {}", ENTITY_NAME);
//        if (userNotification.getId() == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok(userNotificationService.save(userNotification));
//    }

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<UserNotificationDto>> getUserNotifications(@PathVariable Long userId) {
//        log.info("REST request to get list {} by user id: {}", ENTITY_NAME, userId);
//        return ResponseEntity.ok(userNotificationService.getByUserId(userId));
//    }

    @GetMapping("/current-user")
    public ResponseEntity<List<UserNotificationDto>> getCurrentUserNotification(@ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        log.info("REST request to get list {} by current user: {}", ENTITY_NAME, userPrincipal.getId());
        return ResponseEntity.ok(userNotificationService.getByUserId(userPrincipal.getId()));
    }

    @GetMapping("/unseen")
    public ResponseEntity<List<UserNotificationDto>> getUnseenNotification(@ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        log.info("REST request to get list {} by current user: {}", ENTITY_NAME, userPrincipal.getId());
        return ResponseEntity.ok(userNotificationService.getUnseenNotification(userPrincipal.getId()));
    }

    @PutMapping("/seen")
    public void userSeenNotification(@RequestBody Long notificationId, @ApiIgnore @CurrentUser UserPrincipal userPrincipal){
        userNotificationService.userSeenNotification(userPrincipal.getId(), notificationId);
    }
}
