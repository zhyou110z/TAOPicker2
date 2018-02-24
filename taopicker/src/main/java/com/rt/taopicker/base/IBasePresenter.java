package com.rt.taopicker.base;

/**
 * Created by yaoguangyao on 2017/9/1.
 */

public interface IBasePresenter<V extends IBaseView> {
    void attachView(V view);
    void detachView();
    void create();
    void start();
    void needUpdate();


}
