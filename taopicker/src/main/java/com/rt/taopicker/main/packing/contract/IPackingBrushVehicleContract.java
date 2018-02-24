package com.rt.taopicker.main.packing.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;
import com.rt.taopicker.data.api.entity.packageScanCodeRespEntity.PackageScanCodeRespEntity;

/**
 * Created by wangzhi on 2018/1/31.
 */

public interface IPackingBrushVehicleContract {
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
         * 刷拣货袋
         * @param vehicleNo
         * @param waveOrderNo
         * @param hasSuspender
         */
        void brushVehicle(String vehicleNo, String waveOrderNo, boolean hasSuspender);
    }
}
