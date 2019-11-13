package vn.ptit.pms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.pms.domain.audit.AuditEvent;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "project_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectLog extends AuditEvent implements Serializable {
    private Long id;
    private Long projectId;
    private Long userId;
    private String type;
}
