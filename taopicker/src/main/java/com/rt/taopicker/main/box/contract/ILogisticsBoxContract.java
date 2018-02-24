package com.rt.taopicker.main.box.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;

/**
 * Created by chensheng on 2018/1/30.
 */

public interface ILogisticsBoxContract {

    interface IView extends IBaseView {
        void hideKb();

        void clearInput();

        void doFinish(String boxNo);
    }

    interface IPresenter extends IBasePresenter<ILogisticsBoxContract.IView> {
        void httpSetBoxRecycling(String boxNo);
    }

}
