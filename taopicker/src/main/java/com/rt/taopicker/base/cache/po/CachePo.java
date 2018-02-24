package com.rt.taopicker.base.cache.po;

import com.rt.taopicker.base.cache.entity.CacheEntity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yaoguangyao on 2017/10/27.
 */

public class CachePo extends RealmObject{
    @PrimaryKey
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void fromEntity(CacheEntity cache) {
        if (cache != null) {
            this.key = cache.getKey();
            this.value = cache.getValue();
        }
    }

    public CacheEntity newEntity() {
        CacheEntity cacheEntity = new CacheEntity();
        cacheEntity.setKey(this.key);
        cacheEntity.setValue(this.value);
        return cacheEntity;
    }
}
