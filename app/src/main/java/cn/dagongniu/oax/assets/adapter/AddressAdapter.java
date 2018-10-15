package cn.dagongniu.oax.assets.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.CoinAddressListBean;
import cn.dagongniu.oax.assets.bean.WTSearchBean;
import cn.dagongniu.oax.kline.bean.CommitteeBean;

/**
 * 添加地址  适配器
 */
public class AddressAdapter extends BaseQuickAdapter<CoinAddressListBean.DataBean.ListBean, BaseViewHolder> {


    Context context;

    public AddressAdapter(Context context) {
        super(R.layout.bottom_address_item_layout);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinAddressListBean.DataBean.ListBean data) {
        helper.setText(R.id.tv_address, data.getRemark() + "   " +data.getAddress());
    }
}
