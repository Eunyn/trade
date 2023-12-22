package com.foreign.trade.service.impl;

import com.foreign.trade.dao.GoodsAdminMapper;
import com.foreign.trade.entity.GoodsAdmin;
import com.foreign.trade.service.GoodsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsAdminServiceImpl.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 21:39:00
 **/
@Service
public class GoodsAdminServiceImpl implements GoodsAdminService {

    @Resource
    private GoodsAdminMapper goodsAdminMapper;

    @Override
    public int insert(GoodsAdmin record) {
        return 0;
    }

    @Override
    public GoodsAdmin login(String userName, String password) {

        return goodsAdminMapper.login(userName, password);
    }

    @Override
    public GoodsAdmin selectByName(String userName) {
        return goodsAdminMapper.selectByName(userName);
    }

    @Override
    public int updatePassword(GoodsAdmin admin) {
        return goodsAdminMapper.updatePassword(admin);
    }
}
