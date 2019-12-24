package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Notification;
import vn.ptit.pms.repository.NotificationRepository;
import vn.ptit.pms.socket.WebSocketService;
import vn.ptit.pms.util.WSConstants;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    WebSocketService webSocketService;

    public Notification save(Notification notification) {
        webSocketService.sendMessage(WSConstants.NOTIFICATION);
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
