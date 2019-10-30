package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Activity;
import vn.ptit.pms.domain.Task;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.domain.enumeration.ActivityType;
import vn.ptit.pms.repository.ActivityRepository;
import vn.ptit.pms.service.dto.ActivityDto;

import java.util.ArrayList;
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

    public List<ActivityDto> getByTaskId(Long taskId) {
        List<Activity> activities = activityRepository.findByTaskIdOrderByCreatedDateDesc(taskId);
        return new ArrayList<>();
    }
}
