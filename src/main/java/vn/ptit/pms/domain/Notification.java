package vn.ptit.pms.domain;

import vn.ptit.pms.domain.audit.AuditEvent;
import vn.ptit.pms.domain.enumeration.NotificationType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "notification")
public class Notification extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    public Notification(Long id, @NotBlank String content, NotificationType type) {
        this.id = id;
        this.content = content;
        this.type = type;
    }

    public Notification() {
    }

    public Long getId() {
        return this.id;
    }

    public @NotBlank String getContent() {
        return this.content;
    }

    public NotificationType getType() {
        return this.type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(@NotBlank String content) {
        this.content = content;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Notification)) return false;
        final Notification other = (Notification) o;
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
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Notification;
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
        return result;
    }

    public String toString() {
        return "Notification(id=" + this.getId() + ", content=" + this.getContent() + ", type=" + this.getType() + ")";
    }
}
