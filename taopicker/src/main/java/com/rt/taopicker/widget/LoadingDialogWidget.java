package com.rt.taopicker.widget;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.rt.taopicker.R;

/**
 * Created by yaoguangyao on 2017/12/6.
 */

public class LoadingDialogWidget extends DialogFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.LoadingDialog); //背景，遮罩要透明
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCancelable(false); //dialog弹出后会点击屏幕或物理返回键，dialog不消失，试验中返回键还是会取消。
        View view = inflater.inflate(R.layout.widget_loading, container, false);
        return view;
    }
}
