package vn.ptit.pms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;
import vn.ptit.pms.domain.audit.AuditEvent;
import vn.ptit.pms.domain.enumeration.TaskPriority;
import vn.ptit.pms.domain.enumeration.TaskStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
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
    private TaskPriority priority = TaskPriority.NONE;

    private long progress;

    private Instant estimateStartDate;
    private Instant estimateEndDate;

    private Instant startDate;
    private Instant endDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.NO_PROGRESS;

    private boolean overdue = false;

    @JsonIgnore
    private boolean reminded = false;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @JsonIgnore
    private boolean deleted = false;

    public Task(Long taskId) {
        this.id = taskId;
    }
}
