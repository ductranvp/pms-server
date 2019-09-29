package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Notification;
import vn.ptit.pms.repository.NotificationRepository;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification getOneById(Long id) {
        return notificationRepository.findById(id).get();
    }

    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }
}
