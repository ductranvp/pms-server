package vn.ptit.pms.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.ptit.pms.domain.Notification;
import vn.ptit.pms.domain.Task;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.domain.UserNotification;
import vn.ptit.pms.domain.key.UserNotificationKey;
import vn.ptit.pms.service.*;
import vn.ptit.pms.service.dto.NotificationDto;

import java.time.Instant;
import java.util.List;

@Component
public class Scheduler {
    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);
    @Autowired
    UserService userService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserNotificationService userNotificationService;
    @Autowired
    private UserTaskService userTaskService;

    @Scheduled(fixedRate = 300000)
    public void checkTaskOverdue() {
        log.info("Check task overdue");
        List<Task> tasks = taskService.getAll();
        tasks.forEach(task -> {
            if (task.getEstimateEndDate() != null && task.getEstimateEndDate().getNano() >= Instant.now().getNano()) {
                Notification savedNotification = notificationService.save(NotificationDto.taskOverdue(task));
                List<User> users = userTaskService.getListUserOfTask(task.getId());
                users.forEach(user -> {
                    /* Create notification for user */
                    UserNotificationKey key = new UserNotificationKey(user.getId(), savedNotification.getId());
                    userNotificationService.save(new UserNotification(key));
                });
            }
        });
    }
}
