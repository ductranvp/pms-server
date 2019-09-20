package vn.ptit.pms.domain.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserInvitationKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "invitation_id")
    private Long invitationId;

    public UserInvitationKey(Long userId, Long invitationId) {
        this.userId = userId;
        this.invitationId = invitationId;
    }

    public UserInvitationKey() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getInvitationId() {
        return this.invitationId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserInvitationKey)) return false;
        final UserInvitationKey other = (UserInvitationKey) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$invitationId = this.getInvitationId();
        final Object other$invitationId = other.getInvitationId();
        if (this$invitationId == null ? other$invitationId != null : !this$invitationId.equals(other$invitationId))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserInvitationKey;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $invitationId = this.getInvitationId();
        result = result * PRIME + ($invitationId == null ? 43 : $invitationId.hashCode());
        return result;
    }

    public String toString() {
        return "UserInvitationKey(userId=" + this.getUserId() + ", invitationId=" + this.getInvitationId() + ")";
    }
}
