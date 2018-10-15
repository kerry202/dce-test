package cn.dagongniu.oax.utils.um;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.Map;

import cn.dagongniu.oax.constant.UMConstant;

public class UMManager {

    public static void initUMConfigure(Context context) {
        UMConfigure.init(context, UMConfigure.DEVICE_TYPE_PHONE, "");
    }


    /**
     * 关闭友盟自动统计和确定场景类型
     *
     * @param context
     */
    public static void openActivityDurationTrack(Context context) {
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    /**
     * 用于统计启动次数和时长 配合onPause
     *
     * @param context
     * @param event
     */
    public static void onResume(Context context, String event) {
        MobclickAgent.onPageStart(event);
        MobclickAgent.onResume(context);
    }

    /**
     * 用于统计启动次数和时长 配合onResume
     *
     * @param context
     * @param event
     */
    public static void onPause(Context context, String event) {
        MobclickAgent.onPageEnd(event);
        MobclickAgent.onPause(context);
    }

    /**
     * @param context 当前宿主进程的ApplicationContext上下文。
     * @param eventId 为当前统计的事件ID
     */
    public static void onEvent(Context context, String eventId) {
        MobclickAgent.onEvent(context, eventId);
    }

    /**
     * @param context 当前宿主进程的ApplicationContext上下文。
     * @param eventId 为当前统计的事件ID
     * @param label   事件的标签属性。
     */
    public static void onEvent(Context context, String eventId, String label) {
        MobclickAgent.onEvent(context, eventId, label);
    }

    /**
     * @param context 当前宿主进程的ApplicationContext上下文。
     * @param eventId 为当前统计的事件ID
     * @param map     为当前事件的属性和取值（Key-Value键值对）。
     */
    public static void onEvent(Context context, String eventId, Map<String, String> map) {
        MobclickAgent.onEvent(context, eventId, map);
    }
}
