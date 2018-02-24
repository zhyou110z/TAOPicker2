package com.rt.taopicker.data.api.entity.scanBarcodeRespEntity;

/**
 * Created by wangzhi on 2017/12/5.
 * 条码解析后信息
 */

public class BarcodeInfoEntity {

    /**
     * RT货号
     */
    private String rtNo;

    /**
     * 重量
     */
    private Integer num;

    /**
     * 价格
     */
    private Double price;

    /**
     * 商品编号
     */
    private String productNo;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 订单商品表主键
     */
    private Long orderGoodsId;

    public String getRtNo() {
        return rtNo;
    }

    public void setRtNo(String rtNo) {
        this.rtNo = rtNo;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Long getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(Long orderGoodsId) {
        this.orderGoodsId = orderGoodsId;
    }
}
