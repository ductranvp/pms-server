package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.pms.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
