package com.foreign.trade.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: PatternUtil.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 21:10:00
 **/
public class PatternUtil {
    /**
     * 判断是否是邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        try {
            new InternetAddress(email).validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }
}
