package com.rt.taopicker.main.home.presenter;

import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.Menu;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.PdaLoginRespEntity;
import com.rt.taopicker.main.home.contract.IHomeContract;
import com.rt.taopicker.util.PreferencesUtil;
import com.rt.taopicker.util.CheckVersionHelper;
import com.rt.taopicker.util.UserInfoHelper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yaoguangyao on 2017/12/18.
 */

public class HomePresenter extends BasePresenter<IHomeContract.IView> implements IHomeContract.IPresenter {

    @Override
    public void create() {

    }

    @Override
    public void start() {
        loadPage();
        //版本检测
        CheckVersionHelper.getInstance().newVersionCheck(false);
        //回到home，清空员工信息
        UserInfoHelper.setCurrentEmployeeInfo(null);
    }

    private void loadPage() {
        PdaLoginRespEntity loginRespEntity = PreferencesUtil.getUserInfo();
        if (loginRespEntity != null) {
            List<Menu> menus = loginRespEntity.getMenuList();
            mView.showPage(menus);

            mView.showUser(loginRespEntity.getRealname(), loginRespEntity.getStoreName());
        }
    }


}
