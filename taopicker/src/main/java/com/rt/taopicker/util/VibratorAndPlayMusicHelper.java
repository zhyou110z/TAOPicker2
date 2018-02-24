package com.rt.taopicker.util;

import android.app.Activity;
import android.app.Service;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Vibrator;

/**
 * 震动和播放语音工具类
 * @author zhouyou
 */
public class VibratorAndPlayMusicHelper {

    private static Vibrator vib ;
    private static MediaPlayer mMusic = null; // 播放器引用

    /** 
     * final Activity activity  ：调用该方法的Activity实例 
     * long milliseconds ：震动的时长，单位是毫秒 
     * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒 
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次 
     */

    private static void Vibrate(final Activity activity, long milliseconds) {
        vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
     }

    private static void Vibrate(final Activity activity, long[] pattern, boolean isRepeat) {
         try{
             if(vib == null){
                 vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
             }
             vib.vibrate(pattern, isRepeat ? 0 : -1);
         }catch (Exception e){
             e.printStackTrace();
         }

     }

    private static void Vibrate(Vibrator vibrator , final Activity activity, long[] pattern, boolean isRepeat) {
        vibrator = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, isRepeat ? 1 : -1);
    }

    private static void cancel(){
        if(vib != null){
            vib.cancel();
        }
    }

    /**
     * 开启
     */
    public static void open(){
        try{
            long[] pattern = {300, 800};   // 停止 开启 1次
            VibratorAndPlayMusicHelper.Vibrate(ActivityHelper.getCurrentActivity(), pattern, true);//重复震动
            if(mMusic == null){
                mMusic = MediaPlayer.create(ActivityHelper.getCurrentActivity(), RingtoneManager.getActualDefaultRingtoneUri(ActivityHelper.getCurrentActivity(), RingtoneManager.TYPE_NOTIFICATION));
                mMusic.setLooping(true); //循环播放
            }
            mMusic.start();
        }catch (Exception e){

        }
    }

    /**
     * 关闭
     */
    public static void stop(){
        try{
            VibratorAndPlayMusicHelper.cancel();
            if (mMusic != null) {
                mMusic.stop();
                mMusic.release();
                mMusic = null;
            }
        }catch (Exception e){

        }
    }
  
} 