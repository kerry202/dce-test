package cn.dagongniu.oax.language;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.customview.ClassicsFooter;
import cn.dagongniu.oax.customview.ClassicsHeader;

/**
 * 工具
 */
public class LanguageUtils {

    /**
     * 刷新 国际化适配
     */
    public static void setHeaderLanguage(ClassicsHeader mClassicsHeader, Context context) {
        List<String> list = new ArrayList<>();
        list.add(context.getResources().getString(R.string.srl_header_pulling));
        list.add(context.getResources().getString(R.string.srl_header_refreshing));
        list.add(context.getResources().getString(R.string.srl_header_loading));
        list.add(context.getResources().getString(R.string.srl_header_release));
        list.add(context.getResources().getString(R.string.srl_header_finish));
        list.add(context.getResources().getString(R.string.srl_header_failed));
        list.add(context.getResources().getString(R.string.srl_header_update));
        list.add(context.getResources().getString(R.string.srl_header_secondary));
        mClassicsHeader.setLanguage(list, context);
    }

    /**
     * 加载 国际化适配
     */
    public static void setFooterLanguage(ClassicsFooter mClassicsHeader, Context context) {
        List<String> list = new ArrayList<>();
        list.add(context.getResources().getString(R.string.srl_footer_pulling));
        list.add(context.getResources().getString(R.string.srl_footer_release));
        list.add(context.getResources().getString(R.string.srl_footer_loading));
        list.add(context.getResources().getString(R.string.srl_footer_refreshing));
        list.add(context.getResources().getString(R.string.srl_footer_finish));
        list.add(context.getResources().getString(R.string.srl_footer_failed));
        list.add(context.getResources().getString(R.string.srl_footer_nothing));
        mClassicsHeader.setLanguage(list, context);
    }


}
