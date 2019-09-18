package vn.ptit.qldaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.qldaserver.domain.UserNotification;
import vn.ptit.qldaserver.domain.key.UserNotificationKey;

public interface UserNotificationRepository extends JpaRepository<UserNotification, UserNotificationKey> {
}
