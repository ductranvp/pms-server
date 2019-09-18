package vn.ptit.qldaserver.domain;

import lombok.*;
import org.hibernate.annotations.Where;
import vn.ptit.qldaserver.domain.audit.AuditEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@Where(clause = "deleted=false")
public class Category extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private int pos;

    private boolean archived = false;

    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Category(Long categoryId) {
        this.id = categoryId;
    }
}
