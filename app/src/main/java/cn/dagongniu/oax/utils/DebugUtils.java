package cn.dagongniu.oax.utils;

import android.text.TextUtils;
import android.util.Log;

import com.socks.library.KLog;

/**
 * 请求Debug、  Json log输出
 */
public class DebugUtils {

    //TODO 网络请求是否开启log
    private static boolean isDebug = true;
    private static String Tag = "DebugUtils";


    public static void prinlnLog(String str) {
        if (isDebug) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            KLog.json(str);

        } else {
            KLog.e("请求Debug没有打开");
        }
    }

    public static void prinlnLogRe(String str) {
        if (isDebug) {
            KLog.json(str);
        } else {
            KLog.e("请求Debug没有打开");
        }
    }
}
