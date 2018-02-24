package com.rt.taopicker.main.picker.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.rt.core.widget.ptr.PullToRefreshBase;
import com.rt.core.widget.ptr.PullToRefreshListView;
import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.GoodInfoEntity;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.PickerOrderInfoEntity;
import com.rt.taopicker.data.api.entity.scanBarcodeRespEntity.ScanBarcodeRespEntity;
import com.rt.taopicker.main.picker.adapter.OrderInfoAdapter;
import com.rt.taopicker.main.picker.adapter.PickerAdapter;
import com.rt.taopicker.main.picker.adapter.PickerDoneAdapter;
import com.rt.taopicker.main.picker.contract.IPickerContract;
import com.rt.taopicker.main.picker.listener.GoodItemClickListener;
import com.rt.taopicker.main.picker.presenter.PickerPresenter;
import com.rt.taopicker.main.temporary.activity.TemporaryActivity;
import com.rt.taopicker.util.DialogHelper;
import com.rt.taopicker.util.ToastUtil;
import com.rt.taopicker.util.UserInfoHelper;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.NoticeDialogWidget;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 拣货作业
 * Created by wangzhi on 2017/2/20.
 */

public class PickerActivity extends BaseActivity<IPickerContract.IPresenter> implements IPickerContract.IView, TextView.OnEditorActionListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener {

    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdt;

    @BindView(R.id.tv_user_code)
    protected TextView mUserCodeTv;

    @BindView(R.id.tv_user_name)
    protected TextView mUserNameTv;

    @BindView(R.id.tv_picker_region)
    protected TextView mPickerRegionTv;

    @BindView(R.id.tv_pick_order_no)
    protected TextView mPickOrderNoTv;

    @BindView(R.id.tv_vehicle_no)
    protected TextView mVehicleNoTv;

    @BindView(R.id.ll_vehicle_no_line)
    protected View mVehicleNoLineLl;

    @BindView(R.id.tv_vehicle_no_label)
    protected TextView mVehicleNoLabelTv;

    @BindView(R.id.ptrlv_view)
    protected PullToRefreshListView mTaskListView;

    @BindView(R.id.edt_vehicle_no)
    protected EditText mVehicleNoEdt;

    @BindView(R.id.tv_picked_btn)
    protected TextView mPickedBtnTv;

    private PickerAdapter mPickerAdapter;
    private OrderInfoAdapter mOrderInfoAdapter;
    protected ListView mListView;

    //拣货单信息
    private PickerOrderInfoEntity mPickerOrderInfo;

    //拣货单号
    private String mPickOrderNo;

    //载具信息
    private String mVehicleNos;

    private PopupWindow mFinalPopupWindow = null;

    @Override
    public int getRootViewResId() {
        return R.layout.picker_activity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mPickOrderNo = getIntent().getStringExtra("pickOrderNo");

        mHeadTitleWdt.init(new HeadTitleVo("拣货作业-拣货明细", false, Constant.HeadTitleFuncType.NONE));

        EmployeeLoginRespEntity employeeLoginRespEntity = UserInfoHelper.getCurrentEmployeeInfo();
        if (employeeLoginRespEntity != null) {
            mUserCodeTv.setText(employeeLoginRespEntity.getEmployeeNo());
            mUserNameTv.setText(employeeLoginRespEntity.getEmployeeName());
            mPickerRegionTv.setText(employeeLoginRespEntity.getPickAreaName());
        }

        mTaskListView.setOnRefreshListener(this);
        mTaskListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mListView = mTaskListView.getRefreshableView();
        mVehicleNoEdt.setOnEditorActionListener(this);
        mVehicleNoEdt.requestFocus();
    }

    @OnClick(R.id.ll_vehicle_no_line)
    protected void onVehicleNoLineClick(View view){
        if (mVehicleNos.length() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("载具详情");
            builder.setMessage(mVehicleNos);
            builder.setCancelable(false);

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create().show();
        }
    }

    @OnClick(R.id.tv_picked_btn)
    protected void onPickedBtnClick(View view){
        mPresenter.getPickedData(mPickOrderNo);
    }

    public static Intent newIntent(Context context, String pickOrderNo) {
        Intent intent = new Intent(context, PickerActivity.class);
        intent.putExtra("pickOrderNo", pickOrderNo);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closePopupWindow();
    }

    @Override
    public IPickerContract.IPresenter getPresenter() {
        return new PickerPresenter();
    }

    /**
     * 缺货标记
     */
    private void showSignStockOutDialog(final GoodInfoEntity goodInfo) {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        window.setContentView(R.layout.picker_out_stock_dialog);

        TextView productName = (TextView) window.findViewById(R.id.tv_good_name);
        TextView productSpec = (TextView) window.findViewById(R.id.tv_good_spec);
        TextView barCode1 = (TextView) window.findViewById(R.id.tv_bar_code);
        TextView productPcs = (TextView) window.findViewById(R.id.tv_num);

        productName.setText(goodInfo.getProductName());
        productSpec.setText(goodInfo.getProductSpec());
        barCode1.setText(goodInfo.getRtNo());

        productPcs.setText("x" + goodInfo.getProductPcs());

        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.tv_btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
                mPresenter.signStockOut(goodInfo, mPickerOrderInfo.getPickOrderNo());
            }
        });

        // 关闭alert对话框架
        TextView cancel = (TextView) window.findViewById(R.id.tv_btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
    }

    /**
     * 展示载具信息
     *
     * @param vehicleNo
     */
    private void showVehicleNo(List<String> vehicleNo) {
        String temp = "";
        for (int i = 0; i < vehicleNo.size(); i++) {
            if (temp.length() == 0) {
                temp = vehicleNo.get(i);
            } else {
                temp += "," + vehicleNo.get(i);
            }
        }
        mVehicleNos = temp;

        if (mVehicleNos.indexOf(",") > -1) {
            mVehicleNoTv.setText(mVehicleNos.substring(0, mVehicleNos.indexOf(",")));
        } else {
            mVehicleNoTv.setText(mVehicleNos);
        }

        if (mVehicleNos.length() > 0) {
            mVehicleNoLineLl.setVisibility(View.VISIBLE);
            mVehicleNoLabelTv.setText("商品/载具");
            mVehicleNoEdt.setHint("请刷入拣货商品/增加载具");
        } else {
            mVehicleNoLineLl.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 展示需要选择称重品弹框
     */
    private void showSelectOrderInfoDialog(final ScanBarcodeRespEntity model) {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        window.setContentView(R.layout.picker_order_select_dialog);

        TextView productName = (TextView) window.findViewById(R.id.tv_good_name);
        TextView weightView = (TextView) window.findViewById(R.id.tv_weight);
        TextView barCode1 = (TextView) window.findViewById(R.id.tv_bar_code);

        ListView listView = (ListView) window.findViewById(R.id.lv_order_info);
        mOrderInfoAdapter = new OrderInfoAdapter(getApplicationContext(), model.getGoodsSelectInfo().getGoodsList());
        listView.setAdapter(mOrderInfoAdapter);
        listView.setOnItemClickListener(this);
        mOrderInfoAdapter.notifyDataSetChanged();

        productName.setText(model.getBarcodeInfo().getProductName());
        barCode1.setText(model.getBarcodeInfo().getRtNo());
        weightView.setText(model.getBarcodeInfo().getNum() + "g");

        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.tv_btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GoodInfoEntity selectGood = null;
                if (mOrderInfoAdapter.getSelectItem() > -1) {
                    selectGood = model.getGoodsSelectInfo().getGoodsList().get(mOrderInfoAdapter.getSelectItem());
                }

                if (selectGood == null) {
                    Toast.makeText(getApplicationContext(), "请选择刷入商品是哪张订单", Toast.LENGTH_SHORT).show();
                } else {
                    commitBrushGood(model.getBarcodeInfo().getRtNo(), selectGood.getOrderGoodsId(), model.getBarcodeInfo().getNum(), model.getBarcodeInfo().getPrice(), 0);

                    dlg.cancel();
                }
            }
        });

        // 关闭alert对话框架
        TextView cancel = (TextView) window.findViewById(R.id.tv_btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mOrderInfoAdapter.setSelectItem(i);

        for (int j = 0; j < adapterView.getChildCount(); j++) {
            View v = adapterView.getChildAt(j);
            if (v != null) {
                v.findViewById(R.id.list_item).setBackgroundColor(Color.WHITE);
            }
        }
        view.findViewById(R.id.list_item).setBackgroundColor(getResources().getColor(R.color.rowSelect));
    }

    /**
     * 刷入条码
     * @param barCode
     */
    private void brushBarCode(String barCode){
        mPresenter.brushBarCode(mPickOrderNo, barCode, null, null, null, null, null);
    }

    /**
     * 确认刷入商品。条码已经到后台解析过一次之后就调用此方法刷入商品
     * @param rtNo
     * @param orderGoodsId
     * @param num
     * @param price
     */
    private void commitBrushGood(String rtNo, Long orderGoodsId, Integer num, Double price, Integer isSubmit){
        mPresenter.brushBarCode(mPickOrderNo, null, rtNo, orderGoodsId, num, price, isSubmit);
    }

    /**
     * 展示强制提交提示框
     */
    private void showForceSubmitDialog(final ScanBarcodeRespEntity model){
        LinearLayout dialogView = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.picker_tip_dialog, null, false);
        TextView goodNameTv = (TextView) dialogView.findViewById(R.id.tv_good_name);
        TextView msgTv = (TextView) dialogView.findViewById(R.id.tv_msg);
        goodNameTv.setText(model.getBarcodeInfo().getProductName());
        msgTv.setText(Html.fromHtml(model.getMessage()));

        DialogHelper.getInstance().showDialog(dialogView, true, "取消", "提交", null, new NoticeDialogWidget.DialogListener(){

            @Override
            public void onClick() {
                commitBrushGood(model.getBarcodeInfo().getRtNo(), model.getBarcodeInfo().getOrderGoodsId(), model.getBarcodeInfo().getNum(), model.getBarcodeInfo().getPrice(), 1);
            }
        });

    }

    /**
     * 扫码监听事件
     *
     * @param v
     * @param actionId
     * @param event
     * @return
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.edt_vehicle_no: {
                if ((event != null && event.getAction() == MotionEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    String brushNum = v.getText().toString().trim();
                    if (brushNum.startsWith("\n")) {
                        brushNum = brushNum.substring(1);
                    }
                    if (brushNum.length() > 0) {
                        brushBarCode(brushNum);

                        v.setText(""); //清空刷入的内容
                        v.requestFocus();
                    }

                    //关闭键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mVehicleNoEdt.getWindowToken(), 0);
                    return true;
                }
                break;
            }
        }
        return false;
    }

    /**
     * 拣货完成提示框
     * @param type 完成类型
     * @param message 提示
     */
    private void showCompleteDialog(Integer type, String message){
        LinearLayout dialogView = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.picker_complete_dialog, null, false);
        TextView stallNoView = (TextView) dialogView.findViewById(R.id.tv_stall_no);
        TextView dialog_label1 = (TextView) dialogView.findViewById(R.id.tv_dialog_label1);
        TextView dialog_label2 = (TextView) dialogView.findViewById(R.id.tv_dialog_label2);

        NoticeDialogWidget dialogFragment = new NoticeDialogWidget();
        dialogFragment.setView(dialogView);
        dialogFragment.setCancelable(false);
        dialogFragment.setPositiveListener(new NoticeDialogWidget.DialogListener() {
            @Override
            public void onClick() {
                //回到抢单页面
                gotoPickerWait();
            }
        });

        if(type == 5){ //拣货完成到达道口
            dialog_label1.setText("请挂上流水线到达"); //Html.fromHtml("请挂上流水线到达<font color='#d7063b'><big>15</big></font>道口");
            stallNoView.setText(mPickerOrderInfo.getStallNo());
            dialog_label2.setText("道口");
        }else if(type == 6){ //拣货完成到达备用口
            dialog_label1.setText("请挂上流水线到达");
            stallNoView.setText("备用道口");

            dialogFragment.setNegativeListener(new NoticeDialogWidget.DialogListener() {
                @Override
                public void onClick() {
                    //备用口暂存界面
                    Intent intent = TemporaryActivity.newIntent(PickerActivity.this);
                    startActivity(intent);
                    finish();
                }
            });
            dialogFragment.setPositiveButtonText("确定");
            dialogFragment.setNegativeButtonText("放入暂存箱");
            dialogFragment.setNegative(true);
        }else if(type == 9){ //全部缺货
            dialog_label1.setText(message);
        }
        dialogFragment.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    /**
     * 跳到抢单页面
     */
    private void gotoPickerWait() {
        Intent intent = new Intent(PickerActivity.this, PickerWaitActivity.class);
        startActivity(intent);
        finish();
    }




    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        mPresenter.initPickerOrderInfo(mPickOrderNo);
    }



    private void initDialog(final PickerOrderInfoEntity pickedOrderInfo) {
        if (mFinalPopupWindow != null) {
            View view = mFinalPopupWindow.getContentView();

            ListView pickedListView = (ListView) view.findViewById(R.id.lv_picked_goods_info);
            View noDataLl = view.findViewById(R.id.ll_no_data);
            if(pickedOrderInfo.getGoodsList() == null || pickedOrderInfo.getGoodsList().size() == 0){
                noDataLl.setVisibility(View.VISIBLE);
                pickedListView.setVisibility(View.GONE);
            }else{
                noDataLl.setVisibility(View.GONE);
                pickedListView.setVisibility(View.VISIBLE);

                PickerDoneAdapter adapter = new PickerDoneAdapter(this, getApplicationContext(), pickedOrderInfo.getGoodsList(), null);
                pickedListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            TextView closeTv = (TextView) view.findViewById(R.id.tv_close);
            closeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closePopupWindow();
                }
            });

            View shadowView = view.findViewById(R.id.v_shadow);
            View minShadowView = view.findViewById(R.id.v_min_shadow);
            shadowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closePopupWindow();
                }
            });
            minShadowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closePopupWindow();
                }
            });

        }

    }

    private void closePopupWindow() {
        try {
            if (mFinalPopupWindow != null) {
                mFinalPopupWindow.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 展示拣货单信息
     */
    @Override
    public void showPickerOrderInfo(PickerOrderInfoEntity model, Integer type, String message) {
        mPickerOrderInfo = model;

        mPickerAdapter = new PickerAdapter(this, getApplicationContext(), mPickerOrderInfo.getGoodsList(), new GoodItemClickListener() {
            @Override
            public void onClick(GoodInfoEntity goodInfo) {
                if (mVehicleNos.length() > 0) { //必须先刷载具，才允许标记缺货
                    showSignStockOutDialog(goodInfo);
                }
            }
        });
        mListView.setAdapter(mPickerAdapter);
        mPickerAdapter.notifyDataSetChanged();

        mPickOrderNoTv.setText(mPickerOrderInfo.getPickOrderNo());
        mVehicleNoEdt.setText("");
        showVehicleNo(mPickerOrderInfo.getVehicleNoList());

        mTaskListView.onRefreshComplete();


        if(type == 5){ //拣货完成到达真实道口
            showCompleteDialog(type, message);
        }else if(type == 6){ //拣货完成到达备用口
            showCompleteDialog(type, message);
        }else if(type == -2){ //拣货完成失败
            DialogHelper.getInstance().showDialog(message);
        }else if(type == 9){ //拣货单全部缺货
            showCompleteDialog(type, message);
        }
    }

    @Override
    public void queryPickerOrderInfoFail() {
        mTaskListView.onRefreshComplete();

        //回到抢单页面
        gotoPickerWait();
    }

    @Override
    public void queryPickerOrderInfoError() {
        mTaskListView.onRefreshComplete();
    }

    @Override
    public void scanBarcodeSuccess(ScanBarcodeRespEntity model) {
        if(model.getResultType() == 0 || model.getResultType() == -1){ //请刷入载具、条码错误、查无此商品,刷入载具失败
            DialogHelper.getInstance().showDialog(model.getMessage());
        }else if(model.getResultType() == 1){ //载具刷入成功
            showPickerOrderInfo(model.getPickOrderInfo(), model.getResultType(), model.getMessage());
            DialogHelper.getInstance().showDialog(model.getMessage());
        }else if(model.getResultType() == 2){ //商品扫入成功
            showPickerOrderInfo(model.getPickOrderInfo(), model.getResultType(), model.getMessage());
            ToastUtil.showToast(this, getApplicationContext(), "拣货成功", 300);
        }else if(model.getResultType() == 3){ //提示是否强制提交
            showForceSubmitDialog(model);
        }else if(model.getResultType() == 4){ //选择称重品
            showSelectOrderInfoDialog(model);
        }else if(model.getResultType() == 5 || model.getResultType() == 6 || model.getResultType() == 9 || model.getResultType() == -2){ //拣货完成
            showPickerOrderInfo(model.getPickOrderInfo(), model.getResultType(), model.getMessage());
        }
    }

    @Override
    public void showPickedDialog(PickerOrderInfoEntity pickedOrderInfo) {
        LinearLayout parent = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.picker_done_dialog, null);
        //初始化PopupWindow
        PopupWindow popupWindow = new PopupWindow(parent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(0);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        //不占用状态栏，解决输入框问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(true);
                //true 占用状态栏,false 不占用状态栏
                mLayoutInScreen.set(popupWindow, false);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        popupWindow.showAtLocation(this.findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
        mFinalPopupWindow = popupWindow;

        initDialog(pickedOrderInfo);
    }

    @Override
    public String getPickOrderNo() {
        return mPickOrderNo;
    }
}
