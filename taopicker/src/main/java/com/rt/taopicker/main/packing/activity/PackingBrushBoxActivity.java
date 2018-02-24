package com.rt.taopicker.main.packing.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.packageScanCodeRespEntity.PackageScanCodeRespEntity;
import com.rt.taopicker.main.packing.adapter.PackageAdapter;
import com.rt.taopicker.main.packing.contract.IPackingBrushBoxContract;
import com.rt.taopicker.main.packing.presenter.PackingBrushBoxPresenter;
import com.rt.taopicker.util.ActivityHelper;
import com.rt.taopicker.main.printer.PrinterHelper;
import com.rt.taopicker.util.DialogHelper;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.NoticeDialogWidget;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 包装作业--刷物流箱
 */

public class PackingBrushBoxActivity extends BaseActivity<IPackingBrushBoxContract.IPresenter> implements IPackingBrushBoxContract.IView, TextView.OnEditorActionListener {

    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdt;

    //道口
    @BindView(R.id.tv_stall_no)
    protected TextView mStallNoTv;

    //商品数
    @BindView(R.id.tv_goods_pcs)
    protected TextView mGoodsPcsTv;

    //待包装
    @BindView(R.id.tv_wait_package_count)
    protected TextView mWaitPackageCountTv;

    @BindView(R.id.edt_box_no)
    protected EditText mBoxNoEdt;

    @BindView(R.id.lv_box)
    protected ListView mTaskListView;

    @BindView(R.id.rl_no_suspender)
    protected RelativeLayout mNoSuspenderRl;

    @BindView(R.id.rl_suspender)
    protected RelativeLayout mSuspenderRl;

    private PackageScanCodeRespEntity mPackageScanCodeRespEntity;
    private boolean mHasSuspender; //是否是吊挂包装

    //listView适配器
    private PackageAdapter mPackageAdapter;

    @Override
    public int getRootViewResId() {
        return R.layout.packing_box_activity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mPackageScanCodeRespEntity = (PackageScanCodeRespEntity) getIntent().getSerializableExtra("packageScanCodeRespEntity");
        mHasSuspender = getIntent().getBooleanExtra("hasSuspender", true);

        mHeadTitleWdt.init(new HeadTitleVo("包装作业", false, Constant.HeadTitleFuncType.HOME));

        if(mHasSuspender){
            mSuspenderRl.setVisibility(View.VISIBLE);
            mNoSuspenderRl.setVisibility(View.GONE);
        }else{
            mSuspenderRl.setVisibility(View.GONE);
            mNoSuspenderRl.setVisibility(View.VISIBLE);
        }

        mPackageAdapter = new PackageAdapter(getApplicationContext(), 4);
        mTaskListView.setAdapter(mPackageAdapter);

        if (mPackageScanCodeRespEntity != null) {
            mStallNoTv.setText(mPackageScanCodeRespEntity.getShortStallNo());
            mGoodsPcsTv.setText(mPackageScanCodeRespEntity.getPickPcs().toString());
            mWaitPackageCountTv.setText(mPackageScanCodeRespEntity.getVehicleCount().toString());

            if(mPackageScanCodeRespEntity.getBoxList() != null){
                mPackageAdapter.setData(mPackageScanCodeRespEntity.getBoxList());
                mPackageAdapter.notifyDataSetChanged();
            }

        }

        mBoxNoEdt.setOnEditorActionListener(this);
        mBoxNoEdt.requestFocus();

        PrinterHelper.getInstance().init(ActivityHelper.getCurrentActivity(), mPrinterListener);
    }

    @Override
    public IPackingBrushBoxContract.IPresenter getPresenter() {
        return new PackingBrushBoxPresenter();
    }

    /**
     * @param context
     * @param entity
     * @param hasSuspender 是否是吊挂包装
     * @return
     */
    public static Intent newIntent(Context context, PackageScanCodeRespEntity entity, boolean hasSuspender) {
        Intent intent = new Intent(context, PackingBrushBoxActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("packageScanCodeRespEntity", entity);
        intent.putExtras(bundle);
        intent.putExtra("hasSuspender", hasSuspender);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }


    @OnClick(R.id.btn_clear)
    protected void onClearBtnClick(View view){
        mPresenter.clearBox(mPackageScanCodeRespEntity.getWaveOrderNo());
    }

    @OnClick(R.id.btn_save)
    protected void onSaveBtnClick(View view){
        mPresenter.packFinish(mPackageScanCodeRespEntity.getWaveOrderNo(), mHasSuspender);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.edt_box_no: {
                if ((event != null && event.getAction() == MotionEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    mPresenter.brushBox(mBoxNoEdt.getText().toString(), mPackageScanCodeRespEntity.getWaveOrderNo());
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
        mBoxNoEdt.setText("");
        mBoxNoEdt.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mBoxNoEdt.getWindowToken(), 0);
    }

    @Override
    public void refreshListView(PackageScanCodeRespEntity entity) {
        mPackageAdapter.setData(entity.getBoxList());
        mPackageAdapter.notifyDataSetChanged();
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
            LoadingHelper.getInstance().hideLoading();
            DialogHelper.getInstance().showDialog("包装完成，订单明细是否打印完成?", true, "完成", "未完成", new NoticeDialogWidget.DialogListener(){
                @Override
                public void onClick() { //跳到包装页面，继续包装其它道口数据
                    mPresenter.printFinish();
                    Activity currentActivity = ActivityHelper.getCurrentActivity();
                    Intent intent = null;
                    if(mHasSuspender){
                        intent = PackingSuspenderActivity.newIntent(currentActivity);
                    }else{
                        intent = PackingWaitingActivity.newIntent(currentActivity);
                    }
                    currentActivity.startActivity(intent);
                }
            }, new NoticeDialogWidget.DialogListener(){
                @Override
                public void onClick() { //未完成打印，重新连接并补打
                    mPresenter.rePrintOrder(mPackageScanCodeRespEntity.getWaveOrderNo());
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
}
