package com.rt.taopicker.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 处理请求事务(区别于BasePresenter非继承使用时，可以用在埋点，后台服务等无视图交互的功能中)
 * Created by zhouyou on 2017/9/11.
 */

public class BaseCompositeDisposable {

    private static  BaseCompositeDisposable mBaseCompositeDisposable;
    private CompositeDisposable mCompositeDisposable;

    public synchronized static  BaseCompositeDisposable getInstance() {
        if (mBaseCompositeDisposable == null) {
            mBaseCompositeDisposable = new BaseCompositeDisposable();
        }
        return mBaseCompositeDisposable;
    }


    public void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    public void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.add(disposable);
        }
    }
}
