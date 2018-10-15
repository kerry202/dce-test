package cn.dagongniu.oax.assets.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.CoinListBean;
import cn.dagongniu.oax.assets.bean.WTSearchBean;

/**
 * 充值提现搜索  适配器
 */
public class WithdTopSearchAdapter extends BaseQuickAdapter<CoinListBean.DataBean, BaseViewHolder> {


    private OnItemClickListener mItemClickListener;

    Context context;

    public WithdTopSearchAdapter(Context context) {
        super(R.layout.with_top_search_item_layout);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinListBean.DataBean dataBean) {
        helper.setText(R.id.tv_name, dataBean.getCoinName());

    }


}
