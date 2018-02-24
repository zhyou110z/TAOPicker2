package com.rt.taopicker.main.home.presenter;

import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.LoginModel;
import com.rt.taopicker.data.api.entity.EmployeeLoginReqEntity;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.PdaLoginRespEntity;
import com.rt.taopicker.data.api.entity.queryPickAreaListRespEntity.PickArea;
import com.rt.taopicker.main.home.contract.IEmployeeLoginContract;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.util.LoginHelper;
import com.rt.taopicker.util.PreferencesUtil;
import com.rt.taopicker.util.SingletonHelper;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.ToastUtil;
import com.rt.taopicker.util.UserInfoHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yaoguangyao on 2018/1/25.
 */

public class EmployeeLoginPresenter extends BasePresenter<IEmployeeLoginContract.IView> implements IEmployeeLoginContract.IPresenter {
    private LoginModel mLoginModel = SingletonHelper.getInstance(LoginModel.class);

    private String mMenuCode;
    private String mMenuName;

    private String mAreaCode;

    @Override
    public void start() {
        loadPage();
    }

    @Override
    public void init(String menuCode, String menuName) {
        mMenuCode = menuCode;
        mMenuName = menuName;
    }

    @Override
    public void checkPickArea(String areaCode, String areaName) {
        mView.showAreaName(areaName);
        mAreaCode = areaCode;
    }

    @Override
    public void submit() {
        LoadingHelper.getInstance().showLoading();
        EmployeeLoginReqEntity employeeLoginReqEntity = new EmployeeLoginReqEntity();
        employeeLoginReqEntity.setEmployeeNo(mView.getEmployeeNo());
        employeeLoginReqEntity.setMenuNo(mMenuCode);
        employeeLoginReqEntity.setPickAreaNo(mAreaCode);
        addSubscribe(mLoginModel.employeeLogin(employeeLoginReqEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            UserInfoHelper.setCurrentEmployeeInfo(respEntity.getBody());
                            mView.toMenu(mMenuCode, respEntity.getBody());
                        } else {
                            ToastUtil.toast(respEntity.getErrorDesc());
                        }
                        LoadingHelper.getInstance().hideLoading();
                    }
                }, null);
    }

    private void loadPage() {
        mView.showTitle(mMenuName);

        PdaLoginRespEntity loginRespEntity = PreferencesUtil.getUserInfo();
        if (loginRespEntity != null) {
            mView.showUser(loginRespEntity.getRealname(), loginRespEntity.getStoreName());
        }

        /**
         * 如果是拣货，则需要
         */
        if (StringUtil.isNotBlank(mMenuCode) && mMenuCode.equals(Constant.MenuCode.PICKING)) {
            //拣货需要展示优化菜单
            mView.showPickArea();
            LoadingHelper.getInstance().showLoading();
            addSubscribe(mLoginModel.queryPickAreaList()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()),
                    respEntity -> {
                        if (mView != null) {
                            if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                                mView.showPickAreas(respEntity.getBody());

                                //默认展示第一个拣货区名字
                                if (respEntity.getBody() != null
                                        && respEntity.getBody().getPickAreaList() != null
                                        && respEntity.getBody().getPickAreaList().size() > 0) {
                                    PickArea pickArea = respEntity.getBody().getPickAreaList().get(0);
                                    mView.showAreaName(pickArea.getPickAreaName());
                                    mAreaCode = pickArea.getPickAreaNo();
                                }
                            } else {
                                ToastUtil.toast(respEntity.getErrorDesc());
                            }
                            LoadingHelper.getInstance().hideLoading();
                        }
                    }, null);
        } else {
            mView.hidePickArea();
        }
    }
}
