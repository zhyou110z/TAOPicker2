package com.rt.taopicker.data.api;

import com.rt.taopicker.base.BaseUrl;
import com.rt.taopicker.base.NullEntity;
import com.rt.taopicker.data.api.entity.CheckVersionReqEntity;
import com.rt.taopicker.data.api.entity.CheckVersionRespEntity;
import com.rt.taopicker.data.api.entity.ConfirmGoodsExceptReqEntity;
import com.rt.taopicker.data.api.entity.ConfirmGoodsExceptRespEntity;
import com.rt.taopicker.data.api.entity.EmployeeLoginReqEntity;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.data.api.entity.EmployeeLogoutRespEntity;
import com.rt.taopicker.data.api.entity.GrabOrdersRespEntity;
import com.rt.taopicker.data.api.entity.ManualPutToStallReqEntity;
import com.rt.taopicker.data.api.entity.NotifyOutOfStockNewReqEntity;
import com.rt.taopicker.data.api.entity.OutStockReqEntity;
import com.rt.taopicker.data.api.entity.OutStockRespEntity;
import com.rt.taopicker.data.api.entity.PackageClearBoxReqEntity;
import com.rt.taopicker.data.api.entity.PackageFinishReqEntity;
import com.rt.taopicker.data.api.entity.PackageScanBoxReqEntity;
import com.rt.taopicker.data.api.entity.PackageScanStallReqEntity;
import com.rt.taopicker.data.api.entity.PackageScanVehicleReqEntity;
import com.rt.taopicker.data.api.entity.PdaLoginReqEntity;
import com.rt.taopicker.data.api.entity.PrintOrderDetailsReqEntity;
import com.rt.taopicker.data.api.entity.WaveOrderPrintFinishReqEntity;
import com.rt.taopicker.data.api.entity.printOrderDetailsRespEntity.PrintOrderDetailsRespEntity;
import com.rt.taopicker.data.api.entity.QueryGrabOrdersStatusRespEntity;
import com.rt.taopicker.data.api.entity.QueryPackageUncompleteRespEntity;
import com.rt.taopicker.data.api.entity.QueryPackageWaveOrderReqEntity;
import com.rt.taopicker.data.api.entity.QueryPackageWaveOrderRespEntity;
import com.rt.taopicker.data.api.entity.QueryPickerOrderInfoReqEntity;
import com.rt.taopicker.data.api.entity.QueryTaoPackageStatusRespEntity;
import com.rt.taopicker.data.api.entity.QueryWaitDownWaveReqEntity;
import com.rt.taopicker.data.api.entity.QueryWaitDownWaveRespEntity;
import com.rt.taopicker.data.api.entity.ReqEntity;
import com.rt.taopicker.data.api.entity.RespEntity;
import com.rt.taopicker.data.api.entity.ScanBarcodeReqEntity;
import com.rt.taopicker.data.api.entity.SetBoxRecyclingReqEntity;
import com.rt.taopicker.data.api.entity.SetStoreReqEntitty;
import com.rt.taopicker.data.api.entity.StatusQueryReqEntity;
import com.rt.taopicker.data.api.entity.TakeOutFromZcxReqEntity;
import com.rt.taopicker.data.api.entity.TakeOutFromZcxRespEntity;
import com.rt.taopicker.data.api.entity.TemporaryReqEntity;
import com.rt.taopicker.data.api.entity.TemporaryRespEntity;
import com.rt.taopicker.data.api.entity.packageScanCodeRespEntity.PackageScanCodeRespEntity;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.PdaLoginRespEntity;
import com.rt.taopicker.data.api.entity.queryPickAreaListRespEntity.QueryPickAreaListRespEntity;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.PickerOrderInfoEntity;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.QueryPickerOrderInfoRespEntity;
import com.rt.taopicker.data.api.entity.scanBarcodeRespEntity.ScanBarcodeRespEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.StatusQueryRespEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by yaoguangyao on 2017/12/4.
 */
@BaseUrl("tao-picking")
public interface ITaoPickingApi {
    /**
     * 下发接口（同步接口）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/api/taoapp/env/apiurls")
    Call<RespEntity<Map<String, String>>> apiurls(@Field("data") ReqEntity data, @Field("paramsMD5") String paramsMD5);

    /**
     * PDA登录接口
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@pdaLogin")
    Observable<RespEntity<PdaLoginRespEntity>> pdaLogin(@Field("body") PdaLoginReqEntity body);

    /**
     * APP版本检测
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@checkVersion")
    Observable<RespEntity<CheckVersionRespEntity>> checkVersion(@Field("body") CheckVersionReqEntity body);

    /**
     * 获取当前门店拣货区
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@queryPickAreaList")
    Observable<RespEntity<QueryPickAreaListRespEntity>> queryPickAreaList(@Field("body") NullEntity body);

    /**
     * 获取当前门店拣货区
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@employeeLogin")
    Observable<RespEntity<EmployeeLoginRespEntity>> employeeLogin(@Field("body") EmployeeLoginReqEntity body);

    /**
     * 选择账号门店
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@setStore")
    Observable<RespEntity<PdaLoginRespEntity>> setStore(@Field("body") SetStoreReqEntitty body);

    /**
     * 查询当前用户抢单状态
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@queryGrabOrdersStatus")
    Observable<RespEntity<QueryGrabOrdersStatusRespEntity>> queryGrabOrdersStatus(@Field("body") NullEntity body);

    /**
     * 拣货PDA抢单
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@grabOrders")
    Observable<RespEntity<GrabOrdersRespEntity>> grabOrders(@Field("body") NullEntity body);


    /**
     * 查询拣货单信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@queryPickOrdersInfo")
    Observable<RespEntity<QueryPickerOrderInfoRespEntity>> queryPickOrdersInfo(@Field("body") QueryPickerOrderInfoReqEntity body);

    /**
     * 刷条码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@scanBarcode")
    Observable<RespEntity<ScanBarcodeRespEntity>> scanBarcode(@Field("body") ScanBarcodeReqEntity body);

    /**
     * 标记缺货
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@notifyOutOfStock")
    Observable<RespEntity<QueryPickerOrderInfoRespEntity>> notifyOutOfStock(@Field("body") NotifyOutOfStockNewReqEntity body);

    /**
     * 查询已拣货清单信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@queryDonePickOrdersInfo")
    Observable<RespEntity<PickerOrderInfoEntity>> queryDonePickOrdersInfo(@Field("body") QueryPickerOrderInfoReqEntity body);

    /**
     * 检查是否有未完成的包装
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@queryPackageUncomplete")
    Observable<RespEntity<QueryPackageUncompleteRespEntity>> queryPackageUncomplete(@Field("body") NullEntity body);

    /**
     * 包装扫道口
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@packageScanStall")
    Observable<RespEntity<PackageScanCodeRespEntity>> packageScanStall(@Field("body") PackageScanStallReqEntity body);

    /**
     * 暂存箱取货
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@takeOutFromZcx")
    Observable<RespEntity<TakeOutFromZcxRespEntity>> takeOutFromZcx(@Field("body") TakeOutFromZcxReqEntity body);

    /**
     * 包装扫载具
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@packageScanVehicle")
    Observable<RespEntity<PackageScanCodeRespEntity>> packageScanVehicle(@Field("body") PackageScanVehicleReqEntity body);

    /**
     * 包装扫物流箱
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@packageScanBox")
    Observable<RespEntity<PackageScanCodeRespEntity>> packageScanBox(@Field("body") PackageScanBoxReqEntity body);


    /**
     * 人工刷下道口
     */
    @FormUrlEncoded
    @POST(":@manualPutToStall")
    Observable<RespEntity<NullEntity>> manualPutToStall(@Field("body") ManualPutToStallReqEntity body);

    /**
     * 包装清空物流箱
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@packageClearBox")
    Observable<RespEntity<PackageScanCodeRespEntity>> packageClearBox(@Field("body") PackageClearBoxReqEntity body);

    /**
     * 物流箱回收
     */
    @FormUrlEncoded
    @POST(":@setBoxRecycling")
    Observable<RespEntity<NullEntity>> setBoxRecycling(@Field("body") SetBoxRecyclingReqEntity body);

    /**
     * 包装完成
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@packageFinish")
    Observable<RespEntity<NullEntity>> packageFinish(@Field("body") PackageFinishReqEntity body);

    /**
     * 查询当前用户包装抢单状态
     * @return
     */
    @FormUrlEncoded
    @POST(":@queryTaoPackageStatus")
    Observable<RespEntity<QueryTaoPackageStatusRespEntity>> queryTaoPackageStatus(@Field("body") NullEntity body);

    /**
     * 查询包装中的批次号信息
     * @return
     */
    @FormUrlEncoded
    @POST(":@queryPackageWaveOrder")
    Observable<RespEntity<PackageScanCodeRespEntity>> queryPackageWaveOrder(@Field("body") QueryPackageWaveOrderReqEntity body);

    /**
     * 包装抢单
     * @return
     */
    @FormUrlEncoded
    @POST(":@grabPackageOrder")
    Observable<RespEntity<QueryPackageWaveOrderRespEntity>> grabPackageOrder(@Field("body") NullEntity body);
    /**
     * 状态查询
     *
     * @return
     */
    @FormUrlEncoded
    @POST(":@statusQuery")
    Observable<RespEntity<StatusQueryRespEntity>> statusQuery(@Field("body") StatusQueryReqEntity body);

    /**
     * 缺货待盘点
     * @return
     */
    @FormUrlEncoded
    @POST(":@outOfStock")
    Observable<RespEntity<OutStockRespEntity>> outOfStock(@Field("body") OutStockReqEntity body);

    /**
     * 商品缺货，确认排除
     * @return
     */
    @FormUrlEncoded
    @POST(":@confirmGoodsExcept")
    Observable<RespEntity<ConfirmGoodsExceptRespEntity>> confirmGoodsExcept(@Field("body") ConfirmGoodsExceptReqEntity body);


    /**
     *  取货暂存
     * @return
     */
    @FormUrlEncoded
    @POST(":@putInZcx")
    Observable<RespEntity<TemporaryRespEntity>> putInZcx(@Field("body") TemporaryReqEntity body);

    /**
     *  员工下班
     * @return
     */
    @FormUrlEncoded
    @POST(":@employeeLogout")
    Observable<RespEntity<EmployeeLogoutRespEntity>> employeeLogout(@Field("body") NullEntity body);

    /**
     * 批次号信息打印,通过蓝牙打印机打印
     * @return
     */
    @FormUrlEncoded
    @POST(":@printOrderDetails")
    Observable<RespEntity<PrintOrderDetailsRespEntity>> printOrderDetails(@Field("body") PrintOrderDetailsReqEntity body);

    /**
     * 人工下道口查询
     * @return
     */
    @FormUrlEncoded
    @POST(":@queryWaitDownWave")
    Observable<RespEntity<QueryWaitDownWaveRespEntity>> queryWaitDownWave(@Field("body") QueryWaitDownWaveReqEntity body);

    /**
     * 波次单打印完成
     * @return
     */
    @FormUrlEncoded
    @POST(":@waveOrderPrintFinish")
    Observable<RespEntity<NullEntity>> waveOrderPrintFinish(@Field("body") WaveOrderPrintFinishReqEntity body);


}
