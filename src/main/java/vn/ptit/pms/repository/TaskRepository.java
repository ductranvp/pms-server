package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.ptit.pms.domain.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCategoryId(Long categoryId);
    List<Task> findByCategoryIdOrderByPosAsc(Long categoryId);
    List<Task> findByCategoryIdOrderByPosDesc(Long categoryId);

    @Query(value = "SELECT MAX(pos) FROM task WHERE category_id = ?1 AND status = 'NO_PROGRESS'", nativeQuery = true)
    Integer getLastTaskPos(Long categoryId);
}
