package cn.dagongniu.oax.account.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.CountryCodeBean;
import cn.dagongniu.oax.assets.bean.CoinAddressListBean;

/**
 * 国家列表  适配器
 */
public class CountriesListAdapter extends BaseQuickAdapter<CountryCodeBean.DataBean, BaseViewHolder> {


    Context context;

    public CountriesListAdapter(Context context) {
        super(R.layout.bottom_countries_item_layout);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CountryCodeBean.DataBean data) {
        helper.setText(R.id.tv_countries_name, data.getCnName() + "(" + data.getEnName() + ")");
    }
}
