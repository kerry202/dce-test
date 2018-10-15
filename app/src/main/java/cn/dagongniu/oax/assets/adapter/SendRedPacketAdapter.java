package cn.dagongniu.oax.assets.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.SendRedPacketRecordBean;
import cn.dagongniu.oax.utils.AccountValidatorUtil;

/**
 * 发出红包  适配器
 */
public class SendRedPacketAdapter extends BaseQuickAdapter<SendRedPacketRecordBean.DataBean.PageInfoBean.ListBean, BaseViewHolder> {

    Context context;

    public SendRedPacketAdapter(Context context) {
        super(R.layout.send_receive_redenvelope_item_layout);
        this.context = context;
    }

    @Override
    public void setHeaderAndEmpty(boolean isHeadAndEmpty) {
        super.setHeaderAndEmpty(isHeadAndEmpty);
    }

    @Override
    protected void convert(BaseViewHolder helper, SendRedPacketRecordBean.DataBean.PageInfoBean.ListBean listBean) {
        //type=1 表示普通红包 type=2 表示随机红包红包
        if (listBean.getType() == 2) {
            helper.setText(R.id.tv_red_type, R.string.red_luck);
        } else {
            helper.setText(R.id.tv_red_type, R.string.red_ordinary);
        }

        // 已领取
        int grabPacketQty = listBean.getGrabPacketQty();
        int totalPacketQty = listBean.getTotalPacketQty();

        String ylq = context.getResources().getString(R.string.red_ylq);

        helper.setText(R.id.tv_receive, ylq + grabPacketQty + "/" + totalPacketQty);

        //总额
        BigDecimal totalcoinqty = listBean.getTotalCoinQty().setScale(8, BigDecimal.ROUND_DOWN);
        String totalcoinqtyStr = AccountValidatorUtil.subZeroAndDot(totalcoinqty.toPlainString());

        helper.setText(R.id.tv_market_count, totalcoinqtyStr + listBean.getCoinName());

        //时间
        helper.setText(R.id.tv_data, listBean.getCreateTime());

        //人民币估值
        BigDecimal totalcny = listBean.getTotalCny().setScale(2, BigDecimal.ROUND_DOWN);
        helper.setText(R.id.tv_rmb, "≈¥" + totalcny.toPlainString());

    }
}
