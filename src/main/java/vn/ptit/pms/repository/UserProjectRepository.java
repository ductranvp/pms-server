package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.pms.domain.UserProject;
import vn.ptit.pms.domain.enumeration.ProjectRole;
import vn.ptit.pms.domain.key.UserProjectKey;

import java.util.List;

public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectKey> {
    List<UserProject> findByIdUserId(Long userId);

    List<UserProject> findByIdProjectId(Long projectId);

    List<UserProject> findByProjectIdAndRole(Long projectId, ProjectRole role);

    UserProject findByProjectIdAndUserIdAndRole(Long projectId, Long userId, ProjectRole role);
}
