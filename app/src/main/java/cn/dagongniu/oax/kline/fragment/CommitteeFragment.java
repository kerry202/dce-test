package cn.dagongniu.oax.kline.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.OAXBaseFragment;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.constant.UMConstant;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.https.UrlParams;
import cn.dagongniu.oax.kline.adapter.CommitteeAdapter;
import cn.dagongniu.oax.kline.bean.TradingInfoBean;
import cn.dagongniu.oax.kline.presenter.CommitteePresenter;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.trading.bean.TradeListAndMarketOrdersBean;
import cn.dagongniu.oax.utils.AppManager;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.events.TradeListAndMarketOrdersEvent;
import cn.dagongniu.oax.utils.events.TransactionListEvent;
import cn.dagongniu.oax.utils.um.UMManager;

import static cn.dagongniu.oax.constant.Constant.KLINE_SHOW_LIST_COUNT;

/**
 * 实时委托
 */
public class CommitteeFragment extends OAXBaseFragment implements CommitteeIView {

    @BindView(R.id.buy_recyclerview)
    RecyclerView buyRecyclerview;
    @BindView(R.id.sell_recyclerview)
    RecyclerView sellRecyclerview;
    private CommitteeAdapter mBuyAdapter;
    private CommitteeAdapter mSellAdapter;
    private CommitteePresenter mCommitteePresenter;
    private int marketId = 0;
    private int mPriceDecimal = 8;
    private int mVolDecimal = 5;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_committee;
    }

    @Override
    protected void initView() {
        super.initView();
        Bundle bundle = getArguments();
        if (bundle.containsKey(UrlParams.marketId)) {
            marketId = bundle.getInt(UrlParams.marketId);
        }
        if (OAXApplication.coinsInfoMap.containsKey(marketId)) {
            IndexPageBean.DataBean.AllMaketListBean.MarketListBean bean = OAXApplication.coinsInfoMap.get(marketId);
            mPriceDecimal = bean.getPriceDecimals();
            mVolDecimal = bean.getQtyDecimals();
        }

        mCommitteePresenter = new CommitteePresenter(this);
        buyRecyclerview.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        buyRecyclerview.setNestedScrollingEnabled(false);

        sellRecyclerview.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        sellRecyclerview.setNestedScrollingEnabled(false);
        initBuyAdapter();
        initSellAdapter();

    }

    @Override
    protected void initData() {
        super.initData();
//        WebSocketManager.getInstance().registerMainMarket(Http.TOPIC_MARKETORDERS + marketId, new WebSocketManager.MainMarketStompListener() {
//            @Override
//            public void callData(StompMessage stompMessage) {
//                KLog.d("stompMessage = ", stompMessage.getPayload());
//            }
//        });
        mCommitteePresenter.getTradeListAndMarketOrders(marketId + "");
    }

    private void initBuyAdapter() {
        mBuyAdapter = new CommitteeAdapter(mContext, CommitteeAdapter.TYPE_BUY);
        mBuyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        buyRecyclerview.setAdapter(mBuyAdapter);
        mBuyAdapter.setDecimals(mPriceDecimal, mVolDecimal);
    }

    private void initSellAdapter() {
        mSellAdapter = new CommitteeAdapter(mContext, CommitteeAdapter.TYPE_SELL);
        mSellAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        sellRecyclerview.setAdapter(mSellAdapter);
        mSellAdapter.setDecimals(mPriceDecimal, mVolDecimal);
    }

    @Override
    public void showToast(String str) {

    }


    @Override
    public void setXState(LoadingState xState, String msg) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TransactionListEvent event) {
        if (event != null) {
            TradingInfoBean.MarketOrdersMapBean marketTradeList = event.bean.getMarketOrdersMap();
            if (marketTradeList != null) {
                KLog.d("onEventMainThread = size" + marketTradeList.getBuyList().size());
                KLog.d("onEventMainThread = size" + marketTradeList.getSellList().size());

                Gson gson = new Gson();
                Type type = new TypeToken<List<TradeListAndMarketOrdersBean.BuyOrSellListBean>>() {
                }.getType();
                List<TradeListAndMarketOrdersBean.BuyOrSellListBean> sellList = null;
                List<TradeListAndMarketOrdersBean.BuyOrSellListBean> buyList = null;
                try {
                    sellList = gson.fromJson(new Gson().toJson(marketTradeList.getSellList()), type);
                    buyList = gson.fromJson(new Gson().toJson(marketTradeList.getBuyList()), type);
                } catch (Exception e) {
                    KLog.d("setTradingInfoData Exception = " + e.getMessage());
                }

                mBuyAdapter.setNewData(buyList);
                if (sellList != null && sellList.size() > 1) {
                    Collections.reverse(sellList);
                }
                mSellAdapter.setNewData(sellList);
            }
        }
    }

    @Override
    public void onNewCommitteeList(TradeListAndMarketOrdersBean bean) {
        KLog.d("TradeListAndMarketOrdersBean");
        if (bean != null) {
            EventBus.getDefault().post(new TradeListAndMarketOrdersEvent(bean));
            List<TradeListAndMarketOrdersBean.BuyOrSellListBean> buyList = bean.getBuyList();
            List<TradeListAndMarketOrdersBean.BuyOrSellListBean> sellList = bean.getSellList();
            mBuyAdapter.setNewData(buyList);
            if (sellList != null && sellList.size() > 1) {
                Collections.reverse(sellList);
            }
            mSellAdapter.setNewData(sellList);
            KLog.d("mBuyAdapter ItemCount" + mBuyAdapter.getItemCount());
            KLog.d("mSellAdapter ItemCount" + mSellAdapter.getItemCount());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        UMManager.onResume(mContext, UMConstant.OaxTradingFragment);
        if (!AppManager.isAppIsInBackground(mContext) && OAXApplication.isScreenOn) {
//            String userId = (String) SPUtils.getParamString(mContext, SPConstant.USER_ID, null);
            if (mCommitteePresenter != null) {
                mCommitteePresenter.getTradeListAndMarketOrders(marketId + "");
            }
            KLog.d("WebSocketsManager initWebsocket");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (OAXApplication.mIsBackground || !OAXApplication.isScreenOn) {
            KLog.d("WebSocketsManager disconnect");
            if (mCommitteePresenter != null) {
                mCommitteePresenter.unTopicTradeListAndMarketOrders();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        UMManager.onPause(mContext, UMConstant.OaxTradingFragment);
//        if (AppManager.isAppIsInBackground(mContext) || !OAXApplication.isScreenOn) {
//            KLog.d("WebSocketsManager disconnect");
//            if (mCommitteePresenter != null) {
//                mCommitteePresenter.unTopicTradeListAndMarketOrders();
//            }
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCommitteePresenter.unTopicTradeListAndMarketOrders();
    }
}
