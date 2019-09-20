package vn.ptit.pms.domain;

import vn.ptit.pms.domain.enumeration.NotificationStatus;
import vn.ptit.pms.domain.key.UserNotificationKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_notification")
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

    public UserNotification(UserNotificationKey id, User user, Notification notification, NotificationStatus status) {
        this.id = id;
        this.user = user;
        this.notification = notification;
        this.status = status;
    }

    public UserNotification() {
    }

    public UserNotificationKey getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public Notification getNotification() {
        return this.notification;
    }

    public NotificationStatus getStatus() {
        return this.status;
    }

    public void setId(UserNotificationKey id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserNotification)) return false;
        final UserNotification other = (UserNotification) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$notification = this.getNotification();
        final Object other$notification = other.getNotification();
        if (this$notification == null ? other$notification != null : !this$notification.equals(other$notification))
            return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserNotification;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $notification = this.getNotification();
        result = result * PRIME + ($notification == null ? 43 : $notification.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "UserNotification(id=" + this.getId() + ", user=" + this.getUser() + ", notification=" + this.getNotification() + ", status=" + this.getStatus() + ")";
    }
}
