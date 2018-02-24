package com.rt.taopicker.main.packing.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;
import com.rt.taopicker.data.api.entity.GrabOrdersRespEntity;
import com.rt.taopicker.data.api.entity.QueryGrabOrdersStatusRespEntity;
import com.rt.taopicker.data.api.entity.QueryPackageWaveOrderRespEntity;
import com.rt.taopicker.data.api.entity.QueryTaoPackageStatusRespEntity;
import com.rt.taopicker.main.picker.contract.IPickerWaitContract;

/**
 * Created by wangzhi on 2018/1/31.
 */

public interface IPackingWaitingContract {
    interface IView extends IBaseView {
        /**
         * 包装状态查询成功回调
         * @param entity
         */
        void queryTaoPackageStatusSuccess(QueryTaoPackageStatusRespEntity entity);

        /**
         * 修改页面抢单状态
         * @param flag
         */
        void changeGrabOrderStatus(boolean flag, QueryTaoPackageStatusRespEntity entity);

        /**
         * 包装状态查询失败回调
         */
        void queryTaoPackageStatusFail();

        /**
         * 网络异常
         */
        void netWorkException();

        /**
         * 抢单成功回调
         * @param entity
         */
        void grabOrdersSuccess(QueryPackageWaveOrderRespEntity entity);

        /**
         * 抢单异常
         */
        void grabOrdersException();

    }

    interface IPresenter extends IBasePresenter<IView> {
        /**
         * 包装状态查询
         */
        void queryTaoPackageStatus();

        /**
         * 查询包装波次单信息
         * @param waveOrderNo
         */
        void queryPackageWaveOrder(String waveOrderNo);

        /**
         * 抢单
         */
        void grabPackageOrder();
    }
}
