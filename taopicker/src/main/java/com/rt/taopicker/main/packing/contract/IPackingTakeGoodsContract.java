package com.rt.taopicker.main.packing.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;
import com.rt.taopicker.data.api.entity.TakeOutFromZcxRespEntity;
import com.rt.taopicker.data.api.entity.packageScanCodeRespEntity.PackageScanCodeRespEntity;

/**
 * Created by wangzhi on 2018/1/30.
 */

public interface IPackingTakeGoodsContract {
    interface IView extends IBaseView {
        /**
         * 清理数据
         */
        void clearData();

        /**
         * 刷新暂存箱列表
         * @param entity
         */
        void refreshZcxListView(TakeOutFromZcxRespEntity entity);
    }

    interface IPresenter extends IBasePresenter<IView> {
        /**
         * 刷暂存箱取货
         * @param zcxNo
         * @param mPackageScanCodeRespEntity
         * @param hasSuspender
         */
        void takeGoods(String zcxNo, PackageScanCodeRespEntity mPackageScanCodeRespEntity, boolean hasSuspender);
    }
}
