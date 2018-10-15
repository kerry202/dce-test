package cn.dagongniu.oax.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.https.UrlParams;
import cn.dagongniu.oax.kline.KLinesActivity;
import cn.dagongniu.oax.main.MainActivity;

public class SkipActivityUtil {

    public SkipActivityUtil() {
        throw new UnsupportedOperationException("ActivitySkipUtil不能实例化");
    }

    /**
     * 功能描述:简单地Activity的跳转(不携带任何数据) 不关闭当前页面
     *
     * @param context
     * @param toClass 目标Activity实例
     */
    public static void skipAnotherActivity(Context context,
                                           Class<? extends Activity> toClass) {
        Intent intent = new Intent(context, toClass);
        context.startActivity(intent);
    }

    /**
     * 功能描述:简单地Activity的跳转(不携带任何数据)
     *
     * @param fromActivity 发起跳转的Activity实例
     * @param toClass      目标Activity实例
     */
    public static void skipAnotherActivity(Activity fromActivity,
                                           Class<? extends Activity> toClass, Boolean isFinishFromActivity) {
        Intent intent = new Intent(fromActivity, toClass);
        fromActivity.startActivity(intent);
        if (isFinishFromActivity) {
            fromActivity.finish();
        }
    }

    /**
     * 功能描述：带数据的Activity之间的跳转  仅限String Boolean Integer Float Double
     *
     * @param fromActivity
     * @param toClass
     * @param hashMap
     */
    public static void skipAnotherActivity(Activity fromActivity,
                                           Class<? extends Activity> toClass,
                                           HashMap<String, ? extends Object> hashMap, Boolean isFinishFromActivity) {
        Intent intent = new Intent(fromActivity, toClass);
        Iterator<?> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            @SuppressWarnings("unchecked")
            Map.Entry<String, Object> entry = (Entry<String, Object>) iterator
                    .next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                intent.putExtra(key, (String) value);
            }
            if (value instanceof Boolean) {
                intent.putExtra(key, (boolean) value);
            }
            if (value instanceof Integer) {
                intent.putExtra(key, (int) value);
            }
            if (value instanceof Float) {
                intent.putExtra(key, (float) value);
            }
            if (value instanceof Double) {
                intent.putExtra(key, (double) value);
            }
        }
        fromActivity.startActivity(intent);
        if (isFinishFromActivity) {
            fromActivity.finish();
        }
    }

    public static void guidanceSkipToMain(Activity activity, Context context) {
        SkipActivityUtil.skipAnotherActivity(activity, MainActivity.class, true);
        if (context != null) {
            SPUtils.setParam(context, SPConstant.IS_FIRST_LAUNCHED, false);
        }
    }

    public static void startActivityBundle(Activity activity, Class<? extends Activity> toClass, Bundle bundle) {
        Intent intent = new Intent(activity, toClass);
        activity.startActivity(intent, bundle);
    }


    public static void skipToKLineActivity(int marketId, int minType, Activity activity) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put(UrlParams.marketId, marketId);
        map.put(UrlParams.minType, minType);
        skipAnotherActivity(activity, KLinesActivity.class, map, false);
    }
}
