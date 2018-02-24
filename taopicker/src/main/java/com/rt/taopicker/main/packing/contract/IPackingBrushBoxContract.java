package com.rt.taopicker.main.packing.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;
import com.rt.taopicker.data.api.entity.packageScanCodeRespEntity.PackageScanCodeRespEntity;

/**
 * Created by wangzhi on 2018/1/31.
 */

public interface IPackingBrushBoxContract {
    interface IView extends IBaseView {
        /**
         * 清理数据
         */
        void clearData();

        /**
         * 刷新列表
         * @param entity
         */
        void refreshListView(PackageScanCodeRespEntity entity);
    }

    interface IPresenter extends IBasePresenter<IView> {
        /**
         * 刷物流箱
         * @param boxNo
         * @param waveOrderNo
         */
        void brushBox(String boxNo, String waveOrderNo);

        /**
         * 清空物流箱
         * @param waveOrderNo
         */
        void clearBox(String waveOrderNo);

        /**
         * 包装完成
         * @param waveOrderNo
         * @param mHasSuspender 是否有吊挂
         */
        void packFinish(String waveOrderNo, boolean mHasSuspender);

        /**
         * 打印小票
         * @param no
         */
        void printOrder(String no);

        /**
         * 补打小票
         * @param no
         */
        void rePrintOrder(String no);

        /**
         * 波次单打印完成
         */
        void printFinish();
    }
}
