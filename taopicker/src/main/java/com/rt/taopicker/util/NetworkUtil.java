package com.rt.taopicker.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseApplication;
import com.rt.taopicker.config.AppConfig;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.LoginModel;
import com.rt.taopicker.data.api.entity.CheckVersionReqEntity;
import com.rt.taopicker.data.api.entity.CheckVersionRespEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 网络状态帮助类
 * <p>
 * Created by chensheng on 2017/1/11.
 */
public class NetworkUtil {

    private static final String TAG = "NetworkUtil";

    /**
     * 网络是否连接
     */
    public static boolean isNetworkConnected() {
        if (BaseApplication.sContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.sContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 是否wifi连接
     */
    public static boolean isWifiConnected() {
        if (BaseApplication.sContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.sContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 是否手机网络连接
     */
    public static boolean isMobileConnected() {
        if (BaseApplication.sContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.sContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static int getConnectedType() {
        if (BaseApplication.sContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.sContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 1：WIFI网络2：wap网络3：net网络
     */
    public static int getAPNType() {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) BaseApplication.sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                netType = 3;
            } else {
                netType = 2;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;
        }
        return netType;
    }

    /**
     * 获取网络格式
     */
    public static String getNetworkType() {
        String strNetworkType = "";
        ConnectivityManager connMgr = (ConnectivityManager) BaseApplication.sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }
                        break;
                }
            }
        }
        return strNetworkType;
    }

    /**
     * 获取IP地址
     */
    public static String getHostIP() {
        String hostIp = "";
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostIp;
    }

    public static boolean isServerChecking = false;
    public static boolean isPinging = false;

    /**
     * 显示popupWindow
     */
    public static void showPopwindow(final Activity activity, int resId) {
        isServerChecking = false;
        isPinging = false;
        try {
            // 利用layoutInflater获得View
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.base_net_check, null);

            // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

            final PopupWindow window = new PopupWindow(view,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);

            // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
            window.setFocusable(true);
            //设置后点击外部空白处可关闭window
            window.setOutsideTouchable(true);


            // 实例化一个ColorDrawable颜色为半透明
            // ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 点击PopupWindow最外层布局以及点击返回键PopupWindow不会消失
            // 新手在遇到这个问题的时候可能会折腾半天，最后通过强大的网络找到一个解决方案，那就是跟PopupWindow设置一个背景
            // popupWindow.setBackgroundDrawable(drawable),这个drawable随便一个什么类型的都可以，只要不为空。
//        window.setBackgroundDrawable(new ColorDrawable());
//        window.setBackgroundDrawable(new ColorDrawable(0x55000000));
            // 在底部显示
            window.showAtLocation(activity.findViewById(resId), Gravity.TOP, 0, 0);


            View shadow = view.findViewById(R.id.shadow);
            shadow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    window.dismiss();
                }
            });

            View shadow2 = view.findViewById(R.id.shadow2);
            shadow2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    window.dismiss();
                }
            });

            final EditText editText = (EditText) view.findViewById(R.id.editText);
            final EditText ipAddress = (EditText) view.findViewById(R.id.ipAddress);
            ipAddress.setText(PreferencesUtil.readString("net_check_ipAddress"));

            final Button checkBtn = (Button) view.findViewById(R.id.checkBtn);
            checkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBtn.setText("检测中");
                    checkBtn.setEnabled(false);
                    editText.setText("");
                    if(isMobileConnected()){
                        editText.setText(editText.getText().toString()+"数据连接：已连接\n");
                    }else{
                        editText.setText(editText.getText().toString()+"数据连接：未连接\n");
                    }

                    if(isWifiConnected()){
                        editText.setText(editText.getText().toString()+"wifi连接：已连接\n");
                    }else{
                        editText.setText(editText.getText().toString()+"wifi连接：未连接\n");
                    }


                    if(StringUtil.isBlank(ipAddress.getText().toString())){
                        editText.setText(editText.getText().toString()+"站点可用性：未检测\n");
                    }else{

                        PreferencesUtil.writeString("net_check_ipAddress",ipAddress.getText().toString());
                        isPinging = true;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final StringBuffer result = new StringBuffer();
                                final boolean flag = ping(ipAddress.getText().toString(), result);

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        editText.setText(editText.getText().toString()+"站点可用性："+(flag?"成功":"失败")+"\n");
                                        editText.setText(editText.getText().toString()+"-------------------\n");
                                        editText.setText(editText.getText().toString()+result.toString()+"\n");
                                        editText.setText(editText.getText().toString()+"-------------------\n");
                                        isPinging = false;
                                        if (!isPinging  && !isServerChecking){
                                            checkBtn.setEnabled(true);
                                            checkBtn.setText("开始检测");
                                        }
                                    }
                                });

                            }
                        }).start();


                    }

                    serverCheck(editText,checkBtn);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean ping(String host, StringBuffer stringBuffer) {
        String line = null;
        Process process = null;
        BufferedReader successReader = null;
//        String command = "ping -c " + pingCount + " -w 5 " + host;
        String command = "ping -c 1 -w 5 " + host;
        boolean isSuccess = false;
        try {
            append(stringBuffer, command);
            process = Runtime.getRuntime().exec(command);
            if (process == null) {
                Log.e(TAG, "ping fail:process is null." );
                append(stringBuffer, "ping fail:process is null.");
                return false;
            }
            successReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String targetIp = null;
            while ((line = successReader.readLine()) != null) {
                Log.d(TAG, line );
                if(StringUtil.isBlank(targetIp)){
                    targetIp = getTargetIp(line);
                }
                append(stringBuffer, line);
            }

            if(StringUtil.isBlank(targetIp)){
                targetIp = host;
            }
            int status = process.waitFor();
            if (status == 0 && host.equals(targetIp)) {
                Log.d(TAG, "ping "+host+" success");
                append(stringBuffer, "ping "+host+" success");
                isSuccess = true;
            } else {
                Log.e(TAG, "ping fail");
                append(stringBuffer, "ping fail");
                isSuccess = false;
            }
        } catch (Exception e) {
            Log.e(TAG, "ping: ", e);
        } finally {
            Log.d(TAG, "ping exit.");
            if (process != null) {
                process.destroy();
            }
            if (successReader != null) {
                try {
                    successReader.close();
                } catch (IOException e) {
                    Log.e(TAG, "ping: ", e);
                }
            }
        }
        return isSuccess;
    }

    private static void append(StringBuffer stringBuffer, String text) {
        if (stringBuffer != null) {
            stringBuffer.append(text + "\n");
        }
    }

    public static String getTargetIp(String line){
        if (line.contains("bytes from")){
            String[] strs = line.split(" ");
            for (String var :strs){
                if(var.contains(":")){
                    String[] tps = var.split(":");
                    for (String temp :tps){
                        if(checkIP(temp)){
                            return temp;
                        }
                    }
                }
                if(checkIP(var)){
                    return var;
                }
            }

        }
        return "";
    }

    // 判断输入的IP是否合法
    public static boolean checkIP(String str) {
        Pattern pattern = Pattern
                .compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]"
                        + "|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
        return pattern.matcher(str).matches();
    }

    /**
     * 后台更新。
     */
    public static void serverCheck(final EditText editText,final Button checkBtn){
        checkBtn.setText("检测中");
        checkBtn.setEnabled(false);
        isServerChecking = true;

        CheckVersionReqEntity reqEntity = new CheckVersionReqEntity();
        reqEntity.setAppType(AppConfig.sAppType);

        LoginModel mLoginModel = SingletonHelper.getInstance(LoginModel.class);
        mLoginModel.checkVersion(reqEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respEntity -> {
                    editText.setText(editText.getText().toString()+"服务可用性：成功\n");
                    isServerChecking = false;
                    if (!isPinging  && !isServerChecking){
                        checkBtn.setEnabled(true);
                        checkBtn.setText("开始检测");
                    }
                }, err -> {
                    editText.setText(editText.getText().toString()+"服务可用性：失败\n");
                    isServerChecking = false;
                    if (!isPinging  && !isServerChecking){
                        checkBtn.setEnabled(true);
                        checkBtn.setText("开始检测");
                    }
                });
    }
}
