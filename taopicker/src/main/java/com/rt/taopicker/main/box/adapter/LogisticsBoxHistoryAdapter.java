package com.rt.taopicker.main.box.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.main.box.vo.LogisticsBoxVo;
import com.rt.taopicker.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 17/1/6.
 */

public class LogisticsBoxHistoryAdapter extends BaseAdapter {


    private Context context;

    private List<LogisticsBoxVo> data = new ArrayList<LogisticsBoxVo>();


    /**
     * 构造函数
     *
     * @param context 页面正文
     */
    public LogisticsBoxHistoryAdapter(Context context) {
        this.context = context;
    }

    public void addLastModel(LogisticsBoxVo vo) {
        data.add(vo);
        // 刷新
        notifyDataSetChanged();
    }

    public void addList(List<LogisticsBoxVo> boxList) {
        data.addAll(boxList);
        // 刷新
        notifyDataSetChanged();
    }

    public void addFirstModel(LogisticsBoxVo vo) {
        data.add(0, vo);
        // 刷新
        notifyDataSetChanged();
    }

    public void clear() {
        // 先清空
        data.clear();
        notifyDataSetChanged();
    }

    /**
     * @param model
     * @return true 包含 false 不包含
     */
    public boolean checkContain(LogisticsBoxVo model) {
        for (LogisticsBoxVo boxModel : data) {
            if (StringUtil.equals(boxModel.getBoxNo(), model.getBoxNo())) {
                return true;
            }
        }
        return false;
    }

    public List<LogisticsBoxVo> getData() {
        return data;
    }

    public String getBoxIds() {
        StringBuffer sb = new StringBuffer();
        for (LogisticsBoxVo model : data) {
            sb.append(model.getBoxNo()).append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.logistics_box_history_item, parent, false);
            holder = new ViewHolder();
            holder.boxNoTv = convertView.findViewById(R.id.tv_box_no);
            holder.boxTimeTv = convertView.findViewById(R.id.tv_box_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LogisticsBoxVo boxModel = (LogisticsBoxVo) getItem(position);
        holder.boxNoTv.setText(boxModel.getBoxNo());
        holder.boxTimeTv.setText(boxModel.getCreateTime());
        return convertView;
    }

    public class ViewHolder {
        TextView boxNoTv;
        TextView boxTimeTv;
    }
}
