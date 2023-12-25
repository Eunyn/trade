package com.foreign.trade.controller.admin;

import com.foreign.trade.config.Constants;
import com.foreign.trade.service.RedisService;
import com.foreign.trade.util.Result;
import com.foreign.trade.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Year;
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

    @Resource
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

    @GetMapping("/daily/{selectId}")
    @ResponseBody
    public Map<String, Long> getDailyAccessCounts(@PathVariable("selectId") Integer selectId) {
        TreeMap<String, Long> dailyCounts = new TreeMap<>();
        LocalDate currentDate = LocalDate.now();
        int monthDay = getDays(currentDate);
        int days = selectId == 1 ? 7 : monthDay;
        for (int i = 0; i < days; i++) {
            String key = Constants.ACCESS_DAILY + currentDate.minusDays(i);
            long count = Long.parseLong(redisService.getAccessCounts(key));
            String day = currentDate.minusDays(i).toString().substring(5);
            dailyCounts.put(day, count);
        }

        return dailyCounts;
    }

    private int getDays(LocalDate currentDate) {
        int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();

        if (Year.isLeap(year)) {
            days[2] = days[2] + 1;
        }

        return days[month];
    }
}
