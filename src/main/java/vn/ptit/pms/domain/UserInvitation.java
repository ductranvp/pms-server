package vn.ptit.pms.domain;

import vn.ptit.pms.domain.enumeration.InvitationStatus;
import vn.ptit.pms.domain.key.UserInvitationKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_invitation")
public class UserInvitation implements Serializable {
    public static final long serialVersionUID = 1L;
    @EmbeddedId
    private UserInvitationKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("invitation_id")
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status = InvitationStatus.NO_ACTION;

    public UserInvitation(UserInvitationKey userInvitationKey) {
        this.id = userInvitationKey;
    }

    public UserInvitation(UserInvitationKey id, User user, Invitation invitation, InvitationStatus status) {
        this.id = id;
        this.user = user;
        this.invitation = invitation;
        this.status = status;
    }

    public UserInvitation() {
    }

    public UserInvitationKey getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public Invitation getInvitation() {
        return this.invitation;
    }

    public InvitationStatus getStatus() {
        return this.status;
    }

    public void setId(UserInvitationKey id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserInvitation)) return false;
        final UserInvitation other = (UserInvitation) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$invitation = this.getInvitation();
        final Object other$invitation = other.getInvitation();
        if (this$invitation == null ? other$invitation != null : !this$invitation.equals(other$invitation))
            return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserInvitation;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $invitation = this.getInvitation();
        result = result * PRIME + ($invitation == null ? 43 : $invitation.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "UserInvitation(id=" + this.getId() + ", user=" + this.getUser() + ", invitation=" + this.getInvitation() + ", status=" + this.getStatus() + ")";
    }
}
