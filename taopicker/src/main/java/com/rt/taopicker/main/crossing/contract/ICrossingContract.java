package com.rt.taopicker.main.crossing.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;

/**
 * Created by chensheng on 2018/1/30.
 */

public interface ICrossingContract {

    interface IView extends IBaseView {
        /**
         * 清空波次号
         */
        void clearWaveNumber();

        /**
         * 清空道口输入框
         */
        void clearCrossingNumber();

        /**
         * 清空所有
         */
        void clearAllInput();

        void setWaveNumber(String waveNumber);

        void crossingFocus();

        void vehicleFocus();

    }

    interface IPresenter extends IBasePresenter<ICrossingContract.IView> {
        /**
         * 根据道口查询波次号
         */
        void httpQueryWaveOrderNoByStallNo(String crossingNumber);

        /**
         * 人工刷下道口
         */
        void httpManualPutToStall(String waveNumber, String vehicle);
    }
}
