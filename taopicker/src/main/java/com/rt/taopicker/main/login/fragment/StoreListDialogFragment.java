package com.rt.taopicker.main.login.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.Store;
import com.rt.taopicker.main.login.adapter.StoreAdapter;
import com.rt.taopicker.main.login.listener.IStoreListDialogListener;
import com.rt.taopicker.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yaoguangyao on 2018/1/25.
 */

public class StoreListDialogFragment extends DialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String ARGS_STORE_LIST = "storeList";

    @BindView(R.id.ll_store)
    protected ListView mStoreLv;
    private StoreAdapter mStoreAdapter;

    @BindView(R.id.tv_cancel)
    protected TextView mCancelTv;

    @BindView(R.id.tv_ok)
    protected TextView mOkTv;

    private IStoreListDialogListener mListener;

    private String mChooseStoreName;
    private String mChooseStoreCode;

    public void setListener(IStoreListDialogListener listener) {
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.login_store_list_dialog_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(DensityUtil.dip2px(280), DensityUtil.dip2px(273));
    }

    private void initView() {
        mStoreAdapter = new StoreAdapter(this.getContext());
        mStoreLv.setAdapter(mStoreAdapter);
        mStoreLv.setOnItemClickListener(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<Store> storeList = (ArrayList<Store>) bundle.getSerializable(ARGS_STORE_LIST);
            mStoreAdapter.setDatas(storeList);

            //默认选中第一个
            if (storeList != null && storeList.size() > 0) {
                Store store = storeList.get(0);
                if (store != null) {
                    mChooseStoreName = store.getStoreName();
                    mChooseStoreCode = store.getStoreNo();
                    mStoreAdapter.setChoosePosition(0);
                }
            }
        }

        mCancelTv.setOnClickListener(this);
        mOkTv.setOnClickListener(this);
    }

    public static StoreListDialogFragment newInstance(ArrayList<Store> storeList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGS_STORE_LIST, storeList);

        StoreListDialogFragment fragment = new StoreListDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_ok:
                if (mListener != null) {
                    mListener.chooseStore(mChooseStoreCode, mChooseStoreName);
                }
                dismiss();
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mChooseStoreName = (String) view.getTag(R.id.tag_store_name);
        mChooseStoreCode = (String) view.getTag(R.id.tag_store_code);

        mStoreAdapter.setChoosePosition(i);
        mStoreAdapter.notifyDataSetChanged();
    }
}
