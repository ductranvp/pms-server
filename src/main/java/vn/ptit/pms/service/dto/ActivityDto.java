package vn.ptit.pms.service.dto;

import lombok.*;
import vn.ptit.pms.domain.Activity;
import vn.ptit.pms.domain.enumeration.ActivityType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivityDto {
    private Long id;
    private String content;
    private ActivityType type;
    private Long taskId;

    public Activity toEntity() {
        return new Activity(id, content, type, taskId);
    }

    public static Activity created(String actor, Long taskId) {
        Activity activity = new Activity();
        activity.setType(ActivityType.CREATED);
        activity.setTaskId(taskId);
        activity.setContent("<b>" + actor + "</b> created this task");
        return activity;
    }

    public static Activity addMember(String actor, String added, Long taskId){
        Activity activity = new Activity();
        activity.setType(ActivityType.ADD_MEMBER);
        activity.setTaskId(taskId);
        activity.setContent("<b>" + actor + "</b> add <b>" + added + "</b> to this task");
        return activity;
    }

    public static Activity moveTask(String actor, String to, Long taskId){
        Activity activity = new Activity();
        activity.setType(ActivityType.MOVE_TASK);
        activity.setTaskId(taskId);
        activity.setContent("<b>" + actor + "</b> move to <b>" + to + "</b>");
        return activity;
    }

    public static Activity changePriority(String actor, String to, Long taskId){
        Activity activity = new Activity();
        activity.setType(ActivityType.CHANGE_PRIORITY);
        activity.setTaskId(taskId);
        activity.setContent("<b>" + actor + "</b> change priority to <b>" + to + "</b>");
        return activity;
    }

    public static Activity addSubTask(String actor, String subTaskName, Long taskId){
        Activity activity = new Activity();
        activity.setType(ActivityType.ADD_SUB_TASK);
        activity.setTaskId(taskId);
        activity.setContent("<b>" + actor + "</b> add sub task <b>" + subTaskName + "</b>");
        return activity;
    }

    public static Activity checkSubTask(String actor, String subTaskName, Long taskId){
        Activity activity = new Activity();
        activity.setType(ActivityType.CHECK_SUB_TASK);
        activity.setTaskId(taskId);
        activity.setContent("<b>" + actor + "</b> mark <b>" + subTaskName + "</b> completed");
        return activity;
    }

    public static Activity unCheckSubTask(String actor, String subTaskName, Long taskId){
        Activity activity = new Activity();
        activity.setType(ActivityType.UNCHECK_SUB_TASK);
        activity.setTaskId(taskId);
        activity.setContent("<b>" + actor + "</b> mark <b>" + subTaskName + "</b> uncompleted");
        return activity;
    }
}
