package com.foreign.trade.entity;

import java.util.Date;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsCart.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 20:53:00
 **/
public class GoodsCart {
    private Integer goodsId;
    private Date createTime;
    private Byte isDeleted;
    private String remain1;
    private String remain2;
    private String remain3;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "GoodsCart{" +
                "goodsId=" + goodsId +
                ", createTime=" + createTime +
                '}';
    }
}
