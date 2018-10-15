package cn.dagongniu.oax.assets.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.PropertyRechargeBean;
import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.oax.utils.DateUtils;

/**
 * 提现记录  适配器
 */
public class WithdrawalRecordAdapter extends BaseQuickAdapter<PropertyWithdrawBean.DataBean.ListBean, BaseViewHolder> {

    Context context;

    public WithdrawalRecordAdapter(Context context) {
        super(R.layout.top_with_record_item_layout);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, PropertyWithdrawBean.DataBean.ListBean item) {

        //	状态 -3:转出失败-2:终审不通过 -1：初审不通过 0：待初审 1:待终审 2：已转出(已产生交易hash) 3：已广播（离开txpool） 4：已确认

        helper.setText(R.id.tv_item_time, DateUtils.getStringTime(DateUtils.StrToDate(item.getCreateTime())));

        helper.setText(R.id.tv_market_name, item.getShortName());
        helper.setText(R.id.tv_ent_count, item.getQty().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());

        String stateStr = "";
        switch (item.getStatus()) {
            //审核中
            case 0:
            case 1:
                stateStr = context.getResources().getString(R.string.withdrawal_reviewing);
                break;
            //失败
            case -3:
            case -1:
            case -2:
                stateStr = context.getResources().getString(R.string.withdrawal_failed);
                break;
                //成功
            case 2:
            case 3:
            case 4:
                stateStr = context.getResources().getString(R.string.success);
                break;

        }


        helper.setText(R.id.tv_item_success, stateStr);

    }

}
