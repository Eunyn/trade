package com.foreign.trade.controller.common;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: CommonController.java
 * @Description: TODO
 * @CreateTime: 2023/12/1 11:42:00
 **/
@Controller
public class CommonController {

    @GetMapping("/common/kaptcha")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");

        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(150, 40, 4, 2);
        // 验证码存入 session
        request.getSession().setAttribute("verifyCode", shearCaptcha);
        // 输出图片流
        shearCaptcha.write(response.getOutputStream());
    }

    @GetMapping("/common/mall/kaptcha")
    public void mallKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");

        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(150, 40, 4, 2);
        // 验证码存入 session
        request.getSession().setAttribute("mallVerifyCode", shearCaptcha);
        // 输出图片流
        shearCaptcha.write(response.getOutputStream());
    }


}
