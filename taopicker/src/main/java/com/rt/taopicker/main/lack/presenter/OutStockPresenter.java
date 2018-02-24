package com.rt.taopicker.main.lack.presenter;

import android.app.Activity;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.OutStockModel;
import com.rt.taopicker.data.api.entity.ConfirmGoodsExceptReqEntity;
import com.rt.taopicker.data.api.entity.ConfirmGoodsExceptRespEntity;
import com.rt.taopicker.data.api.entity.OutStockReqEntity;
import com.rt.taopicker.data.api.entity.OutStockRespEntity;
import com.rt.taopicker.main.lack.contract.IOutStockContract;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.util.SingletonHelper;
import com.rt.taopicker.util.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhouyou on 2018/1/30.
 */

public class OutStockPresenter extends BasePresenter<IOutStockContract.IView> implements IOutStockContract.IPresenter {

    private OutStockModel moutStockModel = SingletonHelper.getInstance(OutStockModel.class);
    private Activity mContext;

    public OutStockPresenter(Activity context){
        this.mContext = context;
    }

    @Override
    public void start() {
        if (mNeedUpdate) {
            mNeedUpdate = false;
        }
    }

    @Override
    public void outOfStock(OutStockReqEntity req) {
        LoadingHelper.getInstance().showLoading();

        addSubscribe(moutStockModel.outOfStock(req)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        LoadingHelper.getInstance().hideLoading();
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            OutStockRespEntity resp  = respEntity.getBody();
                            mView.loadDataSuccess(resp);
                        }else{
                            ToastUtil.toast(respEntity.getErrorDesc());
                            mView.loadDataFail();
                        }
                    }
                }, err -> {
                    mView.loadDataFail();
                    LoadingHelper.getInstance().hideLoading();
                }, R.id.ll_container);

    }

    @Override
    public void confirmGoodsExcept(ConfirmGoodsExceptReqEntity req) {
        LoadingHelper.getInstance().showLoading();

        addSubscribe(moutStockModel.confirmGoodsExcept(req)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        LoadingHelper.getInstance().hideLoading();
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            ConfirmGoodsExceptRespEntity resp  = respEntity.getBody();
                            mView.confirmGoodsExceptSuccess(req.getProductNo());
                        }
                        else{
                          ToastUtil.toast(respEntity.getErrorDesc());
                            mView.confirmGoodsExceptFail();
                        }
                    }
                }, err -> {
                    LoadingHelper.getInstance().hideLoading();
                },null);

    }
}
