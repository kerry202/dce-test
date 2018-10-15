package cn.dagongniu.oax.kline.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.OAXBaseFragment;
import cn.dagongniu.oax.https.UrlParams;
import cn.dagongniu.oax.kline.adapter.TransactionAdapter;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.trading.bean.TradeListAndMarketOrdersBean;
import cn.dagongniu.oax.utils.events.TradeListAndMarketOrdersEvent;
import cn.dagongniu.oax.utils.events.TransactionListEvent;


/**
 * 实时成交
 */
public class TransactionFragment extends OAXBaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private TransactionAdapter mTransactionAdapter;
    //    private TransactionPresenter mTransactionPrecenter;
    private int marketId = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transaction;
    }


    @Override
    protected void initView() {
        super.initView();
        Bundle bundle = getArguments();
        if (bundle.containsKey(UrlParams.marketId)) {
            marketId = bundle.getInt(UrlParams.marketId);
        }
//        mTransactionPrecenter = new TransactionPresenter(this);
        mContext = getContext();
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerview.setNestedScrollingEnabled(false);
        mTransactionAdapter = new TransactionAdapter(mContext);
        recyclerview.setAdapter(mTransactionAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
//        mTransactionPrecenter.registerWebSocket(marketId + "");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TransactionListEvent event) {
        if (event != null) {
            List<TradeListAndMarketOrdersBean.TradeListBean> list = new ArrayList<>();

            Gson gson = new Gson();
            Type type = new TypeToken<List<TradeListAndMarketOrdersBean.TradeListBean>>() {
            }.getType();
            List<TradeListAndMarketOrdersBean.TradeListBean> marketTradeList = null;
            try {
                marketTradeList = gson.fromJson(new Gson().toJson(event.bean.getMarketTradeList()), type);
            } catch (Exception e) {
                KLog.d("setTradingInfoData Exception = " + e.getMessage());
            }

            setDecimal();
            //限定显示15条数据
//            if (marketTradeList != null && marketTradeList.size() >= Constant.KLINE_SHOW_LIST_COUNT) {
//                for (int i = 0; i < Constant.KLINE_SHOW_LIST_COUNT; i++) {
//                    list.add(marketTradeList.get(i));
//                }
//                mTransactionAdapter.setNewData(list);
//            } else {
            mTransactionAdapter.setNewData(marketTradeList);
//            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TradeListAndMarketOrdersEvent event) {
        //限定显示15条数
        if (event.mBean != null) {
            List<TradeListAndMarketOrdersBean.TradeListBean> marketTradeList = event.mBean.getTradeList();
//            if (marketTradeList != null && marketTradeList.size() != 0) {
//                for (int i = 0; i < marketTradeList.size(); i++) {
//                    int itemCount = mTransactionAdapter.getItemCount();
//                    if (itemCount == KLINE_SHOW_LIST_COUNT) {
//                        mTransactionAdapter.remove(KLINE_SHOW_LIST_COUNT);
//                    }
            mTransactionAdapter.setNewData(marketTradeList);
            KLog.d("mTransactionAdapter 0 = " + new Gson().toJson(mTransactionAdapter.getItem(0)));
//                }
//            }
            KLog.d("mTransactionAdapter ItemCount = " + mTransactionAdapter.getItemCount());
        }
    }

    private void setDecimal() {
        if (OAXApplication.coinsInfoMap.containsKey(marketId)) {
            IndexPageBean.DataBean.AllMaketListBean.MarketListBean marketListBean = OAXApplication.coinsInfoMap.get(marketId);
            mTransactionAdapter.setDecimals(marketListBean.getPriceDecimals(), marketListBean.getQtyDecimals());
        }
    }

}
