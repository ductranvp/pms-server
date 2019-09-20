package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.pms.domain.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
