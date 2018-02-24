package com.rt.taopicker.data.api.entity.statusQueryRespEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 物流箱信息
 * Created by wangzhi on 2017/7/14.
 */

public class BoxInfoEntity implements Serializable {
    //消息类型
    private int infoType;
    //载具状态。1 已绑定  0 未绑定
    private String boxStatusName;
    //包装员
    private String packEmployeeName;
    //波次单状态
    private String waveStatusName;
    //包装完成时间
    private String packCompleteTimeStr;
    //其它物流箱列表
    private List<String> boxList;
    //道口编号
    private String stallNo;
    //拣货单列表
    private List<PickAreaGoodEntity> pickOrderList;


    public int getInfoType() {
        return infoType;
    }

    public void setInfoType(int infoType) {
        this.infoType = infoType;
    }

    public String getBoxStatusName() {
        return boxStatusName;
    }

    public void setBoxStatusName(String boxStatusName) {
        this.boxStatusName = boxStatusName;
    }

    public String getPackEmployeeName() {
        return packEmployeeName;
    }

    public void setPackEmployeeName(String packEmployeeName) {
        this.packEmployeeName = packEmployeeName;
    }

    public String getWaveStatusName() {
        return waveStatusName;
    }

    public void setWaveStatusName(String waveStatusName) {
        this.waveStatusName = waveStatusName;
    }

    public String getPackCompleteTimeStr() {
        return packCompleteTimeStr;
    }

    public void setPackCompleteTimeStr(String packCompleteTimeStr) {
        this.packCompleteTimeStr = packCompleteTimeStr;
    }

    public List<String> getBoxList() {
        return boxList;
    }

    public void setBoxList(List<String> boxList) {
        this.boxList = boxList;
    }

    public String getStallNo() {
        return stallNo;
    }

    public void setStallNo(String stallNo) {
        this.stallNo = stallNo;
    }

    public List<PickAreaGoodEntity> getPickOrderList() {
        return pickOrderList;
    }

    public void setPickOrderList(List<PickAreaGoodEntity> pickOrderList) {
        this.pickOrderList = pickOrderList;
    }
}
