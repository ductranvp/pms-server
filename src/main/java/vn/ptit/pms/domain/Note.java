package vn.ptit.pms.domain;

import lombok.*;
import vn.ptit.pms.domain.audit.AuditEvent;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "note")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class Note extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private int pos;

    private String color;

}
