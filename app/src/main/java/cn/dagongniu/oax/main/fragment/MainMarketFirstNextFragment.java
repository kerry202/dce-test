package cn.dagongniu.oax.main.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.main.adapter.MainMarketAdapter;
import cn.dagongniu.oax.main.adapter.MainMarketNextAdapter;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.bean.OaxMarketBean;
import cn.dagongniu.oax.utils.ClassConversionUtils;
import cn.dagongniu.oax.utils.ListDataSave;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.events.MyEvents;


/**
 * 首页推荐主流市场  第二页
 */
public class MainMarketFirstNextFragment extends BaseFragment {

    private static final String TAG = "MainMarketFirstNextFrag";
    private List<IndexPageBean.DataBean.RecommendMarketListBean> recommendMarketListBeans;

    public void setRecommendMarketListNext(List<IndexPageBean.DataBean.RecommendMarketListBean> recommendMarketListBeans) {
        this.recommendMarketListBeans = recommendMarketListBeans;
    }

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private MainMarketNextAdapter mainMarketNextAdapter;
    //websocket 排序出来的需要刷新的数据
    List<IndexPageBean.DataBean.RecommendMarketListBean> MarKetRefreshBeans = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mainmarket_first;
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerview.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerview.setNestedScrollingEnabled(false);
        initAdapter();
    }

    @Override
    protected void initData() {
        super.initData();
        mainMarketNextAdapter.setNewData(recommendMarketListBeans);
    }

    private void initAdapter() {
        mainMarketNextAdapter = new MainMarketNextAdapter(getContext(), recommendMarketListBeans);
        recyclerview.setAdapter(mainMarketNextAdapter);
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

    @Subscribe
    public void onEvent(MyEvents event) {
        switch (event.status_type) {
            case MyEvents.Home_WebSocket_Market_List://首页交易对的websocket推送数据数据
                Logger.e(TAG, "第二页websocket收到刷新数据通知！");
                List<OaxMarketBean> webMarketBeans = event.listOaxMarketBeanGson;
                MarKetRefreshBeans = ClassConversionUtils.toRecommendMarketListBean(recommendMarketListBeans, webMarketBeans);
                if (MarKetRefreshBeans != null && recommendMarketListBeans != null && MarKetRefreshBeans.size() == recommendMarketListBeans.size()) {
                    mainMarketNextAdapter.setWebSocketMainMarket(MarKetRefreshBeans);
                }
                mainMarketNextAdapter.notifyDataSetChanged();
                break;
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);

    }
}
