package cn.dagongniu.oax.account.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.dagongniu.oax.R;

public class EarningToolBar extends Toolbar implements View.OnClickListener {

    private RelativeLayout mLeft;
    private TextView mTvTitle;
    private TextView mTvRight;
    private EarningToolBarClickListener mListener;

    public EarningToolBar(Context context) {
        this(context, null);
    }

    public EarningToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EarningToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View v = LayoutInflater.from(context).inflate(R.layout.earning_toolbar, this, false);
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
                if (mListener != null) {
                    mListener.onLeftClick();
                }
                break;
            case R.id.tv_right:
                if (mListener != null) {
                    mListener.onRightClick();
                }
                break;
        }
    }

    public interface EarningToolBarClickListener {
        void onLeftClick();

        void onRightClick();
    }

    public void setOnCommonToolbarLeftClickListener(EarningToolBarClickListener listener) {
        this.mListener = listener;
    }
}
