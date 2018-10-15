package cn.dagongniu.oax.assets.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.PropertyRechargeBean;
import cn.dagongniu.oax.kline.bean.CommitteeBean;
import cn.dagongniu.oax.utils.DateUtils;

/**
 * 充值记录  适配器
 */
public class TopUpRecordAdapter extends BaseQuickAdapter<PropertyRechargeBean.DataBean.ListBean, BaseViewHolder> {

    Context context;

    public TopUpRecordAdapter(Context context) {
        super(R.layout.top_with_record_item_layout);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, PropertyRechargeBean.DataBean.ListBean item) {

        helper.setText(R.id.tv_item_time, DateUtils.getStringTime(DateUtils.StrToDate(item.getCreateTime())));
        helper.setText(R.id.tv_market_name, item.getShortName());
        helper.setText(R.id.tv_ent_count, item.getQty().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
        helper.setText(R.id.tv_item_success, context.getString(R.string.success));

    }

}
