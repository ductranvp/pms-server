package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.ptit.pms.domain.Project;
import vn.ptit.pms.domain.ProjectLog;

import java.util.List;

public interface ProjectLogRepository extends JpaRepository<ProjectLog, Long> {
    @Query(value = "SELECT DISTINCT p.* FROM project_log pl " +
            "JOIN project p ON p.id = pl.project_id " +
            "WHERE pl.user_id = ?1 " +
            "ORDER BY pl.created_date DESC LIMIT 6", nativeQuery = true)
    List<Project> findRecentProject(Long userId);
}
