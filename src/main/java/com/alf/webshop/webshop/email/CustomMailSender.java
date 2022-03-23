package com.alf.webshop.webshop.email;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;

@Service
public class CustomMailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(List<String> toEmails) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("pintertamas99@gmail.com", "to_2@gmail.com", "to_3@yahoo.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);
    }

    public void sendEmailWithAttachment(String toEmail) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toEmail);

            helper.setSubject("Log in to your account please");
            helper.setText("<h1>Hello! We haven't seen you in a while and we miss you! Please log in to your account in order to keep it.</h1>", true);
            helper.addAttachment("my_photo.png", new ClassPathResource("image.jpg"));

            javaMailSender.send(msg);
        } catch (MessagingException e) {
            LoggerFactory.getLogger(CustomMailSender.class).error("Could not send message to " + toEmail);
        }
    }
}
