package cn.dagongniu.oax.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.dagongniu.oax.main.MainActivity;


/**
 * Activity管理类，可用于一键关闭应用
 * 不过每个Activity应继承BaseActivity
 */
public class AppManager {

    private boolean removable = true;
    public static List<Activity> activitys = new ArrayList<Activity>();

    public static void activityCreated(Activity activity) {
        activitys.add(activity);
    }

    public static void activityDestroyed(Activity activity) {
        activitys.remove(activity);
    }

    public static void quitApp() {
        if (activitys.size() != 0) {
            Iterator<Activity> aIterator = activitys.iterator();
            while (aIterator.hasNext()) {
                Activity activity = aIterator.next();
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
        activitys.clear();
    }

    public void remove(Activity activity) {
        for (int i = 0; i < activitys.size(); i++) {
            Activity a = activitys.get(i);
            if (activitys.getClass().getSimpleName().equals(activity.getClass().getSimpleName())) {
                a.finish();
                activitys.remove(i);
            }
        }
//        if (activitys != null && removable) {
//            activitys.remove(activity.getClass().getSimpleName());
//        }
    }


    public static void quit() {
        if (activitys.size() != 0) {
            Iterator<Activity> aIterator = activitys.iterator();
            while (aIterator.hasNext()) {
                Activity activity = aIterator.next();
                if (activity != null && !activity.isFinishing()) {
                    if (activity instanceof MainActivity) {
                    } else {
                        activity.finish();
                    }

                }
            }
        }
    }

    public static void quitExceptMain() {
        if (activitys.size() != 0) {
            Iterator<Activity> aIterator = activitys.iterator();
            while (aIterator.hasNext()) {
                Activity activity = aIterator.next();
                if (activity != null && !activity.isFinishing()) {
                    if (activity instanceof MainActivity) {
                    } else {
                        activity.finish();
                    }
                }
            }
        }
    }

    public static void quitJustLogin() {
        if (activitys.size() != 0) {
            Iterator<Activity> aIterator = activitys.iterator();
            while (aIterator.hasNext()) {
                Activity activity = aIterator.next();
                if (activity != null && !activity.isFinishing()) {
                    if (activity instanceof MainActivity) {
                    } else if (activity instanceof MainActivity) {

                    } else {
                        activity.finish();
                    }
                }
            }
        }
    }

    public static void remove(String activityName) {
        for (int i = 0; i < activitys.size(); i++) {
            Activity a = activitys.get(i);
            if (a.getClass().getSimpleName().equals(activityName)) {
                a.finish();
                activitys.remove(i);
            }
        }
    }


    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                //前台程序
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

}
