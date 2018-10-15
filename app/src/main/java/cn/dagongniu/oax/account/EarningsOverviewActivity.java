package cn.dagongniu.oax.account;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.EarningsOverviewDataBean;
import cn.dagongniu.oax.account.bean.EarningsOverviewItemBean;
import cn.dagongniu.oax.account.presenter.EarningsOverviewPresenter;
import cn.dagongniu.oax.base.OAXBaseActivity;
import cn.dagongniu.oax.base.OAXIViewBean;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.customview.CommonToolbar;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.customview.ShadowTextView;
import cn.dagongniu.oax.customview.XHLoadingView;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.language.LanguageUtils;
import cn.dagongniu.oax.utils.DensityUtils;

public class EarningsOverviewActivity extends OAXBaseActivity implements OAXIViewBean<EarningsOverviewDataBean>, OnRefreshListener, OnLoadMoreListener, XHLoadingView.OnReTryClickListener {

    @BindView(R.id.toolbar)
    CommonToolbar mToolbar;
    //    @BindView(R.id.recyclerview)
//    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_loading)
    XHLoadingView mLoadingView;
    @BindView(R.id.tv_circulation_market_value_count)
    ShadowTextView tvCirculationMarketValueCount;
    @BindView(R.id.tv_total_count)
    ShadowTextView tvTotalCount;
    @BindView(R.id.tv_total_market_value_count)
    ShadowTextView tvTotalMarketValueCount;
    @BindView(R.id.tv_circulation_total_count)
    ShadowTextView tvCirculationTotalCount;
    @BindView(R.id.ll_circulation_market_value)
    AutoLinearLayout llCirculationMarketValue;
    @BindView(R.id.ll_total)
    AutoLinearLayout llTotal;
    @BindView(R.id.ll_total_market_value)
    AutoLinearLayout llTotalMarketValue;
    @BindView(R.id.ll_circulation_total)
    AutoLinearLayout llCirculationTotal;

    private ClassicsHeader mClassicsHeader;
    //    private EarningsOverviewAdapter mAdapter;
    private EarningsOverviewPresenter mPresenter;
    private double mCount;
    private float minTextSize;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_earnings_overview;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();
        initSmartRefresh();
        initEmpty();
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        initAdapter();
        int earnings_shadow = getResources().getColor(R.color.earnings_shadow);
        new CrazyShadow.Builder()
                .setContext(mContext)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(DensityUtils.px2dp(getResources().getDimension(R.dimen.earnings_shadow)))
//                        .setCorner(DensityUtils.px2dp(3))
                .setBaseShadowColor(earnings_shadow)
                .setImpl(CrazyShadow.IMPL_DRAW)
                .action(llCirculationMarketValue);

        new CrazyShadow.Builder()
                .setContext(mContext)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(DensityUtils.px2dp(getResources().getDimension(R.dimen.earnings_shadow)))
//                        .setCorner(DensityUtils.px2dp(3))
                .setBaseShadowColor(earnings_shadow)
                .setImpl(CrazyShadow.IMPL_DRAW)
                .action(llCirculationTotal);


        new CrazyShadow.Builder()
                .setContext(mContext)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(DensityUtils.px2dp(getResources().getDimension(R.dimen.earnings_shadow)))
//                        .setCorner(DensityUtils.px2dp(3))
                .setBaseShadowColor(earnings_shadow)
                .setImpl(CrazyShadow.IMPL_DRAW)
                .action(llTotal);

        new CrazyShadow.Builder()
                .setContext(mContext)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(DensityUtils.px2dp(getResources().getDimension(R.dimen.earnings_shadow)))
//                        .setCorner(DensityUtils.px2dp(3))
                .setBaseShadowColor(earnings_shadow)
                .setImpl(CrazyShadow.IMPL_DRAW)
                .action(llTotalMarketValue);
    }

    private void initSmartRefresh() {
        mClassicsHeader = (ClassicsHeader) refreshLayout.getRefreshHeader();
        LanguageUtils.setHeaderLanguage(mClassicsHeader, this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }

    private void initEmpty() {
        mLoadingView.withLoadEmptyText(getResources().getString(R.string.no_data)).
                withEmptyIcon(R.mipmap.state_empty).
                withBtnEmptyEnnable(false)
                .withLoadErrorText(getResources().getString(R.string.network_errors))
                .withErrorIco(R.mipmap.state_error)
                .withBtnErrorText(getResources().getString(R.string.http_clickretry))
                .withLoadNoNetworkText(getResources().getString(R.string.network_request_failed))
                .withNoNetIcon(R.mipmap.state_no_net)
                .withBtnNoNetText(getResources().getString(R.string.http_clickretry))
                .build();
        mLoadingView.setWithOnRetryListener(this);
    }

    private void initToolbar() {
        mToolbar.setTitleText(getResources().getString(R.string.earnings_overview));
        mToolbar.setOnCommonToolbarLeftClickListener(new CommonToolbar.CommonToolbarLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    private void initAdapter() {
//        mAdapter = new EarningsOverviewAdapter(mContext);
//        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        refreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter == null) {
            mPresenter = new EarningsOverviewPresenter(this);
        }
        mPresenter.getData(null, RequestState.STATE_REFRESH);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRetry(View view) {
        refreshLayout.autoRefresh();
    }

    @Override
    public void setData(CommonJsonToBean<EarningsOverviewDataBean> data) {
        try {
            mLoadingView.setVisibility(View.GONE);
            refreshLayout.finishRefresh();
            EarningsOverviewDataBean dataBean = data.getData();

            if (dataBean == null) {
                return;
            }
            //防止下拉刷新时设置边框颜色崩溃
            ArrayList<EarningsOverviewItemBean> list = new ArrayList<>();
//            mAdapter.setNewData(list);
//            if (mCount == 0) {
//                mAdapter.isFromReflesh(false);

//            } else {
//                mAdapter.isFromReflesh(true);
//            }
//            mCount++;

            //1.流通总量
//            EarningsOverviewItemBean circulation_market_value = new EarningsOverviewItemBean();
//            circulation_market_value.setDes(getResources().getString(R.string.x_token_circulation_market_value));
//            circulation_market_value.setCount(dataBean.getCirculationMarketValue());
            tvCirculationMarketValueCount.setText(dataBean.getCirculationMarketValue().toPlainString());
            minTextSize = tvCirculationMarketValueCount.getTextSize();
            //2.总量
//            EarningsOverviewItemBean total = new EarningsOverviewItemBean();
//            total.setDes(getResources().getString(R.string.x_token_total));
//            total.setCount(dataBean.getTotal());
            tvTotalCount.setText(dataBean.getTotal().toPlainString());
            float TotalCountTextSize = tvTotalCount.getTextSize();
            if (TotalCountTextSize < minTextSize) {
                minTextSize = TotalCountTextSize;
            }
            //3.总市值
//            EarningsOverviewItemBean total_market_value = new EarningsOverviewItemBean();
//            total_market_value.setDes(getResources().getString(R.string.x_token_total_market_value));
//            total_market_value.setCount(dataBean.getTotalMarketValue());
            tvTotalMarketValueCount.setText(dataBean.getTotalMarketValue().toPlainString());
            float TotalMarketValueCountTextSize1 = tvTotalMarketValueCount.getTextSize();
            if (TotalMarketValueCountTextSize1 < minTextSize) {
                minTextSize = TotalMarketValueCountTextSize1;
            }
            //4.流通市值
//            EarningsOverviewItemBean circulation_total = new EarningsOverviewItemBean();
//            circulation_total.setDes(getResources().getString(R.string.x_token_circulation_total));
//            circulation_total.setCount(dataBean.getCirculationTotal());
            tvCirculationTotalCount.setText(dataBean.getCirculationTotal().toPlainString());
            float CirculationTotalCountTextSize = tvCirculationTotalCount.getTextSize();
            if (CirculationTotalCountTextSize < minTextSize) {
                minTextSize = CirculationTotalCountTextSize;
            }
//            list.add(circulation_market_value);
//            list.add(total);
//            list.add(total_market_value);
//            list.add(circulation_total);
//            mAdapter.setNewData(list);
            float textSize = DensityUtils.px2sp(minTextSize);
            tvCirculationMarketValueCount.setTextSize(textSize);
            tvTotalCount.setTextSize(textSize);
            tvTotalMarketValueCount.setTextSize(textSize);
            tvCirculationTotalCount.setTextSize(textSize);
        } catch (Exception e) {
            KLog.d("setData Exception = " + e.getMessage());
        }

    }

    @Override
    public void setRefresh(CommonJsonToBean<EarningsOverviewDataBean> data) {

    }

    @Override
    public void setLoadMore(CommonJsonToBean<EarningsOverviewDataBean> data) {

    }

    @Override
    public void setXState(LoadingState xState, String msg) {
        try {
            refreshLayout.finishRefresh();
            mLoadingView.setState(xState, msg);
        } catch (Exception e) {
            KLog.d("setXState Exception = " + e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
