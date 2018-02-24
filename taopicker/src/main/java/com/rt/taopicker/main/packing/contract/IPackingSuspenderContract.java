package com.rt.taopicker.main.packing.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;

/**
 * Created by wangzhi on 2018/1/29.
 */

public interface IPackingSuspenderContract {
    interface IView extends IBaseView {
        /**
         * 清空数据
         */
        void clearData();

        /**
         * 检查蓝牙
         */
        void checkBluetooth();
    }

    interface IPresenter extends IBasePresenter<IView> {
        /**
         * 刷入道口号
         * @param stallNo
         */
        void brushStallNo(String stallNo);

        /**
         * 查询是否存在未完成的包装作业
         */
        void queryIsExistUnCompletePackage();
    }
}
