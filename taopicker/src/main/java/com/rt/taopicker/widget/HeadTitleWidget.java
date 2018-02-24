package com.rt.taopicker.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.main.home.activity.HomeActivity;
import com.rt.taopicker.main.login.activity.LoginActivity;
import com.rt.taopicker.util.ActivityHelper;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.UserInfoHelper;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yaoguangyao on 2017/12/18.
 */

public class HeadTitleWidget extends LinearLayout {
    @BindView(R.id.tv_title)
    protected TextView mTitleTv;

    @BindView(R.id.iv_func_type)
    protected ImageView mFuncTypeIv;

    @BindView(R.id.ll_func_type)
    protected LinearLayout mFuncTypeLl;

    @BindView(R.id.rl_head_title)
    protected RelativeLayout mHeadTitleRl;

    @BindView(R.id.ll_back)
    protected LinearLayout mBackLl;

    private Context mContext;

    public HeadTitleWidget(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.widget_head_title, this);
        ButterKnife.bind(this, view);
    }

    /**
     * 初始化样式
     *
     * @param headTitleVo
     */
    public void init(HeadTitleVo headTitleVo) {
        String title = headTitleVo.getTitle();
        if (StringUtil.isNotBlank(title)) {
            mTitleTv.setText(title);
        }

        //返回按钮
        if (headTitleVo.getHasBack()) {
            mBackLl.setVisibility(VISIBLE);
        } else {
            mBackLl.setVisibility(GONE);
        }
        //字体颜色
        Integer textColor = headTitleVo.getTextColor();
        if (textColor != null) {
            mTitleTv.setTextColor(textColor);
        }
        //背景颜色
        Integer backgroundColor = headTitleVo.getBackgroundColor();
        if (backgroundColor != null) {
            mHeadTitleRl.setBackgroundColor(backgroundColor);
        }

        //右边功能类型按钮
        Integer funcType = headTitleVo.getFuncType();
        if (Constant.HeadTitleFuncType.NONE.equals(funcType)) {
            mFuncTypeLl.setVisibility(GONE);
        } else if (Constant.HeadTitleFuncType.HOME.equals(funcType)) {
            mFuncTypeLl.setVisibility(VISIBLE);
            mFuncTypeIv.setImageResource(R.drawable.icon_home);
            mFuncTypeLl.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = HomeActivity.newIntent(mContext);
                    ActivityHelper.getCurrentActivity().startActivity(intent);
                }
            });
        } else if (Constant.HeadTitleFuncType.SIGNOUT.equals(funcType)) {
            mFuncTypeLl.setVisibility(VISIBLE);
            mFuncTypeIv.setImageResource(R.drawable.icon_signout);
            mFuncTypeLl.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = LoginActivity.newIntent(mContext);
                    ActivityHelper.getCurrentActivity().startActivity(intent);
                }
            });
        }

        mBackLl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.getCurrentActivity().onBackPressed();
            }
        });
    }

    public void setTitle(String title) {
        mTitleTv.setText(title);
    }
}
