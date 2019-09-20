package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.pms.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
