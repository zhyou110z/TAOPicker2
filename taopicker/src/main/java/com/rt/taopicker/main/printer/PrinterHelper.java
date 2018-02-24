package com.rt.taopicker.main.printer;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;

import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.printOrderDetailsRespEntity.PrintCommandEntity;
import com.rt.taopicker.data.api.entity.printOrderDetailsRespEntity.PrintOrderDetailsRespEntity;
import com.rt.taopicker.main.printer.activity.DeviceListActivity;
import com.rt.taopicker.main.printer.sdk.jqPrinter.JQPrinter;
import com.rt.taopicker.main.printer.sdk.jqPrinter.Port;
import com.rt.taopicker.main.printer.sdk.qrPrinter.QRPrinter;
import com.rt.taopicker.util.DialogHelper;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.ToastUtil;
import com.rt.taopicker.widget.NoticeDialogWidget;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

import java.util.List;

/**
 * 打印帮助类
 * Created by wangzhi on 2018/1/31.
 */

public class PrinterHelper {
    public static PrinterHelper instance = null;

    private PrinterHelper(){
    }

    public static PrinterHelper getInstance(){
        if(instance == null){
            instance = new PrinterHelper();
        }
        return instance;
    }

    private BluetoothAdapter mBluetoothAdapter = null;
    private JQPrinter mJQPrinter = null;
    private QRPrinter mQrPrinter = null;
    private PrinterListener mPrinterListener = null;
    private Activity mCurrentActivity = null;
    private BluetoothDevice mDeviceItem = null; //选择连接的蓝牙设备

    public void init(Activity activity, PrinterListener listener){
        mCurrentActivity = activity;
        mPrinterListener = listener;
        initBluetooth();
    }

    /**
     * 初始化蓝牙。
     */
    private void initBluetooth(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            ToastUtil.toast("本机没有找到蓝牙硬件或驱动！");  //不支持蓝牙功能
            if(mPrinterListener != null){
                mPrinterListener.initBluetoothCallBack(false);
            }
            return;
        }else{
            // 如果本地蓝牙没有开启，则开启
            //以下操作需要在AndroidManifest.xml中注册权限android.permission.BLUETOOTH ；android.permission.BLUETOOTH_ADMIN
            if (!mBluetoothAdapter.isEnabled()) {
                ToastUtil.toast("正在开启蓝牙");

                if(ContextCompat.checkSelfPermission(mCurrentActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){ //当前Activity没有获得蓝牙权限时，需要申请权限
                    //请求权限
                    ActivityCompat.requestPermissions(
                            mCurrentActivity,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            Constant.REQUEST_CODE_PERMISSION_ACCESS_COARSE_LOCATION);

                    //判断是否需要 向用户解释，为什么要申请该权限
                    if(ActivityCompat.shouldShowRequestPermissionRationale(mCurrentActivity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        ToastUtil.toast("蓝牙打印机需要获取蓝牙权限，请允许");
                    }
                }else{ //已获取权限，则直接开启
                    openBluetooth();
                }
            } else {
                if(mPrinterListener != null){
                    mPrinterListener.initBluetoothCallBack(true);
                }
            }
        }
    }

    /**
     * 开启蓝牙
     */
    public void openBluetooth(){
        mBluetoothAdapter.enable();
        ToastUtil.toast("本地蓝牙已打开");
        if(mPrinterListener != null){
            mPrinterListener.initBluetoothCallBack(true);
        }
    }

    /**
     * 拒绝开启蓝牙权限
     */
    public void refuseBluetoothPermission(){
        ToastUtil.toast("您已拒绝蓝牙权限的申请，包装完成后将无法打印小票！");
        if(mPrinterListener != null){
            mPrinterListener.initBluetoothCallBack(false);
        }
    }

    /**
     * 检测蓝牙是否连接设备，并选择了一台打印机。
     * @return
     */
    public boolean checkBluetoothConnect(){
        if(mBluetoothAdapter.getBondedDevices().size() > 0 && mDeviceItem != null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 初始化蓝牙设备连接。
     */
    public void initBluetoothConnect(){
        if(mBluetoothAdapter.getBondedDevices().size() == 0 || mDeviceItem == null){ //未连接任何蓝牙设备，或者未指定打印机，直接去连接设备的页面
            DialogHelper.getInstance().showDialog("请先连接打印机", new NoticeDialogWidget.DialogListener() {
                @Override
                public void onClick() {
                    Intent intent = DeviceListActivity.newIntent(mCurrentActivity);
                    mCurrentActivity.startActivity(intent);
                }
            });
        }else{
            if(mPrinterListener != null){
                mPrinterListener.initBluetoothConnectCallBack(true);
            }
        }
    }

    /**
     * 指定打印设备。
     * @param deviceItem
     */
    public void setSelectDeviceItem(BluetoothDevice deviceItem) {
        mDeviceItem = deviceItem;

        if(mPrinterListener != null){
            mPrinterListener.initBluetoothConnectCallBack(true);
        }else{
            ToastUtil.toast("mPrinterListener为空！");
        }
    }

    public void resetSelectDeviceItem(){
        mDeviceItem = null;
    }

    /**
     * 获取指定的打印设备。
     * @return
     */
    public BluetoothDevice getDeviceItem() {
        return mDeviceItem;
    }

    /**
     * 打印内容
     * @param entity
     */
    public void printContent(PrintOrderDetailsRespEntity entity){
        boolean flag;
        if(checkBluetoothConnect()){
            //QR打印机
            if(entity != null && entity.getCommand() != null) {
                //------------------------测试数据-----------------------------------------------
//                        List<PrintCommandEntity> data = GsonUtil.jsonToBean2("[{\"command\":[\"33\",\"32\",\"48\",\"32\",\"50\",\"48\",\"48\",\"32\",\"50\",\"48\",\"48\",\"32\",\"49\",\"52\",\"50\",\"56\",\"32\",\"49\",\"13\",\"10\",\"80\",\"65\",\"71\",\"69\",\"45\",\"87\",\"73\",\"68\",\"84\",\"72\",\"32\",\"53\",\"56\",\"54\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"66\",\"32\",\"81\",\"82\",\"32\",\"49\",\"50\",\"48\",\"32\",\"49\",\"48\",\"32\",\"77\",\"32\",\"50\",\"32\",\"85\",\"32\",\"54\",\"32\",\"72\",\"65\",\"44\",\"32\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\",\"69\",\"78\",\"68\",\"81\",\"82\",\"13\",\"10\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"85\",\"78\",\"68\",\"69\",\"82\",\"76\",\"73\",\"78\",\"69\",\"32\",\"79\",\"70\",\"70\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"83\",\"69\",\"84\",\"66\",\"79\",\"76\",\"68\",\"32\",\"49\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"83\",\"69\",\"84\",\"77\",\"65\",\"71\",\"32\",\"49\",\"32\",\"49\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"84\",\"69\",\"88\",\"84\",\"32\",\"52\",\"32\",\"48\",\"32\",\"54\",\"32\",\"49\",\"49\",\"48\",\"32\",\"-78\",\"-88\",\"-76\",\"-50\",\"-70\",\"-59\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"84\",\"69\",\"88\",\"84\",\"32\",\"52\",\"32\",\"48\",\"32\",\"54\",\"32\",\"49\",\"49\",\"48\",\"32\",\"-78\",\"-88\",\"-76\",\"-50\",\"-70\",\"-59\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"85\",\"78\",\"68\",\"69\",\"82\",\"76\",\"73\",\"78\",\"69\",\"32\",\"79\",\"70\",\"70\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"83\",\"69\",\"84\",\"66\",\"79\",\"76\",\"68\",\"32\",\"49\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"83\",\"69\",\"84\",\"77\",\"65\",\"71\",\"32\",\"49\",\"32\",\"49\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"84\",\"69\",\"88\",\"84\",\"32\",\"52\",\"32\",\"48\",\"32\",\"54\",\"32\",\"49\",\"53\",\"48\",\"32\",\"-59\",\"-28\",\"-53\",\"-51\",\"-57\",\"-8\",\"-45\",\"-14\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"84\",\"69\",\"88\",\"84\",\"32\",\"52\",\"32\",\"48\",\"32\",\"54\",\"32\",\"49\",\"53\",\"48\",\"32\",\"-59\",\"-28\",\"-53\",\"-51\",\"-57\",\"-8\",\"-45\",\"-14\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"85\",\"78\",\"68\",\"69\",\"82\",\"76\",\"73\",\"78\",\"69\",\"32\",\"79\",\"70\",\"70\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"83\",\"69\",\"84\",\"66\",\"79\",\"76\",\"68\",\"32\",\"49\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"83\",\"69\",\"84\",\"77\",\"65\",\"71\",\"32\",\"49\",\"32\",\"49\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"84\",\"69\",\"88\",\"84\",\"32\",\"52\",\"32\",\"48\",\"32\",\"54\",\"32\",\"49\",\"57\",\"48\",\"32\",\"-59\",\"-28\",\"-53\",\"-51\",\"-64\",\"-32\",\"-48\",\"-51\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"84\",\"69\",\"88\",\"84\",\"32\",\"52\",\"32\",\"48\",\"32\",\"54\",\"32\",\"49\",\"57\",\"48\",\"32\",\"-59\",\"-28\",\"-53\",\"-51\",\"-64\",\"-32\",\"-48\",\"-51\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"85\",\"78\",\"68\",\"69\",\"82\",\"76\",\"73\",\"78\",\"69\",\"32\",\"79\",\"70\",\"70\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"83\",\"69\",\"84\",\"66\",\"79\",\"76\",\"68\",\"32\",\"49\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"83\",\"69\",\"84\",\"77\",\"65\",\"71\",\"32\",\"49\",\"32\",\"49\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"84\",\"69\",\"88\",\"84\",\"32\",\"52\",\"32\",\"48\",\"32\",\"54\",\"32\",\"50\",\"51\",\"48\",\"32\",\"-78\",\"-88\",\"-76\",\"-50\",\"-70\",\"-59\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"84\",\"69\",\"88\",\"84\",\"32\",\"52\",\"32\",\"48\",\"32\",\"54\",\"32\",\"50\",\"51\",\"48\",\"32\",\"-78\",\"-88\",\"-76\",\"-50\",\"-70\",\"-59\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"85\",\"78\",\"68\",\"69\",\"82\",\"76\",\"73\",\"78\",\"69\",\"32\",\"79\",\"70\",\"70\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"83\",\"69\",\"84\",\"66\",\"79\",\"76\",\"68\",\"32\",\"49\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"83\",\"69\",\"84\",\"77\",\"65\",\"71\",\"32\",\"49\",\"32\",\"49\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"84\",\"69\",\"88\",\"84\",\"32\",\"52\",\"32\",\"48\",\"32\",\"54\",\"32\",\"50\",\"55\",\"48\",\"32\",\"-50\",\"-17\",\"-63\",\"-9\",\"-49\",\"-28\",\"-54\",\"-3\",\"-63\",\"-65\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"84\",\"69\",\"88\",\"84\",\"32\",\"52\",\"32\",\"48\",\"32\",\"54\",\"32\",\"50\",\"55\",\"48\",\"32\",\"-50\",\"-17\",\"-63\",\"-9\",\"-49\",\"-28\",\"-54\",\"-3\",\"-63\",\"-65\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"85\",\"78\",\"68\",\"69\",\"82\",\"76\",\"73\",\"78\",\"69\",\"32\",\"79\",\"70\",\"70\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"83\",\"69\",\"84\",\"66\",\"79\",\"76\",\"68\",\"32\",\"49\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"83\",\"69\",\"84\",\"77\",\"65\",\"71\",\"32\",\"49\",\"32\",\"49\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"84\",\"69\",\"88\",\"84\",\"32\",\"52\",\"32\",\"48\",\"32\",\"54\",\"32\",\"51\",\"49\",\"48\",\"32\",\"-74\",\"-87\",\"-75\",\"-91\",\"-54\",\"-3\",\"-63\",\"-65\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"84\",\"69\",\"88\",\"84\",\"32\",\"52\",\"32\",\"48\",\"32\",\"54\",\"32\",\"51\",\"49\",\"48\",\"32\",\"-74\",\"-87\",\"-75\",\"-91\",\"-54\",\"-3\",\"-63\",\"-65\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"85\",\"78\",\"68\",\"69\",\"82\",\"76\",\"73\",\"78\",\"69\",\"32\",\"79\",\"70\",\"70\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"83\",\"69\",\"84\",\"66\",\"79\",\"76\",\"68\",\"32\",\"49\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"83\",\"69\",\"84\",\"77\",\"65\",\"71\",\"32\",\"49\",\"32\",\"49\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"84\",\"69\",\"88\",\"84\",\"32\",\"52\",\"32\",\"48\",\"32\",\"54\",\"32\",\"51\",\"53\",\"48\",\"32\",\"-55\",\"-52\",\"-58\",\"-73\",\"-54\",\"-3\",\"-63\",\"-65\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"84\",\"69\",\"88\",\"84\",\"32\",\"52\",\"32\",\"48\",\"32\",\"54\",\"32\",\"51\",\"53\",\"48\",\"32\",\"-55\",\"-52\",\"-58\",\"-73\",\"-54\",\"-3\",\"-63\",\"-65\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"48\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"71\",\"65\",\"80\",\"45\",\"83\",\"69\",\"78\",\"83\",\"69\",\"13\",\"10\",\"70\",\"79\",\"82\",\"77\",\"13\",\"10\"],\"commandType\":0},{\"command\":[\"80\",\"82\",\"73\",\"78\",\"84\",\"13\",\"10\"],\"commandType\":0}]",
//                            new TypeToken<List<PrintCommandEntity>>() {}.getType());
                //------------------------测试数据-----------------------------------------------

                if("QR386".equals(entity.getPrinterType())){ //QR打印机
                    flag = qrPrinterContent(entity.getCommand());
                }else if("JLP352".equals(entity.getPrinterType())){ //jlp打印机
                    flag = jqPrinterContent(entity.getCommand());
                }else if("EZ320".equals(entity.getPrinterType())){ //zebra打印机
                    flag = zebraPrinterContent(entity.getCommand());
                }else{
                    ToastUtil.toast("没有该类型的打印机sdk！");
                    flag = false;
                }
            }else{
                ToastUtil.toast("没有打印指令！");
                flag = false;
            }
        }else{
            ToastUtil.toast("蓝牙没有连接！");
            flag = false;
        }

        if(mPrinterListener != null){
            mPrinterListener.printCompleteCallBack(flag);
        }
    }

    /**
     * 使用QRPrinter sdk打印
     * @param data
     */
    private boolean qrPrinterContent(List<PrintCommandEntity> data){
        boolean flag = true;
        try{
            mQrPrinter = new QRPrinter();
            if (mQrPrinter.connect(mDeviceItem.getName(), mDeviceItem.getAddress())) {
                Log.e(Constant.PRINT_TAG, "printer begin!");
                for(PrintCommandEntity printCommandEntity:data){
                    List<String> command = printCommandEntity.getCommand();
                    if(command != null){
                        if(printCommandEntity.getCommandType() == 0){ //指令
                            byte[] buffer=new byte[command.size()];
                            for(int i=0; i<command.size(); i++){
                                buffer[i] = Byte.parseByte(command.get(i));
                            }
                            mQrPrinter.portSendCmd(buffer);
                        }
                    }
                }
                Log.e(Constant.PRINT_TAG, "printer end!");
            }else{
                return false;
            }
        }catch (Exception e){
            flag = false;
            ToastUtil.toast(e.getMessage());
            e.printStackTrace();
        }finally {
            mQrPrinter.disconnect();  //打印完需要关闭连接，释放端口
        }
        return flag;
    }

    public boolean zebraPrinterContent(List<PrintCommandEntity> data){
        boolean flag = true;
        Connection connection = null;
        try{
            Log.e(Constant.PRINT_TAG, "printer begin!");
            connection = new BluetoothConnection(mDeviceItem.getAddress());
            connection.open();
            ZebraPrinter printer = ZebraPrinterFactory.getInstance(connection);
            for(PrintCommandEntity printCommandEntity:data){
                if(StringUtil.isNotBlank(printCommandEntity.getZebraText())){
                    byte[] zebraText = printCommandEntity.getZebraText().getBytes();
                    connection.write(zebraText);
                }
            }

//            byte[] zebraText = createZebraTxt().getBytes();
//            connection.write(zebraText);

            Log.e(Constant.PRINT_TAG, "printer end!");
        }catch (Exception e){
            flag = false;
            ToastUtil.toast(e.getMessage());
            e.printStackTrace();
        }finally {
            if(connection != null){ //打印完需要关闭连接，释放端口
                try {
                    connection.close();
                } catch (ConnectionException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    private String createZebraTxt() {
        String wholeZplLabel = "! 0 200 200 990 1\n" +
                "JOURNAL\n" +
                "CONTRAST 0\n" +
                "SPEED 1\n" +
                "PAGE-WIDTH 600\n" +
                "ENCODING UTF-8\n" +
                "SETMAG 2 2\n" +
                "SETBOLD 1\n" +
                "TEXT GBUNSG24.CPF 0 30 30        欢迎光临\n" +
                "TEXT GBUNSG24.CPF 0 30 80   斑马公司中国旗舰店\n" +
                "SETMAG 1 1\n" +
                "SETBOLD 0\n" +
                "TEXT GBUNSG24.CPF 0 30 130              中国 上海 No.00001\n" +
                "TEXT GBUNSG24.CPF 0 30 170 单号:2-599950  卡号:1002-6-908500  柜台:28\n" +
                "TEXT GBUNSG24.CPF 0 30 200 时间:2011-02-24 15:18        销售员:陈利军\n" +
                "TEXT GBUNSG24.CPF 0 30 240 货号      品名        数量        金额\n" +
                "TEXT GBUNSG24.CPF 0 30 260 －－－－－－－－－－－－－－－－－－－－－\n" +
                "TEXT GBUNSG24.CPF 0 30 280 690878    116Xi4       11         549450\n" +
                "TEXT GBUNSG24.CPF 0 30 310 682085    105SL-300    15         434250\n" +
                "TEXT GBUNSG24.CPF 0 30 340 670353    ZM600-300    12         418800\n" +
                "TEXT GBUNSG24.CPF 0 30 370 670368    ZM400-200    14         237300\n" +
                "TEXT GBUNSG24.CPF 0 30 400 660578    S4M-203      19         236550\n" +
                "TEXT GBUNSG24.CPF 0 30 430 430898    QL220plus    26         272740\n" +
                "TEXT GBUNSG24.CPF 0 30 460 460352    P4T          6          89700\n" +
                "TEXT GBUNSG24.CPF 0 30 490 232979    A3200碳带    50         15500\n" +
                "TEXT GBUNSG24.CPF 0 30 520 240326    4000T标签    40         6840\n" +
                "TEXT GBUNSG24.CPF 0 30 550 930934    P330i        15         499500\n" +
                "TEXT GBUNSG24.CPF 0 30 580 588632    GX430t       11         93500\n" +
                "TEXT GBUNSG24.CPF 0 30 610 520523    888TT        70         252000\n" +
                "TEXT GBUNSG24.CPF 0 30 630 －－－－－－－－－－－－－－－－－－－－－\n" +
                "SETBOLD 1\n" +
                "TEXT GBUNSG24.CPF 0 30 655 购买总数:259           应付总额:￥3106130\n" +
                "SETBOLD 0\n" +
                "TEXT GBUNSG24.CPF 0 30 690 付款方式:现金\n" +
                "TEXT GBUNSG24.CPF 0 30 720 付款金额:3106200             找零:70\n" +
                "SETBOLD 1\n" +
                "TEXT GBUNSG24.CPF 0 30 750 合计金额:叁佰壹拾万陆仟壹佰叁拾整\n" +
                "TEXT GBUNSG24.CPF 0 30 780 开票金额:叁佰壹拾万陆仟壹佰叁拾整\n" +
                "SETBOLD 0\n" +
                "TEXT GBUNSG24.CPF 0 30 830 －－－－－－－ 祝您旅途愉快 －－－－－－－\n" +
                "TEXT GBUNSG24.CPF 0 30 860 －－－－－－－ 欢迎下次光临 －－－－－－－\n" +
                "CENTER\n" +
                "B 128 1 1 50 0 900 123ABCDEFG1234567890\n" +
                "LEFT\n" +
                "PRINT\n" ;
        return wholeZplLabel;
    }


    /**
     * 使用JQPrinter sdk打印
     * @param data
     */
    private boolean jqPrinterContent(List<PrintCommandEntity> data){
        boolean flag = true;
        try{
            if(mBluetoothAdapter.isDiscovering())
                mBluetoothAdapter.cancelDiscovery();

            if (mJQPrinter != null) {
                mJQPrinter.close();
            }

            mJQPrinter = new JQPrinter(mBluetoothAdapter,mDeviceItem.getAddress());

            if (!mJQPrinter.open(JQPrinter.PRINTER_TYPE.ULT113x)) {
                Log.e(Constant.PRINT_TAG, "printer open error!");
                return false;
            }

            if (!mJQPrinter.wakeUp())
                return false;

            if (mJQPrinter.getPortState() != Port.PORT_STATE.PORT_OPEND)
            {
                Log.e(Constant.PRINT_TAG, "Fail:" +mJQPrinter.getPortState());
                ToastUtil.toast("打印机未开启！");
                return false;
            }

            if (mJQPrinter == null) {
                Log.e(Constant.PRINT_TAG, "printer is null");
                ToastUtil.toast("打印机未初始化！");
                return false;
            }

            if (!getJQPrinterState()) {
                return false;
            }

            Log.e(Constant.PRINT_TAG, "printer begin!");
            for(PrintCommandEntity printCommandEntity:data){
                List<String> command = printCommandEntity.getCommand();
                if(command != null){
                    if(printCommandEntity.getCommandType() == 0){ //指令字节
                        byte[] buffer=new byte[command.size()];
                        for(int i=0; i<command.size(); i++){
                            buffer[i] = Byte.parseByte(command.get(i));
                        }
                        mJQPrinter.getPort().write(buffer);
                    }else if(printCommandEntity.getCommandType() == 1){ //图片bitmap
                        mJQPrinter.esc.image.drawOut(0,0, base64ToBitmap(printCommandEntity.getBase64Data()));
                    }
                }
            }
            Log.e(Constant.PRINT_TAG, "printer end!");
        }catch (Exception e){
            flag = false;
            ToastUtil.toast(e.getMessage());
            e.printStackTrace();
        }finally {
            mJQPrinter.close();//打印完需要关闭连接，释放端口
        }
        return flag;
    }

    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    private Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 获取JQPrinter打印机状态
     * @return
     */
    private boolean getJQPrinterState() {
        if(mJQPrinter.getPortState() != Port.PORT_STATE.PORT_OPEND) {
            ToastUtil.toast("蓝牙错误");
            return false;
        }

        if (!mJQPrinter.getPrinterState(3000)) {
            ToastUtil.toast("获取打印机状态失败");
            return false;
        }

        if (mJQPrinter.printerInfo.isCoverOpen) {
            ToastUtil.toast("打印机纸仓盖未关闭");
            return false;
        }else if (mJQPrinter.printerInfo.isNoPaper) {
            ToastUtil.toast("打印机缺纸");
            return false;
        }
        return true;
    }

    public interface PrinterListener{
        /**
         * 初始化蓝牙回调。
         * @param result true为初始化成功，false为初始化失败
         */
        void initBluetoothCallBack(Boolean result);

        /**
         * 初始化蓝牙连接回调。
         * @param result true为初始化成功，false为初始化失败
         */
        void initBluetoothConnectCallBack(Boolean result);

        /**
         * 打印完成回调
         * @param result
         */
        void printCompleteCallBack(Boolean result);
    }


}
