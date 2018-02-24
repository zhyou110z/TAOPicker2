package com.rt.taopicker.base;


import android.support.v4.app.Fragment;

import com.rt.taopicker.base.exception.BaseException;

/**
 * Created by yaoguangyao on 2017/9/1.
 */

public interface IBaseView {
    void showError(BaseException e, Integer errorViewId);

    void finishView();

    BaseFragment showSubFrament(String code);
}