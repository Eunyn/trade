package com.foreign.trade.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsCategoryInfo.java
 * @Description: TODO
 * @CreateTime: 2023/12/12 17:20:00
 **/
public class GoodsCategoryInfo {
    private String categoryName;
    private Integer goodsId;
    private String goodsName;
    private String goodsProductionTime;
    private Integer goodsCategoryId;
    private String goodsCoverImg;
    private String goodsColor;
    private String goodsSize;
    private String goodsMaterial;
    private String goodsImprintMethod;
    private String goodsDetails;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(Integer goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public String getGoodsCoverImg() {
        return goodsCoverImg;
    }

    public void setGoodsCoverImg(String goodsCoverImg) {
        this.goodsCoverImg = goodsCoverImg;
    }

    public String getGoodsColor() {
        return goodsColor;
    }

    public void setGoodsColor(String goodsColor) {
        this.goodsColor = goodsColor;
    }

    public String getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(String goodsSize) {
        this.goodsSize = goodsSize;
    }

    public String getGoodsMaterial() {
        return goodsMaterial;
    }

    public void setGoodsMaterial(String goodsMaterial) {
        this.goodsMaterial = goodsMaterial;
    }

    public String getGoodsDetails() {
        return goodsDetails;
    }

    public void setGoodsDetails(String goodsDetails) {
        this.goodsDetails = goodsDetails;
    }

    public String getGoodsProductionTime() {
        return goodsProductionTime;
    }

    public void setGoodsProductionTime(String goodsProductionTime) {
        this.goodsProductionTime = goodsProductionTime;
    }

    public String getGoodsImprintMethod() {
        return goodsImprintMethod;
    }

    public void setGoodsImprintMethod(String goodsImprintMethod) {
        this.goodsImprintMethod = goodsImprintMethod;
    }
}
