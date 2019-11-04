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
import vn.ptit.pms.repository.TaskRepository;
import vn.ptit.pms.service.NotificationService;
import vn.ptit.pms.service.UserNotificationService;
import vn.ptit.pms.service.UserService;
import vn.ptit.pms.service.UserTaskService;
import vn.ptit.pms.service.dto.NotificationDto;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Component
public class Scheduler {
    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);
    @Autowired
    UserService userService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserNotificationService userNotificationService;
    @Autowired
    private UserTaskService userTaskService;
    @Autowired
    private TaskRepository taskRepository;

//    @Scheduled(fixedRate = 300000)
//    @Transactional
//    public void checkTaskOverdue() {
//        log.info("Check task overdue");
//        List<Task> tasks = taskRepository.findByOverdueAndEstimateEndDateIsNotNull(false);
//        tasks.forEach(task -> {
//            if (task.getEstimateEndDate().getNano() >= Instant.now().getNano()) {
//                taskRepository.setOverdue(task.getId(), true);
//            }
//        });
//    }
//
//    @Scheduled(fixedRate = 300000)
//    public void overdueReminder() {
//        log.info("Overdue reminder");
//        List<Task> tasks = taskRepository.findByRemindedAndOverdueAndEstimateEndDateIsNotNull(false, false);
//        long oneDay = 86400 * 1000;
//        tasks.forEach(task -> {
//            if (task.getEstimateEndDate().toEpochMilli() - oneDay <= Instant.now().toEpochMilli()) {
//                Notification savedNotification = notificationService.save(NotificationDto.taskOverdue(task.getId()));
//                List<User> users = userTaskService.getListUserOfTask(task.getId());
//                users.forEach(user -> {
//                    /* Create notification for user */
//                    UserNotificationKey key = new UserNotificationKey(user.getId(), savedNotification.getId());
//                    userNotificationService.save(new UserNotification(key));
//                });
//                task.setOverdue(true);
//                taskRepository.setReminded(task.getId(), true);
//            }
//        });
//    }
}
