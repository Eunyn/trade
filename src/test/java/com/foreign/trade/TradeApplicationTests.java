package com.foreign.trade;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class TradeApplicationTests {

    @Test
    void contextLoads() {
        String timeString = "20231211_13325688";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSS");

        try {
            Date date = dateFormat.parse(timeString);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the parsing exception as needed
        }
    }

}
