package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.pms.domain.Activity;
import vn.ptit.pms.service.ActivityService;
import vn.ptit.pms.service.dto.ActivityDto;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityResource {
    private final Logger log = LoggerFactory.getLogger(ActivityResource.class);
    private final String ENTITY_NAME = "Activity";

    @Autowired
    private ActivityService activityService;

    @PostMapping("/create")
    public ResponseEntity<Activity> create(@RequestBody Activity activity) {
        log.info("REST request to create {}: {}", ENTITY_NAME, activity);
        if (activity.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(activityService.save(activity));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<ActivityDto>> getTaskActivities(@PathVariable Long taskId) {
        log.info("REST request to get list {} by task id: {}", ENTITY_NAME, taskId);
        return ResponseEntity.ok(activityService.getByTaskId(taskId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Activity>> getAll() {
        log.info("REST request to get list {}", ENTITY_NAME);
        return ResponseEntity.ok(activityService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Activity> get(@PathVariable Long id) {
        log.info("REST request to get {}: {}", ENTITY_NAME, id);
        return ResponseEntity.ok(activityService.getOneById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Activity> update(@RequestBody Activity activity) {
        log.info("REST request to update {}: {}", ENTITY_NAME, activity);
        if (activity.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(activityService.save(activity));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST request to delete {}: {}", ENTITY_NAME, id);
        activityService.delete(id);
        return ResponseEntity.ok().build();
    }
}
