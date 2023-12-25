package com.foreign.trade.config;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: Constants.java
 * @Description: TODO
 * @CreateTime: 2023/12/1 19:07:00
 **/
public class Constants {

    public final static String FILE_UPLOAD_DIC = "E:/upload/";  // 上传文件总位置
//    public static final String FILE_UPLOAD_DIC = "/home/euny/document/upload/";  // 上传文件总位置
    public final static String FILE_UPLOAD_DIC_MAIN = FILE_UPLOAD_DIC + "main/"; // 上传文件主图位置
    public final static String FILE_UPLOAD_DIC_DETAILS = FILE_UPLOAD_DIC + "details/"; // 上传文件详情图位置
    public final static Integer GOODS_SEARCH_PAGE_LIMIT = 8; // 搜索分页默认条数， 每页 8 条
    public final static String ACCESS_DAILY = "access:daily:";
    public final static Integer INQUIRY_RATE = 3;  // 一分钟内 inquiry 次数
    public final static Integer INTERVAL_TIME = 60; // inquiry 间隔时长，单位: s


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
