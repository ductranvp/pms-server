package vn.ptit.pms.service.dto;

import vn.ptit.pms.domain.Project;

import java.util.List;

public class TotalUserProjectDto {
    private List<Project> all;
    private List<Project> managing;
    private List<Project> member;
    private List<Project> closed;

    public TotalUserProjectDto(List<Project> all, List<Project> managing, List<Project> member, List<Project> closed) {
        this.all = all;
        this.managing = managing;
        this.member = member;
        this.closed = closed;
    }

    public TotalUserProjectDto() {
    }

    public List<Project> getAll() {
        return this.all;
    }

    public List<Project> getManaging() {
        return this.managing;
    }

    public List<Project> getMember() {
        return this.member;
    }

    public List<Project> getClosed() {
        return this.closed;
    }

    public void setAll(List<Project> all) {
        this.all = all;
    }

    public void setManaging(List<Project> managing) {
        this.managing = managing;
    }

    public void setMember(List<Project> member) {
        this.member = member;
    }

    public void setClosed(List<Project> closed) {
        this.closed = closed;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TotalUserProjectDto)) return false;
        final TotalUserProjectDto other = (TotalUserProjectDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$all = this.getAll();
        final Object other$all = other.getAll();
        if (this$all == null ? other$all != null : !this$all.equals(other$all)) return false;
        final Object this$managing = this.getManaging();
        final Object other$managing = other.getManaging();
        if (this$managing == null ? other$managing != null : !this$managing.equals(other$managing)) return false;
        final Object this$member = this.getMember();
        final Object other$member = other.getMember();
        if (this$member == null ? other$member != null : !this$member.equals(other$member)) return false;
        final Object this$closed = this.getClosed();
        final Object other$closed = other.getClosed();
        if (this$closed == null ? other$closed != null : !this$closed.equals(other$closed)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TotalUserProjectDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $all = this.getAll();
        result = result * PRIME + ($all == null ? 43 : $all.hashCode());
        final Object $managing = this.getManaging();
        result = result * PRIME + ($managing == null ? 43 : $managing.hashCode());
        final Object $member = this.getMember();
        result = result * PRIME + ($member == null ? 43 : $member.hashCode());
        final Object $closed = this.getClosed();
        result = result * PRIME + ($closed == null ? 43 : $closed.hashCode());
        return result;
    }

    public String toString() {
        return "TotalUserProjectDto(all=" + this.getAll() + ", managing=" + this.getManaging() + ", member=" + this.getMember() + ", closed=" + this.getClosed() + ")";
    }
}
