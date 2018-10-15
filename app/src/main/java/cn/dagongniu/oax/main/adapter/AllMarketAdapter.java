package cn.dagongniu.oax.main.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;
import com.zhy.autolayout.AutoRelativeLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.kline.bean.CommitteeBean;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.utils.Logs;


/**
 * 所有市场适配器
 */
public class AllMarketAdapter extends BaseQuickAdapter<IndexPageBean.DataBean.AllMaketListBean.MarketListBean, BaseViewHolder> {

    List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> marketListBeans;

    Context context;

    public AllMarketAdapter(Context context, List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> marketListBeans) {
        super(R.layout.btc_eth_market_item_layout);
        this.context = context;
        this.marketListBeans = marketListBeans;
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexPageBean.DataBean.AllMaketListBean.MarketListBean data) {
        helper.addOnClickListener(R.id.iv_collection);
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

            AutoRelativeLayout view = helper.getView(R.id.bt_applies);
            view.setBackground(ContextCompat.getDrawable(context, R.drawable.home_incrate_green_bg));

            BigDecimal incRateBD = new BigDecimal(data.getIncRate());
            helper.setText(R.id.tv_incRate, "+" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
            helper.setTextColor(R.id.tv_incRate, context.getResources().getColor(R.color.white));
        } else if (Double.parseDouble(data.getIncRate()) < 0) {

            AutoRelativeLayout view = helper.getView(R.id.bt_applies);
            view.setBackground(ContextCompat.getDrawable(context, R.drawable.home_incrate_red_bg));

            BigDecimal incRateBD = new BigDecimal(data.getIncRate());
            helper.setText(R.id.tv_incRate, "" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
            helper.setTextColor(R.id.tv_incRate, context.getResources().getColor(R.color.white));
        } else {
            AutoRelativeLayout view = helper.getView(R.id.bt_applies);
            view.setBackground(ContextCompat.getDrawable(context, R.drawable.home_incrate_gray_bg));

            BigDecimal incRateBD = new BigDecimal(data.getIncRate());
            helper.setText(R.id.tv_incRate, "" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
            helper.setTextColor(R.id.tv_incRate, context.getResources().getColor(R.color.df_999999));
        }

        //-------
        //人民币换算
        BigDecimal cny = data.getCnyPrice().multiply(new BigDecimal(data.getLastTradePrice()))
                .setScale(2, BigDecimal.ROUND_DOWN);
        helper.setText(R.id.tv_cny_price, "≈¥" + cny.toPlainString() + "");

        //-------
        //额
        BigDecimal bigDecimalQty = data.getTotalAmount();
        BigDecimal tradePriceQty = bigDecimalQty.setScale(2, BigDecimal.ROUND_DOWN);
        helper.setText(R.id.tv_market_qty, tradePriceQty.toPlainString());

        //设置标星
        ImageView collection = helper.getView(R.id.iv_collection);
        int marketId = data.getMarketId();
        //获取自选列表 然后设置星点

        boolean b = OAXApplication.collectCoinsMap.containsKey(marketId);

        Logs.s("      设置星星  boolean :     "+b);
        Logs.s("      设置星星  marketId :     "+marketId);
        if (OAXApplication.collectCoinsMap.containsKey(marketId)) {
            collection.setSelected(true);
            data.setIsCollection(1);
        } else {
            collection.setSelected(false);
        }


//        AutoRelativeLayout mian = helper.getView(R.id.mian_item);
//        //判断最后一个
//        if (this.getData().size() - 1 == helper.getLayoutPosition()) {
//            mian.setPadding(0, 0, 0, 30);
//        }else{
//            mian.setPadding(0, 0, 0, 0);
//        }

    }

}
