package vn.ptit.pms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vn.ptit.pms.domain.audit.AuditEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "invitation")
public class Invitation extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Invitation(String content, Long projectId) {
        this.content = content;
        this.project = new Project(projectId);
    }

    public Invitation(Long id, @NotBlank String content, Project project) {
        this.id = id;
        this.content = content;
        this.project = project;
    }

    public Invitation() {
    }

    public Long getId() {
        return this.id;
    }

    public @NotBlank String getContent() {
        return this.content;
    }

    public Project getProject() {
        return this.project;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(@NotBlank String content) {
        this.content = content;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Invitation)) return false;
        final Invitation other = (Invitation) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$content = this.getContent();
        final Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
        final Object this$project = this.getProject();
        final Object other$project = other.getProject();
        if (this$project == null ? other$project != null : !this$project.equals(other$project)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Invitation;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        final Object $project = this.getProject();
        result = result * PRIME + ($project == null ? 43 : $project.hashCode());
        return result;
    }

    public String toString() {
        return "Invitation(id=" + this.getId() + ", content=" + this.getContent() + ", project=" + this.getProject() + ")";
    }
}
