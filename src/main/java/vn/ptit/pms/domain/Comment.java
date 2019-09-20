package vn.ptit.pms.domain;

import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;
import vn.ptit.pms.domain.audit.AuditEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "comment")
@Where(clause = "deleted=false")
public class Comment extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    private boolean deleted = false;

    @Nullable
    @OneToOne
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Comment(Long id, @NotBlank String content, boolean deleted, Attachment attachment, Task task) {
        this.id = id;
        this.content = content;
        this.deleted = deleted;
        this.attachment = attachment;
        this.task = task;
    }

    public Comment() {
    }

    public Long getId() {
        return this.id;
    }

    public @NotBlank String getContent() {
        return this.content;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    @Nullable
    public Attachment getAttachment() {
        return this.attachment;
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

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setAttachment(@Nullable Attachment attachment) {
        this.attachment = attachment;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Comment)) return false;
        final Comment other = (Comment) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$content = this.getContent();
        final Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
        if (this.isDeleted() != other.isDeleted()) return false;
        final Object this$attachment = this.getAttachment();
        final Object other$attachment = other.getAttachment();
        if (this$attachment == null ? other$attachment != null : !this$attachment.equals(other$attachment))
            return false;
        final Object this$task = this.getTask();
        final Object other$task = other.getTask();
        if (this$task == null ? other$task != null : !this$task.equals(other$task)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Comment;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        result = result * PRIME + (this.isDeleted() ? 79 : 97);
        final Object $attachment = this.getAttachment();
        result = result * PRIME + ($attachment == null ? 43 : $attachment.hashCode());
        final Object $task = this.getTask();
        result = result * PRIME + ($task == null ? 43 : $task.hashCode());
        return result;
    }

    public String toString() {
        return "Comment(id=" + this.getId() + ", content=" + this.getContent() + ", deleted=" + this.isDeleted() + ", attachment=" + this.getAttachment() + ", task=" + this.getTask() + ")";
    }
}
