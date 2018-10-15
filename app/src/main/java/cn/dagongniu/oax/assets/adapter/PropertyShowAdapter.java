package cn.dagongniu.oax.assets.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.util.List;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.PropertyRechargeBean;
import cn.dagongniu.oax.assets.bean.PropertyShowBean;
import cn.dagongniu.oax.assets.presenter.PropertyShowPresenter;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.utils.DateUtils;

/**
 * 充值显示详情  适配器
 */
public class PropertyShowAdapter extends BaseQuickAdapter<PropertyShowBean.DataBean.TradeListBean, BaseViewHolder> {

    Context context;
    List<PropertyShowBean.DataBean.TradeListBean> lineData;//第一次

    public void setLineData(List<PropertyShowBean.DataBean.TradeListBean> lineData) {
        this.lineData = lineData;
    }

    public PropertyShowAdapter(Context context) {
        super(R.layout.property_show_item_layout);
        this.context = context;
    }

    List<PropertyShowBean.DataBean.TradeListBean> lineDataRefresh = null;//刷新的值

    //websocket过来的数据  刷新
    public void setWebSocketMainMarket(List<PropertyShowBean.DataBean.TradeListBean> lineDataRefresh) {
        this.lineDataRefresh = lineDataRefresh;

    }

    @Override
    protected void convert(BaseViewHolder helper, PropertyShowBean.DataBean.TradeListBean data1) {
        helper.setText(R.id.tv_name, lineData.get(helper.getLayoutPosition()).getName());

        /**
         * 第一次进来
         */
        if (lineDataRefresh == null) {
            if (Double.parseDouble(lineData.get(helper.getLayoutPosition()).getRate()) > 0) {
                helper.setTextColor(R.id.tv_newPrice, ContextCompat.getColor(context, R.color.kline_buy_bg));
            } else if (Double.parseDouble(lineData.get(helper.getLayoutPosition()).getRate()) < 0) {
                helper.setTextColor(R.id.tv_newPrice, ContextCompat.getColor(context, R.color.kline_sell_bg));
            } else {
                helper.setTextColor(R.id.tv_newPrice, ContextCompat.getColor(context, R.color.df_gray_666));
            }

            if (Double.parseDouble(lineData.get(helper.getLayoutPosition()).getRate()) > 0) {
                helper.setTextColor(R.id.tv_rate, ContextCompat.getColor(context, R.color.kline_buy_bg));
                BigDecimal incRateBD = new BigDecimal(lineData.get(helper.getLayoutPosition()).getRate());
                helper.setText(R.id.tv_rate, "+" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");

            } else if (Double.parseDouble(lineData.get(helper.getLayoutPosition()).getRate()) < 0) {
                helper.setTextColor(R.id.tv_rate, ContextCompat.getColor(context, R.color.kline_sell_bg));
                BigDecimal incRateBD = new BigDecimal(lineData.get(helper.getLayoutPosition()).getRate());
                helper.setText(R.id.tv_rate, "" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
            } else {
                helper.setTextColor(R.id.tv_rate, ContextCompat.getColor(context, R.color.df_gray_666));
                BigDecimal incRateBD = new BigDecimal(lineData.get(helper.getLayoutPosition()).getRate());
                helper.setText(R.id.tv_rate, "" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
            }

            helper.setText(R.id.tv_newPrice, "" + lineData.get(helper.getLayoutPosition()).getNewPrice().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());

            //-------
            //人民币换算
            BigDecimal cny = lineData.get(helper.getLayoutPosition()).getCnyPrice()
                    .setScale(2, BigDecimal.ROUND_DOWN);


            helper.setText(R.id.tv_cny_price, "≈¥" + cny.toPlainString() + "");
        } else {
            /**
             * web 推送过来
             */
            BigDecimal bigDecimaNew = lineDataRefresh.get(helper.getLayoutPosition()).getNewPrice();
            BigDecimal bigDecimaLast = lineData.get(helper.getLayoutPosition()).getNewPrice();

            switch (bigDecimaLast.compareTo(bigDecimaNew)) {
                case 1://大于 时，返回 1
                    helper.setTextColor(R.id.tv_newPrice, ContextCompat.getColor(mContext, R.color.kline_sell_bg));
                    break;
                case 0://等于 时，返回 0
                    helper.setTextColor(R.id.tv_newPrice, ContextCompat.getColor(mContext, R.color.df_font));
                    break;
                case -1://小于 时，返回 -1
                    helper.setTextColor(R.id.tv_newPrice, ContextCompat.getColor(mContext, R.color.kline_buy_bg));
                    break;
            }

            if (Double.parseDouble(lineDataRefresh.get(helper.getLayoutPosition()).getRate()) > 0) {
                helper.setTextColor(R.id.tv_rate, ContextCompat.getColor(context, R.color.kline_buy_bg));

                BigDecimal incRateBD = new BigDecimal(lineDataRefresh.get(helper.getLayoutPosition()).getRate());
                helper.setText(R.id.tv_rate, "+" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");

            } else if (Double.parseDouble(lineDataRefresh.get(helper.getLayoutPosition()).getRate()) < 0) {
                helper.setTextColor(R.id.tv_rate, ContextCompat.getColor(context, R.color.kline_sell_bg));

                BigDecimal incRateBD = new BigDecimal(lineDataRefresh.get(helper.getLayoutPosition()).getRate());
                helper.setText(R.id.tv_rate, "" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
            } else {
                helper.setTextColor(R.id.tv_rate, ContextCompat.getColor(context, R.color.df_gray_666));

                BigDecimal incRateBD = new BigDecimal(lineDataRefresh.get(helper.getLayoutPosition()).getRate());
                helper.setText(R.id.tv_rate, "" + incRateBD.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
            }


            helper.setText(R.id.tv_newPrice, "" + lineDataRefresh.get(helper.getLayoutPosition()).getNewPrice().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());

            //人民币换算
            BigDecimal cny = lineDataRefresh.get(helper.getLayoutPosition()).getCnyPrice()
                    .setScale(2, BigDecimal.ROUND_DOWN);
            helper.setText(R.id.tv_cny_price, "≈¥" + cny.toPlainString() + "");


            if (helper.getLayoutPosition() == lineDataRefresh.size() - 1) {
                lineData.clear();
                lineData.addAll(lineDataRefresh);
            }
        }
    }

}
