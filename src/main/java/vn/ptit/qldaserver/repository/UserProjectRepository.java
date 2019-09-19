package vn.ptit.qldaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.qldaserver.domain.UserProject;
import vn.ptit.qldaserver.domain.key.UserProjectKey;

import java.util.List;

public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectKey> {
    List<UserProject> findByIdUserId(Long userId);

    List<UserProject> findByIdProjectId(Long projectId);
}
