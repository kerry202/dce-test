package cn.dagongniu.oax.trading.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.trading.bean.TransactionIndexBean;


/**
 * 交易首页 买 数据适配器
 */
public class TradingMarketBuyAdapter extends BaseQuickAdapter<TransactionIndexBean.DataBean.MarketOrdersMapBean.BuyListBean, BaseViewHolder> {

    Context context;


    public TradingMarketBuyAdapter(Context context) {
        super(R.layout.trading_fall_item_layout);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, TransactionIndexBean.DataBean.MarketOrdersMapBean.BuyListBean item) {
        helper.setTextColor(R.id.fall_item_jiage, context.getResources().getColor(R.color.button_bu));

        BigDecimal bigDecimalQty = item.getQty().setScale(7, BigDecimal.ROUND_DOWN);

        BigDecimal bigDecimalPrice = item.getPrice().setScale(7, BigDecimal.ROUND_DOWN);

        helper.setText(R.id.fall_item_count, bigDecimalQty.toString());
        helper.setText(R.id.fall_item_jiage, bigDecimalPrice.toString());
    }
}
