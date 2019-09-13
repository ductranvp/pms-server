package vn.ptit.qldaserver.domain.key;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class UserNotificationKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "notification_id")
    private Long notificationId;
}
