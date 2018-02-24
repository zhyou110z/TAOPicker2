package com.rt.taopicker.util;

import android.view.View;

import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.widget.NoticeDialogWidget;

/**
 * Created by wangzhi on 2018/1/26.
 */

public class DialogHelper {
    public static DialogHelper instance = null;

    private DialogHelper(){

    }

    public static DialogHelper getInstance(){
        if(instance == null){
            instance = new DialogHelper();
        }
        return instance;
    }

    public NoticeDialogWidget showDialog(View customView, boolean negative, NoticeDialogWidget.DialogListener positionDialogListener, NoticeDialogWidget.DialogListener negativeDialogListener) {
        NoticeDialogWidget newFragment = new NoticeDialogWidget();
        newFragment.setCancelable(false);
        newFragment.setPositiveListener(positionDialogListener);
        newFragment.setNegativeListener(negativeDialogListener);
        newFragment.setView(customView);
        newFragment.setNegative(negative);
        newFragment.show(((BaseActivity)ActivityHelper.getCurrentActivity()).getSupportFragmentManager(), "NoticeDialogFragment");
        return newFragment;
    }

    public NoticeDialogWidget showDialog(View customView, boolean negative, String positionBtnText, String negativeBtnText, NoticeDialogWidget.DialogListener positionDialogListener, NoticeDialogWidget.DialogListener negativeDialogListener) {
        NoticeDialogWidget newFragment = new NoticeDialogWidget();
        newFragment.setCancelable(false);
        newFragment.setPositiveListener(positionDialogListener);
        newFragment.setNegativeListener(negativeDialogListener);
        newFragment.setPositiveButtonText(positionBtnText);
        newFragment.setNegativeButtonText(negativeBtnText);
        newFragment.setView(customView);
        newFragment.setNegative(negative);
        newFragment.show(((BaseActivity)ActivityHelper.getCurrentActivity()).getSupportFragmentManager(), "NoticeDialogFragment");
        return newFragment;
    }

    public NoticeDialogWidget showDialog(String message, boolean negative, NoticeDialogWidget.DialogListener positionDialogListener, NoticeDialogWidget.DialogListener negativeDialogListener) {
        NoticeDialogWidget newFragment = new NoticeDialogWidget();
        newFragment.setCancelable(false);
        newFragment.setPositiveListener(positionDialogListener);
        newFragment.setNegativeListener(negativeDialogListener);
        newFragment.setContent(message);
        newFragment.setNegative(negative);
        newFragment.show(((BaseActivity)ActivityHelper.getCurrentActivity()).getSupportFragmentManager(), "NoticeDialogFragment");
        return newFragment;
    }

    public NoticeDialogWidget showDialog(String message, boolean negative, String positionBtnText, String negativeBtnText, NoticeDialogWidget.DialogListener positionDialogListener, NoticeDialogWidget.DialogListener negativeDialogListener) {
        NoticeDialogWidget newFragment = new NoticeDialogWidget();
        newFragment.setCancelable(false);
        newFragment.setPositiveListener(positionDialogListener);
        newFragment.setNegativeListener(negativeDialogListener);
        newFragment.setPositiveButtonText(positionBtnText);
        newFragment.setNegativeButtonText(negativeBtnText);
        newFragment.setContent(message);
        newFragment.setNegative(negative);
        newFragment.show(((BaseActivity)ActivityHelper.getCurrentActivity()).getSupportFragmentManager(), "NoticeDialogFragment");
        return newFragment;
    }

    public NoticeDialogWidget showDialog(String message, String positionBtnText, NoticeDialogWidget.DialogListener positionDialogListener, boolean autoClose) {
        NoticeDialogWidget newFragment = new NoticeDialogWidget();
        newFragment.setCancelable(false);
        newFragment.setAutoClose(autoClose);
        newFragment.setPositiveListener(positionDialogListener);
        newFragment.setPositiveButtonText(positionBtnText);
        newFragment.setContent(message);
        newFragment.show(((BaseActivity)ActivityHelper.getCurrentActivity()).getSupportFragmentManager(), "NoticeDialogFragment");
        return newFragment;
    }

    public NoticeDialogWidget showDialog(String message, String positionBtnText, NoticeDialogWidget.DialogListener positionDialogListener) {
        NoticeDialogWidget newFragment = new NoticeDialogWidget();
        newFragment.setCancelable(false);
        newFragment.setPositiveListener(positionDialogListener);
        newFragment.setPositiveButtonText(positionBtnText);
        newFragment.setContent(message);
        newFragment.show(((BaseActivity)ActivityHelper.getCurrentActivity()).getSupportFragmentManager(), "NoticeDialogFragment");
        return newFragment;
    }


    public NoticeDialogWidget showDialog(String message, NoticeDialogWidget.DialogListener positionDialogListener) {
        return showDialog(message, false, positionDialogListener, null);
    }

    public NoticeDialogWidget showDialog(String message) {
        return showDialog(message, null);
    }


}
