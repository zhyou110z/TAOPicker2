package com.rt.taopicker.main.picker.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;
import com.rt.taopicker.data.api.entity.GrabOrdersRespEntity;
import com.rt.taopicker.data.api.entity.QueryGrabOrdersStatusRespEntity;
import com.rt.taopicker.main.login.contract.ILoginContract;

/**
 * Created by wangzhi on 2018/1/26.
 */

public interface IPickerWaitContract {
    interface IView extends IBaseView {
        void queryGrabOrdersStatusSuccess(QueryGrabOrdersStatusRespEntity entity);

        void queryGrabOrdersStatusFail(int errorCode, String message);

        void queryGrabOrdersStatusError();

        void grabOrdersSuccess(GrabOrdersRespEntity entity);

        void grabOrdersFail(int errorCode, String message);

        void grabOrdersError();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void queryUserOrderState();

        void grabOrder();
    }
}
