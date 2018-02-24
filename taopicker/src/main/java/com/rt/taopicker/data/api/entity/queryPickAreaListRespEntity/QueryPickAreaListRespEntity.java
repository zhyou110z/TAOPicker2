package com.rt.taopicker.data.api.entity.queryPickAreaListRespEntity;

import com.rt.taopicker.base.BaseEntity;

import java.util.List;

/**
 * Created by yaoguangyao on 2018/1/25.
 */

public class QueryPickAreaListRespEntity extends BaseEntity {
    private List<PickArea> pickAreaList;

    public List<PickArea> getPickAreaList() {
        return pickAreaList;
    }

    public void setPickAreaList(List<PickArea> pickAreaList) {
        this.pickAreaList = pickAreaList;
    }
}
