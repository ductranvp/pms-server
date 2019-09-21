package vn.ptit.pms.domain;

import lombok.*;
import vn.ptit.pms.domain.key.UserTaskKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserTask implements Serializable {
    public static final long serialVersionUID = 1L;
    @EmbeddedId
    private UserTaskKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("task_id")
    @JoinColumn(name = "task_id")
    private Task task;

    public UserTask(UserTaskKey userTaskKey) {
        this.id = userTaskKey;
    }
}
