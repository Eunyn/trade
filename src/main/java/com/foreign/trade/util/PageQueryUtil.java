package com.foreign.trade.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: PageQueryUtil.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 21:21:00
 **/
public class PageQueryUtil extends LinkedHashMap<String, Object> {
    // 当前页码
    private final int page;
    // 每页条数
    private final int limit;

    public PageQueryUtil(Map<String, Object> params) {
        this.putAll(params);
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("start",(page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
    }

    public int getCurrPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }
}
