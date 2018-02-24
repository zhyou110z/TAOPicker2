package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.data.api.entity.packageScanCodeRespEntity.PackageScanCodeRespEntity;

/**
 * Created by wangzhi on 2018/1/30.
 */

public class QueryPackageUncompleteRespEntity extends PackageScanCodeRespEntity {

    //是否存在未完成的包装
    private Boolean isExist;

    public Boolean getExist() {
        return isExist;
    }

    public void setExist(Boolean exist) {
        isExist = exist;
    }
}
