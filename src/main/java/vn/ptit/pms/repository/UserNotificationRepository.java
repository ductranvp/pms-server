package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.pms.domain.UserNotification;
import vn.ptit.pms.domain.key.UserNotificationKey;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotification, UserNotificationKey> {
    List<UserNotification> findByIdUserIdOrderByNotificationIdDesc(Long userId);

    List<UserNotification> findByIdNotificationId(Long notificationId);
}
