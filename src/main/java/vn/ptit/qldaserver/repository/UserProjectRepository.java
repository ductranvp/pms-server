package vn.ptit.qldaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.qldaserver.domain.UserProject;
import vn.ptit.qldaserver.domain.key.UserProjectKey;

public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectKey> {
}
