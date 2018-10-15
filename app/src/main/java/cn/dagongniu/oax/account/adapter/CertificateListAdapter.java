package cn.dagongniu.oax.account.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.CountryCodeBean;

/**
 * 证件类型  适配器
 */
public class CertificateListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    Context context;

    public CertificateListAdapter(Context context) {
        super(R.layout.bottom_countries_item_layout);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String data) {
        helper.setText(R.id.tv_countries_name, data);
    }
}
