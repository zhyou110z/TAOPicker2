package com.rt.core.widget.ptr;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.rt.core.R;
import com.rt.core.widget.ptr.internal.FlipLoadingLayout;
import com.rt.core.widget.ptr.internal.LoadingLayout;

/**
 * FN下拉刷新列表
 * Created by shengdong.huang on 2017/7/7.
 */
public class FNPullToRefreshListView extends PullToRefreshAdapterViewBase<FNPullToRefreshListView.PTRListView> {

    private BaseAdapter adapter;

    public FNPullToRefreshListView(Context context) {
        super(context);
        setMode(Mode.PULL_FROM_START);
    }

    public FNPullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMode(Mode.PULL_FROM_START);
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        if (getRefreshableView() != null) {
            getRefreshableView().setAdapter(adapter);
        }
    }

    @Override
    protected LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray attrs) {
        LoadingLayout loadingLayout = new FlipLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
        loadingLayout.setVisibility(INVISIBLE);
        return loadingLayout;
    }

    @Override
    protected PTRListView createRefreshableView(Context context, AttributeSet attrs) {
        final PTRListView listView = new PTRListView(context, attrs);
        listView.setId(R.id.listview);
        if (adapter != null) {
            listView.setAdapter(adapter);
        }
        return listView;
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected boolean isReadyForPullStart() {
        if (mRefreshableView.getChildCount() <= 0)
            return true;
        int firstVisiblePosition = mRefreshableView.getFirstVisiblePosition();
        if (firstVisiblePosition == 0)
            return mRefreshableView.getChildAt(0).getTop() == 0;
        else
            return false;
    }

    public static class PTRListView extends ListView {

        public PTRListView(Context context) {
            super(context);
        }

        public PTRListView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public PTRListView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                       int scrollRangeX, int scrollRangeY, int maxOverScrollX,
                                       int maxOverScrollY, boolean isTouchEvent) {
            return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                    maxOverScrollX, 0, isTouchEvent);
        }
    }
}
