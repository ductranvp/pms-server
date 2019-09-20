package vn.ptit.pms.domain;

import org.hibernate.annotations.Where;
import vn.ptit.pms.domain.audit.AuditEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "category")
@Where(clause = "deleted=false")
public class Category extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private int pos;

    private boolean archived = false;

    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Category(Long id, @NotBlank String name, int pos, boolean archived, boolean deleted, Project project) {
        this.id = id;
        this.name = name;
        this.pos = pos;
        this.archived = archived;
        this.deleted = deleted;
        this.project = project;
    }

    public Category() {
    }

    public Long getId() {
        return this.id;
    }

    public @NotBlank String getName() {
        return this.name;
    }

    public int getPos() {
        return this.pos;
    }

    public boolean isArchived() {
        return this.archived;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public Project getProject() {
        return this.project;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Category)) return false;
        final Category other = (Category) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        if (this.getPos() != other.getPos()) return false;
        if (this.isArchived() != other.isArchived()) return false;
        if (this.isDeleted() != other.isDeleted()) return false;
        final Object this$project = this.getProject();
        final Object other$project = other.getProject();
        if (this$project == null ? other$project != null : !this$project.equals(other$project)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Category;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        result = result * PRIME + this.getPos();
        result = result * PRIME + (this.isArchived() ? 79 : 97);
        result = result * PRIME + (this.isDeleted() ? 79 : 97);
        final Object $project = this.getProject();
        result = result * PRIME + ($project == null ? 43 : $project.hashCode());
        return result;
    }

    public String toString() {
        return "Category(id=" + this.getId() + ", name=" + this.getName() + ", pos=" + this.getPos() + ", archived=" + this.isArchived() + ", deleted=" + this.isDeleted() + ", project=" + this.getProject() + ")";
    }
}
