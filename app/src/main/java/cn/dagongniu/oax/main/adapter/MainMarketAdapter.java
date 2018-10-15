package cn.dagongniu.oax.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;

import java.math.BigDecimal;
import java.util.List;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.bean.MainMarketInfoBean;
import cn.dagongniu.oax.utils.ListDataSave;
import cn.dagongniu.oax.utils.Logger;

/**
 * 推荐市场- 第一页初始化值 适配器
 */
public class MainMarketAdapter extends BaseQuickAdapter<IndexPageBean.DataBean.RecommendMarketListBean, BaseViewHolder> {

    private Context context;
    List<IndexPageBean.DataBean.RecommendMarketListBean> lineData;//第一次

    public MainMarketAdapter(Context context, List<IndexPageBean.DataBean.RecommendMarketListBean> lineData) {
        super(R.layout.adapter_mainmarket);
        this.context = context;
        this.lineData = lineData;
    }

    List<IndexPageBean.DataBean.RecommendMarketListBean> lineDataRefresh = null;//刷新的值


    //websocket过来的数据  刷新
    public void setWebSocketMainMarket(List<IndexPageBean.DataBean.RecommendMarketListBean> lineDataRefresh) {
        this.lineDataRefresh = lineDataRefresh;
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexPageBean.DataBean.RecommendMarketListBean data1) {
        if (lineDataRefresh == null) {
            //第一次
            if (Double.parseDouble(lineData.get(helper.getLayoutPosition()).getMarketCoin().getIncRate()) > 0) {
                helper.setTextColor(R.id.tv_left_coin_price, ContextCompat.getColor(context, R.color.kline_buy_bg));
            } else if (Double.parseDouble(lineData.get(helper.getLayoutPosition()).getMarketCoin().getIncRate()) < 0) {
                helper.setTextColor(R.id.tv_left_coin_price, ContextCompat.getColor(context, R.color.kline_sell_bg));
            } else {
                helper.setTextColor(R.id.tv_left_coin_price, ContextCompat.getColor(context, R.color.df_333333));
            }

            BigDecimal bigDecimal = new BigDecimal(lineData.get(helper.getLayoutPosition()).getMarketCoin().getLastTradePrice());
            //币种单价
            BigDecimal tradePrice = bigDecimal.setScale(lineData.get(helper.getLayoutPosition()).getMarketCoin().getPriceDecimals(), BigDecimal.ROUND_DOWN);

            //交易对   币种getCoinName/市场getMarketCoinName
            helper.setText(R.id.tv_market_name, lineData.get(helper.getLayoutPosition()).getMarketCoin().getCoinName() + "/" + lineData.get(helper.getLayoutPosition()).getMarketCoin().getMarketCoinName());

            //币种当前价格 保留getPriceDecimals位小数
            helper.setText(R.id.tv_left_coin_price, tradePrice.toPlainString());

            //涨跌幅 百分比
            if (Double.parseDouble(lineData.get(helper.getLayoutPosition()).getMarketCoin().getIncRate()) > 0) {
                helper.setTextColor(R.id.tv_applies, ContextCompat.getColor(context, R.color.kline_buy_bg));

                BigDecimal incRateBD = new BigDecimal(lineData.get(helper.getLayoutPosition()).getMarketCoin().getIncRate());

                helper.setText(R.id.tv_applies, "+" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
            } else if (Double.parseDouble(lineData.get(helper.getLayoutPosition()).getMarketCoin().getIncRate()) < 0) {
                helper.setTextColor(R.id.tv_applies, ContextCompat.getColor(context, R.color.kline_sell_bg));

                BigDecimal incRateBD = new BigDecimal(lineData.get(helper.getLayoutPosition()).getMarketCoin().getIncRate());

                helper.setText(R.id.tv_applies, "" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
            } else {
                helper.setTextColor(R.id.tv_applies, ContextCompat.getColor(context, R.color.df_font));

                BigDecimal incRateBD = new BigDecimal(lineData.get(helper.getLayoutPosition()).getMarketCoin().getIncRate());

                helper.setText(R.id.tv_applies, "" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
                helper.setTextColor(R.id.tv_applies, ContextCompat.getColor(context, R.color.df_333333));
            }

            //人民币换算
            BigDecimal cny = lineData.get(helper.getLayoutPosition()).getMarketCoin().getCnyPrice().multiply(new BigDecimal(lineData.get(helper.getLayoutPosition()).getMarketCoin().getLastTradePrice()))
                    .setScale(2, BigDecimal.ROUND_DOWN);
            helper.setText(R.id.tv_cny_price, "≈¥" + cny + "");

        } else {
            BigDecimal bigDecimaNew = new BigDecimal(lineDataRefresh.get(helper.getLayoutPosition()).getMarketCoin().getLastTradePrice());
            BigDecimal bigDecimaLast = new BigDecimal(lineData.get(helper.getLayoutPosition()).getMarketCoin().getLastTradePrice());

            switch (bigDecimaLast.compareTo(bigDecimaNew)) {
                case 1://大于 时，返回 1
                    helper.setTextColor(R.id.tv_left_coin_price, ContextCompat.getColor(mContext, R.color.kline_sell_bg));
                    break;
                case 0://等于 时，返回 0
                    helper.setTextColor(R.id.tv_left_coin_price, ContextCompat.getColor(mContext, R.color.df_333333));
                    break;
                case -1://小于 时，返回 -1
                    helper.setTextColor(R.id.tv_left_coin_price, ContextCompat.getColor(mContext, R.color.kline_buy_bg));
                    break;
            }


            BigDecimal bigDecimal = new BigDecimal(lineDataRefresh.get(helper.getLayoutPosition()).getMarketCoin().getLastTradePrice());
            //币种单价
            BigDecimal tradePrice = bigDecimal.setScale(lineDataRefresh.get(helper.getLayoutPosition()).getMarketCoin().getPriceDecimals(), BigDecimal.ROUND_DOWN);

            //交易对   币种getCoinName/市场getMarketCoinName
            helper.setText(R.id.tv_market_name, lineDataRefresh.get(helper.getLayoutPosition()).getMarketCoin().getCoinName() + "/" + lineDataRefresh.get(helper.getLayoutPosition()).getMarketCoin().getMarketCoinName());

            //币种当前价格 保留getPriceDecimals位小数
            helper.setText(R.id.tv_left_coin_price, tradePrice.toPlainString());

            //涨跌幅 百分比
            if (Double.parseDouble(lineDataRefresh.get(helper.getLayoutPosition()).getMarketCoin().getIncRate()) > 0) {
                helper.setTextColor(R.id.tv_applies, ContextCompat.getColor(context, R.color.kline_buy_bg));

                BigDecimal incRateBD = new BigDecimal(lineDataRefresh.get(helper.getLayoutPosition()).getMarketCoin().getIncRate());

                helper.setText(R.id.tv_applies, "+" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");

            } else if (Double.parseDouble(lineDataRefresh.get(helper.getLayoutPosition()).getMarketCoin().getIncRate()) < 0) {
                helper.setTextColor(R.id.tv_applies, ContextCompat.getColor(context, R.color.kline_sell_bg));

                BigDecimal incRateBD = new BigDecimal(lineDataRefresh.get(helper.getLayoutPosition()).getMarketCoin().getIncRate());

                helper.setText(R.id.tv_applies, "" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");

            } else {
                helper.setTextColor(R.id.tv_applies, ContextCompat.getColor(context, R.color.df_font));

                BigDecimal incRateBD = new BigDecimal(lineDataRefresh.get(helper.getLayoutPosition()).getMarketCoin().getIncRate());

                helper.setText(R.id.tv_applies, "" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
                helper.setTextColor(R.id.tv_applies, ContextCompat.getColor(context, R.color.df_333333));
            }

            //人民币换算
            BigDecimal cny = lineDataRefresh.get(helper.getLayoutPosition()).getMarketCoin().getCnyPrice().multiply(new BigDecimal(lineDataRefresh.get(helper.getLayoutPosition()).getMarketCoin().getLastTradePrice()))
                    .setScale(2, BigDecimal.ROUND_DOWN);
            helper.setText(R.id.tv_cny_price, "≈¥" + cny + "");

            if (helper.getLayoutPosition() == lineDataRefresh.size() - 1) {
                lineData.clear();
                lineData.addAll(lineDataRefresh);
            }
        }

    }
}
