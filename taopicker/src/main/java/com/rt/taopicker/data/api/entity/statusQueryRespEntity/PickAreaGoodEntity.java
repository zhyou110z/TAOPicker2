package com.rt.taopicker.data.api.entity.statusQueryRespEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangzhi on 2017/7/17.
 */

public class PickAreaGoodEntity implements Serializable {
    //拣货区名称
    private String pickAreaName;
    //拣货商品列表
    private List<GoodStatusEntity> pickGoodsList;

    public String getPickAreaName() {
        return pickAreaName;
    }

    public void setPickAreaName(String pickAreaName) {
        this.pickAreaName = pickAreaName;
    }

    public List<GoodStatusEntity> getPickGoodsList() {
        return pickGoodsList;
    }

    public void setPickGoodsList(List<GoodStatusEntity> pickGoodsList) {
        this.pickGoodsList = pickGoodsList;
    }
}
