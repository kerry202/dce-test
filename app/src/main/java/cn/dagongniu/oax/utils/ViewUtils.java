package cn.dagongniu.oax.utils;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.kaopiz.kprogresshud.KProgressHUD;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.customview.DividerLine;

public class ViewUtils {

    /**
     * 获取RecyclerView 分割线
     */
    public static DividerLine getRecyclerViewDividerLine(Context context) {
        DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
        dividerLine.setColor(ContextCompat.getColor(context, R.color.df_line));
        dividerLine.setSize(context.getResources().getDimensionPixelSize(R.dimen.d02));
        return dividerLine;
    }


    /**
     * 显示自定义toast
     */
    public static void showKProgressHUD(Context context,int drawable,int string) {
        ImageView imageView = new ImageView(context);
        imageView.setBackgroundResource(drawable);
        final KProgressHUD hud = KProgressHUD.create(context)
                .setCustomView(imageView)
                .setAnimationSpeed(200)
                .setLabel(context.getResources().getString(string)
                ).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hud.dismiss();
            }
        }, 1000);
    }
}
