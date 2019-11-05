package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Activity;
import vn.ptit.pms.domain.enumeration.ActivityType;
import vn.ptit.pms.repository.ActivityRepository;
import vn.ptit.pms.service.dto.ActivityDto;
import vn.ptit.pms.service.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

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
        return activities.stream().map(activity -> {
            ActivityDto dto = new ActivityDto(activity);
            if (activity.getType().equals(ActivityType.ADD_MEMBER) || activity.getType().equals(ActivityType.REMOVE_MEMBER))
                dto.setTarget(new UserDto(userService.getUserById(Long.parseLong(activity.getTarget()))));
            dto.setCreatedBy(new UserDto(userService.getUserById(activity.getCreatedBy())));
            return dto;
        }).collect(Collectors.toList());
    }
}
