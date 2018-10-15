package cn.dagongniu.oax.assets.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.GrabRedPacketRecordBean;
import cn.dagongniu.oax.assets.bean.TakeRedPacketDetailsBean;
import cn.dagongniu.oax.utils.AccountValidatorUtil;

/**
 * 发出红包收到红包  适配器
 */
public class TakeRedPacketDetailsAdapter extends BaseQuickAdapter<TakeRedPacketDetailsBean.DataBean.RedPacketLogDetailsPageInfoBean.ListBean, BaseViewHolder> {


    Context context;
    String marketName;

    public TakeRedPacketDetailsAdapter(Context context) {
        super(R.layout.send_receive_redenvelope_item_layout);
        this.context = context;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    @Override
    protected void convert(BaseViewHolder helper, TakeRedPacketDetailsBean.DataBean.RedPacketLogDetailsPageInfoBean.ListBean listBean) {

        helper.getView(R.id.rl_send).setVisibility(View.GONE);
        helper.getView(R.id.rl_receive).setVisibility(View.VISIBLE);
        String accountNumber = listBean.getAccountNumber();
        String accountNameTv = "";
        if (TextUtils.isEmpty(accountNumber)) {
            accountNameTv = accountNumber;
        } else if (AccountValidatorUtil.isEmail(accountNumber)) {
            int indexStart = accountNumber.indexOf("@");
            String toStr = accountNumber.substring(0, indexStart);
            if (toStr.length() > 4) {
                accountNameTv = "****" + accountNumber.substring(indexStart - 4, accountNumber.length());
            } else {
                accountNameTv = accountNumber;
            }
        } else if (listBean.getAccountNumber().length() == 11) {
            accountNameTv = accountNumber.substring(0, 3) + "****" + accountNumber.substring(accountNumber.length() - 4);
        } else {
            accountNameTv = accountNumber;
        }

        //领取账户
        helper.setText(R.id.tv_phone, accountNameTv);

        //总额
        BigDecimal totalcoinqty = listBean.getCoinQty().setScale(8, BigDecimal.ROUND_DOWN);
        String totalcoinqtyStr = AccountValidatorUtil.subZeroAndDot(totalcoinqty.toPlainString());

        helper.setText(R.id.tv_market_count, totalcoinqtyStr + marketName);

        //时间
        helper.setText(R.id.tv_data, listBean.getCreateTime());

        //人民币估值
        BigDecimal totalcny = listBean.getCny().setScale(2, BigDecimal.ROUND_DOWN);
        helper.setText(R.id.tv_rmb, "≈¥" + totalcny.toPlainString());

    }
}
