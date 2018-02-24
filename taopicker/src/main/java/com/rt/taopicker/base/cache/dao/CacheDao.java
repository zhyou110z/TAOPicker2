package com.rt.taopicker.base.cache.dao;

import com.rt.taopicker.base.cache.entity.CacheEntity;
import com.rt.taopicker.base.cache.po.CachePo;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by yaoguangyao on 2017/10/27.
 */

public class CacheDao {

    public CacheEntity queryByKey(String key) {
        Realm realm = Realm.getDefaultInstance();
        try {
            CachePo cachePo = realm.where(CachePo.class).equalTo("key", key).findFirst(); //最新的日志最新
            if (cachePo != null) {
                return cachePo.newEntity();
            }
        } finally {
            realm.close();
        }

        return null;
    }

    public void save(CacheEntity cache) {
        if (cache != null) {
            Realm realm = Realm.getDefaultInstance();
            try {
                realm.executeTransactionAsync(bgRealm -> {
                    CachePo cachePo = new CachePo();
                    cachePo.fromEntity(cache);
                    bgRealm.copyToRealmOrUpdate(cachePo);
                });
            } finally {
                realm.close();
            }
        }
    }

    public void delete(String key) {
        if (key != null) {
            Realm realm = Realm.getDefaultInstance();
            try {
                realm.executeTransactionAsync(bgRealm -> {
                    RealmResults results = bgRealm.where(CachePo.class).equalTo("key", key).findAll();
                    if (results != null) {
                        results.deleteAllFromRealm();
                    }
                });
            } finally {
                realm.close();
            }
        }
    }

    public void deleteAll() {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransactionAsync(bgRealm -> {
                RealmResults results = bgRealm.where(CachePo.class).findAll();
                if (results != null) {
                    results.deleteAllFromRealm();
                }
            });
        } finally {
            realm.close();
        }
    }
}
