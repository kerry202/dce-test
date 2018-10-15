package cn.dagongniu.oax.trading.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.util.List;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.main.bean.IndexPageBean;

/**
 * 交易页自选市场适配器
 */
public class TradingZXMarketAdapter extends BaseQuickAdapter<IndexPageBean.DataBean.UserMaketListBean, BaseViewHolder> {

    List<IndexPageBean.DataBean.UserMaketListBean> marketListBeans;

    Context context;

    public TradingZXMarketAdapter(Context context, List<IndexPageBean.DataBean.UserMaketListBean> marketListBeans) {
        super(R.layout.market_choose_item_layout);
        this.context = context;
        this.marketListBeans = marketListBeans;
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexPageBean.DataBean.UserMaketListBean data) {

        //-------
        //交易对   币种getCoinName/市场getMarketCoinName
        helper.setText(R.id.tv_market_name, data.getCoinName() + "/" + data.getMarketCoinName());

        //-------
        BigDecimal bigDecimal = new BigDecimal(data.getLastTradePrice());
        //币种单价
        BigDecimal tradePrice = bigDecimal.setScale(data.getPriceDecimals(), BigDecimal.ROUND_DOWN);
        //币种当前价格 保留getPriceDecimals位小数
        helper.setText(R.id.tv_left_coin_price, tradePrice.toPlainString());

        //-------
        //涨跌幅 百分比
        if (Double.parseDouble(data.getIncRate()) > 0) {
            helper.setTextColor(R.id.tv_market_incrate, ContextCompat.getColor(context, R.color.kline_buy_bg));
            helper.setText(R.id.tv_market_incrate, "+" + data.getIncRate() + "%");
        } else if (Double.parseDouble(data.getIncRate()) < 0) {
            helper.setTextColor(R.id.tv_market_incrate, ContextCompat.getColor(context, R.color.kline_sell_bg));
            helper.setText(R.id.tv_market_incrate, "" + data.getIncRate() + "%");
        } else {
            helper.setTextColor(R.id.tv_market_incrate, ContextCompat.getColor(context, R.color.df_font));
            helper.setText(R.id.tv_market_incrate, "" + data.getIncRate() + "%");
        }



    }

}
