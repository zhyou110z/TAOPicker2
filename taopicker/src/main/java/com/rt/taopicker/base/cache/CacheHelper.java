package com.rt.taopicker.base.cache;


import com.rt.taopicker.base.cache.dao.CacheDao;
import com.rt.taopicker.base.cache.entity.CacheEntity;
import com.rt.taopicker.util.SingletonHelper;

/**
 * Created by yaoguangyao on 2017/10/27.
 */

public class CacheHelper {
    private static CacheHelper sCache;

    private CacheDao mCacheDao = SingletonHelper.getInstance(CacheDao.class);

    private CacheHelper() {
    }

    public static CacheHelper getInstance() {
        if (sCache == null) {
            sCache = new CacheHelper();
        }
        return sCache;
    }

    public CacheEntity get(String key) {
        if (key != null) {
            return mCacheDao.queryByKey(key);
        }
        return null;
    }

    public String getValue(String key) {
        if (key != null) {
            CacheEntity cacheEntity =  mCacheDao.queryByKey(key);
            if (cacheEntity != null) {
                return cacheEntity.getValue();
            }
        }
        return null;
    }

    public void add(String key, String value) {
        if (key != null) {
            CacheEntity cacheEntity = new CacheEntity();
            cacheEntity.setKey(key);
            cacheEntity.setValue(value);
            mCacheDao.save(cacheEntity);
        }
    }

    public void remove(String key) {
        if (key != null) {
            mCacheDao.delete(key);
        }
    }

    public void clear() {
        mCacheDao.deleteAll();
    }


}
