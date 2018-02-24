package com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity;

import com.rt.taopicker.base.BaseEntity;

import java.io.Serializable;

/**
 * Created by wangzhi on 2017/12/11.
 */

public class QueryPickerOrderInfoRespEntity extends BaseEntity {

    /**
     -2拣货完成失败
     1 查询成功
     5 拣货完成道口
     6 拣货完成备用道口
     9 拣货单全部缺货
     */
    private Integer resultType;

    /**
     resultType=-2
     拣货完成失败
     resultType=5
     拣货完成，请挂上流水到达<red>12</red>道口
     resultType=6
     拣货完成，请挂上流水到达<red>备用道口</red>
     resultType=9
     此次拣货无需上吊挂，绑定的拣货袋已自动解绑
     */
    private String message;

    private PickerOrderInfoEntity pickOrderInfo;

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
}
