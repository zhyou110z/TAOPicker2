package com.rt.taopicker.widget;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.zxing.common.StringUtils;
import com.rt.taopicker.R;
import com.rt.taopicker.util.StringUtil;


/**
 * Created by chensheng on 2017/3/20.
 */

public class NoticeDialogFragmentWidget extends DialogFragment implements View.OnClickListener {

    //是否有取消按钮
    private boolean negative = false;
    //消息内容
    private String message;
    //确定按钮回调
    private PositionDialogListener positionListener;
    //取消按钮回调
    private NegativeDialogListener negativeListener;
    //自定义内容
    private View customView;
    //确定按钮名称
    private String positionButtonText;
    //取消按钮名称
    private String negativeButtonText;
    //点击按钮关闭弹框
    private boolean autoClose = true;

    public void setPositionListener(PositionDialogListener positionListener) {
        this.positionListener = positionListener;
    }

    public void setNegativeListener(NegativeDialogListener negativeListener) {
        this.negativeListener = negativeListener;
    }

    public void setPositionButtonText(String positionButtonText) {
        this.positionButtonText = positionButtonText;
    }

    public void setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
    }

    public void setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
    }

    /**
     * 设置自定义视图
     *
     * @param customView
     */
    public void setView(View customView) {
        this.customView = customView;
    }

    /**
     * 是否有取消按钮，默认没有
     *
     * @param negative
     */
    public void setNegative(boolean negative) {
        this.negative = negative;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = null;
        TextView messageView = null;
        TextView positionButton = null;
        TextView negativeButton = null;

        if (StringUtil.isNotBlank(message)) {
            view = inflater.inflate(R.layout.widget_dialog, container, false);
            messageView = (TextView) view.findViewById(R.id.messageView);
            messageView.setText(message);
        } else {
            view = inflater.inflate(R.layout.widget_custom_dialog, container, false);
            FrameLayout customLayout = (FrameLayout) view.findViewById(R.id.customLayout);

            if (customView != null) {
                customLayout.addView(customView);
            }
        }

        positionButton = (TextView) view.findViewById(R.id.positionButton);
        positionButton.setOnClickListener(this);
        if (StringUtil.isNotBlank(positionButtonText)) {
            positionButton.setText(positionButtonText);
        }

        negativeButton = (TextView) view.findViewById(R.id.negativeButton);
        negativeButton.setOnClickListener(this);
        if (StringUtil.isNotBlank(negativeButtonText)) {
            negativeButton.setText(negativeButtonText);
        }

        View negativeDivide = (View) view.findViewById(R.id.negativeDivide);

        if (negative) {
            negativeButton.setVisibility(View.VISIBLE);
            negativeDivide.setVisibility(View.VISIBLE);
        } else {
            negativeButton.setVisibility(View.GONE);
            negativeDivide.setVisibility(View.GONE);
        }

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.positionButton: {
                if (autoClose) {
                    dismissAllowingStateLoss();
                }
                if (positionListener != null) {
                    positionListener.onClick();
                }
                break;
            }
            case R.id.negativeButton: {
                if (autoClose) {
                    dismissAllowingStateLoss();
                }
                if (negativeListener != null) {
                    negativeListener.onClick();
                }
                break;
            }
        }
    }


    public interface PositionDialogListener {
        void onClick();
    }

    public interface NegativeDialogListener {
        void onClick();
    }
}

