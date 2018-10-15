package cn.dagongniu.oax.trading.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.kline.bean.CommitteeBean;
import cn.dagongniu.oax.trading.bean.MarketCategoryBean;
import cn.dagongniu.oax.trading.bean.SelectMarketInfoBean;

public class SelectMarketAdapter extends BaseQuickAdapter<MarketCategoryBean.DataBean, BaseViewHolder> {


    public SelectMarketAdapter() {
        super(R.layout.adapter_selectmarket);
    }

    @Override
    protected void convert(BaseViewHolder helper, MarketCategoryBean.DataBean item) {

        helper.setText(R.id.tv_coin_name, item.getCategoryName());

    }
}
