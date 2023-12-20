package com.foreign.trade.dao;

import com.foreign.trade.entity.GoodsAdmin;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsAdminMapper.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 21:13:00
 **/
public interface GoodsAdminMapper {
    int insert(GoodsAdmin record);

    GoodsAdmin login(@Param("userName") String userName, @Param("userPassword") String password);

    GoodsAdmin selectByName(String userName);

    int updatePassword(GoodsAdmin admin);
}
