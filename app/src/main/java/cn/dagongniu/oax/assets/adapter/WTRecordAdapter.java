package cn.dagongniu.oax.assets.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.kline.bean.CommitteeBean;

/**
 * 充值 提现  适配器
 */
public class WTRecordAdapter extends BaseQuickAdapter<CommitteeBean, BaseViewHolder> {


    public WTRecordAdapter() {
        super(R.layout.top_with_record_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommitteeBean item) {
    }

}
