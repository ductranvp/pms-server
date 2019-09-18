package vn.ptit.qldaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.qldaserver.domain.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
