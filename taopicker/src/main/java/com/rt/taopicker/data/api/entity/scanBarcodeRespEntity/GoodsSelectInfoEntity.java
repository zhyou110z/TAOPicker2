package com.rt.taopicker.data.api.entity.scanBarcodeRespEntity;

import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.GoodInfoEntity;

import java.util.List;

/**
 * Created by wangzhi on 2017/12/5.
 * 商品选择信息
 */

public class GoodsSelectInfoEntity {

    /**
     * RT货号
     */
    private String rtNo;

    /**
     * 商品编号
     */
    private String productNo;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品列表
     */
    private List<GoodInfoEntity> goodsList;

    public String getRtNo() {
        return rtNo;
    }

    public void setRtNo(String rtNo) {
        this.rtNo = rtNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<GoodInfoEntity> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodInfoEntity> goodsList) {
        this.goodsList = goodsList;
    }
}
