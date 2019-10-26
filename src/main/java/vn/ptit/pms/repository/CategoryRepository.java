package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.ptit.pms.domain.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByProjectId(Long projectId);
    List<Category> findByProjectIdOrderByPosAsc(Long projectId);

    @Query(value = "SELECT MAX(pos) FROM category WHERE project_id = ?1", nativeQuery = true)
    Integer getLastCategoryPos(Long projectId);
}
