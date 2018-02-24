package com.rt.taopicker.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.util.DensityUtil;
import com.rt.taopicker.util.StringUtil;

/**
 * Fragment提示框，仿IOS样式
 * <p>
 * Created by chensheng on 2017/3/20.
 */

public class NoticeDialogWidget extends DialogFragment implements View.OnClickListener {

    //是否有取消按钮
    private boolean negative = false;
    //消息标题
    private String title;
    //消息内容
    private String content;
    //确定按钮回调
    private DialogListener positiveListener;
    //取消按钮回调
    private DialogListener negativeListener;
    //自定义内容
    private View customView;
    //确定按钮名称
    private String positiveButtonText;
    //取消按钮名称
    private String negativeButtonText;
    //点击按钮关闭弹框
    private boolean autoClose = true;
    //是否展示按钮
    private boolean showButton = true;
    //是否html格式化内容
    private boolean isHtml = false;

    private int gravity = Gravity.CENTER;

    private TextView positionButton;
    private TextView negativeButton;

    public NoticeDialogWidget() {
        //默认不可点击空白关闭窗口
        setCancelable(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = null;
        TextView messageView = null;
        TextView contentView = null;

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (customView != null) {
            view = inflater.inflate(R.layout.widget_dialog_custom, container, false);
            LinearLayout customLayout = (LinearLayout) view.findViewById(R.id.customLayout);
            customLayout.addView(customView);
        } else {
            view = inflater.inflate(R.layout.widget_dialog, container, false);
            messageView = (TextView) view.findViewById(R.id.messageView);
            contentView = (TextView) view.findViewById(R.id.contentView);

            if (StringUtil.isNotBlank(title)) {
                if (isHtml) {
                    messageView.setText(Html.fromHtml(title));
                } else {
                    messageView.setText(title);
                }
            } else {
                messageView.setVisibility(View.GONE);
            }

            if (StringUtil.isNotBlank(content)) {
                contentView.setGravity(gravity);

                if (isHtml) {
                    contentView.setText(Html.fromHtml(content));
                } else {
                    contentView.setText(content);
                }


                LinearLayout.LayoutParams contentViewLP = (LinearLayout.LayoutParams) contentView.getLayoutParams();
                if (messageView.getVisibility() == View.GONE) {
                    contentViewLP.setMargins(0, 0, 0, 0);
                } else {
                    contentViewLP.setMargins(0, DensityUtil.dip2px(15), 0, 0);
                }

            } else {
                contentView.setVisibility(View.GONE);
            }


        }

        positionButton = (TextView) view.findViewById(R.id.positionButton);
        positionButton.setOnClickListener(this);
        if (StringUtil.isNotBlank(positiveButtonText)) {
            positionButton.setText(positiveButtonText);
        }

        negativeButton = (TextView) view.findViewById(R.id.negativeButton);
        negativeButton.setOnClickListener(this);
        if (StringUtil.isNotBlank(negativeButtonText)) {
            negativeButton.setText(negativeButtonText);
        }

        View negativeDivide = view.findViewById(R.id.negativeDivide);

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
                if (positiveListener != null) {
                    positiveListener.onClick();
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


    public interface DialogListener {
        void onClick();
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setPositiveListener(DialogListener positiveListener) {
        this.positiveListener = positiveListener;
    }

    public void setPositiveListener(String buttonText, DialogListener positiveListener) {
        this.positiveButtonText = buttonText;
        this.positiveListener = positiveListener;
    }

    public void setNegativeListener(DialogListener negativeListener) {
        this.negativeListener = negativeListener;
    }

    public void setNegativeListener(String buttonText, DialogListener negativeListener) {
        this.negativeButtonText = buttonText;
        this.negativeListener = negativeListener;
    }

    public void setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }

    public void setShowButton(boolean showButton) {
        this.showButton = showButton;
    }

    public TextView getPositionButton() {
        return positionButton;
    }

    public void setPositionButton(TextView positionButton) {
        this.positionButton = positionButton;
    }

    public TextView getNegativeButton() {
        return negativeButton;
    }

    public void setNegativeButton(TextView negativeButton) {
        this.negativeButton = negativeButton;
    }
}

