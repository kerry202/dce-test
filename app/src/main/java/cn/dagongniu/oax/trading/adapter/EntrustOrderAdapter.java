package cn.dagongniu.oax.trading.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.language.MultiLanguageUtil;
import cn.dagongniu.oax.trading.bean.CurrentEntrustBean;
import cn.dagongniu.oax.trading.bean.EntrustInfoBean;

/**
 * 委托 fragment适配器
 */
public class EntrustOrderAdapter extends BaseQuickAdapter<CurrentEntrustBean.ListBean, BaseViewHolder> {

    public static int TYPE_ENTRUST = 1; // 委托
    public static int TYPE_ORDER = 2; // 订单
    private int currentType;
    Context context;

    public EntrustOrderAdapter(int type, Context context) {
        super(R.layout.entrust_order_item_layout);
        currentType = type;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CurrentEntrustBean.ListBean item) {
        helper.addOnClickListener(R.id.bt_item_cz);
        if (currentType == TYPE_ENTRUST) {
            helper.setVisible(R.id.tv_item_cz, false);
        } else if (currentType == TYPE_ORDER) {
            helper.setVisible(R.id.bt_item_cz, false);
        }
        helper.setText(R.id.tv_item_time, item.getCreateTime());
        helper.setText(R.id.tv_ent_jyd, item.getMarketName());
        if (item.getType() == 1) {
            helper.setTextColor(R.id.tv_ent_jg, context.getResources().getColor(R.color.kline_buy_bg));
        } else {
            helper.setTextColor(R.id.tv_ent_jg, context.getResources().getColor(R.color.kline_sell_bg));
        }
//        BigDecimal price = new BigDecimal();
        helper.setText(R.id.tv_ent_jg, item.getPrice().setScale(item.getPriceDecimal(), BigDecimal.ROUND_DOWN).toPlainString());

//        BigDecimal qty = new BigDecimal();
        helper.setText(R.id.tv_ent_count, item.getQty().setScale(item.getQtyDecimal(), BigDecimal.ROUND_DOWN).toPlainString());

        if (MultiLanguageUtil.getInstance().getLanguageStringType().equals("en")) {
            helper.setImageDrawable(R.id.im_liang, context.getResources().getDrawable(R.drawable.liang_log_en));
        } else {
            helper.setImageDrawable(R.id.im_liang, context.getResources().getDrawable(R.drawable.liang_log));
        }

    }

}
