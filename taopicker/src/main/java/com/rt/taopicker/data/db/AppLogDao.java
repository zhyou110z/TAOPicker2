package com.rt.taopicker.data.db;

import com.rt.taopicker.data.api.entity.AppLogEntity;
import com.rt.taopicker.data.db.po.AppLogPo;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by yaoguangyao on 2017/9/28.
 */

public class AppLogDao {

    /**
     * 插入日志
     */
    public void insert(AppLogEntity log) {
        if (log != null) {
            Realm realm = Realm.getDefaultInstance();
            try {
                realm.executeTransactionAsync(bgRealm -> {
                    AppLogPo logPo = new AppLogPo();
                    logPo.fromEntity(log);
                    logPo.setAppLogId(System.currentTimeMillis());
                    bgRealm.copyToRealm(logPo);
                });
            } finally {
                realm.close();
            }
        }
    }

    /**
     * 获取日志
     */
    public List<AppLogEntity> getAllByDesc() {
        Realm realm = Realm.getDefaultInstance();
        try {
            List<AppLogEntity> logEntities = new ArrayList<>();
            RealmResults<AppLogPo> logResult = realm.where(AppLogPo.class).findAllSorted("appLogId", Sort.DESCENDING); //最新的日志最新
            if (logResult != null && logResult.size() > 0) {
                for (AppLogPo logPo : logResult) {
                    if (logPo != null) {
                        AppLogEntity logEntity = logPo.newEntity();
                        logEntities.add(logEntity);
                    }
                }
            }
            return logEntities;
        } finally {
            realm.close();
        }
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Long> appLogIds) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            if (appLogIds != null && appLogIds.size() > 0) {
                for (Long appLogId : appLogIds) {
                    if (appLogId != null) {
                        realm.where(AppLogPo.class)
                                .equalTo("appLogId", appLogId)
                                .findAll()
                                .deleteAllFromRealm();
                    }
                }
            }
            realm.commitTransaction();
        } finally {
            realm.close();
        }

    }

}