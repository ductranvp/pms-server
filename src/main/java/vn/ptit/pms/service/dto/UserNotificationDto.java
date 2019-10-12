package vn.ptit.pms.service.dto;

import lombok.*;
import vn.ptit.pms.domain.Notification;
import vn.ptit.pms.domain.enumeration.NotificationStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserNotificationDto {
    private Long userId;
    private Long notificationId;
    private NotificationStatus status;
    private Notification notification;
}
