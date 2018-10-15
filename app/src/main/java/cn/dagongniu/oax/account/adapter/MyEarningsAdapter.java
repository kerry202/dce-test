package cn.dagongniu.oax.account.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;
import com.socks.library.KLog;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.MyEarningsItemBean;
import cn.dagongniu.oax.utils.DensityUtils;

public class MyEarningsAdapter extends BaseQuickAdapter<MyEarningsItemBean, BaseViewHolder> {
    Context context;
    private boolean isFromReflesh;

    public MyEarningsAdapter(Context context) {
        super(R.layout.adapter_myearnings);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyEarningsItemBean item) {
        try {
            //标题
            helper.setText(R.id.tv_des, item.getTitle());
            //收益
            RecyclerView recyclerview = helper.getView(R.id.recyclerview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                }
            };
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerview.setLayoutManager(layoutManager);
            recyclerview.setNestedScrollingEnabled(false);
            MyEarningsInnerAdapter adapter = new MyEarningsInnerAdapter();
            recyclerview.setAdapter(adapter);
            adapter.setNewData(item.getCoins());
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
        } catch (Exception e) {
            KLog.d("MyEarningsAdapter Exception = " + e.getMessage());
        }


    }

    public void isFromReflesh(boolean isFromReflesh) {
        this.isFromReflesh = isFromReflesh;
    }
}
