package cn.dagongniu.oax.trading.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.trading.adapter.TradingMarketBuyAdapter;
import cn.dagongniu.oax.trading.bean.TransactionIndexBean;


/**
 * 交易页面 买
 */
public class TradingBuyView extends FrameLayout {

    private static final String TAG = "TradingBuyView";

    Context context;
    @BindView(R.id.fall_recyclerView)
    RecyclerView fallRecyclerView;

    TradingMarketBuyAdapter tradingMarketBuyAdapter;

    public TradingBuyView(Context context) {
        super(context);
        this.context = context;
    }

    public TradingBuyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public TradingBuyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }


    /**
     * 交易首页设置过来的数据
     *
     * @param marketOrdersBuyList
     */
    public void setMarketOrdersBuyList(TransactionIndexBean.DataBean.MarketOrdersMapBean marketOrdersBuyList) {
        fallRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tradingMarketBuyAdapter = new TradingMarketBuyAdapter(getContext());
        tradingMarketBuyAdapter.setNewData(marketOrdersBuyList.getBuyList());
        fallRecyclerView.setAdapter(tradingMarketBuyAdapter);
    }

}
