package cn.dagongniu.oax.main.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.util.List;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.main.bean.IndexPageBean;


/**
 * 市场搜索适配器
 */
public class SearchCoinAdapter extends BaseQuickAdapter<IndexPageBean.DataBean.AllMaketListBean.MarketListBean, BaseViewHolder> {


    Context context;

    public SearchCoinAdapter(Context context) {
        super(R.layout.search_coin_market_item_layout);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexPageBean.DataBean.AllMaketListBean.MarketListBean data) {

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
            helper.setTextColor(R.id.tv_applies, ContextCompat.getColor(context, R.color.kline_buy_bg));

            BigDecimal incRateBD = new BigDecimal(data.getIncRate());
            helper.setText(R.id.tv_applies, "+" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");

        } else if (Double.parseDouble(data.getIncRate()) < 0) {
            helper.setTextColor(R.id.tv_applies, ContextCompat.getColor(context, R.color.kline_sell_bg));

            BigDecimal incRateBD = new BigDecimal(data.getIncRate());
            helper.setText(R.id.tv_applies, "" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
        } else {
            helper.setTextColor(R.id.tv_applies, ContextCompat.getColor(context, R.color.df_2A2A2A));

            BigDecimal incRateBD = new BigDecimal(data.getIncRate());
            helper.setText(R.id.tv_applies, "" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
        }



    }

}
