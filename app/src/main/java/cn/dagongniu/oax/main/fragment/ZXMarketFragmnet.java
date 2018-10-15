package cn.dagongniu.oax.main.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.LoginActivity;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.constant.UMConstant;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.kline.presenter.KLineActivityPresenter;
import cn.dagongniu.oax.main.adapter.AllMarketAdapter;
import cn.dagongniu.oax.main.adapter.BTCorETHMarketAdapter;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.bean.OaxMarketBean;
import cn.dagongniu.oax.main.presenter.MarketPresenter;
import cn.dagongniu.oax.main.view.MarketIView;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.ClassConversionUtils;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.CollectMarketStateEvent;
import cn.dagongniu.oax.utils.events.MyEvents;
import cn.dagongniu.oax.utils.um.UMManager;

/**
 * 自选 市场
 */
public class ZXMarketFragmnet extends BaseFragment implements MarketIView {

    private static final String TAG = "ZXMarketFragmnet";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_zx_hint)
    AutoRelativeLayout rlZxHint;

    AllMarketAdapter adapter;

    MarketPresenter mPresenter;
    //websocket 排序出来的需要刷新的数据
    List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> MarKetRefreshBeans = new ArrayList<>();
    private List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> marketListBeans = new ArrayList<>();
    List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> jsonObjects;

    /**
     * 第一次进来装数据
     */
    public void dataChange() {
        List<IndexPageBean.DataBean.UserMaketListBean> mapValueList = new ArrayList<>(OAXApplication.collectCoinsMap.values());
        Type type = new TypeToken<List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean>>() {
        }.getType();
        Gson gson = new Gson();

        jsonObjects = gson.fromJson(gson.toJson(mapValueList), type);
        marketListBeans.clear();
        marketListBeans.addAll(jsonObjects);
        adapter = new AllMarketAdapter(OAXApplication.getContext(), jsonObjects);
        adapter.setNewData(jsonObjects);
    }

    /**
     * 收藏刷新
     */
    public void dataSilentChange() {
        List<IndexPageBean.DataBean.UserMaketListBean> mapValueList = new ArrayList<>(OAXApplication.collectCoinsMap.values());
        Type type = new TypeToken<List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean>>() {
        }.getType();
        Gson gson = new Gson();
        jsonObjects = gson.fromJson(gson.toJson(mapValueList), type);

        if (jsonObjects.size() > 0) {
            rlZxHint.setVisibility(View.GONE);
        } else {
            rlZxHint.setVisibility(View.VISIBLE);
        }

        marketListBeans.clear();
        marketListBeans.addAll(jsonObjects);
        adapter.setNewData(jsonObjects);

        adapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oax_home_zx_frament;
    }

    @Override
    protected void initView() {
        super.initView();
        mPresenter = new MarketPresenter(this);
        initRecyc();

    }

    /**
     * 适配器
     */
    private void initRecyc() {
        //屏蔽滑动事件
        //TODO NestedScrollView嵌套RecyclerView时滑动不流畅问题的解决办法
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<IndexPageBean.DataBean.UserMaketListBean> mapValueList = new ArrayList<>(OAXApplication.collectCoinsMap.values());
        Type type = new TypeToken<List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean>>() {
        }.getType();
        Gson gson = new Gson();
        jsonObjects = gson.fromJson(gson.toJson(mapValueList), type);
        adapter = new AllMarketAdapter(OAXApplication.getContext(), jsonObjects);
        adapter.setNewData(jsonObjects);
        mRecyclerView.setAdapter(adapter);
        if (jsonObjects.size() > 0) {
            rlZxHint.setVisibility(View.GONE);
        } else {
            rlZxHint.setVisibility(View.VISIBLE);
        }
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                IndexPageBean.DataBean.AllMaketListBean.MarketListBean item = (IndexPageBean.DataBean.AllMaketListBean.MarketListBean) adapter.getItem(position);
                if (item != null) {
                    SkipActivityUtil.skipToKLineActivity(item.getMarketId(), Constant.KLINE_MINTYPE_1, getActivity());
                }
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                IndexPageBean.DataBean.AllMaketListBean.MarketListBean bean = (IndexPageBean.DataBean.AllMaketListBean.MarketListBean) adapter.getItem(position);
                String userid = (String) SPUtils.getParamString(getActivity(), SPConstant.USER_ID, null);
                //更具id判断自选是否出现
                if (userid != null) {
                    if (bean != null) {
                        if (bean.getIsCollection() == 1) {
                            mPresenter.cancelCollectMarket(bean.getMarketId(), RequestState.STATE_REFRESH);
                            UMManager.onEvent(mContext, UMConstant.OaxHomeNFragment, UMConstant.uncollect_coin);
                        } else {
                            mPresenter.collectMarket(bean.getMarketId(), RequestState.STATE_REFRESH);
                            UMManager.onEvent(mContext, UMConstant.OaxHomeNFragment, UMConstant.collect_coin);
                        }
                    }
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra(AppConstants.LOGINTYPE, AppConstants.LOGINPHONE);
                    startActivity(intent);
                }
            }
        });
    }

    @Subscribe
    public void onEvent(MyEvents event) {
        switch (event.status_type) {
            case MyEvents.Home_WebSocket_Market_List://首页交易对的websocket推送数据数据
                List<OaxMarketBean> webMarketBeans = event.listOaxMarketBeanGson;
                KLog.d("webMarketBeans = " + new Gson().toJson(webMarketBeans));
                if (marketListBeans != null) {
                    MarKetRefreshBeans = ClassConversionUtils.toAllMarketListBean(marketListBeans, webMarketBeans);
                    adapter.setNewData(MarKetRefreshBeans);
                    adapter.notifyDataSetChanged();
                    if (MarKetRefreshBeans != null && MarKetRefreshBeans.size() > 0) {
                        rlZxHint.setVisibility(View.GONE);
                    } else {
                        rlZxHint.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }

    @Override
    public void collectMarketState(CommonJsonToBean<String> data) {

    }

    @Override
    public void cancelCollectMarket(CommonJsonToBean<String> data) {
        EventBus.getDefault().post(new CollectMarketStateEvent());
    }


    @Override
    public void showToast(String msg) {

    }
}
