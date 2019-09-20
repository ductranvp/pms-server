package vn.ptit.pms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;
import vn.ptit.pms.domain.audit.AuditEvent;
import vn.ptit.pms.domain.enumeration.TaskPriority;
import vn.ptit.pms.domain.enumeration.TaskStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "task")
@Where(clause = "deleted=false")
public class Task extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    private int pos;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private Instant estimateStartDate;
    private Instant estimateEndDate;

    private Instant startDate;
    private Instant endDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.DRAFT;

    private boolean overdue = false;

    @JsonIgnore
    private boolean deleted = false;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Task parent;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Task(Long id, @NotBlank String name, String description, int pos, TaskPriority priority, Instant estimateStartDate, Instant estimateEndDate, Instant startDate, Instant endDate, TaskStatus status, boolean overdue, boolean deleted, Task parent, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pos = pos;
        this.priority = priority;
        this.estimateStartDate = estimateStartDate;
        this.estimateEndDate = estimateEndDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.overdue = overdue;
        this.deleted = deleted;
        this.parent = parent;
        this.category = category;
    }

    public Task() {
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

    public int getPos() {
        return this.pos;
    }

    public TaskPriority getPriority() {
        return this.priority;
    }

    public Instant getEstimateStartDate() {
        return this.estimateStartDate;
    }

    public Instant getEstimateEndDate() {
        return this.estimateEndDate;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    public boolean isOverdue() {
        return this.overdue;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    @Nullable
    public Task getParent() {
        return this.parent;
    }

    public Category getCategory() {
        return this.category;
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

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public void setEstimateStartDate(Instant estimateStartDate) {
        this.estimateStartDate = estimateStartDate;
    }

    public void setEstimateEndDate(Instant estimateEndDate) {
        this.estimateEndDate = estimateEndDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setParent(@Nullable Task parent) {
        this.parent = parent;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Task)) return false;
        final Task other = (Task) o;
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
        if (this.getPos() != other.getPos()) return false;
        final Object this$priority = this.getPriority();
        final Object other$priority = other.getPriority();
        if (this$priority == null ? other$priority != null : !this$priority.equals(other$priority)) return false;
        final Object this$estimateStartDate = this.getEstimateStartDate();
        final Object other$estimateStartDate = other.getEstimateStartDate();
        if (this$estimateStartDate == null ? other$estimateStartDate != null : !this$estimateStartDate.equals(other$estimateStartDate))
            return false;
        final Object this$estimateEndDate = this.getEstimateEndDate();
        final Object other$estimateEndDate = other.getEstimateEndDate();
        if (this$estimateEndDate == null ? other$estimateEndDate != null : !this$estimateEndDate.equals(other$estimateEndDate))
            return false;
        final Object this$startDate = this.getStartDate();
        final Object other$startDate = other.getStartDate();
        if (this$startDate == null ? other$startDate != null : !this$startDate.equals(other$startDate)) return false;
        final Object this$endDate = this.getEndDate();
        final Object other$endDate = other.getEndDate();
        if (this$endDate == null ? other$endDate != null : !this$endDate.equals(other$endDate)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        if (this.isOverdue() != other.isOverdue()) return false;
        if (this.isDeleted() != other.isDeleted()) return false;
        final Object this$parent = this.getParent();
        final Object other$parent = other.getParent();
        if (this$parent == null ? other$parent != null : !this$parent.equals(other$parent)) return false;
        final Object this$category = this.getCategory();
        final Object other$category = other.getCategory();
        if (this$category == null ? other$category != null : !this$category.equals(other$category)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Task;
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
        result = result * PRIME + this.getPos();
        final Object $priority = this.getPriority();
        result = result * PRIME + ($priority == null ? 43 : $priority.hashCode());
        final Object $estimateStartDate = this.getEstimateStartDate();
        result = result * PRIME + ($estimateStartDate == null ? 43 : $estimateStartDate.hashCode());
        final Object $estimateEndDate = this.getEstimateEndDate();
        result = result * PRIME + ($estimateEndDate == null ? 43 : $estimateEndDate.hashCode());
        final Object $startDate = this.getStartDate();
        result = result * PRIME + ($startDate == null ? 43 : $startDate.hashCode());
        final Object $endDate = this.getEndDate();
        result = result * PRIME + ($endDate == null ? 43 : $endDate.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        result = result * PRIME + (this.isOverdue() ? 79 : 97);
        result = result * PRIME + (this.isDeleted() ? 79 : 97);
        final Object $parent = this.getParent();
        result = result * PRIME + ($parent == null ? 43 : $parent.hashCode());
        final Object $category = this.getCategory();
        result = result * PRIME + ($category == null ? 43 : $category.hashCode());
        return result;
    }

    public String toString() {
        return "Task(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", pos=" + this.getPos() + ", priority=" + this.getPriority() + ", estimateStartDate=" + this.getEstimateStartDate() + ", estimateEndDate=" + this.getEstimateEndDate() + ", startDate=" + this.getStartDate() + ", endDate=" + this.getEndDate() + ", status=" + this.getStatus() + ", overdue=" + this.isOverdue() + ", deleted=" + this.isDeleted() + ", parent=" + this.getParent() + ", category=" + this.getCategory() + ")";
    }
}
