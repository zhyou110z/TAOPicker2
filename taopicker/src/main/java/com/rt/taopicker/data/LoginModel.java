package com.rt.taopicker.data;

import com.rt.taopicker.base.NullEntity;
import com.rt.taopicker.data.api.ITaoPickingApi;
import com.rt.taopicker.data.api.entity.CheckVersionReqEntity;
import com.rt.taopicker.data.api.entity.CheckVersionRespEntity;
import com.rt.taopicker.data.api.entity.EmployeeLoginReqEntity;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.data.api.entity.EmployeeLogoutRespEntity;
import com.rt.taopicker.data.api.entity.PdaLoginReqEntity;
import com.rt.taopicker.data.api.entity.RespEntity;
import com.rt.taopicker.data.api.entity.SetStoreReqEntitty;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.PdaLoginRespEntity;
import com.rt.taopicker.data.api.entity.queryPickAreaListRespEntity.QueryPickAreaListRespEntity;
import com.rt.taopicker.util.RetrofitHelper;

import io.reactivex.Observable;

/**
 * 登录
 * Created by yaoguangyao on 2017/9/29.
 */
public class LoginModel {

    private ITaoPickingApi mStoreManagerApi;

    public LoginModel() {
        mStoreManagerApi = RetrofitHelper.getInstance().createService(ITaoPickingApi.class);
    }

    /**
     * 登录
     * @param body
     * @return
     */
    public Observable<RespEntity<PdaLoginRespEntity>> pdaLogin(PdaLoginReqEntity body) {
        return mStoreManagerApi.pdaLogin(body);
    }

    /**
     * APP版本检测
     * @param body
     * @return
     */
    public Observable<RespEntity<CheckVersionRespEntity>> checkVersion(CheckVersionReqEntity body) {
        return mStoreManagerApi.checkVersion(body);
    }

    /**
     * 获取当前门店拣货区
     * @return
     */
    public Observable<RespEntity<QueryPickAreaListRespEntity>> queryPickAreaList() {
        return mStoreManagerApi.queryPickAreaList(new NullEntity());
    }

    /**
     * 员工工号登录
     * @return
     */
    public Observable<RespEntity<EmployeeLoginRespEntity>> employeeLogin(EmployeeLoginReqEntity body) {
        return mStoreManagerApi.employeeLogin(body);
    }

    /**
     * 选择账号门店
     * @return
     */
    public Observable<RespEntity<PdaLoginRespEntity>> setStore(SetStoreReqEntitty body) {
        return mStoreManagerApi.setStore(body);
    }


    /**
     * 员工下班
     * @return
     */
    public Observable<RespEntity<EmployeeLogoutRespEntity>> employeeLogout() {
        return mStoreManagerApi.employeeLogout(new NullEntity());
    }



}
