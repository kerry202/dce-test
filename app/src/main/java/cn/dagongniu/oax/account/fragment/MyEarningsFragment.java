package cn.dagongniu.oax.account.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;


import java.util.ArrayList;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.adapter.MyEarningsAdapter;
import cn.dagongniu.oax.account.bean.MyEarningsDataBean;
import cn.dagongniu.oax.account.bean.MyEarningsItemBean;
import cn.dagongniu.oax.account.presenter.MyEarningsPresenter;
import cn.dagongniu.oax.base.OAXBaseFragment;
import cn.dagongniu.oax.base.OAXIViewBean;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.customview.XHLoadingView;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.language.LanguageUtils;

public class MyEarningsFragment extends OAXBaseFragment implements OAXIViewBean<MyEarningsDataBean>, OnRefreshListener, XHLoadingView.OnReTryClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_loading)
    XHLoadingView mLoadingView;

    private ClassicsHeader mClassicsHeader;
    private MyEarningsAdapter mAdapter;
    private MyEarningsPresenter mPresenter;
    private int mCount;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_myearnings;
    }

    @Override
    protected void initView() {
        super.initView();
        initSmartRefresh();
        initEmpty();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        initAdapter();
    }

    private void initSmartRefresh() {
        /**
         * 事件处理
         */
        mClassicsHeader = (ClassicsHeader) refreshLayout.getRefreshHeader();
        LanguageUtils.setHeaderLanguage(mClassicsHeader, getActivity());
        refreshLayout.setOnRefreshListener(this);
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

    private void initAdapter() {
        mAdapter = new MyEarningsAdapter(mContext);
        recyclerview.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        refreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getData();
    }

    private void getData() {
        if (mPresenter == null) {
            mPresenter = new MyEarningsPresenter(this);
        }
        mPresenter.getData(null, RequestState.STATE_REFRESH);
    }

    @Override
    public void onRetry(View view) {
        refreshLayout.autoRefresh();
    }

    @Override
    public void setData(CommonJsonToBean<MyEarningsDataBean> data) {
        try {
            mLoadingView.setVisibility(View.GONE);
            refreshLayout.finishRefresh();
            MyEarningsDataBean dataBean = data.getData();

            if (dataBean == null) {
                return;
            }
            //防止下拉刷新时设置边框颜色崩溃
            ArrayList<MyEarningsItemBean> list = new ArrayList<>();
//            mAdapter.setNewData(list);
            if (mCount == 0) {
                mAdapter.isFromReflesh(false);
            } else {
                mAdapter.isFromReflesh(true);
            }
            mCount++;

            MyEarningsItemBean bean = new MyEarningsItemBean();


            //重新组合数据
            //1.昨日回馈收益折合
            bean.setTitle(getResources().getString(R.string.yesterday_feedback));
            MyEarningsItemBean.CoinsBean coinsBean = new MyEarningsItemBean.CoinsBean();
            coinsBean.setCoinsName("X");
            coinsBean.setCount(dataBean.getYesterdayFeedBack());
            bean.getCoins().add(coinsBean);

            list.add(bean);

            mAdapter.setNewData(list);
        } catch (Exception e) {
            KLog.d("setData Exception = " + e.getMessage());
        }

    }

    @Override
    public void setRefresh(CommonJsonToBean<MyEarningsDataBean> data) {

    }

    @Override
    public void setLoadMore(CommonJsonToBean<MyEarningsDataBean> data) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void setXState(LoadingState xState, String msg) {
        try {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            mLoadingView.setState(xState, msg);
        }catch (Exception e){
            KLog.d("setXState Exception = " + e.getMessage());
        }
    }


}
