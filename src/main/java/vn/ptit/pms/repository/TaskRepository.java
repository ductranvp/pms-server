package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.ptit.pms.domain.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCategoryId(Long categoryId);

    List<Task> findByOverdueAndEstimateEndDateIsNotNull(boolean overdue);
    List<Task> findByRemindedAndOverdueAndEstimateEndDateIsNotNull(boolean reminded, boolean overdue);

    List<Task> findByCategoryIdOrderByPosAsc(Long categoryId);

    List<Task> findByCategoryIdOrderByPosDesc(Long categoryId);

    @Query(value = "SELECT MAX(pos) FROM task WHERE category_id = ?1 AND status = 'NO_PROGRESS'", nativeQuery = true)
    Integer getLastTaskPos(Long categoryId);

    @Query(value = "UPDATE task SET overdue = ?2  WHERE id = ?1", nativeQuery = true)
    Task setOverdue(Long taskId, boolean overdue);

    @Query(value = "UPDATE task SET reminded = ?2  WHERE id = ?1", nativeQuery = true)
    Task setReminded(Long taskId, boolean reminded);

    @Query(value = "UPDATE task SET pos = ?2  WHERE id = ?1", nativeQuery = true)
    Task updatePos(Long taskId, int pos);
}
