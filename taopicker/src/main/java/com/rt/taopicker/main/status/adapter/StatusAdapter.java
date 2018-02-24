package com.rt.taopicker.main.status.adapter;

import android.content.Context;

import com.rt.taopicker.base.multiTypeList.BaseMultiTypeAdapter;
import com.rt.taopicker.base.multiTypeList.TypeDesc;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.BoxInfoEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.GoodStatusEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.PickAreaGoodEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.StallNoInfoEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.VehicleInfoEntity;
import com.rt.taopicker.main.status.adapter.holderView.BoxHeadView;
import com.rt.taopicker.main.status.adapter.holderView.GoodRegionView;
import com.rt.taopicker.main.status.adapter.holderView.GoodStatusInfoView;
import com.rt.taopicker.main.status.adapter.holderView.StallNoHeadView;
import com.rt.taopicker.main.status.adapter.holderView.VehicleHeadView;
import com.rt.taopicker.main.status.vo.GoodsRegionVo;

/**
 * Created by chensheng on 2018/1/31.
 */

public class StatusAdapter extends BaseMultiTypeAdapter {

    public StatusAdapter(Context context) {
        super(context);
        init();
    }

    protected void init() {
        addHoldView(new TypeDesc(ViewType.BOX_HEAD, BoxHeadView.class));
        addHoldView(new TypeDesc(ViewType.GOOD_REGION, GoodRegionView.class));
        addHoldView(new TypeDesc(ViewType.GOOD_STATUS_INFO, GoodStatusInfoView.class));
        addHoldView(new TypeDesc(ViewType.STALL_NO_HEAD, StallNoHeadView.class));
        addHoldView(new TypeDesc(ViewType.VEHICLE_HEAD, VehicleHeadView.class));
    }


    public static class ViewType {
        public static final Integer BOX_HEAD = 2;
        public static final Integer GOOD_REGION = 3;
        public static final Integer GOOD_STATUS_INFO = 4;
        public static final Integer STALL_NO_HEAD = 5;
        public static final Integer VEHICLE_HEAD = 6;
    }


    public void addVehicleInfoModelList(VehicleInfoEntity vehicleInfoModel) {
        addVehicleHeaderRow(vehicleInfoModel);
        addGoodRegionRow("拣货单商品：", vehicleInfoModel.getPickAreaName());

        if (vehicleInfoModel.getPickGoodsList() != null) {
            for (int i = 0; i < vehicleInfoModel.getPickGoodsList().size(); i++) {
                if (i == vehicleInfoModel.getPickGoodsList().size() - 1) {
                    addGoodStatusInfoRow(vehicleInfoModel.getPickGoodsList().get(i), false);
                } else {
                    addGoodStatusInfoRow(vehicleInfoModel.getPickGoodsList().get(i), true);
                }
            }
        }
        // 刷新
        notifyDataSetChanged();
    }

    public void addBoxInfoModelList(BoxInfoEntity boxInfoModel) {
        addBoxHeaderRow(boxInfoModel);

        if (boxInfoModel.getPickOrderList() != null) {
            for (int i = 0; i < boxInfoModel.getPickOrderList().size(); i++) {
                PickAreaGoodEntity pickAreaGoodModel = boxInfoModel.getPickOrderList().get(i);

                if (i == 0) {
                    addGoodRegionRow("物流箱波次全部商品：", pickAreaGoodModel.getPickAreaName());
                } else {
                    addGoodRegionRow("", pickAreaGoodModel.getPickAreaName());
                }

                for (int j = 0; j < pickAreaGoodModel.getPickGoodsList().size(); j++) {
                    if (j == pickAreaGoodModel.getPickGoodsList().size() - 1) {
                        addGoodStatusInfoRow(pickAreaGoodModel.getPickGoodsList().get(j), false);
                    } else {
                        addGoodStatusInfoRow(pickAreaGoodModel.getPickGoodsList().get(j), true);
                    }

                }
            }
        }

        // 刷新
        notifyDataSetChanged();
    }

    public void addStallNoInfoModelList(StallNoInfoEntity stallNoInfoModel) {
        addStallNoHeaderRow(stallNoInfoModel);

        if (stallNoInfoModel.getPickOrderList() != null) {
            for (int i = 0; i < stallNoInfoModel.getPickOrderList().size(); i++) {
                PickAreaGoodEntity pickAreaGoodModel = stallNoInfoModel.getPickOrderList().get(i);
                if (i == 0) {
                    addGoodRegionRow("道口波次全部商品：", pickAreaGoodModel.getPickAreaName());
                } else {
                    addGoodRegionRow("", pickAreaGoodModel.getPickAreaName());
                }

                for (int j = 0; j < pickAreaGoodModel.getPickGoodsList().size(); j++) {
                    if (j == pickAreaGoodModel.getPickGoodsList().size() - 1) {
                        addGoodStatusInfoRow(pickAreaGoodModel.getPickGoodsList().get(j), false);
                    } else {
                        addGoodStatusInfoRow(pickAreaGoodModel.getPickGoodsList().get(j), true);
                    }
                }
            }
        }

        // 刷新
        notifyDataSetChanged();
    }


    /**
     * 构造渲染列表
     */
    public void renderList(BoxInfoEntity boxInfoModel) {
        // 先清空
        clear();
        addBoxInfoModelList(boxInfoModel);
    }

    /**
     * 构造渲染列表
     */
    public void renderList(StallNoInfoEntity stallNoInfoModel) {
        // 先清空
        clear();
        addStallNoInfoModelList(stallNoInfoModel);
    }

    /**
     * 构造渲染列表
     */
    public void renderList(VehicleInfoEntity vehicleInfoModel) {
        // 先清空
        clear();
        addVehicleInfoModelList(vehicleInfoModel);
    }


    public void addVehicleHeaderRow(VehicleInfoEntity vehicleInfoModel) {
        addData(vehicleInfoModel, ViewType.VEHICLE_HEAD);
    }

    public void addBoxHeaderRow(BoxInfoEntity boxInfoModel) {
        addData(boxInfoModel, ViewType.BOX_HEAD);
    }

    public void addStallNoHeaderRow(StallNoInfoEntity stallNoInfoModel) {
        addData(stallNoInfoModel, ViewType.STALL_NO_HEAD);
    }

    public void addGoodRegionRow(String regionLabel, String region) {
        GoodsRegionVo vo = new GoodsRegionVo();
        vo.setRegion(region);
        vo.setRegionLabel(regionLabel);
        addData(vo, ViewType.GOOD_REGION);
    }

    public void addGoodStatusInfoRow(GoodStatusEntity goodStatusModel, boolean needDivide) {
        goodStatusModel.setNeedDivide(needDivide);
        addData(goodStatusModel, ViewType.GOOD_STATUS_INFO);
    }

}
