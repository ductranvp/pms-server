package vn.ptit.pms.domain;

import lombok.*;
import vn.ptit.pms.domain.enumeration.NotificationStatus;
import vn.ptit.pms.domain.key.UserNotificationKey;

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
    public static final long serialVersionUID = 1L;
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

    public UserNotification(UserNotificationKey key) {
        this.id = key;
    }
}
