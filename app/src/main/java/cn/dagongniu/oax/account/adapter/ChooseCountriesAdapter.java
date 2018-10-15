package cn.dagongniu.oax.account.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.CountryCodeBean;


/**
 * 国际化号码区号 适配器
 */
public class ChooseCountriesAdapter extends BaseQuickAdapter<CountryCodeBean.DataBean, BaseViewHolder> {


    Context context;

    public ChooseCountriesAdapter(Context context) {
        super(R.layout.choose_countres_item_layout);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CountryCodeBean.DataBean dataBeanList) {
        helper.setText(R.id.tv_name, dataBeanList.getCnName() + "(" + dataBeanList.getEnName() + ")");
        helper.setText(R.id.tv_number_type, "+" + dataBeanList.getCode());
    }
}
