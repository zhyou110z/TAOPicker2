package com.rt.taopicker.main.status.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.PdaLoginRespEntity;
import com.rt.taopicker.main.status.contract.IStatusViewContract;
import com.rt.taopicker.main.status.presenter.StatusViewPresenter;
import com.rt.taopicker.util.DensityUtil;
import com.rt.taopicker.util.KbUtil;
import com.rt.taopicker.util.PreferencesUtil;
import com.rt.taopicker.util.UserInfoHelper;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by chensheng on 2018/1/31.
 */

public class StatusViewActivity extends BaseActivity<IStatusViewContract.IPresenter> implements IStatusViewContract.IView, View.OnClickListener {

    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdt;

    @BindView(R.id.tv_account)
    protected TextView mAccountTv;

    @BindView(R.id.tv_store)
    protected TextView mStoreTv;

    @BindView(R.id.btn_ok)
    protected Button mOkBtn;

    @BindView(R.id.edt_bar_code)
    protected EditText mBarCodeEdt;

    @Override
    public int getRootViewResId() {
        return R.layout.status_view_activity;
    }

    @Override
    public IStatusViewContract.IPresenter getPresenter() {
        return new StatusViewPresenter();
    }


    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mHeadTitleWdt.init(new HeadTitleVo("状态查询", false, Constant.HeadTitleFuncType.HOME));

        PdaLoginRespEntity loginRespEntity = PreferencesUtil.getUserInfo();
        if (loginRespEntity != null) {
            mAccountTv.setText(loginRespEntity.getRealname());
            mStoreTv.setText(loginRespEntity.getStoreName());
        }

        mOkBtn.setOnClickListener(this);

        mBarCodeEdt.addTextChangedListener(mTextWatcher);
        mBarCodeEdt.setOnEditorActionListener(mOnEditorActionListener);
        mBarCodeEdt.requestFocus();
    }

    @Override
    public void redirectStatusView(String barCode, String infoType, Serializable infoModel) {
        Intent intent = StatusInfoActivity.newIntent(this, barCode, infoType, infoModel);
        startActivity(intent);
    }

    @Override
    public void clearInput() {
        mBarCodeEdt.setText("");
    }


    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mBarCodeEdt.getText().length() > 0) {
                if (!mOkBtn.isClickable()) {
                    mOkBtn.setClickable(true);
                }
                mOkBtn.setBackgroundResource(R.drawable.abc_button_corner_selector);
            } else {
                if (mOkBtn.isClickable()) {
                    mOkBtn.setClickable(false);
                }
                mOkBtn.setBackgroundResource(R.drawable.abc_button_corner_disabled);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            switch (v.getId()) {
                case R.id.edt_bar_code: {
                    if (event != null && event.getAction() == MotionEvent.ACTION_DOWN && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.ACTION_DOWN)) {
                        //查询状态
                        mPresenter.queryStatus(mBarCodeEdt.getText().toString());
                        KbUtil.hideKb(mBarCodeEdt);
                        return true;
                    }
                    break;
                }
            }

            return false;
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok: {
                //查询状态
                mPresenter.queryStatus(mBarCodeEdt.getText().toString());
                break;
            }
        }
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, StatusViewActivity.class);
    }
}
