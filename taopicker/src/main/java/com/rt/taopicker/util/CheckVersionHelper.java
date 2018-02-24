package com.rt.taopicker.util;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.AppConfig;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.LoginModel;
import com.rt.taopicker.data.api.entity.CheckVersionReqEntity;
import com.rt.taopicker.data.api.entity.CheckVersionRespEntity;
import com.rt.taopicker.widget.NoticeDialogWidget;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * APP版本检测
 *
 * Created by zhouyou on 2017/9/10.
 */
public class CheckVersionHelper {
    private static CheckVersionHelper sInstance = new CheckVersionHelper();

    final int REQUEST_WRITE=1;//申请权限的请求码

    private ProgressDialog pBar;
    private LoginModel mLoginModel = SingletonHelper.getInstance(LoginModel.class);

    private String localAppPath = "rt/taopicker.apk";

    private static boolean  mNextUpdateFlag = false;

    private NoticeDialogWidget mNoticeDialogWidget;

    private CheckVersionHelper() {

    }

    public static CheckVersionHelper getInstance() {
        return sInstance;
    }

    /**
     * 检测新版本。
     * @param isNewNoShow 最新提示是否显示 true 显示
     */
    public void newVersionCheck(boolean isNewNoShow) {
        FragmentActivity activity = (FragmentActivity) ActivityHelper.getCurrentActivity();
        CheckVersionReqEntity reqEntity = new CheckVersionReqEntity();
        reqEntity.setAppType(AppConfig.sAppType);
        mLoginModel.checkVersion(reqEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(respEntity -> {
                             if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                                 CheckVersionRespEntity body = respEntity.getBody();
                                 if (body != null) {
                                     showUpdateDialog(
                                             body.getIsEdition(),
                                             body.getVersionMsgTitle(),
                                             body.getVersionMsgNote(),
                                             body.getDownUrl(),
                                             body.getForceUpdate(),
                                             isNewNoShow);
                                 } else {
                                     ToastUtil.toast("版本检测更新信息为空");
                                 }
                             } else {
                                 ToastUtil.toast(respEntity.getErrorDesc());
                             }
                        }, err -> {
                            Log.e("flyzing", "newVersionCheck: ", err);
                            Toast.makeText(activity,"版本检测更新失败" , Toast.LENGTH_SHORT).show();
                            LogServiceHelper.getInstance().error(err, "版本检测更新失败");
                        });
    }

    /**
     * 更新窗口。
     *
     * @param isEdition 是否最新版本0:否 1:是
     * @param title 标题
     * @param message    提示信息
     * @param url        下载的url
     * @param forceUpdate   强制更新 0:否 1:是
     *@param isNewNoShow   最新提示是否显示 true 显示
     */

    public void showUpdateDialog(String isEdition, String title, String message, String url, String forceUpdate, boolean isNewNoShow) {
        BaseActivity activity = (BaseActivity) ActivityHelper.getCurrentActivity();
        if (mNoticeDialogWidget != null && mNoticeDialogWidget.isAdded() && activity.getVisible()) {
            mNoticeDialogWidget.dismissAllowingStateLoss();
            mNoticeDialogWidget = null;
        }

        if (Constant.IsEdition.NO.equals(isEdition)) {
            if(!StringUtil.isNotBlank(url)){
                Toast.makeText(activity,"无可用的下载地址，请检查API", Toast.LENGTH_SHORT).show();
                return;
            }

            mNoticeDialogWidget = new NoticeDialogWidget();
            mNoticeDialogWidget.setCancelable(false);
            mNoticeDialogWidget.setTitle(title);
            mNoticeDialogWidget.setContent(message);
            //强制更新
            if (Constant.ForceUpdate.NO.equals(forceUpdate)) {
                //非强制更新，如果之前点过以后再说，则跳过
                if (mNextUpdateFlag) {
                    return;
                }

                mNoticeDialogWidget.setNegative(true);
                mNoticeDialogWidget.setNegativeListener("以后再说", new NoticeDialogWidget.DialogListener() {
                    @Override
                    public void onClick() {
                        mNoticeDialogWidget.dismissAllowingStateLoss();
                        mNextUpdateFlag = true;
                    }
                });
            }
            mNoticeDialogWidget.setPositiveListener("立即更新", new NoticeDialogWidget.DialogListener() {
                @Override
                public void onClick() {
                    beginDownLoad(url);
                }
            });
            mNoticeDialogWidget.show(activity.getSupportFragmentManager(), "CheckVersionDialog");
        } else {
            if(isNewNoShow) {
                mNoticeDialogWidget = new NoticeDialogWidget();
                mNoticeDialogWidget.setCancelable(false);
                mNoticeDialogWidget.setTitle(title);
                mNoticeDialogWidget.setContent(message);
                mNoticeDialogWidget.setPositiveListener("确定", new NoticeDialogWidget.DialogListener() {
                    @Override
                    public void onClick() {
                        mNoticeDialogWidget.dismissAllowingStateLoss();
                    }
                });
                mNoticeDialogWidget.show(activity.getSupportFragmentManager(), "CheckVersionDialog");
            }
        }


    }

    /**
     * 更新窗口。
     *
     * @param url        下载的url
     */
    private void beginDownLoad(final String url) {
        FragmentActivity activity = (FragmentActivity) ActivityHelper.getCurrentActivity();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            //判断是否6.0以上的手机   不是就不用
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                //判断是否有这个权限
                if(ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    //第一请求权限被取消显示的判断，一般可以不写
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        Log.i("readTosdCard","我们需要这个权限给你提供存储服务");
                        showAlert();
                    }else {
                        //2、申请权限: 参数二：权限的数组；参数三：请求码
                        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE);
                    }
                }else {
                    writeToSdCard(url);
                }
            } else{
                writeToSdCard(url);
            }

        } else {
            Toast.makeText(activity, "SD卡不可用，请插入SD卡",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void writeToSdCard(String url){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                downLoadFile(url);
            }
        });
    }

    private void showAlert(){
        FragmentActivity activity = (FragmentActivity) ActivityHelper.getCurrentActivity();
        Dialog alertDialog = new AlertDialog.Builder(activity).
                setTitle("权限说明").
                setMessage("我们需要这个权限给你提供存储服务").
                setIcon(R.drawable.icon_launcher).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //2、申请权限: 参数二：权限的数组；参数三：请求码
                        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE);
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).
                create();
        alertDialog.show();
    }

    /**
     * 根据url下载最新版本的APP，并安装。
     *
     * @param url
     */
    private void downLoadFile(final String url) {
        FragmentActivity activity = (FragmentActivity) ActivityHelper.getCurrentActivity();
        pBar = new ProgressDialog(activity);    //进度条，在下载的时候实时更新进度，提高用户友好度
        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pBar.setTitle("正在下载");
        pBar.setMessage("请稍候...");
        pBar.setCancelable(false);
        pBar.setProgress(0);
        pBar.show();
        new Thread() {
            public void run() {
                try {
                    URL realUrl = new URL(url);
                    // 打开和URL之间的连接
                    HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
                    // 设置通用的请求属性
                    connection.setRequestProperty("accept", "*/*");
                    connection.setRequestProperty("connection", "Keep-Alive");
                    // 建立实际的连接
                    connection.connect();

                    int fileLength = connection.getContentLength();   //获取文件大小
                    pBar.setMax(fileLength/1024);                            //设置进度条的总长度
                    BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
                    File file = new File(
                            Environment.getExternalStorageDirectory(),
                            localAppPath);
                    if(!file.getParentFile().exists()){
                        file.getParentFile().mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buf = new byte[1024];   //这个是缓冲区，即一次读取10个比特，我弄的小了点，因为在本地，所以数值太大一 下就下载完了，看不出progressbar的效果。
                    int ch = -1;
                    int process = 0;
                    while ((ch = bis.read(buf)) != -1) {
                        fos.write(buf,0,ch);
                        process += ch;
                        pBar.setProgress(process/1024);       //这里就是关键的实时更新进度了！
                    }

                    fos.flush();
                    bis.close();
                    fos.close();
                    connection.disconnect();

                    update();
                } catch (Exception e) {
                    Log.e("flyzing", "run: ", e);
                }
            }

        }.start();
    }

    //安装文件，一般固定写法
    private void update() {
        FragmentActivity activity = (FragmentActivity) ActivityHelper.getCurrentActivity();
        pBar.cancel();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //ANDROID 7.0 以上版本安装
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            Uri uriForFile = FileProvider.getUriForFile(activity,
                    activity.getApplicationContext().getPackageName() + ".provider",
                    new File(Environment.getExternalStorageDirectory(), localAppPath));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uriForFile, activity.getContentResolver().getType(uriForFile));
        }else{
            intent.setDataAndType(Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), localAppPath)),
                    "application/vnd.android.package-archive");
        }
        activity.startActivity(intent);
    }
}
