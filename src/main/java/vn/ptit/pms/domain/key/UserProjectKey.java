package vn.ptit.pms.domain.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserProjectKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "project_id")
    private Long projectId;

    public UserProjectKey(Long userId, Long projectId) {
        this.userId = userId;
        this.projectId = projectId;
    }

    public UserProjectKey() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserProjectKey)) return false;
        final UserProjectKey other = (UserProjectKey) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$projectId = this.getProjectId();
        final Object other$projectId = other.getProjectId();
        if (this$projectId == null ? other$projectId != null : !this$projectId.equals(other$projectId)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserProjectKey;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $projectId = this.getProjectId();
        result = result * PRIME + ($projectId == null ? 43 : $projectId.hashCode());
        return result;
    }

    public String toString() {
        return "UserProjectKey(userId=" + this.getUserId() + ", projectId=" + this.getProjectId() + ")";
    }
}
