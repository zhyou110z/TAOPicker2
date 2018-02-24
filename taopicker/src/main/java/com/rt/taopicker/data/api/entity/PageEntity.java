package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.config.AppConfig;
import com.rt.taopicker.config.Constant;

/**
 * 分页
 * Created by yaoguangyao on 2017/12/20.
 */
public class PageEntity {
    //每页行数
    protected Integer pageSize = AppConfig.sPageSize;
    //当前页
    protected Integer curPage = 1;

    //加载类型
    private Integer loadType = Constant.ListLoadType.REFRESH;

    /**
     * 下一步
     */
    public void next() {
        loadType = Constant.ListLoadType.LOAD_MORE;
        curPage ++;
    }

    /**
     * 刷新
     */
    public void refresh() {
        loadType = Constant.ListLoadType.REFRESH;
        curPage = 1;
    }

    /**
     * 判断是否需要清空数据
     * @return
     */
    public Boolean needClear() {
        return loadType.equals(Constant.ListLoadType.REFRESH);
    }


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public boolean isShowLoading() {
        return curPage == null;
    }

    public Integer getLoadType() {
        return loadType;
    }

    public void setLoadType(Integer loadType) {
        this.loadType = loadType;
    }
}
