package com.rt.taopicker.main.home.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.data.api.entity.queryPickAreaListRespEntity.PickArea;
import com.rt.taopicker.data.api.entity.queryPickAreaListRespEntity.QueryPickAreaListRespEntity;

/**
 * Created by yaoguangyao on 2018/1/25.
 */

public interface IEmployeeLoginContract {
    interface IView extends IBaseView {

        void showTitle(String menuName);

        void showPickAreas(QueryPickAreaListRespEntity body);

        void showPickArea();

        void hidePickArea();


        void showAreaName(String pickAreaName);

        String getEmployeeNo();

        void toMenu(String menuCode, EmployeeLoginRespEntity body);

        void showUser(String realname, String storeName);
    }

    interface IPresenter extends IBasePresenter<IView> {

        void init(String menuCode, String menuName);

        void checkPickArea(String areaCode, String areaName);

        void submit();
    }
}
