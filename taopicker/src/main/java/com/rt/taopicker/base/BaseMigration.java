package com.rt.taopicker.base;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/**
 * Created by yaoguangyao on 2017/9/14.
 */

public class BaseMigration implements RealmMigration{
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        //发现列不同时，先全部删除，再创建
        realm.deleteAll();
    }
}
