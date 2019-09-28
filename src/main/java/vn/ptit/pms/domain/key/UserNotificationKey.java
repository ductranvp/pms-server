package vn.ptit.pms.domain.key;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserNotificationKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "notification_id")
    private Long notificationId;
}
