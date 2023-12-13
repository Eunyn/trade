package com.foreign.trade.controller.admin;

import cn.hutool.captcha.ShearCaptcha;
import com.foreign.trade.entity.GoodsAdmin;
import com.foreign.trade.service.GoodsAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsAdminController.java
 * @Description: TODO
 * @CreateTime: 2023/12/1 11:12:00
 **/
@Controller
@RequestMapping("/admin")
public class GoodsAdminController {

    @Resource
    private GoodsAdminService goodsAdminService;

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping({"", "/", "/index"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");

        return "admin/index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("userPassword") String userPassword,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {


        if (!StringUtils.hasLength(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空");
            return "admin/login";
        }

        if (!StringUtils.hasLength(userName) || StringUtils.isEmpty(userPassword)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            return "admin/login";
        }

        ShearCaptcha shearCaptcha = (ShearCaptcha) session.getAttribute("verifyCode");
        if (shearCaptcha == null) {
            session.setAttribute("errorMsg", "验证码为空");
            return "admin/login";
        }

        if (!shearCaptcha.getCode().equals(verifyCode)) {
            session.setAttribute("errorMsg", "验证码错误");
            return "admin/login";
        }

        GoodsAdmin goodsAdmin = goodsAdminService.login(userName, userPassword);
        if (goodsAdmin != null) {
            session.setAttribute("loginUser", goodsAdmin.getUserName());
            session.setAttribute("loginUserId", goodsAdmin.getAdminUserId());
            session.setMaxInactiveInterval(2 * 60 * 60);
            return "redirect:/admin/index";
        } else {
            session.setAttribute("errorMsg", "账号或密码错误");
            return "admin/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("errorMsg");

        return "admin/login";
    }


}
