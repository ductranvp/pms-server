package vn.ptit.pms.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.ptit.pms.util.WSConstants;

@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendMessage(String message){
        switch (message){
            case WSConstants.NOTIFICATION:
                this.messagingTemplate.convertAndSend("/info/notification", message);
                break;
            case WSConstants.TASK:
                this.messagingTemplate.convertAndSend("/info/task", message);
                break;
        }
    }
}
