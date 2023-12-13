package com.foreign.trade.controller.common;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GlobalException.java
 * @Description: TODO
 * @CreateTime: 2023/12/1 18:41:00
 **/
public class GlobalException extends RuntimeException{

    public GlobalException(){}

    public GlobalException(String message) {
        super(message);
    }

    public static void fail(String message) {
        throw new GlobalException(message);
    }
}
