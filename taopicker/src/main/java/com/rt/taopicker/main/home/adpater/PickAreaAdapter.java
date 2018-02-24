package com.rt.taopicker.main.home.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.data.api.entity.queryPickAreaListRespEntity.PickArea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoguangyao on 2018/1/25.
 */

public class PickAreaAdapter extends BaseAdapter {
    private List<PickArea> mDatas = new ArrayList<>();
    private Context mContext;

    public PickAreaAdapter(Context context) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.home_employee_login_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mAreaName = view.findViewById(R.id.tv_area_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        PickArea pickArea = (PickArea) getItem(i);
        viewHolder.mAreaName.setText(pickArea.getPickAreaName());

        view.setTag(R.id.tag_area_code, pickArea.getPickAreaNo());
        view.setTag(R.id.tag_area_name, pickArea.getPickAreaName());
        return view;
    }

    public void setDatas(List<PickArea> datas) {
        if (datas != null) {
            mDatas = datas;
        } else {
            mDatas.clear();
        }
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView mAreaName;
    }


}
