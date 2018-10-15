package cn.dagongniu.oax.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.dagongniu.oax.R;

public class CommonToolbar extends Toolbar implements View.OnClickListener {
    private Context mContext;
    private RelativeLayout mLeft;
    private TextView mTvTitle;
    private CommonToolbarLeftClickListener mLeftListener;
    private CommonToolbarRightClickListener mRightListener;
    private TextView mTvRight;

    public CommonToolbar(Context context) {
        this(context, null);
    }

    public CommonToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View v = LayoutInflater.from(context).inflate(R.layout.common_toolbar, this, false);
        addView(v);
        mLeft = v.findViewById(R.id.ll_left);
        mTvRight = v.findViewById(R.id.tv_right);
        mTvTitle = v.findViewById(R.id.tv_title);
        mLeft.setOnClickListener(this);
        mTvRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                if (mLeftListener != null) {
                    mLeftListener.onLeftClick();
                }
                break;
            case R.id.tv_right:
                if (mRightListener != null) {
                    mRightListener.onRightClick();
                }
                break;
        }
    }

    public interface CommonToolbarLeftClickListener {
        void onLeftClick();
    }

    public void setOnCommonToolbarLeftClickListener(CommonToolbarLeftClickListener listener) {
        this.mLeftListener = listener;
    }

    public interface CommonToolbarRightClickListener {
        void onRightClick();
    }

    public void setOnCommonToolbarRightClickListener(CommonToolbarRightClickListener listener) {
        this.mRightListener = listener;
    }

    public void setTitleText(String title) {
        if (title != null) {
            mTvTitle.setText(title);
        }
    }

    public void setRightText(String text) {
        if (text != null) {
            mTvRight.setText(text);
            mTvRight.setVisibility(VISIBLE);
        }
    }
}
