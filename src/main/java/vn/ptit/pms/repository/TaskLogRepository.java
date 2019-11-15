package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.ptit.pms.domain.Project;
import vn.ptit.pms.domain.Task;
import vn.ptit.pms.domain.TaskLog;
import vn.ptit.pms.service.dto.TaskDrawerDto;

import java.util.List;

public interface TaskLogRepository extends JpaRepository<TaskLog, Long> {

    @Query(value = "SELECT DISTINCT t.* FROM task_log tl " +
            "JOIN task t ON t.id = tl.task_id " +
            "WHERE tl.user_id = ?1 " +
            "ORDER BY tl.created_date DESC LIMIT 6", nativeQuery = true)
    List<Task> findRecentTask(Long userId);
}
