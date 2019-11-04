package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.ptit.pms.domain.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByProjectId(Long projectId);
    List<Category> findByProjectIdAndArchivedOrderByPosAsc(Long projectId, boolean archived);

    @Query(value = "SELECT MAX(pos) FROM category WHERE project_id = ?1", nativeQuery = true)
    Integer getLastCategoryPos(Long projectId);

    @Query(value = "SELECT p.id FROM project p JOIN category c on p.id = c.project_id WHERE c.id = ?1", nativeQuery = true)
    Long findProjectIdByCategoryId(Long categoryId);
}
