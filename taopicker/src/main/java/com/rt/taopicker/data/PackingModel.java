package com.rt.taopicker.data;

import com.rt.taopicker.base.NullEntity;
import com.rt.taopicker.data.api.ITaoPickingApi;
import com.rt.taopicker.data.api.entity.PackageClearBoxReqEntity;
import com.rt.taopicker.data.api.entity.PackageFinishReqEntity;
import com.rt.taopicker.data.api.entity.PackageScanBoxReqEntity;
import com.rt.taopicker.data.api.entity.PackageScanVehicleReqEntity;
import com.rt.taopicker.data.api.entity.PrintOrderDetailsReqEntity;
import com.rt.taopicker.data.api.entity.WaveOrderPrintFinishReqEntity;
import com.rt.taopicker.data.api.entity.printOrderDetailsRespEntity.PrintOrderDetailsRespEntity;
import com.rt.taopicker.data.api.entity.QueryPackageUncompleteRespEntity;
import com.rt.taopicker.data.api.entity.QueryPackageWaveOrderReqEntity;
import com.rt.taopicker.data.api.entity.QueryPackageWaveOrderRespEntity;
import com.rt.taopicker.data.api.entity.QueryTaoPackageStatusRespEntity;
import com.rt.taopicker.data.api.entity.RespEntity;
import com.rt.taopicker.data.api.entity.PackageScanStallReqEntity;
import com.rt.taopicker.data.api.entity.TakeOutFromZcxReqEntity;
import com.rt.taopicker.data.api.entity.TakeOutFromZcxRespEntity;
import com.rt.taopicker.data.api.entity.packageScanCodeRespEntity.PackageScanCodeRespEntity;
import com.rt.taopicker.util.RetrofitHelper;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 拣货
 */
public class PackingModel {

    private ITaoPickingApi mTaoPickingApi;

    public PackingModel() {
        mTaoPickingApi = RetrofitHelper.getInstance().createService(ITaoPickingApi.class);
    }

    /**
     * 包装扫道口
     * @param entity
     * @return
     */
    public Observable<RespEntity<PackageScanCodeRespEntity>> packageScanStall(PackageScanStallReqEntity entity) {
        return mTaoPickingApi.packageScanStall(entity);
    }

    /**
     * 暂存箱取货
     * @param entity
     * @return
     */
    public Observable<RespEntity<TakeOutFromZcxRespEntity>> takeOutFromZcx(TakeOutFromZcxReqEntity entity) {
        return mTaoPickingApi.takeOutFromZcx(entity);
    }

    /**
     * 检查是否有未完成的包装
     * @return
     */
    public Observable<RespEntity<QueryPackageUncompleteRespEntity>> queryPackageUncomplete() {
        return mTaoPickingApi.queryPackageUncomplete(new NullEntity());
    }

    /**
     * 包装扫载具
     * @param entity
     * @return
     */
    public Observable<RespEntity<PackageScanCodeRespEntity>> packageScanVehicle(PackageScanVehicleReqEntity entity) {
        return mTaoPickingApi.packageScanVehicle(entity);
    }


    /**
     * 包装扫物流箱
     * @param entity
     * @return
     */
    public Observable<RespEntity<PackageScanCodeRespEntity>> packageScanBox(PackageScanBoxReqEntity entity) {
        return mTaoPickingApi.packageScanBox(entity);
    }

    /**
     * 包装清空物流箱
     * @param entity
     * @return
     */
    public Observable<RespEntity<PackageScanCodeRespEntity>> packageClearBox(PackageClearBoxReqEntity entity) {
        return mTaoPickingApi.packageClearBox(entity);
    }

    /**
     * 包装完成
     * @param entity
     * @return
     */
    public Observable<RespEntity<NullEntity>> packageFinish(PackageFinishReqEntity entity) {
        return mTaoPickingApi.packageFinish(entity);
    }

    /**
     * 查询当前用户包装抢单状态
     * @return
     */
    public Observable<RespEntity<QueryTaoPackageStatusRespEntity>> queryTaoPackageStatus() {
        return mTaoPickingApi.queryTaoPackageStatus(new NullEntity());
    }

    /**
     * 查询包装中的批次号信息
     * @param waveOrderNo
     * @return
     */
    public Observable<RespEntity<PackageScanCodeRespEntity>> queryPackageWaveOrder(String waveOrderNo) {
        QueryPackageWaveOrderReqEntity entity = new QueryPackageWaveOrderReqEntity();
        entity.setWaveOrderNo(waveOrderNo);
        return mTaoPickingApi.queryPackageWaveOrder(entity);
    }

    /**
     * 包装抢单
     * @return
     */
    public Observable<RespEntity<QueryPackageWaveOrderRespEntity>> grabPackageOrder() {
        return mTaoPickingApi.grabPackageOrder(new NullEntity());
    }

    /**
     * 批次号信息打印,通过蓝牙打印机打印
     * @return
     */
    public Observable<RespEntity<PrintOrderDetailsRespEntity>> printOrderDetails(PrintOrderDetailsReqEntity entity) {
        return mTaoPickingApi.printOrderDetails(entity);
    }

    /**
     * 批次号打印完成
     * @param waveOrderNo
     * @return
     */
    public Observable<RespEntity<NullEntity>> waveOrderPrintFinish(String waveOrderNo) {
        WaveOrderPrintFinishReqEntity entity = new WaveOrderPrintFinishReqEntity();
        entity.setWaveOrderNo(waveOrderNo);
        return mTaoPickingApi.waveOrderPrintFinish(entity);
    }

}
