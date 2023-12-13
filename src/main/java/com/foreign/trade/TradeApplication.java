package com.foreign.trade;

import com.foreign.trade.config.MailProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@MapperScan("com.foreign.trade.dao")
@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy
@EnableConfigurationProperties(MailProperties.class)
public class TradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeApplication.class, args);
    }

}
