package vn.ptit.qldaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.qldaserver.domain.UserTask;
import vn.ptit.qldaserver.domain.key.UserTaskKey;

import java.util.List;

public interface UserTaskRepository extends JpaRepository<UserTask, UserTaskKey> {
    List<UserTask> findByIdUserId(Long userId);

    List<UserTask> findByIdTaskId(Long taskId);
}
