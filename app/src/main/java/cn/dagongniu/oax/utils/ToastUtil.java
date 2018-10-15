package cn.dagongniu.oax.utils;


import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import cn.dagongniu.oax.base.BaseActivity;


/**
 * Toast 工具
 */
public class ToastUtil {

    public static final String TAG = "ToastUtil";

    public static void ShowToast(String message) {
        if (null != BaseActivity.baseActivity) {
            Toast.makeText(BaseActivity.baseActivity, "" + message, Toast.LENGTH_SHORT).show();
        } else {
            Logger.d(TAG, "" + message);
        }
    }

    public static void ShowToast(String message, Activity activity) {
        if (null != activity) {
            Toast.makeText(activity, "" + message, Toast.LENGTH_SHORT).show();
        } else {
            Logger.d(TAG, "" + message);
        }
    }

}
