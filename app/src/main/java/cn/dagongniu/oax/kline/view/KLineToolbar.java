package cn.dagongniu.oax.kline.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.dagongniu.oax.R;

public class KLineToolbar extends Toolbar implements View.OnClickListener {

    private Context mContext;
    private ImageView mIvRight;
    private RelativeLayout mLeft, mRight;
    private TextView mTvTitle;
    private OnKLineToolbarClickListener mListener;

    public KLineToolbar(Context context) {
        this(context, null);
    }

    public KLineToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KLineToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View v = LayoutInflater.from(context).inflate(R.layout.kline_toolbar, this, false);
        addView(v);
        mLeft = v.findViewById(R.id.ll_left);
        mRight = v.findViewById(R.id.ll_right);
        mIvRight = v.findViewById(R.id.iv_right);
        mTvTitle = v.findViewById(R.id.tv_title);
        mRight.setOnClickListener(this);
        mLeft.setOnClickListener(this);
        mTvTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                if (mListener != null) {
                    mListener.onLeftClick();
                }
                break;
            case R.id.ll_right:
                if (mListener != null) {
                    mListener.onRightClick();
                }
                break;
            case R.id.tv_title:
                if (mListener != null) {
                    mListener.onTvTitleClick();
                }
                break;
        }
    }

    public interface OnKLineToolbarClickListener {
        void onLeftClick();

        void onRightClick();

        void onTvTitleClick();
    }

    public void setOnKLineToolbarClickListener(OnKLineToolbarClickListener listener) {
        this.mListener = listener;
    }

    public void setIvRightState(boolean isSelected) {
        if (isSelected) {
            mIvRight.setSelected(true);
        } else {
            mIvRight.setSelected(false);
        }
    }

    public void setTvTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setText(title);
        }
    }
}
