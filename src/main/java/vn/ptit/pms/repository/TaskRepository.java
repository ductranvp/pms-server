package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.ptit.pms.domain.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCategoryId(Long categoryId);

    List<Task> findByOverdueAndEstimateEndDateIsNotNull(boolean overdue);

    List<Task> findByRemindedAndOverdueAndEstimateEndDateIsNotNull(boolean reminded, boolean overdue);

    List<Task> findByCategoryIdOrderByPosAsc(Long categoryId);

    List<Task> findByCategoryIdOrderByPosDesc(Long categoryId);

    @Query(value = "SELECT * FROM task", nativeQuery = true)
    List<Task> findByFilter(Long categoryId, String priority, String userIds, boolean overdue);

    @Query(value = "SELECT MAX(pos) FROM task WHERE category_id = ?1 AND status = 'NO_PROGRESS'", nativeQuery = true)
    Integer getLastTaskPos(Long categoryId);

    @Modifying
    @Query(value = "UPDATE task SET overdue = ?2  WHERE id = ?1", nativeQuery = true)
    void setOverdue(Long taskId, boolean overdue);

    @Modifying
    @Query(value = "UPDATE task SET reminded = ?2  WHERE id = ?1", nativeQuery = true)
    void setReminded(Long taskId, boolean reminded);

    @Modifying
    @Query(value = "UPDATE task SET pos = ?2  WHERE id = ?1", nativeQuery = true)
    void updatePos(Long taskId, int pos);

    @Query(value = "SELECT p.id FROM project p " +
            "JOIN category c ON p.id = c.project_id " +
            "JOIN task t on c.id = t.category_id " +
            "WHERE t.id = ?1", nativeQuery = true)
    Long findProjectIdByTaskId(Long taskId);
}
