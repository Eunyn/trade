package com.foreign.trade.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: MailConfig.java
 * @Description: TODO
 * @CreateTime: 2023/12/11 21:51:00
 **/
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

    private String mailFrom;
    private String mailTo;

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }
}
