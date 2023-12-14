package com.foreign.trade.controller.admin;

import com.foreign.trade.service.RedisService;
import com.foreign.trade.util.PageResult;
import com.foreign.trade.util.Result;
import com.foreign.trade.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsIndexController.java
 * @Description: TODO
 * @CreateTime: 2023/12/13 14:10:00
 **/
@Controller
@RequestMapping("/admin")
public class GoodsIndexController {

    @Autowired
    private RedisService redisService;


    @GetMapping("/accessCount")
    @ResponseBody
    public Result getAccessCount() {
        Map<Object, Double> topProductVisits = redisService.getTopProductVisits(10);
        List<Map<String, Object>> resultLists = new ArrayList<>();
        int index = 1;
        for (Map.Entry<Object, Double> entry : topProductVisits.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("index", index);
            item.put("goodsName", entry.getKey().toString());
            item.put("score", entry.getValue());
            resultLists.add(item);
            ++index;
        }

        return ResultGenerator.genSuccessResult(resultLists);
    }

    @GetMapping("/inquiryCount")
    @ResponseBody
    public Result getInquiryCount() {
        Map<Object, Double> topProductVisits = redisService.getTopProductInquiries(10);
        List<Map<String, Object>> resultLists = new ArrayList<>();
        int index = 1;
        for (Map.Entry<Object, Double> entry : topProductVisits.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("index", index);
            item.put("goodsName", entry.getKey().toString());
            item.put("score", entry.getValue());
            resultLists.add(item);
            ++index;
        }

        return ResultGenerator.genSuccessResult(resultLists);
    }

    @GetMapping("/test")
    @ResponseBody
    public String testRequest(HttpServletRequest request) {
        request.setAttribute("test", "testRequest");

        return "mall/index";
    }
}
