package com.rt.taopicker.main.login.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoguangyao on 2018/1/25.
 */

public class StoreAdapter extends BaseAdapter {
    private List<Store> mDatas = new ArrayList<>();
    private Context mContext;
    private int mChoosePosition;

    public StoreAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.login_store_list_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mStoreTv = view.findViewById(R.id.tv_store);
            viewHolder.mLineV = view.findViewById(R.id.v_line);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Store store = (Store) getItem(i);
        viewHolder.mStoreTv.setText(store.getStoreName());
        if (mChoosePosition != i) {
            viewHolder.mStoreTv.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            viewHolder.mStoreTv.setBackgroundColor(Color.parseColor("#ffeaef"));
        }

        if (i == mDatas.size() - 1) {
            viewHolder.mLineV.setVisibility(View.GONE);
        } else {
            viewHolder.mLineV.setVisibility(View.VISIBLE);
        }

        view.setTag(R.id.tag_store_code, store.getStoreNo());
        view.setTag(R.id.tag_store_name, store.getStoreName());
        return view;
    }

    public void setDatas(List<Store> datas) {
        if (datas != null) {
            mDatas = datas;
        } else {
            mDatas.clear();
        }
        notifyDataSetChanged();
    }

    public void setChoosePosition(int choosePosition) {
        mChoosePosition = choosePosition;
    }

    class ViewHolder {
        TextView mStoreTv;
        View mLineV;
    }
}
