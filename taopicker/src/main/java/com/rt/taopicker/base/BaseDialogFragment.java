package com.rt.taopicker.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rt.taopicker.R;
import com.rt.taopicker.base.exception.BaseException;
import com.rt.taopicker.util.ActivityHelper;
import com.rt.taopicker.util.NetworkUtil;
import com.rt.taopicker.util.ReplaceViewHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yaoguangyao on 2017/9/1.
 */

public abstract class BaseDialogFragment<P extends IBasePresenter> extends DialogFragment
        implements IBaseView {
    protected View mRootView; //复用fragment
    protected P mPresenter;
    private Unbinder mUnbinder;

    public BaseDialogFragment() {
        mPresenter = getPresenter();
        mPresenter.attachView(this);
    }

    public void handleNotification() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = createRootView(inflater, container);
        mUnbinder = ButterKnife.bind(this, mRootView);
        initView(savedInstanceState);
        mPresenter.create();
        return mRootView;
    }


    abstract public View createRootView(LayoutInflater inflater, @Nullable ViewGroup container);

    abstract public void initView(@Nullable Bundle savedInstanceState);

    @Override
    public void onPause() {
        super.onPause();
        mIsVisible = false;
    }

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
        handleNotification();
        mPresenter.start();
    }

    public abstract P getPresenter();

    @Override
    public void showError(BaseException e, Integer errorViewId) {
        if (mRootView != null) {
            if (errorViewId != null) {
                View errorView = mRootView.findViewById(errorViewId);
                if (errorView != null) {
                    ReplaceViewHelper replaceViewHelper = ReplaceViewHelper.getInstance();
                    View replaceView = replaceViewHelper.toReplaceView(errorView, R.layout.base_no_network).getView();
                    if (replaceView != null) {
                        TextView msgTv = (TextView) replaceView.findViewById(R.id.tv_meg);
                        msgTv.setText(e.getMsg());
                        Toast.makeText(this.getContext(), e.getMsg(), Toast.LENGTH_SHORT).show();

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

            Toast.makeText(this.getContext(), e.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void finishView() {
        getActivity().finish();
    }

    public BaseFragment showSubFrament(String code) {
        return null;
    }

    protected Boolean mIsVisible = true;

    /**
     * 使用show和hide来显隐的Fragment
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mIsVisible = !hidden;
        if (mIsVisible) {
            mPresenter.start();
        }
    }

    /**
     * 在ViewPager中的Fragment
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisible = isVisibleToUser;
        if (mIsVisible) {
            mPresenter.start();
        }
    }





}
