package vn.ptit.qldaserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;
import vn.ptit.qldaserver.domain.audit.AuditEvent;
import vn.ptit.qldaserver.domain.enumeration.TaskPriority;
import vn.ptit.qldaserver.domain.enumeration.TaskStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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
    private TaskPriority priority;

    private Instant estimateStartDate;
    private Instant estimateEndDate;

    private Instant startDate;
    private Instant endDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private boolean overdue = false;

    @JsonIgnore
    private boolean deleted = false;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Task parent;

    @OneToMany(mappedBy = "parent")
    private Set<Task> children = new HashSet<>();

    @Nullable
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(name = "user_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    public Task(Long parentId) {
        this.id = parentId;
    }
}
