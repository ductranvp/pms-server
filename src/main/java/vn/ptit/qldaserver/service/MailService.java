package vn.ptit.qldaserver.service;

import org.apache.commons.lang3.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import vn.ptit.qldaserver.model.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.Locale;

import static vn.ptit.qldaserver.util.CommonConstants.*;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String host;

    private final SpringTemplateEngine templateEngine;

    public MailService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml){
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
    public void sendActivationMail(User user) {
        Context context = new Context(Locale.ROOT);
        String link = CLIENT_BASE_URL + "/activate?key=" + user.getActivationKey();
        context.setVariable("user", user);
        context.setVariable("activationLink", link);
        String content = templateEngine.process("activationEmail", context);
        sendEmail(user.getEmail(), "Account Activation", content, false, true);
    }

    @Async
    public void sendResetPasswordMail(User user){
        Context context = new Context(Locale.ROOT);
        String link = CLIENT_BASE_URL + "/resetPassword/finish?key=" + user.getResetKey();
        context.setVariable("user", user);
        context.setVariable("resetLink", link);
        String content = templateEngine.process("resetPasswordEmail", context);
        sendEmail(user.getEmail(), "Password Reset", content, false, true);
    }
}
