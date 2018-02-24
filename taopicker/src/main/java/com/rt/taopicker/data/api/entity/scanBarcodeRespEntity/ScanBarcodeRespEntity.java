package com.rt.taopicker.data.api.entity.scanBarcodeRespEntity;

import com.rt.taopicker.base.BaseEntity;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.PickerOrderInfoEntity;

/**
 * Created by wangzhi on 2017/12/5.
 */

public class ScanBarcodeRespEntity extends BaseEntity {

    /**
     返回结果类型：
     -2 拣货完成失败
     -1 刷入载具失败
     0 刷入商品失败
     1 载具刷入成功
     2 商品扫入成功
     3 提示是否强制提交
     4 选择称重品
     5 拣货完成道口
     6 拣货完成备用道口
     9 拣货单全部缺货
     */
    private Integer resultType;

    /**
     消息提醒：
     resultType=-2
     拣货完成失败
     resultType=-1
     载具已绑定
     resultType= 0
     刷入商品失败
     resultType=1
     载具绑定成功
     resultType=3
     不够客需重量
     超过客需重量
     resultType=5
     拣货完成，请挂上流水到达<red>12</red>道口
     resultType=6
     拣货完成，请挂上流水到达<red>备用道口</red>
     resultType=9
     此次拣货无需上吊挂，绑定的拣货袋已自动解绑
     */
    private String message;

    /**
     * 拣货单信息
     */
    private PickerOrderInfoEntity pickOrderInfo;

    /**
     * 商品选择信息。称重品选择对应的商品消除
     */
    private GoodsSelectInfoEntity goodsSelectInfo;

    /**
     * 条码解析后信息
     */
    private BarcodeInfoEntity barcodeInfo;

    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PickerOrderInfoEntity getPickOrderInfo() {
        return pickOrderInfo;
    }

    public void setPickOrderInfo(PickerOrderInfoEntity pickOrderInfo) {
        this.pickOrderInfo = pickOrderInfo;
    }

    public GoodsSelectInfoEntity getGoodsSelectInfo() {
        return goodsSelectInfo;
    }

    public void setGoodsSelectInfo(GoodsSelectInfoEntity goodsSelectInfo) {
        this.goodsSelectInfo = goodsSelectInfo;
    }

    public BarcodeInfoEntity getBarcodeInfo() {
        return barcodeInfo;
    }

    public void setBarcodeInfo(BarcodeInfoEntity barcodeInfo) {
        this.barcodeInfo = barcodeInfo;
    }
}
