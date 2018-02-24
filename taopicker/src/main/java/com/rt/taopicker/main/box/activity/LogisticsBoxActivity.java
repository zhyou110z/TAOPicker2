package com.rt.taopicker.main.box.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.main.box.adapter.LogisticsBoxHistoryAdapter;
import com.rt.taopicker.main.box.contract.ILogisticsBoxContract;
import com.rt.taopicker.main.box.presenter.LogisticsBoxPresenter;
import com.rt.taopicker.main.box.vo.LogisticsBoxVo;
import com.rt.taopicker.util.DateUtil;
import com.rt.taopicker.util.KbUtil;
import com.rt.taopicker.util.UserInfoHelper;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import java.util.Date;

import butterknife.BindView;

/**
 * Created by chensheng on 2018/1/30.
 */

public class LogisticsBoxActivity extends BaseActivity<ILogisticsBoxContract.IPresenter> implements ILogisticsBoxContract.IView {


    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdt;

    @BindView(R.id.tv_user_code)
    protected TextView mUserCodeTv;

    @BindView(R.id.tv_user_name)
    protected TextView mUserNameTv;

    @BindView(R.id.edt_box_number)
    protected EditText mBoxNumberEdt;

    @BindView(R.id.lv_task_list)
    protected ListView mTaskListLv;

    private LogisticsBoxHistoryAdapter mLogisticsBoxHistoryAdapter;

    @Override
    public int getRootViewResId() {
        return R.layout.logistics_box_activity;
    }


    @Override
    public ILogisticsBoxContract.IPresenter getPresenter() {
        return new LogisticsBoxPresenter();
    }


    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mHeadTitleWdt.init(new HeadTitleVo("物流箱回收", false, Constant.HeadTitleFuncType.HOME));

        EmployeeLoginRespEntity employeeLoginRespEntity = UserInfoHelper.getCurrentEmployeeInfo();
        if (employeeLoginRespEntity != null) {
            mUserCodeTv.setText(employeeLoginRespEntity.getEmployeeNo());
            mUserNameTv.setText(employeeLoginRespEntity.getEmployeeName());
        }

        mBoxNumberEdt.setOnEditorActionListener(mOnEditorActionListener);

        mLogisticsBoxHistoryAdapter = new LogisticsBoxHistoryAdapter(this);
        mTaskListLv.setAdapter(mLogisticsBoxHistoryAdapter);
    }

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (v.getId()) {
                case R.id.edt_box_number: {
                    //扫码最后一位||点击键盘完成
                    if ((event != null && event.getAction() == MotionEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                        mPresenter.httpSetBoxRecycling(mBoxNumberEdt.getText().toString());
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
    public void hideKb() {
        KbUtil.hideKb(this);
    }

    @Override
    public void clearInput() {
        mBoxNumberEdt.setText("");
        mBoxNumberEdt.requestFocus();
        hideKb();
    }

    @Override
    public void doFinish(String boxNo) {
        LogisticsBoxVo logisticsBoxVo = new LogisticsBoxVo();
        logisticsBoxVo.setCreateTime(DateUtil.dateToString19(new Date()));
        logisticsBoxVo.setBoxNo(boxNo);
        mLogisticsBoxHistoryAdapter.addFirstModel(logisticsBoxVo);
        mTaskListLv.setSelection(0);
        clearInput();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LogisticsBoxActivity.class);
    }
}
