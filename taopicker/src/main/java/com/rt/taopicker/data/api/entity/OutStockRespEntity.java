package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

import java.util.List;

/**
 * Created by zhouyou on 2018/1/30.
 *
 * 缺货商品数据
 */

public class OutStockRespEntity extends BaseEntity {


    private int curPage;

    private PageData pageData;

    public class PageData{

        private List<OutStockGoodRespEntity> goodsList;

        private String storeNo;

        public List<OutStockGoodRespEntity> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<OutStockGoodRespEntity> goodsList) {
            this.goodsList = goodsList;
        }

        public String getStoreNo() {
            return storeNo;
        }

        public void setStoreNo(String storeNo) {
            this.storeNo = storeNo;
        }
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public PageData getPageData() {
        return pageData;
    }

    public void setPageData(PageData pageData) {
        this.pageData = pageData;
    }
}
