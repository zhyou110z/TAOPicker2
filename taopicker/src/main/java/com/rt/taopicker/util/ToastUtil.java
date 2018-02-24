package com.rt.taopicker.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rt.taopicker.base.BaseApplication;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 提示框
 * Created by yaoguangyao on 2017/12/5.
 */
public class ToastUtil {

    public static void toast(String msg, int induration) {
        Toast.makeText(BaseApplication.sContext, msg, induration).show();
    }

    public static void toast(String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 自定义显示
     * @param mActivity
     * @param mContext
     * @param msg 消息内容
     * @param cnt 显示时长
     */
    public static void showToast(Activity mActivity, Context mContext, String msg, int cnt){
        final Toast mToast  = Toast.makeText(mContext, null, Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout)mToast.getView();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(145), DensityUtil.dip2px(63));
        layoutParams.gravity = Gravity.CENTER;
        toastView.setLayoutParams(layoutParams);

        TextView tv=new TextView(mActivity);
        tv.setTextSize(DensityUtil.dip2px(16));
        tv.setTextColor(Color.parseColor("#ffffff"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.setMargins(0, 0, 0, 80);
        tv.setLayoutParams(params);

        mToast.setView(toastView);
        toastView.addView(tv);
        tv.setText(msg);
        mToast.show();

        final Timer timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mToast.show();
            }
        },0,3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mToast.cancel();
                timer.cancel();
            }
        }, cnt );

    }

}
