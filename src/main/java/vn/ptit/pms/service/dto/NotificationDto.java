package vn.ptit.pms.service.dto;

import lombok.*;
import vn.ptit.pms.domain.Category;
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
    private Object target;

    public static Notification inviteToProject(Long target){
        Notification notification = new Notification();
        notification.setType(NotificationType.INVITE_TO_PROJECT);
        notification.setTarget(String.valueOf(target));
        return notification;
    }

    public static Notification assignTask(Long target){
        Notification notification = new Notification();
        notification.setType(NotificationType.ASSIGN_TASK);
        notification.setTarget(String.valueOf(target));
        return notification;
    }

    public static Notification addSubTask(Long target){
        Notification notification = new Notification();
        notification.setType(NotificationType.ADD_SUB_TASK);
        notification.setTarget(String.valueOf(target));
        return notification;
    }

    public static Notification comment(Long target){
        Notification notification = new Notification();
        notification.setType(NotificationType.COMMENT);
        notification.setTarget(String.valueOf(target));
        return notification;
    }

    public static Notification attachment(Long target){
        Notification notification = new Notification();
        notification.setType(NotificationType.ATTACHMENT);
        notification.setTarget(String.valueOf(target));
        return notification;
    }

    public static Notification taskOverdue(Long target){
        Notification notification = new Notification();
        notification.setType(NotificationType.TASK_OVERDUE);
        notification.setTarget(String.valueOf(target));
        return notification;
    }
}
