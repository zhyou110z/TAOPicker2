package com.rt.taopicker.main.status.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;

import java.io.Serializable;

/**
 * Created by chensheng on 2018/1/31.
 */

public interface IStatusViewContract {

    interface IView extends IBaseView {

        void redirectStatusView(String barCode, String infoType, Serializable infoModel);

        void clearInput();
    }

    interface IPresenter extends IBasePresenter<IStatusViewContract.IView> {

        /**
         * 状态查询
         */
        void queryStatus(String code);
    }

}
