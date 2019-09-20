package vn.ptit.pms.domain;

import vn.ptit.pms.domain.key.UserTaskKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_task")
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

    public UserTask(UserTaskKey id, User user, Task task) {
        this.id = id;
        this.user = user;
        this.task = task;
    }

    public UserTask() {
    }

    public UserTaskKey getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public Task getTask() {
        return this.task;
    }

    public void setId(UserTaskKey id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserTask)) return false;
        final UserTask other = (UserTask) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$task = this.getTask();
        final Object other$task = other.getTask();
        if (this$task == null ? other$task != null : !this$task.equals(other$task)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserTask;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $task = this.getTask();
        result = result * PRIME + ($task == null ? 43 : $task.hashCode());
        return result;
    }

    public String toString() {
        return "UserTask(id=" + this.getId() + ", user=" + this.getUser() + ", task=" + this.getTask() + ")";
    }
}
