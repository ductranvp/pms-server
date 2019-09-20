package vn.ptit.pms.service.dto;

import vn.ptit.pms.domain.Invitation;
import vn.ptit.pms.domain.Project;

public class UserInvitationDto {
    private String inviter;
    private Project project;
    private Invitation invitation;

    public UserInvitationDto(String inviter, Project project, Invitation invitation) {
        this.inviter = inviter;
        this.project = project;
        this.invitation = invitation;
    }

    public UserInvitationDto() {
    }

    public String getInviter() {
        return this.inviter;
    }

    public Project getProject() {
        return this.project;
    }

    public Invitation getInvitation() {
        return this.invitation;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserInvitationDto)) return false;
        final UserInvitationDto other = (UserInvitationDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$inviter = this.getInviter();
        final Object other$inviter = other.getInviter();
        if (this$inviter == null ? other$inviter != null : !this$inviter.equals(other$inviter)) return false;
        final Object this$project = this.getProject();
        final Object other$project = other.getProject();
        if (this$project == null ? other$project != null : !this$project.equals(other$project)) return false;
        final Object this$invitation = this.getInvitation();
        final Object other$invitation = other.getInvitation();
        if (this$invitation == null ? other$invitation != null : !this$invitation.equals(other$invitation))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserInvitationDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $inviter = this.getInviter();
        result = result * PRIME + ($inviter == null ? 43 : $inviter.hashCode());
        final Object $project = this.getProject();
        result = result * PRIME + ($project == null ? 43 : $project.hashCode());
        final Object $invitation = this.getInvitation();
        result = result * PRIME + ($invitation == null ? 43 : $invitation.hashCode());
        return result;
    }

    public String toString() {
        return "UserInvitationDto(inviter=" + this.getInviter() + ", project=" + this.getProject() + ", invitation=" + this.getInvitation() + ")";
    }
}
