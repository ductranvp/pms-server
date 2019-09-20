package vn.ptit.pms.domain.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserTaskKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "task_id")
    private Long taskId;

    public UserTaskKey(Long userId, Long taskId) {
        this.userId = userId;
        this.taskId = taskId;
    }

    public UserTaskKey() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserTaskKey)) return false;
        final UserTaskKey other = (UserTaskKey) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$taskId = this.getTaskId();
        final Object other$taskId = other.getTaskId();
        if (this$taskId == null ? other$taskId != null : !this$taskId.equals(other$taskId)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserTaskKey;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $taskId = this.getTaskId();
        result = result * PRIME + ($taskId == null ? 43 : $taskId.hashCode());
        return result;
    }

    public String toString() {
        return "UserTaskKey(userId=" + this.getUserId() + ", taskId=" + this.getTaskId() + ")";
    }
}
