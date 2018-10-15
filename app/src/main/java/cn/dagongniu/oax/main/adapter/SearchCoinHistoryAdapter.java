package cn.dagongniu.oax.main.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.main.bean.IndexPageBean;


/**
 * 市场搜索历史适配器
 */
public class SearchCoinHistoryAdapter extends BaseQuickAdapter<IndexPageBean.DataBean.AllMaketListBean.MarketListBean, BaseViewHolder> {


    Context context;

    public SearchCoinHistoryAdapter(Context context) {
        super(R.layout.search_coin_history_item_layout);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexPageBean.DataBean.AllMaketListBean.MarketListBean data) {

        //-------
        //交易对   币种getCoinName/市场getMarketCoinName
        helper.setText(R.id.tv_market_name, data.getCoinName() + "/" + data.getMarketCoinName());




    }

}
