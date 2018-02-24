package com.rt.taopicker.main.packing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.main.packing.contract.IPackingSuspenderContract;
import com.rt.taopicker.main.packing.presenter.PackingSuspenderPresenter;
import com.rt.taopicker.util.ActivityHelper;
import com.rt.taopicker.main.printer.PrinterHelper;
import com.rt.taopicker.util.UserInfoHelper;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.vo.EventBusVo;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * 有吊挂包装作业
 */

public class PackingSuspenderActivity extends BaseActivity<IPackingSuspenderContract.IPresenter> implements IPackingSuspenderContract.IView, TextView.OnEditorActionListener {

    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdt;

    @BindView(R.id.tv_user_code)
    protected TextView mUserCodeTv;

    @BindView(R.id.tv_user_name)
    protected TextView mUserNameTv;

    //道口
    @BindView(R.id.edt_stall_no)
    protected EditText mStallNoEdt;

    @Override
    public int getRootViewResId() {
        return R.layout.packing_suspender_activity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mHeadTitleWdt.init(new HeadTitleVo("包装作业", false, Constant.HeadTitleFuncType.HOME));

        EmployeeLoginRespEntity employeeLoginRespEntity = UserInfoHelper.getCurrentEmployeeInfo();
        if (employeeLoginRespEntity != null) {
            mUserCodeTv.setText(employeeLoginRespEntity.getEmployeeNo());
            mUserNameTv.setText(employeeLoginRespEntity.getEmployeeName());
        }

        mStallNoEdt.setOnEditorActionListener(this);
        mStallNoEdt.requestFocus();
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PackingSuspenderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public IPackingSuspenderContract.IPresenter getPresenter() {
        return new PackingSuspenderPresenter();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.edt_stall_no: {
                if ((event != null && event.getAction() == MotionEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    mPresenter.brushStallNo(mStallNoEdt.getText().toString().trim()); //根据道口号查询波次单信息
                    return true;
                }
                break;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void clearData() {
        mStallNoEdt.setText("");
        mStallNoEdt.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mStallNoEdt.getWindowToken(), 0);
    }

    @Override
    public void checkBluetooth() {
        PrinterHelper.getInstance().init(ActivityHelper.getCurrentActivity(), mPrinterListener);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(EventBusVo event) {
        if (event != null) {
            if (event.getType() == Constant.EventCode.PACKAGE_TAKE_GOODS_BACK) {
                clearData();
            }
        }
    }

    /**
     * 1.初始化蓝牙：开启蓝牙；
     * 2.初始化蓝牙连接：检查蓝牙是否连接设备，没有连接设备，则去连接设备；
     * 3.打印内容：连接打印机，打印内容
     */
    private PrinterHelper.PrinterListener mPrinterListener = new PrinterHelper.PrinterListener() {
        @Override
        public void initBluetoothCallBack(Boolean result) {
            if(result){
                PrinterHelper.getInstance().initBluetoothConnect();
            }
        }

        @Override
        public void initBluetoothConnectCallBack(Boolean result) {
        }

        @Override
        public void printCompleteCallBack(Boolean result) {

        }

    };

    /**
     * 权限申请回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constant.REQUEST_CODE_PERMISSION_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户已授权
                    PrinterHelper.getInstance().openBluetooth();
                } else {
                    //用户拒绝权限
                    PrinterHelper.getInstance().refuseBluetoothPermission();
                }
                return;
            }
        }
    }

}
