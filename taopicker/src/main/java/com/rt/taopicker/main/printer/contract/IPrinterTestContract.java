package com.rt.taopicker.main.printer.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;

/**
 * Created by wangzhi on 2018/2/2.
 */

public interface IPrinterTestContract {
    interface IView extends IBaseView {

    }

    interface IPresenter extends IBasePresenter<IView> {
        /**
         * 打印
         * @param no
         */
        void printOrder(String no);
    }
}
