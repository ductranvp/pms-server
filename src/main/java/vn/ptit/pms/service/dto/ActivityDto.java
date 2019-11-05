package vn.ptit.pms.service.dto;

import lombok.*;
import vn.ptit.pms.domain.Activity;
import vn.ptit.pms.domain.enumeration.ActivityType;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivityDto {
    private Long id;
    private ActivityType type;
    private Long taskId;
    private Object target;
    private UserDto createdBy;
    private Instant createdTime;

    public ActivityDto(Activity activity) {
        this.id = activity.getId();
        this.type = activity.getType();
        this.taskId = activity.getTaskId();
        this.target = activity.getTarget();
        this.createdTime = activity.getCreatedDate();
    }

    public static Activity created(Long taskId) {
        Activity activity = new Activity();
        activity.setType(ActivityType.CREATED);
        activity.setTaskId(taskId);
        return activity;
    }

    public static Activity addMember(Long taskId, Long target){
        Activity activity = new Activity();
        activity.setType(ActivityType.ADD_MEMBER);
        activity.setTaskId(taskId);
        activity.setTarget(String.valueOf(target));
        return activity;
    }

    public static Activity removeMember(Long taskId, Long target){
        Activity activity = new Activity();
        activity.setType(ActivityType.REMOVE_MEMBER);
        activity.setTaskId(taskId);
        activity.setTarget(String.valueOf(target));
        return activity;
    }

    public static Activity updateProgress(Long taskId, Long target){
        Activity activity = new Activity();
        activity.setType(ActivityType.UPDATE_PROGRESS);
        activity.setTaskId(taskId);
        activity.setTarget(String.valueOf(target));
        return activity;
    }

    public static Activity moveTask(Long taskId, String target){
        Activity activity = new Activity();
        activity.setType(ActivityType.MOVE_TASK);
        activity.setTaskId(taskId);
        activity.setTarget(target);
        return activity;
    }

    public static Activity changePriority(Long taskId, String target){
        Activity activity = new Activity();
        activity.setType(ActivityType.CHANGE_PRIORITY);
        activity.setTaskId(taskId);
        activity.setTarget(target);
        return activity;
    }

    public static Activity addSubTask(Long taskId, Long target){
        Activity activity = new Activity();
        activity.setType(ActivityType.ADD_SUB_TASK);
        activity.setTaskId(taskId);
        activity.setTarget(String.valueOf(target));
        return activity;
    }

    public static Activity checkSubTask(Long taskId, Long target){
        Activity activity = new Activity();
        activity.setType(ActivityType.CHECK_SUB_TASK);
        activity.setTaskId(taskId);
        activity.setTarget(String.valueOf(target));
        return activity;
    }

    public static Activity unCheckSubTask(Long taskId, Long target){
        Activity activity = new Activity();
        activity.setType(ActivityType.UNCHECK_SUB_TASK);
        activity.setTaskId(taskId);
        activity.setTarget(String.valueOf(target));
        return activity;
    }
}
