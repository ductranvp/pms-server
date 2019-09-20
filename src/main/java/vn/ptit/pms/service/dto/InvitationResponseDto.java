package vn.ptit.pms.service.dto;

public class InvitationResponseDto {
    private Long invitationId;
    private boolean accept;

    public InvitationResponseDto() {
    }

    public Long getInvitationId() {
        return this.invitationId;
    }

    public boolean isAccept() {
        return this.accept;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof InvitationResponseDto)) return false;
        final InvitationResponseDto other = (InvitationResponseDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$invitationId = this.getInvitationId();
        final Object other$invitationId = other.getInvitationId();
        if (this$invitationId == null ? other$invitationId != null : !this$invitationId.equals(other$invitationId))
            return false;
        if (this.isAccept() != other.isAccept()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof InvitationResponseDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $invitationId = this.getInvitationId();
        result = result * PRIME + ($invitationId == null ? 43 : $invitationId.hashCode());
        result = result * PRIME + (this.isAccept() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "InvitationResponseDto(invitationId=" + this.getInvitationId() + ", accept=" + this.isAccept() + ")";
    }
}
