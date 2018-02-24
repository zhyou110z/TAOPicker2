package com.rt.taopicker.main.status.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.BoxInfoEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.StallNoInfoEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.VehicleInfoEntity;
import com.rt.taopicker.main.status.adapter.StatusAdapter;

/**
 * Created by chensheng on 2018/1/31.
 */

public interface IStatusInfoContract {

    interface IView extends IBaseView {
        void showStatsInfo();

        void hideStatsInfo();
    }

    interface IPresenter extends IBasePresenter<IStatusInfoContract.IView> {

        void init(String infoType, VehicleInfoEntity vehicleInfoEntity, BoxInfoEntity boxInfoEntity, StallNoInfoEntity stallNoInfoEntity, StatusAdapter statusAdapter);

        void showLastStatus();


    }

}
