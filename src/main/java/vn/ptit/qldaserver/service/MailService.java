package vn.ptit.qldaserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import static vn.ptit.qldaserver.util.CommonConstants.*;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendActivationMail(String email, String activationKey) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Verify account");
        msg.setText("<h3>Click <a target='_blank' href="+ CLIENT_BASE_URL + "/activate?key=" + activationKey +">here</a> to activate your account</h3>");
        javaMailSender.send(msg);
    }

    public void sendResetPasswordMail(String email, String resetKey){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Reset Password");
        msg.setText("<h3>Click <a target='_blank' href="+ CLIENT_BASE_URL + "/resetPassword/finish?key=" + resetKey +">here</a> to reset your account password</h3>");
        javaMailSender.send(msg);
    }
}
