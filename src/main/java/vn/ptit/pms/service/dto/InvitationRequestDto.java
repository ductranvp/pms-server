package vn.ptit.pms.service.dto;

import vn.ptit.pms.domain.Invitation;
import vn.ptit.pms.domain.Project;

import java.util.Set;

public class InvitationRequestDto {
    private Long projectId;
    private String content;
    private Set<String> listEmail;

    public InvitationRequestDto() {
    }

    public Invitation toBean(){
        Invitation invitation = new Invitation();
        invitation.setContent(content);
        invitation.setProject(new Project(projectId));
        return invitation;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public String getContent() {
        return this.content;
    }

    public Set<String> getListEmail() {
        return this.listEmail;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setListEmail(Set<String> listEmail) {
        this.listEmail = listEmail;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof InvitationRequestDto)) return false;
        final InvitationRequestDto other = (InvitationRequestDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$projectId = this.getProjectId();
        final Object other$projectId = other.getProjectId();
        if (this$projectId == null ? other$projectId != null : !this$projectId.equals(other$projectId)) return false;
        final Object this$content = this.getContent();
        final Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
        final Object this$listEmail = this.getListEmail();
        final Object other$listEmail = other.getListEmail();
        if (this$listEmail == null ? other$listEmail != null : !this$listEmail.equals(other$listEmail)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof InvitationRequestDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $projectId = this.getProjectId();
        result = result * PRIME + ($projectId == null ? 43 : $projectId.hashCode());
        final Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        final Object $listEmail = this.getListEmail();
        result = result * PRIME + ($listEmail == null ? 43 : $listEmail.hashCode());
        return result;
    }

    public String toString() {
        return "InvitationRequestDto(projectId=" + this.getProjectId() + ", content=" + this.getContent() + ", listEmail=" + this.getListEmail() + ")";
    }
}
