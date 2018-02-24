package com.rt.taopicker.main.lack.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;
import com.rt.taopicker.data.api.entity.ConfirmGoodsExceptReqEntity;
import com.rt.taopicker.data.api.entity.ConfirmGoodsExceptRespEntity;
import com.rt.taopicker.data.api.entity.OutStockReqEntity;
import com.rt.taopicker.data.api.entity.OutStockRespEntity;

/**
 * Created by zhouyou on 2018/1/30.
 */

public interface IOutStockContract {

    interface IView extends IBaseView {
        void loadDataSuccess(OutStockRespEntity resp);
        void loadDataFail();

        void confirmGoodsExceptSuccess(String goodNo);
        void confirmGoodsExceptFail();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void outOfStock(OutStockReqEntity req);
        void confirmGoodsExcept(ConfirmGoodsExceptReqEntity req);

    }
}
