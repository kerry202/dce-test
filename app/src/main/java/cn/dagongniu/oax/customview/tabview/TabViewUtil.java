package cn.dagongniu.oax.customview.tabview;

import android.content.Context;
import android.util.TypedValue;

/**
 *  底部导航 dp sp 转化工具
 */
public class TabViewUtil {

    public TabViewUtil() {
    }
    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }
}
