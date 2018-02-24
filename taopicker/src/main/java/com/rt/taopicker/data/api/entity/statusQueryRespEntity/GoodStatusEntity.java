package com.rt.taopicker.data.api.entity.statusQueryRespEntity;

import com.rt.taopicker.base.NotApi;

import java.io.Serializable;

/**
 * Created by wangzhi on 2017/2/20.
 * 商品状态信息。
 */

public class GoodStatusEntity implements Serializable {
    //商品名称
    private String goodsName;
    //商品规格
    private String goodsSpec;
    //Rt货号
    private String rtNo;
    //商品数量
    private int productPcs;
    //拣货商品状态,1正常拣货 2缺货
    private int pickGoodsStatus;
    //缺货信息
    private String goodsLockNumMsg;
    //是否标品,1为标品，2，3为称重品。
    private int goodsType;
    //特殊需求
    private String attachName;

    @NotApi
    private boolean needDivide;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getRtNo() {
        return rtNo;
    }

    public void setRtNo(String rtNo) {
        this.rtNo = rtNo;
    }

    public int getProductPcs() {
        return productPcs;
    }

    public void setProductPcs(int productPcs) {
        this.productPcs = productPcs;
    }

    public int getPickGoodsStatus() {
        return pickGoodsStatus;
    }

    public void setPickGoodsStatus(int pickGoodsStatus) {
        this.pickGoodsStatus = pickGoodsStatus;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public String getGoodsLockNumMsg() {
        return goodsLockNumMsg;
    }

    public void setGoodsLockNumMsg(String goodsLockNumMsg) {
        this.goodsLockNumMsg = goodsLockNumMsg;
    }

    public boolean isNeedDivide() {
        return needDivide;
    }

    public void setNeedDivide(boolean needDivide) {
        this.needDivide = needDivide;
    }
}
