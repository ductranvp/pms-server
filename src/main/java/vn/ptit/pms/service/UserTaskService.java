package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.*;
import vn.ptit.pms.domain.key.UserNotificationKey;
import vn.ptit.pms.domain.key.UserTaskKey;
import vn.ptit.pms.repository.UserRepository;
import vn.ptit.pms.repository.UserTaskRepository;
import vn.ptit.pms.service.dto.ActivityDto;
import vn.ptit.pms.service.dto.AssignTaskDto;
import vn.ptit.pms.service.dto.NotificationDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTaskService {
    @Autowired
    UserTaskRepository userTaskRepository;

    @Autowired
    UserService userService;

    @Autowired
    ActivityService activityService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserNotificationService userNotificationService;
    @Autowired
    private TaskService taskService;

    public void save(AssignTaskDto dto) {
        String actor = userService.getCurrentUserFullName();

        Notification savedNotification = notificationService.save(NotificationDto.assignTask(taskService.getById(dto.getTaskId())));

        for (Long userId : dto.getListUserId()) {
            activityService.save(ActivityDto.addMember(actor, userService.getUserFullName(userId), dto.getTaskId()));
            UserTask userTask = new UserTask(new UserTaskKey(userId, dto.getTaskId()));
            userTaskRepository.save(userTask);

            /* Create notification for user */
            UserNotificationKey key = new UserNotificationKey(userId, savedNotification.getId());
            userNotificationService.save(new UserNotification(key));
        }
    }

    public void delete(AssignTaskDto dto) {
        for (Long userId : dto.getListUserId()) {
            UserTask userTask = new UserTask(new UserTaskKey(userId, dto.getTaskId()));
            userTaskRepository.delete(userTask);
        }
    }

    public List<Task> getListTaskOfUser(Long userId) {
        List<Task> result = new ArrayList<>();
        List<UserTask> userTasks = userTaskRepository.findByIdUserId(userId);
        for (UserTask userTask : userTasks) {
            result.add(userTask.getTask());
        }
        return result;
    }

    public List<User> getListUserOfTask(Long taskId) {
        List<User> result = new ArrayList<>();
        List<UserTask> userTasks = userTaskRepository.findByIdTaskId(taskId);
        for (UserTask userTask : userTasks) {
            result.add(userTask.getUser());
        }
        return result;
    }
}
