package com.rt.taopicker.main.temporary.presenter;

import android.app.Activity;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.LoginModel;
import com.rt.taopicker.data.TemporaryModel;
import com.rt.taopicker.data.api.entity.TemporaryReqEntity;
import com.rt.taopicker.data.api.entity.TemporaryRespEntity;
import com.rt.taopicker.main.temporary.contract.ITemporaryContract;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.util.SingletonHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by zhouyou on 2018/2/1.
 */

public class TemporaryPresenter extends BasePresenter<ITemporaryContract.IView> implements ITemporaryContract.IPresenter {

    private TemporaryModel mTemporaryModel = SingletonHelper.getInstance(TemporaryModel.class);

    private LoginModel mLoginModel = SingletonHelper.getInstance(LoginModel.class);

    private Activity mContext;

    public TemporaryPresenter(Activity context){
        this.mContext = context;
    }

    @Override
    public void start() {
        if (mNeedUpdate) {
            mNeedUpdate = false;
        }
    }

    @Override
    public void putInZcx(TemporaryReqEntity req) {
        LoadingHelper.getInstance().showLoading();

        addSubscribe(mTemporaryModel.putInZcx(req)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        LoadingHelper.getInstance().hideLoading();
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            TemporaryRespEntity resp  = respEntity.getBody();
                            mView.putInZcxSuccess(resp);
                        }else{
                            mView.putInZcxFail();
                        }
                    }
                }, err -> {
                    LoadingHelper.getInstance().hideLoading();
                    mView.putInZcxFail();
                }, R.id.ll_container);

    }

    @Override
    public void employeeLogout() {
        LoadingHelper.getInstance().showLoading();

        addSubscribe(mLoginModel.employeeLogout()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        LoadingHelper.getInstance().hideLoading();
                        mContext.finish();
                    }
                }, err -> {
                    LoadingHelper.getInstance().hideLoading();
                }, R.id.ll_container);

    }
}
