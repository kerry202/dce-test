package cn.dagongniu.oax.trading.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.main.adapter.AllMarketAdapter;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.bean.OaxMarketBean;
import cn.dagongniu.oax.trading.adapter.TradingAllMarketAdapter;
import cn.dagongniu.oax.trading.adapter.TradingZXMarketAdapter;
import cn.dagongniu.oax.utils.ClassConversionUtils;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.events.MyEvents;
import cn.dagongniu.oax.utils.events.SelectTradingCoinEvent;

/**
 * 交易自选 市场
 */
public class TradingZXMarketFragmnet extends BaseFragment {

    private static final String TAG = "TradingZXMarketFragmnet";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    TradingZXMarketAdapter tradingZXMarketAdapter;


    //websocket 排序出来的需要刷新的数据
    List<IndexPageBean.DataBean.UserMaketListBean> MarKetRefreshBeans = new ArrayList<>();

    private List<IndexPageBean.DataBean.UserMaketListBean> marketListBeans;

    public void setUserMaketList(List<IndexPageBean.DataBean.UserMaketListBean> marketListBeans) {
        this.marketListBeans = marketListBeans;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.trading_zx_frament;
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                IndexPageBean.DataBean.UserMaketListBean item = (IndexPageBean.DataBean.UserMaketListBean) adapter.getItem(position);
                if (item != null) {
//                    SkipActivityUtil.skipToKLineActivity(item.getMarketId(), Constant.KLINE_MINTYPE_1, getActivity());
                    EventBus.getDefault().post(new SelectTradingCoinEvent(item.getMarketId()));
                    getActivity().finish();
                }
            }
        });
        tradingZXMarketAdapter = new TradingZXMarketAdapter(getContext(), null);
        mRecyclerView.setAdapter(tradingZXMarketAdapter);
        tradingZXMarketAdapter.setNewData(marketListBeans);
    }

    @Subscribe
    public void onEvent(MyEvents event) {
        switch (event.status_type) {
            case MyEvents.Home_WebSocket_Market_List://交易页的自选websocket收到刷新数据通知
                Logger.e(TAG, "交易页的自选websocket收到刷新数据通知！");
                List<OaxMarketBean> webMarketBeans = event.listOaxMarketBeanGson;
                if (marketListBeans != null) {

                    MarKetRefreshBeans = ClassConversionUtils.toUserMarketListBean(marketListBeans, webMarketBeans);
                    tradingZXMarketAdapter.setNewData(MarKetRefreshBeans);
                    tradingZXMarketAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

}
