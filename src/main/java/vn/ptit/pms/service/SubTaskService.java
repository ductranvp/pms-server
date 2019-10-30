package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Notification;
import vn.ptit.pms.domain.SubTask;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.domain.UserNotification;
import vn.ptit.pms.domain.key.UserNotificationKey;
import vn.ptit.pms.repository.SubTaskRepository;
import vn.ptit.pms.repository.UserNotificationRepository;
import vn.ptit.pms.service.dto.ActivityDto;
import vn.ptit.pms.service.dto.NotificationDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubTaskService {
    @Autowired
    SubTaskRepository subTaskRepository;

    @Autowired
    ActivityService activityService;

    @Autowired
    UserService userService;
    @Autowired
    UserNotificationRepository userNotificationRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserTaskService userTaskService;

    @Transactional
    public List<SubTask> create(List<SubTask> list) {
        User currentUser = userService.getCurrentUser();
        Notification savedNotification = notificationService.save(NotificationDto.addSubTask(list.get(0).getParentId()));
        List<User> users = userTaskService.getListUserOfTask(list.get(0).getParentId());

        List<UserNotification> userNotifications = new ArrayList<>();
        users.forEach(user -> {
            if (user.getId() != currentUser.getId()) {
                /* Create notification for user */
                UserNotificationKey key = new UserNotificationKey(user.getId(), savedNotification.getId());
                userNotifications.add(new UserNotification(key));
            }
        });
        userNotificationRepository.saveAll(userNotifications);

        List<SubTask> result = subTaskRepository.saveAll(list);
        activityService.save(ActivityDto.addSubTask(list.get(0).getParentId(), result.get(0).getId()));

        return result;
    }

    public SubTask getOneById(Long id) {
        return subTaskRepository.findById(id).get();
    }

    public List<SubTask> getByParentId(Long parentId) {
        return subTaskRepository.findByParentId(parentId);
    }

    public SubTask update(SubTask subTask) {
        if (subTask.isCompleted()) {
            activityService.save(ActivityDto.checkSubTask(subTask.getParentId(), subTask.getId()));
        } else {
            activityService.save(ActivityDto.unCheckSubTask(subTask.getParentId(), subTask.getId()));
        }
        return subTaskRepository.save(subTask);
    }

    public void delete(Long id) {
        subTaskRepository.deleteById(id);
    }

    public List<SubTask> getAll() {
        return subTaskRepository.findAll();
    }
}
