package cn.dagongniu.oax.assets.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.RedPacketIndexBean;
import cn.dagongniu.oax.trading.bean.MarketCategoryBean;

public class SelectRedPacketCoinAdapter extends BaseQuickAdapter<RedPacketIndexBean.DataBean.CoinListBean, BaseViewHolder> {


    public SelectRedPacketCoinAdapter() {
        super(R.layout.adapter_selectmarket);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedPacketIndexBean.DataBean.CoinListBean data) {

        helper.setText(R.id.tv_coin_name, data.getCoinName());

    }
}
