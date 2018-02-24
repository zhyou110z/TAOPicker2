package com.rt.taopicker.main.home.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoguangyao on 2017/12/18.
 */

public class MenuAdapter extends BaseAdapter {
    private List<Menu> mDatas = new ArrayList<>();
    private Context mContext;

    public MenuAdapter(Context context) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.home_menu_grid_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mMenuIconIv = view.findViewById(R.id.iv_menu_icon);
            viewHolder.mMenuNameTv = view.findViewById(R.id.tv_menu_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Menu menu = (Menu) getItem(i);
        if (menu.getMenuIcon() != null) {
            viewHolder.mMenuIconIv.setImageResource(menu.getMenuIcon());
        }
        viewHolder.mMenuNameTv.setText(menu.getMenuName());

        view.setTag(R.id.tag_menu_code, menu.getMenuCode());
        view.setTag(R.id.tag_menu_name, menu.getMenuName());
        return view;
    }


    public void setDatas(List<Menu> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas = datas;
        } else {
            mDatas.clear();
        }
        notifyDataSetChanged();
    }

    class ViewHolder {
        LinearLayout mMenuLl;
        ImageView mMenuIconIv;
        TextView mMenuNameTv;
    }
}
