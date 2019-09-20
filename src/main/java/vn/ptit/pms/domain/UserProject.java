package vn.ptit.pms.domain;

import vn.ptit.pms.domain.enumeration.ProjectRole;
import vn.ptit.pms.domain.key.UserProjectKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_project")
public class UserProject implements Serializable {
    @EmbeddedId
    private UserProjectKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("project_id")
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private ProjectRole role = ProjectRole.ROLE_MEMBER;

    public UserProject(UserProjectKey userProjectKey) {
        this.id = userProjectKey;
    }

    public UserProject(UserProjectKey id, User user, Project project, ProjectRole role) {
        this.id = id;
        this.user = user;
        this.project = project;
        this.role = role;
    }

    public UserProject() {
    }

    public UserProjectKey getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public Project getProject() {
        return this.project;
    }

    public ProjectRole getRole() {
        return this.role;
    }

    public void setId(UserProjectKey id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setRole(ProjectRole role) {
        this.role = role;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserProject)) return false;
        final UserProject other = (UserProject) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$project = this.getProject();
        final Object other$project = other.getProject();
        if (this$project == null ? other$project != null : !this$project.equals(other$project)) return false;
        final Object this$role = this.getRole();
        final Object other$role = other.getRole();
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserProject;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $project = this.getProject();
        result = result * PRIME + ($project == null ? 43 : $project.hashCode());
        final Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        return result;
    }

    public String toString() {
        return "UserProject(id=" + this.getId() + ", user=" + this.getUser() + ", project=" + this.getProject() + ", role=" + this.getRole() + ")";
    }
}
