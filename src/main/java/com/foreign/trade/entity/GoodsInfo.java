package com.foreign.trade.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsInfo.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 20:45:00
 **/
public class GoodsInfo {
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
    private String goodsSubImg;

    public String getGoodsDetails() {
        return goodsDetails;
    }

    public void setGoodsDetails(String goodsDetails) {
        this.goodsDetails = goodsDetails;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGoodsSubImg() {
        return goodsSubImg;
    }

    public void setGoodsSubImg(String goodsSubImg) {
        this.goodsSubImg = goodsSubImg;
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

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsProductionTime='" + goodsProductionTime + '\'' +
                ", goodsCategoryId=" + goodsCategoryId +
                ", goodsCoverImg='" + goodsCoverImg + '\'' +
                ", goodsColor='" + goodsColor + '\'' +
                ", goodsSize='" + goodsSize + '\'' +
                ", goodsMaterial='" + goodsMaterial + '\'' +
                ", goodsImprintMethod='" + goodsImprintMethod + '\'' +
                ", goodsDetails='" + goodsDetails + '\'' +
                ", createTime=" + createTime +
                ", goodsSubImg='" + goodsSubImg + '\'' +
                '}';
    }
}
