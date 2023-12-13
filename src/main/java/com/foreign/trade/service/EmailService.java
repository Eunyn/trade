package com.foreign.trade.service;

import javax.mail.MessagingException;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: EmailService.java
 * @Description: TODO
 * @CreateTime: 2023/12/9 12:33:00
 **/
public interface EmailService {
    CompletableFuture<Boolean> sendEmailAsync(String to, String subject, String text) throws MessagingException;

    void sendEmail(String to, String subject, String text) throws MessagingException;
}
