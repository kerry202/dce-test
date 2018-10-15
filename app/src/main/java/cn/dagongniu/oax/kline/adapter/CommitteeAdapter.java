package cn.dagongniu.oax.kline.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.math.BigDecimal;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.kline.bean.CommitteeBean;
import cn.dagongniu.oax.kline.bean.TradingInfoBean;
import cn.dagongniu.oax.trading.bean.TradeListAndMarketOrdersBean;

public class CommitteeAdapter extends BaseQuickAdapter<TradeListAndMarketOrdersBean.BuyOrSellListBean, BaseViewHolder> {
    private int type = 0;
    private Context context;
    private int priceDecimals = 8;//价格精度
    private int qtyDecimals = 8;//数量精度
    private boolean isEmptyData = false;

    public static final int TYPE_BUY = 1;
    public static final int TYPE_SELL = 2;


    public CommitteeAdapter(Context context, int type) {
        super(R.layout.adapter_committee);
        this.type = type;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TradeListAndMarketOrdersBean.BuyOrSellListBean item) {
        if (!isEmptyData) {
            try {
                //数量
                BigDecimal volume = item.getQty().setScale(qtyDecimals, BigDecimal.ROUND_DOWN);
                helper.setText(R.id.tv_volume, volume.toPlainString());
                //价格
                BigDecimal price = item.getPrice().setScale(priceDecimals, BigDecimal.ROUND_DOWN);
                helper.setText(R.id.tv_price, price.toPlainString());
                if (type == TYPE_BUY) {
                    helper.setTextColor(R.id.tv_price, context.getResources().getColor(R.color.kline_buy_bg));
                } else if (type == TYPE_SELL) {
                    helper.setTextColor(R.id.tv_price, context.getResources().getColor(R.color.kline_sell_bg));
                }
            } catch (Exception e) {

            }
        } else {
            helper.setText(R.id.tv_volume, "--");
            helper.setText(R.id.tv_price, "--");
        }

    }

    public void setDecimals(int priceDecimals, int qtyDecimals) {
        this.priceDecimals = priceDecimals;
        this.qtyDecimals = qtyDecimals;
    }

    public void isEmptyData(boolean isEmptyData) {
        this.isEmptyData = isEmptyData;
    }
}
