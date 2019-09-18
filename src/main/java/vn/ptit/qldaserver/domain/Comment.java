package vn.ptit.qldaserver.domain;

import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;
import vn.ptit.qldaserver.domain.audit.AuditEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
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
}
