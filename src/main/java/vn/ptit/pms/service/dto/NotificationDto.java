package vn.ptit.pms.service.dto;

import lombok.*;
import vn.ptit.pms.domain.Notification;
import vn.ptit.pms.domain.Task;
import vn.ptit.pms.domain.enumeration.NotificationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class NotificationDto {
    private Long id;
    private String content;
    private NotificationType type;

    public static Notification inviteToProject(String actor, String projectName){
        Notification notification = new Notification();
        notification.setContent("<b>" + actor + "</b> invite you to " + projectName);
        notification.setType(NotificationType.INVITE_TO_PROJECT);
        return notification;
    }

    public static Notification assignTask(Task task){
        Notification notification = new Notification();
        notification.setContent("<b>" + task.getName() + "</b> is overdue");
        notification.setTargetUrl("/project/" + task.getCategory().getProject().getId() + "/task/" + task.getId());
        notification.setType(NotificationType.ASSIGN_TASK);
        return notification;
    }

    public static Notification addSubTask(Task task){
        Notification notification = new Notification();
        notification.setContent("<b>" + task.getName() + "</b> is overdue");
        notification.setTargetUrl("/project/" + task.getCategory().getProject().getId() + "/task/" + task.getId());
        notification.setType(NotificationType.ADD_SUB_TASK);
        return notification;
    }

    public static Notification comment(Task task){
        Notification notification = new Notification();
        notification.setContent("<b>" + task.getName() + "</b> is overdue");
        notification.setTargetUrl("/project/" + task.getCategory().getProject().getId() + "/task/" + task.getId());
        notification.setType(NotificationType.COMMENT);
        return notification;
    }

    public static Notification attachment(Task task){
        Notification notification = new Notification();
        notification.setContent("<b>" + task.getName() + "</b> is overdue");
        notification.setTargetUrl("/project/" + task.getCategory().getProject().getId() + "/task/" + task.getId());
        notification.setType(NotificationType.ATTACHMENT);
        return notification;
    }

    public static Notification taskOverdue(Task task){
        Notification notification = new Notification();
        notification.setContent("<b>" + task.getName() + "</b> is overdue");
        notification.setTargetUrl("/project/" + task.getCategory().getProject().getId() + "/task/" + task.getId());
        notification.setType(NotificationType.TASK_OVERDUE);
        return notification;
    }
}
