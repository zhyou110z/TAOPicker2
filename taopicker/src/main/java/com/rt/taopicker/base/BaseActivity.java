package com.rt.taopicker.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rt.taopicker.R;
import com.rt.taopicker.base.exception.BaseException;
import com.rt.taopicker.main.login.activity.LoginActivity;
import com.rt.taopicker.util.ActivityHelper;
import com.rt.taopicker.util.NetworkUtil;
import com.rt.taopicker.util.ReplaceViewHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yaoguangyao on 2017/9/1.
 */

public abstract class BaseActivity<P extends IBasePresenter> extends FragmentActivity
        implements IBaseView {
    protected P mPresenter;
    private Unbinder mUnbinder;

    protected Boolean mIsVisible = false;

    public BaseActivity() {
        mPresenter = getPresenter();
        if(mPresenter != null){
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleNotification();
    }

    protected void handleNotification() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootViewResId());
        mUnbinder = ButterKnife.bind(this);
        initView(savedInstanceState);
        if(mPresenter != null){
            mPresenter.create();
        }
        handleNotification();
    }

    abstract public int getRootViewResId();

    abstract public void initView(@Nullable Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }

        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsVisible = true;
        if (mPresenter != null) {
            mPresenter.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsVisible = false;
    }

    public abstract P getPresenter();

    @Override
    public void showError(BaseException e, Integer errorViewId) {
        if (errorViewId != null) {
            View errorView = this.findViewById(errorViewId);
            if (errorView != null) {
                ReplaceViewHelper replaceViewHelper = ReplaceViewHelper.getInstance();
                View replaceView = replaceViewHelper.toReplaceView(errorView, R.layout.base_no_network).getView();
                if (replaceView != null) {
                    TextView msgTv = (TextView) replaceView.findViewById(R.id.tv_meg);
                    msgTv.setText(e.getMsg());
                    Toast.makeText(this, e.getMsg(), Toast.LENGTH_SHORT).show();

                    TextView refreshTv = replaceView.findViewById(R.id.tv_refresh);
                    refreshTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                        replaceViewHelper.removeView();
                            mPresenter.needUpdate();
                            mPresenter.start();
                        }
                    });

                    TextView healthCheckTv = replaceView.findViewById(R.id.tv_health_check);
                    healthCheckTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            NetworkUtil.showPopwindow(ActivityHelper.getCurrentActivity(), R.id.tv_refresh);
                        }
                    });
                }

                return;
            }
        }

        Toast.makeText(this, e.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    public BaseFragment showSubFrament(String code) {
        return null;
    }

    public void needUpdate() {
        mPresenter.needUpdate();
    }

    public Boolean getVisible() {
        return mIsVisible;
    }

    public void setVisible(Boolean visible) {
        mIsVisible = visible;
    }
}
