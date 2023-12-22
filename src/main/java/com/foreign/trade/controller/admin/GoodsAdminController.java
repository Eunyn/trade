package com.foreign.trade.controller.admin;

import cn.hutool.captcha.ShearCaptcha;
import com.foreign.trade.entity.GoodsAdmin;
import com.foreign.trade.service.GoodsAdminService;
import com.foreign.trade.service.RedisService;
import com.foreign.trade.util.Result;
import com.foreign.trade.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    private RedisService redisService;

    @Resource
    private GoodsAdminService goodsAdminService;

    private final Logger logger = LoggerFactory.getLogger(GoodsAdminController.class);

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping({"", "/", "/index"})
    public String index(HttpServletRequest request) {
        Long accessCount = redisService.getAccessCount();
        request.setAttribute("accessCount", accessCount);
        request.setAttribute("path", "index");

        return "admin/index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("userPassword") String userPassword,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {
        logger.info("用户--{{}}--尝试登录",userName);

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
            logger.info("用户--{{}}--登录成功",userName);
            return "redirect:/admin/index";
        } else {
            session.setAttribute("errorMsg", "账号或密码错误");
            logger.info("用户--{{}}--登录失败",userName);
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

    @PostMapping("/password")
    @ResponseBody
    public Result updatePassword(HttpServletRequest request, @RequestBody GoodsAdmin admin) {
        String loginUser = (String) request.getSession().getAttribute("loginUser");
        GoodsAdmin goodsAdmin = goodsAdminService.selectByName(loginUser);
        if (goodsAdmin == null) {
            return ResultGenerator.genFailResult("没有此用户");
        }
        if (admin.getUserPassword().equals(goodsAdmin.getUserPassword())) {
            return ResultGenerator.genFailResult("密码重复");
        }

        goodsAdmin.setUserPassword(admin.getUserPassword());

        int i = goodsAdminService.updatePassword(goodsAdmin);
        if (i != 1) {
            return ResultGenerator.genFailResult("密码修改失败");
        }
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("errorMsg");

        return ResultGenerator.genSuccessResult("密码修改成功");
    }


}
