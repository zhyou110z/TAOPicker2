package com.rt.taopicker.main.home.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.Menu;

import java.util.List;

/**
 * Created by yaoguangyao on 2017/12/18.
 */

public interface IHomeContract {
    interface IView extends IBaseView {

        void showPage(List<Menu> menus);

        void showUser(String realname, String storeName);
    }

    interface IPresenter extends IBasePresenter<IView> {

    }
}
