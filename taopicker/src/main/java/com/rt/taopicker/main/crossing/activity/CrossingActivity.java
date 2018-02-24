package com.rt.taopicker.main.crossing.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.main.crossing.contract.ICrossingContract;
import com.rt.taopicker.main.crossing.presenter.CrossingPresenter;
import com.rt.taopicker.main.status.activity.StatusInfoActivity;
import com.rt.taopicker.util.KbUtil;
import com.rt.taopicker.util.UserInfoHelper;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import butterknife.BindView;

/**
 * Created by chensheng on 2018/1/30.
 */

public class CrossingActivity extends BaseActivity<ICrossingContract.IPresenter> implements ICrossingContract.IView {


    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdt;

    @BindView(R.id.tv_user_code)
    protected TextView mUserCodeTv;

    @BindView(R.id.tv_user_name)
    protected TextView mUserNameTv;

    /**
     * 道口
     */
    @BindView(R.id.edt_crossing_number)
    protected EditText mCrossingNumberEdt;

    /**
     * 波次号
     */
    @BindView(R.id.tv_wave_number)
    protected TextView mWaveNumberTv;

    /**
     * 拣货载具
     */
    @BindView(R.id.edt_vehicle)
    protected TextView mVehicleEdt;


    @Override
    public int getRootViewResId() {
        return R.layout.crossing_activity;
    }

    @Override
    public ICrossingContract.IPresenter getPresenter() {
        return new CrossingPresenter();
    }


    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mHeadTitleWdt.init(new HeadTitleVo("人工刷下道口", false, Constant.HeadTitleFuncType.HOME));

        EmployeeLoginRespEntity employeeLoginRespEntity = UserInfoHelper.getCurrentEmployeeInfo();
        if (employeeLoginRespEntity != null) {
            mUserCodeTv.setText(employeeLoginRespEntity.getEmployeeNo());
            mUserNameTv.setText(employeeLoginRespEntity.getEmployeeName());
        }

        mCrossingNumberEdt.setOnEditorActionListener(mOnEditorActionListener);
        mCrossingNumberEdt.requestFocus();

        mVehicleEdt.setOnEditorActionListener(mOnEditorActionListener);

    }


    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (v.getId()) {
                case R.id.edt_crossing_number: {
                    if ((event != null && event.getAction() == MotionEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                        mPresenter.httpQueryWaveOrderNoByStallNo(mCrossingNumberEdt.getText().toString());
                        return true;
                    } else if (event != null && event.getAction() == MotionEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        return true;
                    }
                    break;
                }
                case R.id.edt_vehicle: {
                    if ((event != null && event.getAction() == MotionEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                        mPresenter.httpManualPutToStall(mWaveNumberTv.getText().toString(), mVehicleEdt.getText().toString());
                        return true;
                    } else if (event != null && event.getAction() == MotionEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        return true;
                    }
                    break;
                }
            }
            return false;
        }
    };

    @Override
    public void clearWaveNumber() {
        mWaveNumberTv.setText("");
        KbUtil.hideKb(this);
    }

    @Override
    public void clearCrossingNumber() {
        mWaveNumberTv.setText("");
        mCrossingNumberEdt.setText("");
        crossingFocus();
    }

    @Override
    public void clearAllInput() {
        mWaveNumberTv.setText("");
        mCrossingNumberEdt.setText("");
        mVehicleEdt.setText("");
        crossingFocus();
    }

    @Override
    public void setWaveNumber(String waveNumber) {
        mWaveNumberTv.setText(waveNumber);
        vehicleFocus();
    }

    @Override
    public void crossingFocus() {
        mCrossingNumberEdt.requestFocus();
        KbUtil.hideKb(this);
    }

    @Override
    public void vehicleFocus() {
        mVehicleEdt.requestFocus();
        KbUtil.hideKb(this);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, CrossingActivity.class);
    }

}
