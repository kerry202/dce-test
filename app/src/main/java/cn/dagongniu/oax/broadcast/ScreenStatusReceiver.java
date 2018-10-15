package cn.dagongniu.oax.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.socks.library.KLog;

import cn.dagongniu.oax.OAXApplication;

public class ScreenStatusReceiver extends BroadcastReceiver {
    String SCREEN_ON = "android.intent.action.SCREEN_ON";
    String SCREEN_OFF = "android.intent.action.SCREEN_OFF";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SCREEN_ON.equals(intent.getAction())) {
            OAXApplication.isScreenOn = true;
            KLog.d("ScreenStatusReceiver = true");
        } else if (SCREEN_OFF.equals(intent.getAction())) {
            OAXApplication.isScreenOn = false;
            KLog.d("ScreenStatusReceiver = false");
        }
    }
}

