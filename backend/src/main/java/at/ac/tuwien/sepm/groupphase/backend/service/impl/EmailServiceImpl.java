package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendPasswordResetMail(String recipient, String link, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject("Reset your Password");
        message.setText("Dear Customer.\n Here is the password reset link you requests:" + link + "\nToken: " + token + " Click on the link to reset your password.");

        javaMailSender.send(message);
    }
}
