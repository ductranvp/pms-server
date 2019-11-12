package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.ptit.pms.domain.UserProject;
import vn.ptit.pms.domain.enumeration.ProjectRole;
import vn.ptit.pms.domain.key.UserProjectKey;

import java.util.List;

public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectKey> {

    @Query(value = "SELECT * FROM user_project up " +
            "JOIN project p ON up.project_id = p.id " +
            "WHERE p.deleted = FALSE AND up.user_id = ?1", nativeQuery = true)
    List<UserProject> findByIdUserId(Long userId);

    List<UserProject> findByIdProjectId(Long projectId);

    List<UserProject> findByProjectIdAndRole(Long projectId, ProjectRole role);

    UserProject findByProjectIdAndUserIdAndRole(Long projectId, Long userId, ProjectRole role);
}
