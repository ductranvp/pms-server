package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.UserNotification;
import vn.ptit.pms.domain.enumeration.NotificationStatus;
import vn.ptit.pms.domain.key.UserNotificationKey;
import vn.ptit.pms.repository.UserNotificationRepository;
import vn.ptit.pms.service.dto.UserDto;
import vn.ptit.pms.service.dto.UserNotificationDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserNotificationService {
    @Autowired
    UserNotificationRepository userNotificationRepository;

    @Autowired
    UserService userService;

    public UserNotification save(UserNotification userNotification) {
        return userNotificationRepository.save(userNotification);
    }

    public List<UserNotification> getAll() {
        return userNotificationRepository.findAll();
    }

    public void delete(Long id) {

    }

    public List<UserNotificationDto> getByUserId(Long userId) {
        List<UserNotification> list = userNotificationRepository.findByIdUserIdOrderByNotificationIdDesc(userId);
        List<UserNotificationDto> result = new ArrayList<>();
        for (UserNotification noti: list) {
            UserNotificationDto dto = new UserNotificationDto(noti);
            dto.setCreatedBy(new UserDto(userService.getUserById(noti.getNotification().getCreatedBy())));
            result.add(dto);
        }
        return result;
    }

    public List<UserNotificationDto> getUnseenNotification(Long userId) {
        List<UserNotification> list = userNotificationRepository.findByIdUserIdOrderByNotificationIdDesc(userId);
        List<UserNotificationDto> result = new ArrayList<>();
        for (UserNotification noti: list) {
            if (noti.getStatus().equals(NotificationStatus.UNSEEN)){
                UserNotificationDto dto = new UserNotificationDto(noti);
                dto.setCreatedBy(new UserDto(userService.getUserById(noti.getNotification().getCreatedBy())));
                result.add(dto);
            }
        }
        return result;
    }

    public void userSeenNotification(Long userId, Long notificationId) {
        UserNotification notification = userNotificationRepository.findById(new UserNotificationKey(userId, notificationId)).get();
        notification.setStatus(NotificationStatus.SEEN);
        userNotificationRepository.save(notification);
    }
}
