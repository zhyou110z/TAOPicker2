package com.rt.taopicker.main.lack.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.rt.core.widget.ptr.PullToRefreshBase;
import com.rt.core.widget.ptr.PullToRefreshScrollView;
import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.ConfirmGoodsExceptReqEntity;
import com.rt.taopicker.data.api.entity.ConfirmGoodsExceptRespEntity;
import com.rt.taopicker.data.api.entity.OutStockGoodRespEntity;
import com.rt.taopicker.data.api.entity.OutStockReqEntity;
import com.rt.taopicker.data.api.entity.OutStockRespEntity;
import com.rt.taopicker.main.lack.adapter.DataTaskLisener;
import com.rt.taopicker.main.lack.adapter.OutStockAdapter;
import com.rt.taopicker.main.lack.contract.IOutStockContract;
import com.rt.taopicker.main.lack.presenter.OutStockPresenter;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.vo.HeadTitleVo;


import java.util.List;

import butterknife.BindView;

/**
 * 缺货待盘点
 * Created by zhouyou on 2018/1/30.
 */

public class OutStockActivity extends BaseActivity<IOutStockContract.IPresenter>
        implements IOutStockContract.IView ,PullToRefreshBase.OnRefreshListener{

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context,OutStockActivity.class);
        return intent;
    }

    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdt;

    @BindView(R.id.ll_container)
    protected LinearLayout mContainerll;

    @BindView(R.id.pull_to_refresh_listview)
    protected PullToRefreshScrollView mPullToRefreshScrollView;

    @BindView(R.id.ll_out_stock_nodata)
    protected LinearLayout mOutStockNodata;


    @BindView(R.id.blank_title)
    protected TextView mOutStockNodataText;


    @BindView(R.id.recyclerView_outstock)
    protected RecyclerView mListview;

    private OutStockAdapter mOutStockAdapter;

    private int mCurPage = 1;
    private int mPageNum = 20;

    @Override
    public int getRootViewResId() {
        return R.layout.out_stock_activity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mHeadTitleWdt.init(new HeadTitleVo("缺货待盘点", false, Constant.HeadTitleFuncType.HOME));
        mListview.setHasFixedSize(true);
        mListview.setLayoutManager(new LinearLayoutManager(this));
        mPullToRefreshScrollView.setOnRefreshListener(this);
        initData();
    }

    @Override
    public IOutStockContract.IPresenter getPresenter() {
        return new OutStockPresenter(this);
    }

    private void initData(){
        OutStockReqEntity req =  new OutStockReqEntity();
        req.setCurPage(String.valueOf(mCurPage));
        req.setPageNum(String.valueOf(mPageNum));
        mPresenter.outOfStock(req);
    }

    @Override
    public void loadDataSuccess(OutStockRespEntity resp) {
        mPullToRefreshScrollView.onRefreshComplete();
        if(resp != null && resp.getPageData()!=null && resp.getPageData().getGoodsList() != null ){
            List<OutStockGoodRespEntity>  list =  resp.getPageData().getGoodsList();

            if(mOutStockAdapter == null){
                mOutStockAdapter = new OutStockAdapter(this,list,mDataTaskLisener);
                mListview.setAdapter(mOutStockAdapter);
            }else {
                mOutStockAdapter.addData(list);
                mOutStockAdapter.notifyDataSetChanged();
            }

            if(list.size() == mPageNum){
                mCurPage ++;
                mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
            }else{
                mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.DISABLED);
            }

            showDataView();
        }
    }

    @Override
    public void loadDataFail() {
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        initData();
    }

    private DataTaskLisener mDataTaskLisener = new DataTaskLisener() {
        @Override
        public void onClick(String str) {
            handleButton(str);
        }
    };

    public void handleButton(final String goodNo) {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        window.setContentView(R.layout.out_stock_button_dialog);
        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vButton) {
                ConfirmGoodsExceptReqEntity req = new ConfirmGoodsExceptReqEntity();
                req.setProductNo(goodNo);
                mPresenter.confirmGoodsExcept(req);
                dlg.cancel();
            }
        });
        // 关闭alert对话框架
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });

    }

    @Override
    public void confirmGoodsExceptSuccess(String goodNo) {
        Toast.makeText(this, "确认排除成功", Toast.LENGTH_SHORT).show();
        mOutStockAdapter.delete(goodNo);
        showDataView();
    }

    private  void showDataView(){
        if (mOutStockAdapter.getItemCount() == 0){
            mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
            mListview.setVisibility(View.GONE);
            mOutStockNodataText.setText("当前无缺货待盘点数据");
            mOutStockNodata.setVisibility(View.VISIBLE);
        }else{
            mListview.setVisibility(View.VISIBLE);
            mOutStockNodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void confirmGoodsExceptFail() {

    }
}











