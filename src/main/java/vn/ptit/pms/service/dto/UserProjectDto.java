package vn.ptit.pms.service.dto;

import vn.ptit.pms.domain.User;
import vn.ptit.pms.domain.enumeration.ProjectRole;

public class UserProjectDto {
    private User user;
    private ProjectRole role;

    public UserProjectDto(User user, ProjectRole role) {
        this.user = user;
        this.role = role;
    }

    public UserProjectDto() {
    }

    public User getUser() {
        return this.user;
    }

    public ProjectRole getRole() {
        return this.role;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRole(ProjectRole role) {
        this.role = role;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserProjectDto)) return false;
        final UserProjectDto other = (UserProjectDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$role = this.getRole();
        final Object other$role = other.getRole();
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserProjectDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        return result;
    }

    public String toString() {
        return "UserProjectDto(user=" + this.getUser() + ", role=" + this.getRole() + ")";
    }
}
