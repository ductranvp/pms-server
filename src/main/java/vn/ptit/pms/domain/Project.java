package vn.ptit.pms.domain;

import org.hibernate.annotations.Where;
import vn.ptit.pms.domain.audit.AuditEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "project")
@Where(clause = "deleted=false")
public class Project extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;

    private String description;

    private boolean closed = false;

    private boolean deleted = false;

    public Project(Long projectId) {
        this.id = projectId;
    }

    public Project(Long id, @NotBlank String name, String description, boolean closed, boolean deleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.closed = closed;
        this.deleted = deleted;
    }

    public Project() {
    }

    public Long getId() {
        return this.id;
    }

    public @NotBlank String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Project)) return false;
        final Project other = (Project) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        if (this.isClosed() != other.isClosed()) return false;
        if (this.isDeleted() != other.isDeleted()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Project;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        result = result * PRIME + (this.isClosed() ? 79 : 97);
        result = result * PRIME + (this.isDeleted() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "Project(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", closed=" + this.isClosed() + ", deleted=" + this.isDeleted() + ")";
    }
}
