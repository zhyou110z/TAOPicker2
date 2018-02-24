package com.rt.taopicker.main.picker.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rt.core.widget.ptr.PullToRefreshBase;
import com.rt.core.widget.ptr.PullToRefreshScrollView;
import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.data.api.entity.GrabOrdersRespEntity;
import com.rt.taopicker.data.api.entity.QueryGrabOrdersStatusRespEntity;
import com.rt.taopicker.main.picker.contract.IPickerWaitContract;
import com.rt.taopicker.main.picker.presenter.PickerWaitPresenter;
import com.rt.taopicker.util.ActivityHelper;
import com.rt.taopicker.util.DateUtil;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.UserInfoHelper;
import com.rt.taopicker.util.VibratorAndPlayMusicHelper;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import org.w3c.dom.Text;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 等待抢单
 * Created by zhouyou on 2017/2/20.
 */

public class PickerWaitActivity extends BaseActivity<IPickerWaitContract.IPresenter> implements IPickerWaitContract.IView, PullToRefreshBase.OnRefreshListener {

    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdt;

    @BindView(R.id.tv_user_code)
    protected TextView mUserCodeTv;

    @BindView(R.id.tv_user_name)
    protected TextView mUserNameTv;

    @BindView(R.id.tv_region_name)
    protected TextView mRegionNameTv;

    @BindView(R.id.btn_grab_order)
    protected Button mGrabOrderBtn; //抢单按钮

    @BindView(R.id.ll_waiting_on)
    protected LinearLayout mWaitingOnLl; //无单等待布局

    @BindView(R.id.ll_waiting_off)
    protected LinearLayout mWaitingOffLl; //有单来了布局

    @BindView(R.id.ll_pick_area_order_num)
    protected LinearLayout mPickerAreaOrderNumLl; //此区有10张拣货单

    @BindView(R.id.tv_pick_order_no)
    protected TextView mPickerOrderNoTv; //此区拣货数量

    @BindView(R.id.tv_tip)
    protected TextView mTipTv; //需增加拣货人员

    @BindView(R.id.ptrsv_container)
    protected PullToRefreshScrollView mContainerPtrsv; //下拉刷新布局

    private AlertDialog mDlg;

    @Override
    public int getRootViewResId() {
        return R.layout.picker_waiting_activity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mHeadTitleWdt.init(new HeadTitleVo("等待抢单", false, Constant.HeadTitleFuncType.HOME));

        EmployeeLoginRespEntity employeeLoginRespEntity = UserInfoHelper.getCurrentEmployeeInfo();
        if (employeeLoginRespEntity != null) {
            mUserCodeTv.setText(employeeLoginRespEntity.getEmployeeNo());
            mUserNameTv.setText(employeeLoginRespEntity.getEmployeeName());
            mRegionNameTv.setText(employeeLoginRespEntity.getPickAreaName());
        }

        mContainerPtrsv.setOnRefreshListener(this);

        acquireWakeLock();
    }

    @OnClick(R.id.btn_grab_order)
    protected void onGrabOrderBtnClick(View view){
        //多人抢单弹框提示
        mDlg = new AlertDialog.Builder(this).create();
        mDlg.show();
        Window window = mDlg.getWindow();
        window.setContentView(R.layout.picker_waiting_dialog);

        mPresenter.grabOrder();
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PickerWaitActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public IPickerWaitContract.IPresenter getPresenter() {
        return new PickerWaitPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();

        //查询用户订单状态
        handler.removeCallbacks(runnable); //停止刷新
    }

    /**
     * 更新等待状态
     *
     * @param flag false 等待结束 ,true 等待开始
     */
    private void waiting(boolean flag, QueryGrabOrdersStatusRespEntity entity) {
        if (flag) {
            mGrabOrderBtn.setClickable(false);
            mGrabOrderBtn.setBackgroundResource(R.drawable.abc_button_corner_disabled);
            mWaitingOnLl.setVisibility(View.VISIBLE);
            mWaitingOffLl.setVisibility(View.GONE);
            mPickerAreaOrderNumLl.setVisibility(View.GONE);
            mTipTv.setVisibility(View.GONE);
            VibratorAndPlayMusicHelper.stop();
        } else {// false 抢单
            mGrabOrderBtn.setClickable(true);
            mGrabOrderBtn.setBackgroundResource(R.drawable.abc_button_corner_normal);
            mWaitingOnLl.setVisibility(View.GONE);
            mWaitingOffLl.setVisibility(View.VISIBLE);
            if(entity.getPickOrderNum() != null && entity.getPickOrderNum() > 0){
                mPickerAreaOrderNumLl.setVisibility(View.VISIBLE);
                mPickerOrderNoTv.setText(entity.getPickOrderNum().toString());
            }
            if(StringUtil.isNotBlank(entity.getPickerWarnHint())){
                mTipTv.setVisibility(View.VISIBLE);
                mTipTv.setText(entity.getPickerWarnHint());
            }

            VibratorAndPlayMusicHelper.open();
        }
    }

    @Override
    public void onBackPressed() {
    }

    /**
     * 抢单失败
     */
    private void activeOrderFail() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        window.setContentView(R.layout.picker_grab_order_fail_dialog);
        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
        waiting(true, null);
    }

    /**
     * 跳转到拣货明显页面
     * @param pickOrderNo
     */
    private void toOrderDetail(String pickOrderNo){
        if(ActivityHelper.getCurrentActivity() instanceof  PickerWaitActivity){
            VibratorAndPlayMusicHelper.stop();

            Intent intent = PickerActivity.newIntent(PickerWaitActivity.this, pickOrderNo);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        mPresenter.queryUserOrderState();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); //停止刷新
        noNetWorkHandler.removeCallbacks(noNetWorkRunnable);
        VibratorAndPlayMusicHelper.stop();
        releaseWakeLock();
    }

    /**
     * 定时任务
     */
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            this.update();
        }
        void update() {
            mPresenter.queryUserOrderState();
        }
    };

    /**
     * 无网络定时任务
     */
    private Handler noNetWorkHandler = new Handler();
    private Runnable noNetWorkRunnable = new Runnable() {
        public void run() {
            noNetWorkHandler.postDelayed(noNetWorkRunnable,Constant.NO_NETWORK_SCHEDULE_INTERVAL);
            this.update();
        }
        void update() {
            mPresenter.queryUserOrderState();
        }
    };

    PowerManager.WakeLock wakeLock = null;
    /**
     * 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行，当TimerTask开始运行时加入如下方法
     */
    private void acquireWakeLock() {
        if (null == wakeLock) {
            PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK| PowerManager.ON_AFTER_RELEASE, "PostLocationService");
            if (null != wakeLock) {
                wakeLock.acquire();
            }
        }
    }

    /**
     * 释放设备电源锁
     */
    private void releaseWakeLock(){
        if (null != wakeLock){
            wakeLock.release();
            wakeLock = null;
        }
    }

    @Override
    public void queryGrabOrdersStatusSuccess(QueryGrabOrdersStatusRespEntity entity) {
        LoadingHelper.getInstance().hideLoading();
        noNetWorkHandler.removeCallbacks(noNetWorkRunnable);//关闭无网络定时器

        if (Constant.GrabOrderType.NO_ORDER.equals(entity.getPickStatus())) {
            handler.postDelayed(runnable, Constant.SCHEDULE_INTERVAL); // 结果有返回了，再开始执行定时器
            waiting(true, entity);
        } else if (Constant.GrabOrderType.YES_ORDER.equals(entity.getPickStatus())) {
            handler.postDelayed(runnable, Constant.SCHEDULE_INTERVAL); // 结果有返回了，再开始执行定时器
            waiting(false, entity);
        } else if (Constant.GrabOrderType.HAS_ORDER.equals(entity.getPickStatus()) && entity.getPickOrderNo() !=null ) {
            toOrderDetail(entity.getPickOrderNo());
        } else {
            Log.e(Constant.HTTP_TAG, "queryGrabOrdersStatus Error !!!");
        }
        mContainerPtrsv.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("最近更新 " + DateUtil.getTime(new Date()));
        mContainerPtrsv.onRefreshComplete();
    }

    @Override
    public void queryGrabOrdersStatusFail(int errorCode, String message) {
        try{
            LoadingHelper.getInstance().hideLoading();
            VibratorAndPlayMusicHelper.stop();

            handler.postDelayed(runnable, Constant.SCHEDULE_INTERVAL); // 结果有返回了，再开始执行定时器
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            Log.e(Constant.HTTP_TAG, " queryGrabOrdersStatus Error !!!");
            mContainerPtrsv.onRefreshComplete();
        }catch (Exception e){
            handler.postDelayed(runnable, Constant.SCHEDULE_INTERVAL); // 结果有返回了，再开始执行定时器
            Toast.makeText(getApplicationContext(), "查询用户订单状态异常", Toast.LENGTH_SHORT).show();
            Log.e(Constant.HTTP_TAG, e.getMessage());
        }
    }

    @Override
    public void queryGrabOrdersStatusError() {
        LoadingHelper.getInstance().hideLoading();
        Log.e(Constant.HTTP_TAG, " PickerUserOrderStateClient noNetwork Error !!!");
        mContainerPtrsv.onRefreshComplete();
        VibratorAndPlayMusicHelper.stop();
        noNetWorkHandler.postDelayed(noNetWorkRunnable, Constant.NO_NETWORK_SCHEDULE_INTERVAL); // 结果有返回了，再开始执行定时器
    }

    @Override
    public void grabOrdersSuccess(GrabOrdersRespEntity entity) {
        try{
            mDlg.cancel();

            if (StringUtil.isNotBlank(entity.getPickOrderNo())) { //跳转到拣货明显页面
                toOrderDetail(entity.getPickOrderNo());
            } else {
                activeOrderFail();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void grabOrdersFail(int errorCode, String message) {
        LoadingHelper.getInstance().hideLoading();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        mDlg.cancel();

        VibratorAndPlayMusicHelper.stop();
    }

    @Override
    public void grabOrdersError() {
        LoadingHelper.getInstance().hideLoading();
        mDlg.cancel();
        VibratorAndPlayMusicHelper.stop();
    }
}
