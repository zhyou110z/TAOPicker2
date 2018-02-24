package com.rt.taopicker.base;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.rt.taopicker.base.exception.BaseException;
import com.rt.taopicker.config.ExceptionEnum;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.util.LogServiceHelper;
import com.rt.taopicker.util.LoginHelper;
import com.rt.taopicker.util.ReplaceViewHelper;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by yaoguangyao on 2017/9/1.
 */
public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {
    private CompositeDisposable mCompositeDisposable;
    protected V mView;
    protected Boolean mNeedUpdate = true;

    @Override
    public void needUpdate() {
        mNeedUpdate = true;
    }

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        unSubscribe();
    }

    @Override
    public void create() {

    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    protected Consumer<? super Throwable> errors(Integer errorViewId) {
        return e -> {
            BaseException baseException = null;
            if (e instanceof ConnectException) {
                baseException = new BaseException(ExceptionEnum.SERVER_NO_CONNET, e);
            } else if (e instanceof SocketTimeoutException) {
                baseException = new BaseException(ExceptionEnum.SERVER_TIME_OUT, e);
            } else if (e instanceof JsonParseException
                    || e instanceof JsonIOException
                    || e instanceof JsonSyntaxException) {
                baseException = new BaseException(ExceptionEnum.JSON_PARSE_ERROR, e);
            } else if (e instanceof BaseException) {
                baseException = (BaseException) e;
            } else {
                baseException = new BaseException(ExceptionEnum.UNKOWN, e);
            }
            LogServiceHelper.getInstance().error("BasePresenter errors", e);
            if (mView != null) {
                mView.showError(baseException, errorViewId);
            }
        };
    }

    protected <T> void addSubscribe(Observable<T> observable,
                                    Consumer<T> onNext, Integer errorViewId, boolean isCheckLogin) {
        this.addSubscribe(observable, onNext, null, errorViewId, isCheckLogin);
    }


    protected <T> void addSubscribe(Observable<T> observable,
                                    Consumer<T> onNext, Integer errorViewId) {
        this.addSubscribe(observable, onNext, null, errorViewId, true);
    }

    protected <T> void addSubscribe(
            Observable<T> observable,
            Consumer<T> onNext,
            Consumer<? super Throwable> onError,
            Integer errorViewId
    ) {
        this.addSubscribe(observable, onNext, onError, errorViewId, true);
    }

    protected <T> void addSubscribe(
            Observable<T> observable,
            Consumer<T> onNext,
            Consumer<? super Throwable> onError,
            Integer errorViewId,
            boolean isCheckLogin
    ) {
        Disposable disposable = observable.subscribe(obj -> {
            boolean executeNext = true;
            if (isCheckLogin) {
                executeNext = LoginHelper.getInstance().checkLoginStatu(obj);
            }

            if(executeNext){
                onNext.accept(obj);
            }
            ReplaceViewHelper.getInstance().removeView();
        }, err -> {
            LoadingHelper.getInstance().hideLoading();
            ReplaceViewHelper.getInstance().removeView();
            errors(errorViewId).accept(err);
            if (onError != null) {
                onError.accept(err);
            }
        });

        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.add(disposable);
        }
    }
}
