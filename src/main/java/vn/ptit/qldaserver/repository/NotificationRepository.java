package vn.ptit.qldaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.qldaserver.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
