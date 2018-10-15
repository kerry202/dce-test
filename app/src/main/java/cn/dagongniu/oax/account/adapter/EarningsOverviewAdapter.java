package cn.dagongniu.oax.account.adapter;

import android.content.Context;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;
import com.socks.library.KLog;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.EarningsOverviewItemBean;
import cn.dagongniu.oax.utils.DensityUtils;

public class EarningsOverviewAdapter extends BaseQuickAdapter<EarningsOverviewItemBean, BaseViewHolder> {
    Context context;
    private boolean isFromReflesh;

    public EarningsOverviewAdapter(Context context) {
        super(R.layout.adapter_earnings_overview);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, EarningsOverviewItemBean item) {
        try {
            if (!isFromReflesh) {
                new CrazyShadow.Builder()
                        .setContext(context)
                        .setDirection(CrazyShadowDirection.ALL)
                        .setShadowRadius(DensityUtils.px2dp(10))
                        .setCorner(DensityUtils.px2dp(3))
                        .setBaseShadowColor(Color.parseColor("#F8BE13"))
                        .setImpl(CrazyShadow.IMPL_DRAW)
                        .action(helper.getView(R.id.item_container));
            }

            helper.setText(R.id.tv_des, item.getDes());
            helper.setText(R.id.tv_count, item.getCount().toPlainString());
        } catch (Exception e) {
            KLog.d("EarningsOverviewAdapter Exception = " + e.getMessage());
        }


    }

    public void isFromReflesh(boolean isFromReflesh) {
        this.isFromReflesh = isFromReflesh;
    }
}
