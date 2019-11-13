package vn.ptit.pms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.pms.domain.audit.AuditEvent;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "task_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskLog extends AuditEvent implements Serializable {
    private Long id;
    private Long taskId;
    private Long userId;
}
