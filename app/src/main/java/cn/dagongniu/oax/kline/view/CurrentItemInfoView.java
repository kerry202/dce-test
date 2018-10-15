package cn.dagongniu.oax.kline.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.socks.library.KLog;

import java.math.BigDecimal;

import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.kline.bean.TradingInfoBean;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.utils.KLineUtils;

public class CurrentItemInfoView extends RelativeLayout {

    private Context mContext;
    private View mView;
    private TextView mHigh, mLow, mOpen, mClose, mVolume, mLimit, mMA5, mMA10, mMA30, mMA60;
    private boolean mIsFromFullScreen;
    String priceLimit = "0.0";
    String volLimit = "0.0";

    public CurrentItemInfoView(Context context) {
        super(context);
        mContext = context;
    }

    public CurrentItemInfoView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CurrentItemInfoView);
        mIsFromFullScreen = mTypedArray.getBoolean(R.styleable.CurrentItemInfoView_isFromFullScreen, false);
        if (mIsFromFullScreen) {
            mView = View.inflate(context, R.layout.current_item_info_fullscreen_view, null);
        } else {
            mView = View.inflate(context, R.layout.current_item_info_view, null);
        }
        addView(mView);
        mHigh = mView.findViewById(R.id.tv_high);
        mLow = mView.findViewById(R.id.tv_low);
        mOpen = mView.findViewById(R.id.tv_open);
        mClose = mView.findViewById(R.id.tv_close);
        mVolume = mView.findViewById(R.id.tv_volume);
        mLimit = mView.findViewById(R.id.tv_limit);
        mMA5 = mView.findViewById(R.id.tv_ma5);
        mMA10 = mView.findViewById(R.id.tv_ma10);
        mMA30 = mView.findViewById(R.id.tv_ma30);
        mMA60 = mView.findViewById(R.id.tv_ma60);
        mTypedArray.recycle();
    }

    public void dataChange(TradingInfoBean.KlineListBean bean, float ma5, float ma10, float ma30, float ma60) {
        KLog.d("dataChange = " + new Gson().toJson(bean));
        if (bean != null) {
            int marketId = bean.getMarketId();
            int priceDecimal = 8;
            int volDecimal = 4;
            if (OAXApplication.coinsInfoMap.containsKey(marketId)) {
                IndexPageBean.DataBean.AllMaketListBean.MarketListBean marketListBean = OAXApplication.coinsInfoMap.get(marketId);
                priceDecimal = marketListBean.getPriceDecimals();
                volDecimal = marketListBean.getQtyDecimals();
            }
            //成交量
            try {
                String volume = bean.getQty().setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
                mVolume.setText(volume);
            } catch (Exception e) {
                mVolume.setText(volLimit);
            }

            //最高
            try {
                String high = bean.getHigh().setScale(priceDecimal, BigDecimal.ROUND_DOWN).toPlainString();
                mHigh.setText(high);
            } catch (Exception e) {
                mHigh.setText(priceLimit);
            }

            //最低
            try {
                String low = bean.getLow().setScale(priceDecimal, BigDecimal.ROUND_DOWN).toPlainString();
                mLow.setText(low);
            } catch (Exception e) {
                mLow.setText(priceLimit);
            }

            //开盘
            try {
                String opens = bean.getOpen().setScale(priceDecimal, BigDecimal.ROUND_DOWN).toPlainString();
                mOpen.setText(opens);
            } catch (Exception e) {
                mOpen.setText(priceLimit);
            }

            //收盘
            try {
                String closes = bean.getClose().setScale(priceDecimal, BigDecimal.ROUND_DOWN).toPlainString();
                mClose.setText(closes);
            } catch (Exception e) {
                mClose.setText(priceLimit);
            }

            float open = Float.valueOf(bean.getOpen().toPlainString());
            float close = Float.valueOf(bean.getClose().toPlainString());
            float per = 0;
            if (open != 0) {
                per = ((close - open) / open) * 100;
            }
            String limit = KLineUtils.formatFloat(per);
            mLimit.setText(limit + "%");


            //ma5
            try {
                String ma5s = new BigDecimal(ma5).setScale(priceDecimal, BigDecimal.ROUND_DOWN).toPlainString();
                mMA5.setText(ma5s);
            } catch (Exception e) {
                mMA5.setText(priceLimit);
            }

            //ma10
            try {
                String ma10s = new BigDecimal(ma10).setScale(priceDecimal, BigDecimal.ROUND_DOWN).toPlainString();
                mMA10.setText(ma10s);
            } catch (Exception e) {
                mMA10.setText(priceLimit);
            }

            //ma30
            try {
                String ma30s = new BigDecimal(ma30).setScale(priceDecimal, BigDecimal.ROUND_DOWN).toPlainString();
                mMA30.setText(ma30s);
            } catch (Exception e) {
                mMA30.setText(priceLimit);
            }
            //ma60
            try {
                String ma60s = new BigDecimal(ma60).setScale(priceDecimal, BigDecimal.ROUND_DOWN).toPlainString();
                mMA60.setText(ma60s);
            } catch (Exception e) {
                mMA60.setText(priceLimit);
            }
        }

    }
}
