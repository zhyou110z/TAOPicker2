package com.rt.taopicker.service.log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by yaoguangyao on 2017/10/23.
 */

public class AppLogWakeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AppLogService.class);
        context.startService(i);
    }

}
