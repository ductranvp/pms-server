package vn.ptit.pms.domain;

import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;
import vn.ptit.pms.domain.audit.AuditEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "attachment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@Where(clause = "deleted=false")
public class Attachment extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotBlank
    private String url;

    private String type;

    private boolean deleted = false;

    @Column(name = "task_id", nullable = true)
    private Long taskId;
}
