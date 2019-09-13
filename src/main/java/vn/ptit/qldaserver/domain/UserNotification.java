package vn.ptit.qldaserver.domain;

import lombok.*;
import vn.ptit.qldaserver.domain.enumeration.NotificationStatus;
import vn.ptit.qldaserver.domain.key.UserNotificationKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserNotification implements Serializable {
    @EmbeddedId
    UserNotificationKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("notification_id")
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.UNSEEN;
}
