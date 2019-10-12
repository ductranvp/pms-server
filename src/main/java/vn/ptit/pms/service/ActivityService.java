package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Activity;
import vn.ptit.pms.domain.Task;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.domain.enumeration.ActivityType;
import vn.ptit.pms.repository.ActivityRepository;

import java.util.List;

@Service
public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    public List<Activity> getAll() {
        return activityRepository.findAll();
    }

    public Activity getOneById(Long id) {
        return activityRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        activityRepository.deleteById(id);
    }

    public List<Activity> getByTaskId(Long taskId) {
        return activityRepository.findByTaskId(taskId);
    }

    public Activity createActivity(ActivityType type, Long taskId) {
        User user = userService.getCurrentUser();
        Activity activity = new Activity();
        activity.setType(type);
        activity.setTaskId(taskId);
        Task task = taskService.getById(taskId);
        activity.setContent(user.getFirstName() + user.getLastName() + " created " + task.getName());
        return activityRepository.save(activity);
    }

}
