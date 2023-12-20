package com.foreign.trade.config;

import org.springframework.util.StringUtils;

import java.net.URI;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: Constants.java
 * @Description: TODO
 * @CreateTime: 2023/12/1 19:07:00
 **/
public class Constants {
    
//    public static final String FILE_UPLOAD_DIC = "E:/upload/";  // 上传文件总位置
    public static final String FILE_UPLOAD_DIC = "/home/euny/document/upload/";  // 上传文件总位置
    public final static String FILE_UPLOAD_DIC_MAIN = FILE_UPLOAD_DIC + "main/"; // 上传文件主图位置
    public final static String FILE_UPLOAD_DIC_DETAILS = FILE_UPLOAD_DIC + "details/"; // 上传文件详情图位置
    public final static Integer GOODS_SEARCH_PAGE_LIMIT = 8; // 搜索分页默认条数， 每页 8 条
    public final static String ACCESS_DAILY = "access:daily:";

    public static URI getHost(URI uri) {
        URI effectiveURI = null;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (Throwable ignored) {
        }
        return effectiveURI;
    }

    public static String cleanString(String value) {
        if (!StringUtils.hasLength(value)) {
            return "";
        }
        value = value.toLowerCase();
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("onload", "0nl0ad");
        value = value.replaceAll("xml", "xm1");
        value = value.replaceAll("window", "wind0w");
        value = value.replaceAll("click", "cl1ck");
        value = value.replaceAll("var", "v0r");
        value = value.replaceAll("let", "1et");
        value = value.replaceAll("function", "functi0n");
        value = value.replaceAll("return", "retu1n");
        value = value.replaceAll("$", "");
        value = value.replaceAll("document", "d0cument");
        value = value.replaceAll("const", "c0nst");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "scr1pt");
        value = value.replaceAll("insert", "1nsert");
        value = value.replaceAll("drop", "dr0p");
        value = value.replaceAll("create", "cre0ate");
        value = value.replaceAll("update", "upd0ate");
        value = value.replaceAll("alter", "a1ter");
        value = value.replaceAll("from", "fr0m");
        value = value.replaceAll("where", "wh1re");
        value = value.replaceAll("database", "data1base");
        value = value.replaceAll("table", "tab1e");
        value = value.replaceAll("tb", "tb0");
        return value;
    }
}
