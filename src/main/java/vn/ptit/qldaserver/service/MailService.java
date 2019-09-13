package vn.ptit.qldaserver.service;

import org.springframework.scheduling.annotation.Async;
import vn.ptit.qldaserver.domain.User;

public interface MailService {

    @Async
    void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml);

    @Async
    void sendEmailFromTemplate(User user, String templateName, String titleKey);
}
