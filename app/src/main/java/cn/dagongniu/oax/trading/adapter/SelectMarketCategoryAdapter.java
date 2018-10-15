package cn.dagongniu.oax.trading.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.trading.bean.MarketCategoryBean;

public class SelectMarketCategoryAdapter extends BaseQuickAdapter<MarketCategoryBean.DataBean.MarketCategoryListBean, BaseViewHolder> {


    public SelectMarketCategoryAdapter() {
        super(R.layout.adapter_selectmarket);
    }

    @Override
    protected void convert(BaseViewHolder helper, MarketCategoryBean.DataBean.MarketCategoryListBean item) {

        String substring = item.getMarketMame().substring(0, item.getMarketMame().lastIndexOf("/"));

        helper.setText(R.id.tv_coin_name, substring);


    }
}
