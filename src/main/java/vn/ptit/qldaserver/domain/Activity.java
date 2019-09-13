package vn.ptit.qldaserver.domain;

import lombok.*;
import vn.ptit.qldaserver.domain.audit.AuditEvent;
import vn.ptit.qldaserver.domain.enumeration.ActivityType;

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
}
