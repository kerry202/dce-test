package cn.dagongniu.oax.account.adapter;

import android.content.Context;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;
import com.socks.library.KLog;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.TotalFeedbackItemBean;
import cn.dagongniu.oax.utils.DensityUtils;

public class TotalFeedbackAdapter extends BaseQuickAdapter<TotalFeedbackItemBean, BaseViewHolder> {
    Context context;
    private boolean isFromReflesh;

    public TotalFeedbackAdapter(Context context) {
        super(R.layout.adapter_total_feedback);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TotalFeedbackItemBean item) {
        try {
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
            helper.setText(R.id.tv_des, item.getDes());
            //x
            helper.setText(R.id.tv_x_count, item.getFeedBackX().toPlainString());
            //eth
            helper.setText(R.id.tv_eth_count, "≈" + item.getFeedBackETH().toPlainString() + "  ETH");
        } catch (Exception e) {
            KLog.d("TotalFeedbackAdapter Exception = " + e.getMessage());
        }

    }

    public void isFromReflesh(boolean isFromReflesh) {
        this.isFromReflesh = isFromReflesh;
    }
}
