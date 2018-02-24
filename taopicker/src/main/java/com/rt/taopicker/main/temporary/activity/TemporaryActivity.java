package com.rt.taopicker.main.temporary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.data.api.entity.TemporaryReqEntity;
import com.rt.taopicker.data.api.entity.TemporaryRespEntity;
import com.rt.taopicker.main.temporary.contract.ITemporaryContract;
import com.rt.taopicker.main.temporary.presenter.TemporaryPresenter;
import com.rt.taopicker.util.UserInfoHelper;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.NoticeDialogFragmentWidget;
import com.rt.taopicker.widget.vo.HeadTitleVo;


import butterknife.BindView;

/**
 * 备用道口暂存
 * Created by zhouyou on 2018/02/01.
 */

public class TemporaryActivity extends BaseActivity<ITemporaryContract.IPresenter>
        implements ITemporaryContract.IView ,TextView.OnEditorActionListener{

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TemporaryActivity.class);
        return intent;
    }

    //员工编号
    @BindView(R.id.tv_user_code)
    protected TextView mUserCodeTv;

    //员工姓名
    @BindView(R.id.tv_user_name)
    protected TextView mUserNameTv;

    //载具
    @BindView(R.id.edt_vehicle)
    protected EditText mVehicleEdt;

    //暂存箱号
    @BindView(R.id.edt_temp_storage_box)
    protected EditText mTempStorageBoxEdt;


    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdt;

    @Override
    public int getRootViewResId() {
        return R.layout.temporary_storage_activity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mHeadTitleWdt.init(new HeadTitleVo("备用口暂存", false, Constant.HeadTitleFuncType.HOME));
        EmployeeLoginRespEntity employeeLoginRespEntity = UserInfoHelper.getCurrentEmployeeInfo();
        if (employeeLoginRespEntity != null) {
            mUserCodeTv.setText(employeeLoginRespEntity.getEmployeeNo());
            mUserNameTv.setText(employeeLoginRespEntity.getEmployeeName());
        }

        mVehicleEdt.setOnEditorActionListener(this);
        mVehicleEdt.requestFocus();

        mTempStorageBoxEdt.setOnEditorActionListener(this);
    }

    @Override
    public ITemporaryContract.IPresenter getPresenter() {
        return new TemporaryPresenter(this);
    }



    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.edt_vehicle: {
                if ((event != null && event.getAction() == MotionEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    commit();

                    //关闭键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mVehicleEdt.getWindowToken(), 0);
                    return true;
                }
                break;
            }
            case R.id.edt_temp_storage_box: {
                if ((event != null && event.getAction() == MotionEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    commit();

                    //关闭键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mTempStorageBoxEdt.getWindowToken(), 0);
                    return true;
                }
                break;
            }
        }
        return false;
    }


    private void commit(){
        if (mVehicleEdt.getText().length() > 0 && mTempStorageBoxEdt.getText().length() > 0) {

            TemporaryReqEntity req = new TemporaryReqEntity();
            req.setVehicleNo(mVehicleEdt.getText().toString());
            req.setZcxNo(mTempStorageBoxEdt.getText().toString());
            mPresenter.putInZcx(req);
        }
    }

    @Override
    public void putInZcxSuccess(TemporaryRespEntity resp) {
            if(resp.getCode() == 4005){
                showCompleteDialog(resp.getDesc());
            }else{
                Toast.makeText(this, resp.getDesc(), Toast.LENGTH_SHORT).show();
                clearData();
            }
    }


    private void showCompleteDialog(String desc){

        showDialog(desc, true, "返回首页", "继续暂存", new NoticeDialogFragmentWidget.PositionDialogListener() {
            @Override
            public void onClick() {
                //到首页
                EmployeeLoginRespEntity employeeLoginRespEntity = UserInfoHelper.getCurrentEmployeeInfo();
                if (employeeLoginRespEntity != null ) {
                        mPresenter.employeeLogout();
                } else {
                    finish();
                }
            }
        }, new NoticeDialogFragmentWidget.NegativeDialogListener() {
            @Override
            public void onClick() {
                clearData();
            }
        });
    }

    @Override
    public void putInZcxFail() {
        clearData();
    }

    private void clearData(){
        mVehicleEdt.setText("");
        mTempStorageBoxEdt.setText("");
        mVehicleEdt.requestFocus();
    }

    public void showDialog(String message, boolean negative, String positionBtnText, String negativeBtnText,
                           NoticeDialogFragmentWidget.PositionDialogListener positionDialogListener,
                           NoticeDialogFragmentWidget.NegativeDialogListener negativeDialogListener) {
        NoticeDialogFragmentWidget newFragment = new NoticeDialogFragmentWidget();
        newFragment.setCancelable(false);
        newFragment.setPositionListener(positionDialogListener);
        newFragment.setNegativeListener(negativeDialogListener);
        newFragment.setPositionButtonText(positionBtnText);
        newFragment.setNegativeButtonText(negativeBtnText);
        newFragment.setMessage(message);
        newFragment.setNegative(negative);
        newFragment.show(getSupportFragmentManager(), "NoticeDialogFragmentWidget");
    }
}











