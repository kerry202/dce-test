package cn.dagongniu.oax.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import cn.dagongniu.oax.R;

/**
 * 页面工具
 */
public class PageToolUtils {

    /**
     * 复制文本
     * @param context
     * @param tvMsg
     */
    public static void CopyText(Context context, TextView tvMsg) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(tvMsg.getText());
        ToastUtil.ShowToast(context.getResources().getString(R.string.copy_success));
    }

}
