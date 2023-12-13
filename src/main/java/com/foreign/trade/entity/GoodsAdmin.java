package com.foreign.trade.entity;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: Admain.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 20:55:00
 **/
public class GoodsAdmin {
    private Integer adminUserId;
    private String userName;
    private String userPassword;
    private Byte loginStatus;
    private String remain1;
    private String remain2;

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
