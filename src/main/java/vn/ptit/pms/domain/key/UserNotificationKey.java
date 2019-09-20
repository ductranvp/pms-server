package vn.ptit.pms.domain.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserNotificationKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "notification_id")
    private Long notificationId;

    public UserNotificationKey(Long userId, Long notificationId) {
        this.userId = userId;
        this.notificationId = notificationId;
    }

    public UserNotificationKey() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getNotificationId() {
        return this.notificationId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserNotificationKey)) return false;
        final UserNotificationKey other = (UserNotificationKey) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$notificationId = this.getNotificationId();
        final Object other$notificationId = other.getNotificationId();
        if (this$notificationId == null ? other$notificationId != null : !this$notificationId.equals(other$notificationId))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserNotificationKey;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $notificationId = this.getNotificationId();
        result = result * PRIME + ($notificationId == null ? 43 : $notificationId.hashCode());
        return result;
    }

    public String toString() {
        return "UserNotificationKey(userId=" + this.getUserId() + ", notificationId=" + this.getNotificationId() + ")";
    }
}
