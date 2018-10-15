package cn.dagongniu.oax.customview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.utils.ToastUtil;

/**
 * 自定义 标题控件
 */

public class MyToolbar extends Toolbar implements View.OnClickListener {

    @BindView(R.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R.id.tv_title_center)
    TextView mTvTitleCenter;
    @BindView(R.id.iv_title_right)
    ImageView mIvTitleRight;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.iv_title_more)
    ImageView getmIvTitleMore;

    private Context mContext;
    int rightImg;

    public MyToolbar(Context context) {
        this(context, null);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View v = LayoutInflater.from(context).inflate(R.layout.toolbar_layout, this, false);
        addView(v);
        ButterKnife.bind(this, v);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyToolbar);
        initView(ta, context);
        ta.recycle();
    }


    //设置标题大小
    public void setTitleSize() {
//        TextPaint paint = mTvTitleCenter.getPaint();
//        paint.setFakeBoldText(true);
        mTvTitleCenter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19); //单位最好使用SP
    }

    public void setmIvTitleLeftGone() {
        mIvTitleLeft.setVisibility(GONE);
    }

    public void setmIvTitleLeftMoreGone() {
        getmIvTitleMore.setVisibility(GONE);
    }


    private void initView(TypedArray ta, Context context) {
        boolean hideLeft = ta.getBoolean(R.styleable.MyToolbar_hideLeft, false);
        boolean hideRight = ta.getBoolean(R.styleable.MyToolbar_hideRight, false);
        String titleText = ta.getString(R.styleable.MyToolbar_titleText);
        String rightText = ta.getString(R.styleable.MyToolbar_rightText);
        rightImg = ta.getResourceId(R.styleable.MyToolbar_rightImg, R.mipmap.msg2);
        mIvTitleLeft.setOnClickListener(this);
        mIvTitleRight.setOnClickListener(this);
        hideLeft(hideLeft);
        hideRightImg(hideRight, rightImg);
        setTitleText(titleText);
        setRightText(rightText);
    }

    /**
     * 隐藏左侧图片按钮
     *
     * @param hide
     */
    public void hideLeft(boolean hide) {
        mIvTitleLeft.setVisibility(hide ? GONE : VISIBLE);
    }

    public void hideRightImg(boolean hide, int res) {
        mIvTitleRight.setImageResource(res);
        mIvTitleRight.setVisibility(hide ? GONE : VISIBLE);
    }

    public void hideRightMsgImg() {
        mIvTitleRight.setVisibility(GONE);
    }

    public void hideRightText(boolean hide) {
        mTvTitleRight.setVisibility(hide ? GONE : VISIBLE);
    }

    public void hideTitleCenter(boolean hide) {
        mTvTitleCenter.setVisibility(hide ? GONE : VISIBLE);
    }

    public void setTitleText(CharSequence c) {
        mTvTitleCenter.setText(c);
    }

    public void setRightText(CharSequence c) {
        mTvTitleRight.setText(c);
        if (c != null && !c.toString().isEmpty())
            mTvTitleRight.setVisibility(VISIBLE);
        else mTvTitleRight.setVisibility(GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                ((Activity) mContext).onBackPressed();
                break;
            case R.id.iv_title_right:
               // mContext.startActivity(new Intent(mContext, MsgActivity.class));
                ToastUtil.ShowToast("消息");
                break;
            case R.id.tv_title_right:
                break;
        }
    }

    public void setLeftMoreClickListener(OnClickListener listener) {
        getmIvTitleMore.setOnClickListener(listener);
    }

    public void setLeftClickListener(OnClickListener listener) {
        mIvTitleLeft.setOnClickListener(listener);
    }

    public void setRightImgClickListener(OnClickListener listener) {
        mIvTitleRight.setOnClickListener(listener);
    }

    public void setRightTexClickListener(OnClickListener listener) {
        mTvTitleRight.setOnClickListener(listener);
    }


    public void unReadCount(int count) {
        if (rightImg == R.mipmap.msg2 || rightImg == R.mipmap.msg1) {
            if (count != 0) {
                rightImg = R.mipmap.msg1;
                mIvTitleRight.setImageResource(R.mipmap.msg1);
            } else
                mIvTitleRight.setImageResource(R.mipmap.msg2);
        }

    }
}
