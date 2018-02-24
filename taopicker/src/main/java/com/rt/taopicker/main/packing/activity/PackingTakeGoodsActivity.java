package com.rt.taopicker.main.packing.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.TakeOutFromZcxRespEntity;
import com.rt.taopicker.data.api.entity.packageScanCodeRespEntity.PackageScanCodeRespEntity;
import com.rt.taopicker.main.packing.adapter.PackageAdapter;
import com.rt.taopicker.main.packing.contract.IPackingTakeGoodsContract;
import com.rt.taopicker.main.packing.presenter.PackingTakeGoodsPresenter;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.vo.EventBusVo;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * 暂存箱取货
 */

public class PackingTakeGoodsActivity extends BaseActivity<IPackingTakeGoodsContract.IPresenter> implements IPackingTakeGoodsContract.IView, TextView.OnEditorActionListener {

    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdt;

    //道口
    @BindView(R.id.tv_cross_no)
    protected TextView mCrossNoTv;

    //暂存箱号
    @BindView(R.id.edt_box_no)
    protected EditText mBoxNoEdt;

    //待取暂存箱列表
    @BindView(R.id.lv_wait_take)
    protected ListView mWaitTakeLv;

    //已取暂存箱列表
    @BindView(R.id.lv_already_take)
    protected ListView mAlreadyTakeLv;

    private PackageAdapter mNeedGetAdapter;

    private PackageAdapter mGetedAdapter;

    private PackageScanCodeRespEntity mPackageScanCodeRespEntity;

    private boolean mHasSuspender; //是否是吊挂包装

    @Override
    public int getRootViewResId() {
        return R.layout.packing_take_goods_activity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mPackageScanCodeRespEntity = (PackageScanCodeRespEntity) getIntent().getSerializableExtra("packageScanCodeRespEntity");
        mHasSuspender = getIntent().getBooleanExtra("hasSuspender", true);

        mHeadTitleWdt.init(new HeadTitleVo("暂存箱取货", true, Constant.HeadTitleFuncType.HOME));

        mNeedGetAdapter = new PackageAdapter(getApplicationContext(), 1);
        mGetedAdapter = new PackageAdapter(getApplicationContext(), 2);
        mWaitTakeLv.setAdapter(mNeedGetAdapter);
        mAlreadyTakeLv.setAdapter(mGetedAdapter);

        if(mPackageScanCodeRespEntity != null){
            mCrossNoTv.setText(mPackageScanCodeRespEntity.getStallNo());

            mNeedGetAdapter.setData(mPackageScanCodeRespEntity.getNeedZcxNos());
            mNeedGetAdapter.notifyDataSetChanged();
            mGetedAdapter.setData(mPackageScanCodeRespEntity.getGetZcxNos());
            mGetedAdapter.notifyDataSetChanged();
        }

        mBoxNoEdt.setOnEditorActionListener(this);
        mBoxNoEdt.requestFocus();
    }

    @Override
    public IPackingTakeGoodsContract.IPresenter getPresenter() {
        return new PackingTakeGoodsPresenter();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        EventBus.getDefault().post(new EventBusVo(Constant.EventCode.PACKAGE_TAKE_GOODS_BACK, "暂存箱取货返回事件"));
    }

    public static Intent newIntent(Context context, PackageScanCodeRespEntity entity, boolean hasSuspender) {
        Intent intent = new Intent(context, PackingTakeGoodsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("packageScanCodeRespEntity", entity);
        intent.putExtras(bundle);
        intent.putExtra("hasSuspender", hasSuspender);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.edt_box_no: {
                if ((event != null && event.getAction() == MotionEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    mPresenter.takeGoods(mBoxNoEdt.getText().toString(), mPackageScanCodeRespEntity, mHasSuspender);

                    //关闭键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mBoxNoEdt.getWindowToken(), 0);
                    return true;
                }
                break;
            }
        }
        return false;
    }


    @Override
    public void clearData() {
        mBoxNoEdt.setText("");
        mBoxNoEdt.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mBoxNoEdt.getWindowToken(), 0);
    }

    @Override
    public void refreshZcxListView(TakeOutFromZcxRespEntity entity) {
        mNeedGetAdapter.setData(entity.getNeedZcxNos());
        mNeedGetAdapter.notifyDataSetChanged();

        mGetedAdapter.setData(entity.getGetZcxNos());
        mGetedAdapter.notifyDataSetChanged();
    }
}
