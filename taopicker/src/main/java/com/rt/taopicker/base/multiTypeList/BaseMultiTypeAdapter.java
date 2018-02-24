package com.rt.taopicker.base.multiTypeList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rt.taopicker.base.NullEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yaoguangyao on 2017/8/28.
 * 多类型Adapter
 */

public abstract class BaseMultiTypeAdapter extends BaseAdapter {
    private List mDatas = new ArrayList<>(); //item的 数据列表
    private List<Integer> mViewTypes = new ArrayList<>(); //item的 类型列表
    private Map<Integer, TypeDesc> mTypeDescMap = new HashMap<>(); //key为valueType
    protected Context mContext;

    public static class ViewType {
        public static final Integer NO_DATA = 0;
        public static final Integer NO_MORE = 1;
    }

    public BaseMultiTypeAdapter(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        addHoldView(new TypeDesc(ViewType.NO_MORE, NoMoreView.class));
        addHoldView(new TypeDesc(ViewType.NO_DATA, NoDataView.class));
    }

    /**
     * 添加数据类型元数据
     */
    protected void addHoldView(TypeDesc typeDesc) {
        mTypeDescMap.put(typeDesc.getViewType(), typeDesc);
    }

    /**
     * 清空列表
     */
    public void clear() {
        mDatas.clear();
        mViewTypes.clear();
    }

    public Integer getDatasSize() {
        return mDatas.size();
    }

    /**
     * 添加数据模型 和 类型
     * 需要子类notifyDataSetChanged
     * @param data
     * @param viewType
     * @return index ，从0开始
     */
    protected int addData(Object data, Integer viewType) {
        mDatas.add(data);
        mViewTypes.add(viewType);
        return mDatas.size() - 1;
    }

    /**
     * 添加没有更多
     */
    public void addNoMore() {
        addData(new NullEntity(), ViewType.NO_MORE);
        notifyDataSetChanged();
    }

    /**
     * 添加没有数据
     */
    public void addNoData(String msg) {
        clear();
        addData(new NoDataEntity(msg), ViewType.NO_DATA);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolderView holderView;
        Integer viewType = mViewTypes.get(position);
        //根据viewType，发现已经缓存了该类型的holderView，则复用，否则创建新的。
        if (convertView == null || !(convertView instanceof BaseHolderView)) {
            holderView = createHolderView(viewType, parent);
        } else {
            holderView = (BaseHolderView) convertView;
        }

//        Log.d("flyzing", "getView: " + holderView.toString());
        Object item = getItem(position);
        holderView.bind(item, getItemId(position));
        return (View) holderView;
    }

    /**
     * 根据不同viewType，创建不同类的holderView
     *
     * @param viewType
     * @return
     */
    public BaseHolderView createHolderView(Integer viewType, ViewGroup parent) {
        BaseHolderView holderView = null;
        TypeDesc typeDesc = mTypeDescMap.get(viewType);
        if (typeDesc != null) {
            Class<? extends BaseHolderView> holderViewClass = typeDesc.getHolderViewClass();
            if (holderViewClass != null) {
                try {
                    Constructor constructor = holderViewClass.getConstructor(Context.class);
                    holderView = (BaseHolderView) constructor.newInstance(mContext);
                    holderView.setTypeDesc(typeDesc);
                    holderView.setParentGroup(parent);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }


        return holderView;
    }

    /**
     * 实现多类型列表 必须实现两个方法之一
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mViewTypes.get(position);
    }

    /**
     * 实现多类型列表 必须实现两个方法之一，如果没有实现，则convertView 无法复用
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return mTypeDescMap.size();
    }

}
