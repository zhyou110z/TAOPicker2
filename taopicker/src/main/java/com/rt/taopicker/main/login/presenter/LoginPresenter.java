package com.rt.taopicker.main.login.presenter;

import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.LoginModel;
import com.rt.taopicker.data.api.entity.PdaLoginReqEntity;
import com.rt.taopicker.data.api.entity.SetStoreReqEntitty;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.PdaLoginRespEntity;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.Store;
import com.rt.taopicker.main.login.contract.ILoginContract;
import com.rt.taopicker.main.login.vo.LoginInfoVo;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.util.SingletonHelper;
import com.rt.taopicker.util.ToastUtil;
import com.rt.taopicker.util.UserInfoHelper;
import com.rt.taopicker.util.CheckVersionHelper;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yaoguangyao on 2017/12/4.
 */

public class LoginPresenter extends BasePresenter<ILoginContract.IView> implements ILoginContract.IPresenter {
    private LoginModel mLoginModel = SingletonHelper.getInstance(LoginModel.class);

    @Override
    public void create() {

    }

    @Override
    public void start() {
        UserInfoHelper.setCurrentUserInfo(null); //刷新页面清空用户信息，之前app有可能是杀进程
        //版本检测
        CheckVersionHelper.getInstance().newVersionCheck(false);
    }

    @Override
    public void login() {
        LoginInfoVo loginInfoVo = mView.getLoginInfoVo();
        if (loginInfoVo != null) {
            LoadingHelper.getInstance().showLoading();
            PdaLoginReqEntity reqEntity = new PdaLoginReqEntity();
            reqEntity.setUsername(loginInfoVo.getUserName());
            reqEntity.setPasswordMd5(loginInfoVo.getPwd());
            addSubscribe(mLoginModel.pdaLogin(reqEntity)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()),
                    respEntity -> {
                        if (mView != null) {
                            if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                                PdaLoginRespEntity body = respEntity.getBody();
                                List<Store> storeList = body.getStoreList();
                                if (storeList == null || storeList.size() == 0) {
                                    ToastUtil.toast("没有对应门店");
                                } else if (storeList.size() == 1) {
                                    Store store = storeList.get(0);
                                    body.setStoreName(store.getStoreName());
                                    body.setStoreCode(store.getStoreNo());
                                    UserInfoHelper.setCurrentUserInfo(body);
                                    mView.toHome();
                                } else {
                                    UserInfoHelper.setCurrentUserInfo(body);
                                    mView.showStoreListDialog(storeList);
                                }
                            } else {
                                mView.clearLoginInfo();
                                ToastUtil.toast(respEntity.getErrorDesc());
                            }
                            LoadingHelper.getInstance().hideLoading();
                        }
                    }, null);
        }
    }

    @Override
    public void chooseStore(String storeCode, String storeName) {
        PdaLoginRespEntity pdaLoginRespEntity = UserInfoHelper.getCurrentUserInfo();
        if (pdaLoginRespEntity != null) {
            pdaLoginRespEntity.setStoreCode(storeCode);
            pdaLoginRespEntity.setStoreName(storeName);
            UserInfoHelper.setCurrentUserInfo(pdaLoginRespEntity);
        }

        LoadingHelper.getInstance().showLoading();
        SetStoreReqEntitty reqEntity = new SetStoreReqEntitty();
        reqEntity.setStoreNo(storeCode);
        addSubscribe(mLoginModel.setStore(reqEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            PdaLoginRespEntity body = respEntity.getBody();
                            body.setStoreCode(storeCode);
                            body.setStoreName(storeName);
                            UserInfoHelper.setCurrentUserInfo(body);
                            mView.toHome();
                        } else {
                            mView.clearLoginInfo();
                            ToastUtil.toast(respEntity.getErrorDesc());
                        }
                        LoadingHelper.getInstance().hideLoading();
                    }
                }, null);
    }
}
