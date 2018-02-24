package com.rt.taopicker.main.box.presenter;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.BoxModel;
import com.rt.taopicker.data.api.entity.SetBoxRecyclingReqEntity;
import com.rt.taopicker.main.box.contract.ILogisticsBoxContract;
import com.rt.taopicker.util.DialogHelper;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.util.SingletonHelper;
import com.rt.taopicker.util.StringUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chensheng on 2018/1/30.
 */

public class LogisticsBoxPresenter extends BasePresenter<ILogisticsBoxContract.IView> implements ILogisticsBoxContract.IPresenter {

    private BoxModel mBoxModel = SingletonHelper.getInstance(BoxModel.class);

    @Override
    public void start() {

    }

    @Override
    public void httpSetBoxRecycling(String boxNo) {
        if (StringUtil.isBlank(boxNo)) {
            mView.hideKb();
            return;
        }

        LoadingHelper.getInstance().showLoading();
        SetBoxRecyclingReqEntity reqEntity = new SetBoxRecyclingReqEntity();
        reqEntity.setBoxNo(boxNo);
        addSubscribe(mBoxModel.setBoxRecycling(reqEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        LoadingHelper.getInstance().hideLoading();
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            mView.doFinish(boxNo);
                        } else {
                            DialogHelper.getInstance().showDialog(respEntity.getErrorDesc(), () -> {
                                mView.clearInput();
                            });
                        }
                    }
                }, null);
    }
}
