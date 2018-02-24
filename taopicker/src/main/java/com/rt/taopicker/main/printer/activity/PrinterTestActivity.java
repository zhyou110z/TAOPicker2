package com.rt.taopicker.main.printer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.main.packing.activity.PackingWaitingActivity;
import com.rt.taopicker.main.printer.contract.IPrinterTestContract;
import com.rt.taopicker.main.printer.presenter.PrinterTestPresenter;
import com.rt.taopicker.util.ActivityHelper;
import com.rt.taopicker.util.DialogHelper;
import com.rt.taopicker.main.printer.PrinterHelper;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.NoticeDialogWidget;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 打印机测试
 * Created by wangzhi on 2018/2/1.
 */

public class PrinterTestActivity extends BaseActivity<IPrinterTestContract.IPresenter> implements IPrinterTestContract.IView, TextView.OnEditorActionListener {

    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdt;

    @BindView(R.id.edt_wave_order_no)
    protected EditText mWaveOrderNo;

    @Override
    public int getRootViewResId() {
        return R.layout.packing_printer_test_activity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mHeadTitleWdt.init(new HeadTitleVo("打印测试", true, Constant.HeadTitleFuncType.NONE));

        mWaveOrderNo.setOnEditorActionListener(this);
        mWaveOrderNo.requestFocus();

        PrinterHelper.getInstance().init(ActivityHelper.getCurrentActivity(), mPrinterListener);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PrinterTestActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public IPrinterTestContract.IPresenter getPresenter() {
        return new PrinterTestPresenter();
    }

    @OnClick(R.id.btn_printer_test)
    protected void onBtnClick(View view){
        mPresenter.printOrder(mWaveOrderNo.getText().toString()); //调用接口打印

//        PrinterHelper.getInstance().zebraPrinterContent(null); //自测打印
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
            String message = "";
            if(result){
                message = "打印完成";
            }else{
                message = "打印机异常";
            }
            DialogHelper.getInstance().showDialog(message, new NoticeDialogWidget.DialogListener(){
                @Override
                public void onClick() {

                }
            });
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

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.edt_wave_order_no: {
                if ((event != null && event.getAction() == MotionEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
}
