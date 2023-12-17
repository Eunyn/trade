package com.foreign.trade.controller.admin;

import com.foreign.trade.service.RedisService;
import com.foreign.trade.util.PageResult;
import com.foreign.trade.util.Result;
import com.foreign.trade.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
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

    private final static Logger logger = LoggerFactory.getLogger(GoodsIndexController.class);

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

    @GetMapping("/daily")
    @ResponseBody
    public Map<String, Long> getDailyAccessCounts() {
        TreeMap<String, Long> dailyCounts = new TreeMap<>();
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            String key = "access:daily:" + currentDate.minusDays(i);
            logger.info("currentDate.minusDays({}): {}", i, currentDate.minusDays(i));
            long count = Long.parseLong(redisService.getAccessCounts(key));
            String day = currentDate.minusDays(i).toString().substring(5);
            dailyCounts.put(day, count);
        }

        return dailyCounts;
    }

    @GetMapping("/test")
    @ResponseBody
    public String testRequest(HttpServletRequest request) {
        request.setAttribute("test", "testRequest");

        return "mall/index";
    }
}
