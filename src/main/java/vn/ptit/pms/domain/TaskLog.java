package vn.ptit.pms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.pms.domain.audit.AuditEvent;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "task_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskLog extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long taskId;
    private Long userId;

    public TaskLog(Long taskId, Long userId) {
        this.taskId = taskId;
        this.userId = userId;
    }
}
