package cn.dagongniu.oax.assets.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.AssetsPropertyListBean;
import cn.dagongniu.oax.kline.bean.CommitteeBean;


/**
 * 资产首页 我的资产 适配器
 */
public class MyAssetsAdapter extends BaseQuickAdapter<AssetsPropertyListBean.DataBean.CoinListBean, BaseViewHolder> {

    Context context;
    boolean isOpenEyes = true;

    public MyAssetsAdapter(Context context, boolean isOpenEyes) {
        super(R.layout.my_assets_item_layout);
        this.context = context;
        this.isOpenEyes = isOpenEyes;
    }

    public void setOpenEyes(boolean openEyes) {
        isOpenEyes = openEyes;
    }

    @Override
    protected void convert(BaseViewHolder helper, AssetsPropertyListBean.DataBean.CoinListBean item) {
        helper.setText(R.id.tv_market_name, item.getShortName());

        if (isOpenEyes) {
            BigDecimal banlance = new BigDecimal(item.getBanlance());
            helper.setText(R.id.tv_banlance, subZeroAndDot(banlance.setScale(8, BigDecimal.ROUND_DOWN).toPlainString()));

            BigDecimal FreezingBanlance = new BigDecimal(item.getFreezingBanlance());
            helper.setText(R.id.tv_freezing, subZeroAndDot(FreezingBanlance.setScale(8, BigDecimal.ROUND_DOWN).toPlainString()));

            BigDecimal TotalBanlance = new BigDecimal(item.getTotalBanlance());
            helper.setText(R.id.tv_total, subZeroAndDot(TotalBanlance.setScale(8, BigDecimal.ROUND_DOWN).toPlainString()));
        } else {
            BigDecimal banlance = new BigDecimal(item.getBanlance());
            helper.setText(R.id.tv_banlance, "******");

            BigDecimal FreezingBanlance = new BigDecimal(item.getFreezingBanlance());
            helper.setText(R.id.tv_freezing, "******");

            BigDecimal TotalBanlance = new BigDecimal(item.getTotalBanlance());
            helper.setText(R.id.tv_total, "******");
        }

        if (helper.getLayoutPosition() == this.getData().size() - 1) {
            helper.setVisible(R.id.view_line, false);
        }

    }

    public String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

}
