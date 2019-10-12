package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Notification;
import vn.ptit.pms.domain.SubTask;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.domain.UserNotification;
import vn.ptit.pms.domain.key.UserNotificationKey;
import vn.ptit.pms.repository.SubTaskRepository;
import vn.ptit.pms.service.dto.ActivityDto;
import vn.ptit.pms.service.dto.NotificationDto;

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
    private NotificationService notificationService;
    @Autowired
    private UserNotificationService userNotificationService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserTaskService userTaskService;

    public SubTask create(SubTask subTask) {
        User currentUser = userService.getCurrentUser();
        String actor = currentUser.getFirstName() + " " + currentUser.getLastName();
        activityService.save(ActivityDto.addSubTask(actor, subTask.getName(), subTask.getParentId()));

        Notification savedNotification = notificationService.save(NotificationDto.addSubTask(taskService.getById(subTask.getParentId())));
        List<User> users = userTaskService.getListUserOfTask(subTask.getParentId());
        users.forEach(user -> {
            if (user.getId() != currentUser.getId()){
                /* Create notification for user */
                UserNotificationKey key = new UserNotificationKey(user.getId(), savedNotification.getId());
                userNotificationService.save(new UserNotification(key));
            }
        });

        return subTaskRepository.save(subTask);
    }

    public SubTask getOneById(Long id) {
        return subTaskRepository.findById(id).get();
    }

    public List<SubTask> getByParentId(Long parentId) {
        return subTaskRepository.findByParentId(parentId);
    }

    public SubTask update(SubTask subTask) {
        String actor = userService.getCurrentUserFullName();
        if (subTask.isCompleted()){
            activityService.save(ActivityDto.checkSubTask(actor, subTask.getName(), subTask.getParentId()));
        } else {
            activityService.save(ActivityDto.unCheckSubTask(actor, subTask.getName(), subTask.getParentId()));
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
