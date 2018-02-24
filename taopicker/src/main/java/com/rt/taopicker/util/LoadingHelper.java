package com.rt.taopicker.util;

import android.app.Fragment;
import android.app.FragmentManager;

import com.rt.taopicker.base.BaseApplication;
import com.rt.taopicker.widget.LoadingDialogWidget;

/**
 * 加载提示
 * <p>
 */
public class LoadingHelper {
    private static LoadingHelper sInstance = new LoadingHelper();
    private static final String LOADING_DIALOG_TAG = "LoadingDialogWidget";
    private LoadingDialogWidget mLoadingDialogWidget;
    private LoadingHelper() {}

    public static LoadingHelper getInstance() {
        return sInstance;
    }

    public static boolean isAdd = false;

    /**
     * 加载loading
     */
    public synchronized void showLoading() {
        try {
            FragmentManager fm = ActivityHelper.getCurrentActivity().getFragmentManager();
            //判断是否已经存在loading，没有则添加
            if (mLoadingDialogWidget == null) {
                mLoadingDialogWidget = new LoadingDialogWidget();
                mLoadingDialogWidget.setCancelable(false);
            }

            if (!mLoadingDialogWidget.isAdded() && !mLoadingDialogWidget.isVisible()
                    && !mLoadingDialogWidget.isRemoving()) {
                if (isAdd) {
                    hideLoading();
                }

                mLoadingDialogWidget.show(fm, LOADING_DIALOG_TAG);
                isAdd = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏loading
     */
    public void hideLoading() {
        if (mLoadingDialogWidget != null) {
            mLoadingDialogWidget.dismissAllowingStateLoss();
            isAdd = false;
        }
    }
}
