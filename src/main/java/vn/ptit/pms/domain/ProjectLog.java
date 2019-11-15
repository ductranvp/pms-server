package vn.ptit.pms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.pms.domain.audit.AuditEvent;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "project_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectLog extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long projectId;
    private Long userId;

    public ProjectLog(Long projectId, Long userId) {
        this.projectId = projectId;
        this.userId = userId;
    }
}
