package com.rt.taopicker.main.packing.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rt.core.widget.ptr.PullToRefreshBase;
import com.rt.core.widget.ptr.PullToRefreshScrollView;
import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.data.api.entity.QueryPackageWaveOrderRespEntity;
import com.rt.taopicker.data.api.entity.QueryTaoPackageStatusRespEntity;
import com.rt.taopicker.main.packing.contract.IPackingWaitingContract;
import com.rt.taopicker.main.packing.presenter.PackingWaitPresenter;
import com.rt.taopicker.util.DateUtil;
import com.rt.taopicker.util.DialogHelper;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.UserInfoHelper;
import com.rt.taopicker.util.VibratorAndPlayMusicHelper;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 无吊挂店包装抢单页面
 * Created by wangzhi on 2018/1/31.
 */

public class PackingWaitingActivity extends BaseActivity<IPackingWaitingContract.IPresenter> implements IPackingWaitingContract.IView, PullToRefreshBase.OnRefreshListener {

    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdt;

    @BindView(R.id.tv_user_code)
    protected TextView mUserCodeTv;

    @BindView(R.id.tv_user_name)
    protected TextView mUserNameTv;

    @BindView(R.id.btn_grab_order)
    protected Button mGrabOrderBtn; //抢单按钮

    @BindView(R.id.ll_waiting_on)
    protected LinearLayout mWaitingOnLl; //无单等待布局

    @BindView(R.id.ll_waiting_off)
    protected LinearLayout mWaitingOffLl; //有单来了布局

    @BindView(R.id.ll_pack_order_num)
    protected LinearLayout mPackOrderNumLl; //目前有10个道口未包装

    @BindView(R.id.tv_pack_order_no)
    protected TextView mPackOrderNoTv; //未包装道口数

    @BindView(R.id.tv_tip)
    protected TextView mTipTv; //需增加拣货人员

    @BindView(R.id.ptrsv_container)
    protected PullToRefreshScrollView mContainerPtrsv; //下拉刷新布局

    private AlertDialog mDialog;

    private PowerManager.WakeLock mWakeLock = null;

    /**
     * 定时任务
     */
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        public void run() {
            mPresenter.queryTaoPackageStatus();
        }
    };

    @Override
    public int getRootViewResId() {
        return R.layout.packing_waiting_activity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mHeadTitleWdt.init(new HeadTitleVo("等待抢单", false, Constant.HeadTitleFuncType.HOME));

        EmployeeLoginRespEntity employeeLoginRespEntity = UserInfoHelper.getCurrentEmployeeInfo();
        if (employeeLoginRespEntity != null) {
            mUserCodeTv.setText(employeeLoginRespEntity.getEmployeeNo());
            mUserNameTv.setText(employeeLoginRespEntity.getEmployeeName());
        }

        mContainerPtrsv.setOnRefreshListener(this);
        mGrabOrderBtn.setClickable(false); //默认不可点击

        acquireWakeLock();
    }

    @OnClick(R.id.btn_grab_order)
    protected void onGrabOrderBtnClick(View view){
        if(mGrabOrderBtn.isClickable()){
            //多人抢单弹框提示
            mDialog = new AlertDialog.Builder(this).create();
            mDialog.show();
            Window window = mDialog.getWindow();
            window.setContentView(R.layout.picker_waiting_dialog);

            mPresenter.grabPackageOrder();
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PackingWaitingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public IPackingWaitingContract.IPresenter getPresenter() {
        return new PackingWaitPresenter();
    }

    @Override
    public void queryTaoPackageStatusSuccess(QueryTaoPackageStatusRespEntity entity) {
        if (Constant.GrabOrderType.NO_ORDER == entity.getPackageStatus()) { //无单
            changeGrabOrderStatus(false, entity);
            mHandler.postDelayed(mRunnable, Constant.SCHEDULE_INTERVAL);
        } else if (Constant.GrabOrderType.YES_ORDER == entity.getPackageStatus()) {
            changeGrabOrderStatus(true, entity);
            mHandler.postDelayed(mRunnable, Constant.SCHEDULE_INTERVAL);
        } else if (Constant.GrabOrderType.HAS_ORDER == entity.getPackageStatus() && StringUtil.isNotBlank(entity.getWaveOrderNo())) {
            mPresenter.queryPackageWaveOrder(entity.getWaveOrderNo());
        }
        mContainerPtrsv.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("最近更新 " + DateUtil.getTime(new Date()));
        mContainerPtrsv.onRefreshComplete();
    }

    @Override
    public void changeGrabOrderStatus(boolean flag, QueryTaoPackageStatusRespEntity entity) {
        if (flag) { //有单
            mGrabOrderBtn.setClickable(true);
            mGrabOrderBtn.setBackgroundResource(R.drawable.abc_button_corner_normal);
            mWaitingOnLl.setVisibility(View.GONE);
            mWaitingOffLl.setVisibility(View.VISIBLE);
            if(entity.getWaitPackNum() != null && entity.getWaitPackNum() > 0){
                mPackOrderNumLl.setVisibility(View.VISIBLE);
                mPackOrderNoTv.setText(entity.getWaitPackNum().toString());
            }
            if(StringUtil.isNotBlank(entity.getWarnHint())){
                mTipTv.setVisibility(View.VISIBLE);
                mTipTv.setText(entity.getWarnHint());
            }

            VibratorAndPlayMusicHelper.open();
        } else { //无单
            mGrabOrderBtn.setClickable(false);
            mGrabOrderBtn.setBackgroundResource(R.drawable.abc_button_corner_disabled);
            mWaitingOnLl.setVisibility(View.VISIBLE);
            mWaitingOffLl.setVisibility(View.GONE);
            mPackOrderNumLl.setVisibility(View.GONE);
            mTipTv.setVisibility(View.GONE);

            VibratorAndPlayMusicHelper.stop();
        }
    }

    @Override
    public void queryTaoPackageStatusFail() {
        mHandler.postDelayed(mRunnable, Constant.SCHEDULE_INTERVAL); // 结果有返回了，再开始执行定时器
        mContainerPtrsv.onRefreshComplete();
        VibratorAndPlayMusicHelper.stop();
    }

    @Override
    public void netWorkException() {
        mHandler.postDelayed(mRunnable, Constant.NO_NETWORK_SCHEDULE_INTERVAL); // 结果有返回了，再开始执行定时器
        mContainerPtrsv.onRefreshComplete();
        VibratorAndPlayMusicHelper.stop();
    }

    /**
     * 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行，当TimerTask开始运行时加入如下方法
     */
    private void acquireWakeLock() {
        if (null == mWakeLock) {
            PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK| PowerManager.ON_AFTER_RELEASE, "PostLocationService");
            if (null != mWakeLock) {
                mWakeLock.acquire();
            }
        }
    }

    /**
     * 释放设备电源锁
     */
    private void releaseWakeLock(){
        if (null != mWakeLock){
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        mPresenter.queryTaoPackageStatus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable); //停止刷新
        VibratorAndPlayMusicHelper.stop();
        releaseWakeLock();
    }

    @Override
    public void grabOrdersSuccess(QueryPackageWaveOrderRespEntity entity) {
        try{
            mDialog.cancel();

            if (StringUtil.isNotBlank(entity.getWaveOrderNo())) { //抢单成功
                mPresenter.queryPackageWaveOrder(entity.getWaveOrderNo());
            } else { //抢单失败
                DialogHelper.getInstance().showDialog("订单已被抢，请重新等待抢单");
                changeGrabOrderStatus(false, null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void grabOrdersException() {
        mDialog.cancel();
        VibratorAndPlayMusicHelper.stop();
    }

}