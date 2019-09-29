package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Activity;
import vn.ptit.pms.repository.ActivityRepository;

import java.util.List;

@Service
public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;

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
}
