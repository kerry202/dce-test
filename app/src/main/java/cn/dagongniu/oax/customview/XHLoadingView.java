package cn.dagongniu.oax.customview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.utils.Logger;


/**
 * Created by 自定义网络加载详情提示页
 */

public class XHLoadingView extends FrameLayout {

    private static final String TAG = "XHLoadingView";

    private Context mContext;
    // 加载中的布局
    private LinearLayout mLinearLoad;

    private TextView mTvLoad;

    private ImageView mIvLoad;

    private TextView mBtnLoad;

    private LoadingState mState;


    public XHLoadingView(Context context) {
        super(context);
        mContext = context;
    }

    public XHLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public XHLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }


    public void build() {

        LayoutInflater.from(mContext).inflate(R.layout.empty_layout, this, true);

        mLinearLoad = findViewById(R.id.lin_load);

        mIvLoad = findViewById(R.id.iv_load);

        mTvLoad = findViewById(R.id.tv_load);

        mBtnLoad = findViewById(R.id.btn_load);

        mLinearLoad.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRetryListener.onRetry(v);
            }
        });
        mBtnLoad.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRetryListener.onRetry(v);
            }
        });

    }


    /**
     * 设置加载的状态
     *
     * @param state
     */
    public void setState(LoadingState state,String msg) {

        if (mState == state) {
            return;
        } else if (state == LoadingState.STATE_LOADING) {
            mLinearLoad.setVisibility(INVISIBLE);
            this.setVisibility(View.INVISIBLE);
        } else if (state != LoadingState.STATE_LOADING) {
            mLinearLoad.setVisibility(VISIBLE);
            this.setVisibility(View.VISIBLE);
        }
        changeState(state,msg);
    }


    /**
     * 加载中提示文字
     */
    private String mLoadingText;
    private int mLoadingIcon;

    public XHLoadingView withLoadingIcon(int resId) {
        mLoadingIcon = resId;
        return this;
    }

    /**
     * 加载数据为空提示文字
     */
    private String mLoadEmptyText;
    private int mLoadEmptyIcon;

    public XHLoadingView withEmptyIcon(int resId) {
        mLoadEmptyIcon = resId;
        return this;
    }

    /**
     * 无网络提示
     */
    private String mLoadNoNetworkText;
    private int mNoNetworkIcon;

    public XHLoadingView withNoNetIcon(int resId) {
        mNoNetworkIcon = resId;
        return this;
    }

    private OnReTryClickListener mOnRetryListener;

    /**
     * 定义重试的的接口
     */
    public interface OnReTryClickListener {
        void onRetry(View view);
    }

    public void setWithOnRetryListener(OnReTryClickListener clickListener) {
        this.mOnRetryListener = clickListener;
    }


    public boolean btnEmptyEnable = true;
    public boolean btnErrorEnable = true;
    public boolean btnNoNetworkEnable = true;

    public XHLoadingView withBtnNoNetEnnable(boolean ennable) {
        btnNoNetworkEnable = ennable;
        return this;
    }

    public XHLoadingView withBtnErrorEnnable(boolean ennable) {
        btnErrorEnable = ennable;
        return this;
    }


    public XHLoadingView withBtnEmptyEnnable(boolean ennable) {
        btnEmptyEnable = ennable;
        return this;
    }

    /**
     * 改变状态
     *
     * @param state
     */
    private void changeState(LoadingState state,String msg) {
        switch (state) {
            //数据为空
            case STATE_EMPTY:
                mState = LoadingState.STATE_EMPTY;
                mIvLoad.setImageResource(mLoadEmptyIcon);
                mTvLoad.setText(mLoadEmptyText);
                if (btnEmptyEnable) {
                    mBtnLoad.setVisibility(VISIBLE);
                    mBtnLoad.setText(btn_empty_text);
                } else {
                    mBtnLoad.setVisibility(GONE);
                }
                break;
            //加载失败
            case STATE_ERROR:
                mState = LoadingState.STATE_ERROR;
                mIvLoad.setImageResource(mErrorIco);
                mTvLoad.setText(mLoadErrorText);
                if (btnErrorEnable) {
                    mBtnLoad.setVisibility(VISIBLE);
                    mBtnLoad.setText(btn_error_text);
                } else {
                    mBtnLoad.setVisibility(GONE);
                }
                break;
            //无网络
            case STATE_NO_NET:
                mState = LoadingState.STATE_NO_NET;
                mIvLoad.setImageResource(mNoNetworkIcon);
                mTvLoad.setText(mLoadNoNetworkText);
                if (btnNoNetworkEnable) {
                    mBtnLoad.setVisibility(VISIBLE);
                    mBtnLoad.setText(btn_nonet_text);
                } else {
                    mBtnLoad.setVisibility(GONE);
                }
                break;
            //自定义
            case STATE_CUSTOM:
                mState = LoadingState.STATE_CUSTOM;
                mIvLoad.setImageResource(mLoadEmptyIcon);
                mTvLoad.setText(msg);
                if (btnNoNetworkEnable) {
                    mBtnLoad.setVisibility(VISIBLE);
                    mBtnLoad.setText(btn_nonet_text);
                } else {
                    mBtnLoad.setVisibility(GONE);
                }
                break;
        }

    }


    /**
     * 后台或者本地出现错误提示
     */
    private String mLoadErrorText;
    private int mErrorIco;

    public XHLoadingView withErrorIco(int resId) {
        mErrorIco = resId;
        return this;
    }

    /**
     * 加载空数据
     *
     * @param resId
     * @return
     */
    public XHLoadingView withLoadEmptyText(int resId) {
        mLoadEmptyText = getResources().getString(resId);
        return this;
    }

    public XHLoadingView withLoadEmptyText(String mLoadEmptyText) {
        this.mLoadEmptyText = mLoadEmptyText;
        return this;
    }

    /**
     * 无网络时候加载文字
     *
     * @param resId
     * @return
     */
    public XHLoadingView withLoadNoNetworkText(int resId) {
        mLoadNoNetworkText = getResources().getString(resId);
        return this;
    }

    public String btn_empty_text = "重试";
    public String btn_error_text = "重试";
    public String btn_nonet_text = "重试";

    /**
     * 数据为空的Button的文字提示
     *
     * @param text
     * @return
     */
    public XHLoadingView withBtnEmptyText(String text) {
        this.btn_empty_text = text;
        return this;
    }

    /**
     * 加载错误的Button的文字提示
     *
     * @param text
     * @return
     */
    public XHLoadingView withBtnErrorText(String text) {
        this.btn_error_text = text;
        return this;
    }

    /**
     * 加载错误的文字提示
     *
     * @param resId
     * @return
     */
    public XHLoadingView withLoadErrorText(int resId) {
        this.mLoadErrorText = getResources().getString(resId);
        return this;
    }

    public XHLoadingView withLoadErrorText(String mLoadedErrorText) {
        this.mLoadErrorText = mLoadedErrorText;
        return this;
    }

    /**
     * 加载无网络的Button的文字提示
     *
     * @param text
     * @return
     */
    public XHLoadingView withBtnNoNetText(String text) {
        this.btn_nonet_text = text;
        return this;
    }

    /**
     * 加载没有网路的文字提示
     *
     * @param mLoadedNoNetText
     * @return
     */
    public XHLoadingView withLoadNoNetworkText(String mLoadedNoNetText) {
        this.mLoadNoNetworkText = mLoadedNoNetText;
        return this;
    }

    public XHLoadingView withLoadingText(int resId) {
        this.mLoadingText = getResources().getString(resId);
        return this;
    }

    public XHLoadingView withLoadingText(String mLoadingText) {
        this.mLoadingText = mLoadingText;
        return this;
    }

}
