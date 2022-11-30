package com.artShop.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class Mail {
    private String destination, subject, message, email;
    private Boolean confirmed;

    public String getDestination() {
        return destination;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }

    public Boolean isConfirmed() {
        return confirmed;
    }

    public Mail(String email, String message, Boolean confirmed) {
        this.email = email;
        this.message = message;
        this.confirmed = confirmed;
    }

    private static JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("annasan14052003@gmail.com");
        mailSender.setPassword("keahbnupdvdbbysj");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    public static void sendSimpleMessage(String to, String subject, String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Art-Shop<annasan14052003@gmail.com>");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        getJavaMailSender().send(message);
    }
}
