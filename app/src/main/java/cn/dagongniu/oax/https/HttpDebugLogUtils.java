package cn.dagongniu.oax.https;


import android.text.TextUtils;
import android.util.Log;

/**
 * 网络请求 log处理
 */
public class HttpDebugLogUtils {

    private static boolean isDebug = true;
    private static String Tag = "网络请求debug Log";

    public static void prinlnLog(String str) {
        if (isDebug) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (str.length() < 3000)
                Log.e(Tag, str);
            else {
                String str1 = str.substring(0, 3000);
                Log.e(Tag, str1);
                str = str.substring(3000);
                prinlnLog(str);
            }
        } else {
            Log.e(Tag, "Debug没有打开");
        }
    }

    public static void prinlnLogRe(String str) {
        if (isDebug) {
            Log.e(Tag, str);
        } else {
            Log.e(Tag, "Debug没有打开");
        }
    }

}
