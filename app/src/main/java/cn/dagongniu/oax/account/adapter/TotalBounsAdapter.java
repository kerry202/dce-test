package cn.dagongniu.oax.account.adapter;

import android.content.Context;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;
import com.socks.library.KLog;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.TotalBounsItemBean;
import cn.dagongniu.oax.utils.DensityUtils;

public class TotalBounsAdapter extends BaseQuickAdapter<TotalBounsItemBean, BaseViewHolder> {
    Context context;
    private boolean isFromReflesh;

    public TotalBounsAdapter(Context context) {
        super(R.layout.adapter_total_bouns);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TotalBounsItemBean item) {
        try {
            //边框
            if (!isFromReflesh) {
                int earnings_shadow = context.getResources().getColor(R.color.earnings_shadow);
                new CrazyShadow.Builder()
                        .setContext(context)
                        .setDirection(CrazyShadowDirection.ALL)
                        .setShadowRadius(DensityUtils.px2dp(context.getResources().getDimension(R.dimen.earnings_shadow)))
//                        .setCorner(DensityUtils.px2dp(3))
                        .setBaseShadowColor(earnings_shadow)
                        .setImpl(CrazyShadow.IMPL_DRAW)
                        .action(helper.getView(R.id.item_container));
            }
            //des
            helper.setText(R.id.tv_des,item.getCoinName());

            helper.setText(R.id.tv_coin_name_1,item.getCoinName());
            helper.setText(R.id.tv_coin_name_2,item.getCoinName());
            helper.setText(R.id.tv_coin_name_3,item.getCoinName());
            helper.setText(R.id.tv_coin_name_4,item.getCoinName());

        } catch (Exception e) {
            KLog.d("TotalBounsAdapter Exception = " + e.getMessage());
        }

    }

    public void isFromReflesh(boolean isFromReflesh) {
        this.isFromReflesh = isFromReflesh;
    }
}
