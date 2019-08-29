package vn.ptit.qldaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.qldaserver.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
