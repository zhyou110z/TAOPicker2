package com.rt.taopicker.main.picker.presenter;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.PickerModel;
import com.rt.taopicker.data.api.entity.GrabOrdersRespEntity;
import com.rt.taopicker.data.api.entity.QueryGrabOrdersStatusRespEntity;
import com.rt.taopicker.main.picker.contract.IPickerWaitContract;
import com.rt.taopicker.util.SingletonHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangzhi on 2018/1/26.
 */

public class PickerWaitPresenter  extends BasePresenter<IPickerWaitContract.IView> implements IPickerWaitContract.IPresenter {

    private PickerModel mPickerModel = SingletonHelper.getInstance(PickerModel.class);

    @Override
    public void start() {
        queryUserOrderState();
    }

    @Override
    public void queryUserOrderState() {
        addSubscribe(mPickerModel.queryGrabOrdersStatus()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            QueryGrabOrdersStatusRespEntity entity = respEntity.getBody();
                            mView.queryGrabOrdersStatusSuccess(entity);
                        } else {
                            mView.queryGrabOrdersStatusFail(respEntity.getErrorCode(), respEntity.getErrorDesc());
                        }
                    }
                }, err -> {
                    mView.queryGrabOrdersStatusError();
                }, R.id.ptrsv_container);

    }

    @Override
    public void grabOrder() {
        addSubscribe(mPickerModel.grabOrders()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            GrabOrdersRespEntity entity = respEntity.getBody();
                            mView.grabOrdersSuccess(entity);
                        } else {
                            mView.grabOrdersFail(respEntity.getErrorCode(), respEntity.getErrorDesc());
                        }
                    }
                }, err -> {
                    mView.grabOrdersError();
                }, R.id.ptrsv_container);
    }


}
