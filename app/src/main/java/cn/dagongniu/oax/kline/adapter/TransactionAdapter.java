package cn.dagongniu.oax.kline.adapter;

import android.content.Context;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.kline.bean.CommitteeBean;
import cn.dagongniu.oax.kline.bean.TradingInfoBean;
import cn.dagongniu.oax.kline.bean.TransactionBean;
import cn.dagongniu.oax.trading.bean.TradeListAndMarketOrdersBean;

public class TransactionAdapter extends BaseQuickAdapter<TradeListAndMarketOrdersBean.TradeListBean, BaseViewHolder> {
    private int priceDecimals = 8;//价格精度
    private int qtyDecimals = 8;//数量精度
    Context context;

    public TransactionAdapter(Context context) {
        super(R.layout.adapter_transaction);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TradeListAndMarketOrdersBean.TradeListBean item) {
        //数量
        BigDecimal qty = item.getQty();
        BigDecimal volume = qty.setScale(qtyDecimals, BigDecimal.ROUND_DOWN);
        helper.setText(R.id.tv_volume, volume.toPlainString());
        //价格
        BigDecimal price = item.getPrice();
        BigDecimal priceDecimal = price.setScale(priceDecimals, BigDecimal.ROUND_DOWN);
        helper.setText(R.id.tv_price, priceDecimal.toPlainString());
        if (item.getType() == 1) {
            helper.setTextColor(R.id.tv_price, context.getResources().getColor(R.color.kline_buy_bg));
        } else {
            helper.setTextColor(R.id.tv_price, context.getResources().getColor(R.color.kline_sell_bg));
        }
        helper.setText(R.id.tv_time, item.getCreateTime());
    }

    public void setDecimals(int priceDecimals, int qtyDecimals) {
        this.priceDecimals = priceDecimals;
        this.qtyDecimals = qtyDecimals;
    }
}
