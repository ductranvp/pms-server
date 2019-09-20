package vn.ptit.pms.service;

import org.apache.commons.lang3.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import vn.ptit.pms.domain.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class MailService {
    private static final String USER = "user";
    private static final String BASE_URL = "baseUrl";
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${pms.mail.from}")
    private String host;
    @Value("${pms.mail.enable}")
    private String enableSendMail;
    @Value("${pms.mail.base-url}")
    private String clientBaseUrl;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private MessageSource messageSource;

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(host);
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        if (Boolean.valueOf(enableSendMail)){
            Locale locale = Locale.forLanguageTag(user.getLangKey() == null ? "" : user.getLangKey());
            Context context = new Context(locale);
            context.setVariable(USER, user);
            context.setVariable(BASE_URL, clientBaseUrl);
            String content = templateEngine.process(templateName, context);
            String subject = messageSource.getMessage(titleKey, null, locale);
            sendEmail(user.getEmail(), subject, content, false, true);
        }
    }
}
