package com.example.printsystem.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender _mailSender;

    public void sendResetPasswordEmail(String toEmail, String confirmationCode) {
        MimeMessage mimeMessage = _mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject("Reset Password Confirmation Code");
            helper.setText("Your confirmation code is: " + confirmationCode);
            _mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public String generateConfirmationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }

    public void sendDoneNotifyEmail(String toEmail) {
        MimeMessage mimeMessage = _mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject("DONE PROJECT");
            helper.setText("We send you a notification that: Your printed product has been completed and we will send it to you as soon as possible.");
            _mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendDoneDeliveryEmail(String toEmail) {
        MimeMessage mimeMessage = _mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject("DONE DELIVERY");
            helper.setText("We send you a notification that: Your product has been completed delivery to you. Send we feedback and we will very happy serve you!");
            _mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
