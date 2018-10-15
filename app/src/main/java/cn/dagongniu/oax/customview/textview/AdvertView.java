package cn.dagongniu.oax.customview.textview;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.List;

import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.bean.NoticeCenterBean;

/**
 * 上线浮动textview  广告
 */

public class AdvertView extends ViewFlipper {

    private boolean isAniming = false;

    private OnItemClickListener listener;

    public AdvertView(Context context) {
        this(context, null);
    }

    public AdvertView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(500, 3000);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * @param duration 动画duration
     * @param interval flip间隔
     */
    private void init(long duration, int interval) {
        setAutoStart(true);
        setFlipInterval(interval);
        TranslateAnimation inAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
        inAnim.setDuration(duration);
        setInAnimation(inAnim);

        TranslateAnimation outAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, -1);
        outAnim.setDuration(duration);
        setOutAnimation(outAnim);

        inAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAniming = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAniming = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAniming && listener != null) {
                    listener.onItemClick(indexOfChild(getCurrentView()));
                }
            }
        });
    }

    public void setData(List<IndexPageBean.DataBean.ArticleListBean> strList) {
        if (strList == null) return;
        removeAllViews();
        for (int i = 0; i < strList.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setText(strList.get(i).getName());
            textView.setTextColor(Color.BLACK);
            textView.setSingleLine();
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setTextSize(14);
            //居中
            //textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            addView(textView);
        }
    }
}
