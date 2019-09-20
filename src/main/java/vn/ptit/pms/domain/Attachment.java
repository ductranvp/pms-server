package vn.ptit.pms.domain;

import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;
import vn.ptit.pms.domain.audit.AuditEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "attachment")
@Where(clause = "deleted=false")
public class Attachment extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String url;

    private String type;

    private boolean deleted = false;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Attachment(Long id, @NotBlank String url, String type, boolean deleted, Task task) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.deleted = deleted;
        this.task = task;
    }

    public Attachment() {
    }

    public Long getId() {
        return this.id;
    }

    public @NotBlank String getUrl() {
        return this.url;
    }

    public String getType() {
        return this.type;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    @Nullable
    public Task getTask() {
        return this.task;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(@NotBlank String url) {
        this.url = url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setTask(@Nullable Task task) {
        this.task = task;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Attachment)) return false;
        final Attachment other = (Attachment) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$url = this.getUrl();
        final Object other$url = other.getUrl();
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        if (this.isDeleted() != other.isDeleted()) return false;
        final Object this$task = this.getTask();
        final Object other$task = other.getTask();
        if (this$task == null ? other$task != null : !this$task.equals(other$task)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Attachment;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $url = this.getUrl();
        result = result * PRIME + ($url == null ? 43 : $url.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        result = result * PRIME + (this.isDeleted() ? 79 : 97);
        final Object $task = this.getTask();
        result = result * PRIME + ($task == null ? 43 : $task.hashCode());
        return result;
    }

    public String toString() {
        return "Attachment(id=" + this.getId() + ", url=" + this.getUrl() + ", type=" + this.getType() + ", deleted=" + this.isDeleted() + ", task=" + this.getTask() + ")";
    }
}
