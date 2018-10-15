package cn.dagongniu.oax.main.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.HelpDetailActivity;
import cn.dagongniu.oax.account.bean.HelpBean;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.https.UrlParams;
import cn.dagongniu.oax.main.adapter.MainMarketAdapter;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.bean.OaxMarketBean;
import cn.dagongniu.oax.utils.ClassConversionUtils;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.events.MyEvents;


/**
 * 首页推荐主流市场
 */
public class MainMarketFirstFragment extends BaseFragment {

    private static final String TAG = "MainMarketFirstFragment";

    private List<IndexPageBean.DataBean.RecommendMarketListBean> recommendMarketListBeans;

    public void setRecommendMarketList(List<IndexPageBean.DataBean.RecommendMarketListBean> recommendMarketListBeans) {
        this.recommendMarketListBeans = recommendMarketListBeans;
    }

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private MainMarketAdapter mCommitteeAdapter;

    //websocket 排序出来的需要刷新的数据
    List<IndexPageBean.DataBean.RecommendMarketListBean> MarKetRefreshBeans = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mainmarket_first;
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
        initAdapter();
    }

    private void initRecyclerView() {
        recyclerview.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                IndexPageBean.DataBean.RecommendMarketListBean item = (IndexPageBean.DataBean.RecommendMarketListBean) adapter.getItem(position);
                IndexPageBean.DataBean.RecommendMarketListBean.MarketCoinBean marketCoin = item.getMarketCoin();
                if (marketCoin != null) {
                    SkipActivityUtil.skipToKLineActivity(marketCoin.getMarketId(), Constant.KLINE_MINTYPE_1, getActivity());
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private void initAdapter() {
        mCommitteeAdapter = new MainMarketAdapter(getContext(), recommendMarketListBeans);
        mCommitteeAdapter.setNewData(recommendMarketListBeans);
        recyclerview.setAdapter(mCommitteeAdapter);
    }

    @Subscribe
    public void onEvent(MyEvents event) {
        switch (event.status_type) {
            case MyEvents.Home_WebSocket_Market_List://首页交易对的websocket推送数据数据
                Logger.e(TAG, "第一页websocket收到刷新数据通知！");
                List<OaxMarketBean> webMarketBeans = event.listOaxMarketBeanGson;
                MarKetRefreshBeans = ClassConversionUtils.toRecommendMarketListBean(recommendMarketListBeans, webMarketBeans);
                if (MarKetRefreshBeans.size() == recommendMarketListBeans.size()) {
                    mCommitteeAdapter.setWebSocketMainMarket(MarKetRefreshBeans);
                }
                mCommitteeAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
        recommendMarketListBeans = null;
    }
}
