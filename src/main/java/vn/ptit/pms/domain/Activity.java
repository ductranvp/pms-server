package vn.ptit.pms.domain;

import lombok.*;
import vn.ptit.pms.domain.audit.AuditEvent;
import vn.ptit.pms.domain.enumeration.ActivityType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "activity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class Activity extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "target")
    private String target;
}
