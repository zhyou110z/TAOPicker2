package com.rt.taopicker.main.login.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.Store;
import com.rt.taopicker.main.login.vo.LoginInfoVo;

import java.util.List;

/**
 * Created by yaoguangyao on 2017/12/4.
 */

public interface ILoginContract {
    interface IView extends IBaseView {
        /**
         * 获取登录信息
         * @return
         */
        LoginInfoVo getLoginInfoVo();

        /**
         * 登录成功到首页
         */
        void toHome();

        void clearLoginInfo();

        void showStoreListDialog(List<Store> storeList);
    }

    interface IPresenter extends IBasePresenter<IView> {

        /**
         * 提交登录
         */
        void login();

        void chooseStore(String storeCode, String storeName);
    }
}
