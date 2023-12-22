package com.foreign.trade.service.impl;

import com.foreign.trade.config.MailProperties;
import com.foreign.trade.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: EmailServiceImpl.java
 * @Description: TODO
 * @CreateTime: 2023/12/9 12:34:00
 **/
@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    private JavaMailSender javaMailSender;

    private final MailProperties mailProperties;

    @Autowired
    public EmailServiceImpl(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    @Async
    public CompletableFuture<Boolean> sendEmailAsync(String to, String subject, String text) throws MessagingException {
        sendEmail(to, subject, text);
        return CompletableFuture.completedFuture(true);
    }

    @Override
    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String mailFrom = mailProperties.getMailFrom();
        String mailTo = mailProperties.getMailTo();

        helper.setFrom(mailFrom);
        helper.setTo(mailTo);
        helper.setSubject(subject);
        helper.setText(text, true);

        javaMailSender.send(message);
    }
}
