package com.rt.taopicker.main.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.base.SwitchAPILevelDialog;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.Store;
import com.rt.taopicker.main.home.activity.HomeActivity;
import com.rt.taopicker.main.login.contract.ILoginContract;
import com.rt.taopicker.main.login.fragment.StoreListDialogFragment;
import com.rt.taopicker.main.login.listener.IStoreListDialogListener;
import com.rt.taopicker.main.login.presenter.LoginPresenter;
import com.rt.taopicker.main.login.vo.LoginInfoVo;
import com.rt.taopicker.main.printer.activity.PrinterTestActivity;
import com.rt.taopicker.util.ActivityHelper;
import com.rt.taopicker.util.KbUtil;
import com.rt.taopicker.util.NetworkUtil;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.UserInfoHelper;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yaoguangyao on 2017/12/4.
 */

public class LoginActivity extends BaseActivity<ILoginContract.IPresenter> implements ILoginContract.IView, View.OnClickListener, TextWatcher, View.OnTouchListener, IStoreListDialogListener {

    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdt;

    @BindView(R.id.edt_user_name)
    protected EditText mUserNameEdt;

    @BindView(R.id.edt_pwd)
    protected EditText mPwdEdt;

    @BindView(R.id.ll_login)
    protected LinearLayout mLoginLl;

    @BindView(R.id.tv_health_check)
    protected TextView mHealthCheckTv;

    @Override
    public int getRootViewResId() {
        return R.layout.login_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isTaskRoot()){
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action != null && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mUserNameEdt.addTextChangedListener(this);
        mPwdEdt.addTextChangedListener(this);
        mLoginLl.setOnClickListener(this);
        mHeadTitleWdt.init(new HeadTitleVo("登录", false, Constant.HeadTitleFuncType.NONE));
        mHeadTitleWdt.setOnTouchListener(this);

        mHealthCheckTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtil.showPopwindow(ActivityHelper.getCurrentActivity(), R.id.ll_login);
            }
        });
    }

    @Override
    public ILoginContract.IPresenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_login:
                mPresenter.login();
                break;
        }
    }

    @Override
    public LoginInfoVo getLoginInfoVo() {
        String userName = mUserNameEdt.getText().toString();
        String pwd = mPwdEdt.getText().toString();

        LoginInfoVo loginInfoVo = new LoginInfoVo();
        loginInfoVo.setUserName(userName);
        loginInfoVo.setPwd(pwd);

        return loginInfoVo;
    }

    @Override
    public void toHome() {
        Intent intent = HomeActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void clearLoginInfo() {
        //门店号要保留
        mUserNameEdt.setText("");
        mPwdEdt.setText("");
    }

    @Override
    public void showStoreListDialog(List<Store> storeList) {
        StoreListDialogFragment fragment = StoreListDialogFragment.newInstance((ArrayList<Store>) storeList);
        fragment.setListener(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragment.show(ft, "StoreListDialog");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        clearLoginInfo();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        LoginInfoVo loginInfoVo = getLoginInfoVo();
        if (loginInfoVo != null
                && StringUtil.isNotBlank(loginInfoVo.getUserName())
                && StringUtil.isNotBlank(loginInfoVo.getPwd())) {
            mLoginLl.setBackgroundResource(R.drawable.bg_login_btn_enable);
            mLoginLl.setOnClickListener(this);
        } else {
            mLoginLl.setBackgroundResource(R.drawable.bg_login_btn_disable);
            mLoginLl.setOnClickListener(null);

        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public static Intent newIntent(Context context) {
        //清空用户信息
        UserInfoHelper.setCurrentUserInfo(null);
        Intent intent = new Intent(context, LoginActivity.class);
        //启动activity设置singletask，切换到其他页面，点home，退回后台后，每次点击图标会重启会login
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                timer.cancel();
                timer.start();
                break;
            case MotionEvent.ACTION_UP:
                timer.cancel();
                break;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }

    /**
     * 按压5s弹出切环境
     */
    private CountDownTimer timer = new CountDownTimer(5000, 1000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            SwitchAPILevelDialog.showDialog(LoginActivity.this);
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KbUtil.isShouldHideInput(v, ev)) {
                KbUtil.hideKb(this);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    @Override
    public void chooseStore(String storeCode, String storeName) {
        mPresenter.chooseStore(storeCode, storeName);
    }
}
