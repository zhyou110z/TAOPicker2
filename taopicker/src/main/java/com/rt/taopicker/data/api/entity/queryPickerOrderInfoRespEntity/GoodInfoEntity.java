package com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangzhi on 2017/2/20.
 * 商品信息。
 */

public class GoodInfoEntity implements Serializable {
    /**
     * 商品入库编号
     */
    private String productNo;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 商品规格
     */
    private String productSpec;
    /**
     * 商品附加服务
     */
    private String goodsAttach;
    /**
     * 商品数量（份数）
     */
    private int productPcs;
    /**
     * 商品已拣数量（份数）
     */
    private int productDonePcs;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 国标编码
     */
    private List<String> eanNoList;
    /**
     * rt货号
     */
    private String rtNo;
    /**
     * 是否称重。1为标品，2为按件称重，3为称重品。
     */
    private int productType;
    /**
     * 订单商品表主键
     */
    private Long orderGoodsId;

    /**
     * 料位
     */
    private String shelfNo;

    /**
     * 需要购买的重量（纯称重品才有）
     */
    private String productWeight;



    /**
     * 拣货金额
     */
    private String pickPrice;

    /**
     * 实际称重
     */
    private double realWeight;

    public Long getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(Long orderGoodsId) {
        this.orderGoodsId = orderGoodsId;
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

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getProductPcs() {
        return productPcs;
    }

    public void setProductPcs(int productPcs) {
        this.productPcs = productPcs;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getGoodsAttach() {
        return goodsAttach;
    }

    public void setGoodsAttach(String goodsAttach) {
        this.goodsAttach = goodsAttach;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getPickPrice() {
        return pickPrice;
    }

    public void setPickPrice(String pickPrice) {
        this.pickPrice = pickPrice;
    }

    public double getRealWeight() {
        return realWeight;
    }

    public void setRealWeight(double realWeight) {
        this.realWeight = realWeight;
    }

    public List<String> getEanNoList() {
        return eanNoList;
    }

    public void setEanNoList(List<String> eanNoList) {
        this.eanNoList = eanNoList;
    }

    public String getRtNo() {
        return rtNo;
    }

    public void setRtNo(String rtNo) {
        this.rtNo = rtNo;
    }

    public String getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(String shelfNo) {
        this.shelfNo = shelfNo;
    }

    public int getProductDonePcs() {
        return productDonePcs;
    }

    public void setProductDonePcs(int productDonePcs) {
        this.productDonePcs = productDonePcs;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }
}
