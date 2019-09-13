package vn.ptit.qldaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.qldaserver.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
