package vn.ptit.pms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
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
    private TaskPriority priority = TaskPriority.LOW;

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
}
