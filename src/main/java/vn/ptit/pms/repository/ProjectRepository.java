package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.pms.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
