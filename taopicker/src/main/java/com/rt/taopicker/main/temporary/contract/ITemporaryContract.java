package com.rt.taopicker.main.temporary.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;
import com.rt.taopicker.data.api.entity.TemporaryReqEntity;
import com.rt.taopicker.data.api.entity.TemporaryRespEntity;

/**
 * Created by zhouyou on 2018/2/01.
 */

public interface ITemporaryContract {

    interface IView extends IBaseView {
        void putInZcxSuccess(TemporaryRespEntity resp);
        void putInZcxFail();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void putInZcx(TemporaryReqEntity req);
        void employeeLogout();
    }
}
