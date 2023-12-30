package com.driver.BookMyShow.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    //Polymorphism Technique -> Same methods with different parameters

    public void generateMail(String to, String sub, String body) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(sub);
            mimeMessageHelper.setText(body);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateMail(String to, String sub, String body, String filePath) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //whenever we send multimedia,images to email, we have to enable multipart as true
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(sub);
            mimeMessageHelper.setText(body);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            mimeMessageHelper.addAttachment(file.getFilename(), file);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
