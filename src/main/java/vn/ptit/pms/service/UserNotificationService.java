package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.UserNotification;
import vn.ptit.pms.repository.UserNotificationRepository;

import java.util.List;

@Service
public class UserNotificationService {
    @Autowired
    UserNotificationRepository userNotificationRepository;

    public UserNotification save(UserNotification userNotification) {
        return userNotificationRepository.save(userNotification);
    }

    public List<UserNotification> getAll() {
        return userNotificationRepository.findAll();
    }

    public void delete(Long id) {

    }

    public List<UserNotification> getByUserId(Long userId) {
        return userNotificationRepository.findByIdUserId(userId);
    }
}
