package com.rt.taopicker.main.status.presenter;

import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rt.taopicker.R;
import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.StatusModel;
import com.rt.taopicker.data.api.entity.StatusQueryReqEntity;
import com.rt.taopicker.main.status.activity.StatusInfoActivity;
import com.rt.taopicker.main.status.activity.StatusViewActivity;
import com.rt.taopicker.main.status.contract.IStatusViewContract;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.util.SingletonHelper;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chensheng on 2018/1/31.
 */

public class StatusViewPresenter extends BasePresenter<IStatusViewContract.IView> implements IStatusViewContract.IPresenter {

    private StatusModel mStatusModel = SingletonHelper.getInstance(StatusModel.class);


    @Override
    public void start() {
            mView.clearInput();
    }

    @Override
    public void queryStatus(String code) {
        if (StringUtil.isNotBlank(code)) {
            LoadingHelper.getInstance().showLoading();

            StatusQueryReqEntity reqEntity = new StatusQueryReqEntity();
            reqEntity.setNo(code);

            addSubscribe(mStatusModel.statusQuery(reqEntity)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()),
                    respEntity -> {
                        if (mView != null) {
                            LoadingHelper.getInstance().hideLoading();
                            if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                                String infoType = respEntity.getBody().getInfoType();
                                if ("1".equals(infoType)) {//载具
                                    mView.redirectStatusView(code, infoType, respEntity.getBody().getVehicle());
                                } else if ("2".equals(infoType)) {//物流箱
                                    mView.redirectStatusView(code, infoType, respEntity.getBody().getBox());
                                } else if ("3".equals(infoType)) {//道口
                                    mView.redirectStatusView(code, infoType, respEntity.getBody().getStall());
                                } else if ("9".equals(infoType)) {
                                    //未知消息类型
                                    ToastUtil.toast(StringUtil.trim(respEntity.getBody().getMessage()));
                                }
                            } else {
                                ToastUtil.toast(respEntity.getErrorDesc());
                            }
                        }
                    }, null);

        }

    }
}
