package cn.dagongniu.oax.trading.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.bean.OaxMarketBean;
import cn.dagongniu.oax.trading.adapter.TradingAllMarketAdapter;
import cn.dagongniu.oax.utils.ClassConversionUtils;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.events.MyEvents;
import cn.dagongniu.oax.utils.events.SelectTradingCoinEvent;

/**
 * 交易页面所有市场
 */
public class TradingAllMarketFragmnet extends BaseFragment {

    private static final String TAG = "TradingAllMarketFragmne";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    TradingAllMarketAdapter tradingAllMarketAdapter;
    //websocket 排序出来的需要刷新的数据
    List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> MarKetRefreshBeans = new ArrayList<>();

    private List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> marketListBeans = new ArrayList<>();

    public void setAllMaketListList(List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> marketListBeans) {
        this.marketListBeans = marketListBeans;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oax_home_btc_choose_frament;
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyc();
    }

    /**
     * 适配器
     */
    private void initRecyc() {
        //屏蔽滑动事件
        //TODO NestedScrollView嵌套RecyclerView时滑动不流畅问题的解决办法
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setSmoothScrollbarEnabled(true);
//        layoutManager.setAutoMeasureEnabled(true);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setNestedScrollingEnabled(iv_false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                IndexPageBean.DataBean.AllMaketListBean.MarketListBean item = (IndexPageBean.DataBean.AllMaketListBean.MarketListBean) adapter.getItem(position);
                if (item != null) {
//                    SkipActivityUtil.skipToKLineActivity(item.getMarketId(), Constant.KLINE_MINTYPE_1, getActivity());
                    EventBus.getDefault().post(new SelectTradingCoinEvent(item.getMarketId()));
                    getActivity().finish();
                }
            }
        });
        tradingAllMarketAdapter = new TradingAllMarketAdapter(getContext(), null);
        mRecyclerView.setAdapter(tradingAllMarketAdapter);
        tradingAllMarketAdapter.setNewData(marketListBeans);
    }

    @Subscribe
    public void onEvent(MyEvents event) {
        switch (event.status_type) {
            case MyEvents.Home_WebSocket_Market_List://交易页交易对的websocket推送数据数据
                Logger.e(TAG, "交易页的所有市场websocket收到刷新数据通知！");
                List<OaxMarketBean> webMarketBeans = event.listOaxMarketBeanGson;
                if (marketListBeans != null) {
                    MarKetRefreshBeans = ClassConversionUtils.toAllMarketListBean(marketListBeans, webMarketBeans);
                    tradingAllMarketAdapter.setNewData(MarKetRefreshBeans);
                    tradingAllMarketAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


}
