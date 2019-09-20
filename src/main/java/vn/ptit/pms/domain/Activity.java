package vn.ptit.pms.domain;

import vn.ptit.pms.domain.audit.AuditEvent;
import vn.ptit.pms.domain.enumeration.ActivityType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "activity")
public class Activity extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Activity(Long id, @NotBlank String content, ActivityType type, Task task) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.task = task;
    }

    public Activity() {
    }

    public Long getId() {
        return this.id;
    }

    public @NotBlank String getContent() {
        return this.content;
    }

    public ActivityType getType() {
        return this.type;
    }

    public Task getTask() {
        return this.task;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(@NotBlank String content) {
        this.content = content;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Activity)) return false;
        final Activity other = (Activity) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$content = this.getContent();
        final Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$task = this.getTask();
        final Object other$task = other.getTask();
        if (this$task == null ? other$task != null : !this$task.equals(other$task)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Activity;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $task = this.getTask();
        result = result * PRIME + ($task == null ? 43 : $task.hashCode());
        return result;
    }

    public String toString() {
        return "Activity(id=" + this.getId() + ", content=" + this.getContent() + ", type=" + this.getType() + ", task=" + this.getTask() + ")";
    }
}
