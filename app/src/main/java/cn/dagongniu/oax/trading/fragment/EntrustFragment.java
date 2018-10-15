package cn.dagongniu.oax.trading.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.OAXBaseFragment;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.customview.ClassicsFooter;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.customview.XHLoadingView;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.OAXStateBaseUtils;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.UrlParams;
import cn.dagongniu.oax.language.LanguageUtils;
import cn.dagongniu.oax.trading.adapter.EntrustOrderAdapter;
import cn.dagongniu.oax.trading.bean.CurrentEntrustBean;
import cn.dagongniu.oax.trading.bean.EntrustInfoBean;
import cn.dagongniu.oax.trading.presenter.EntrustPresenter;
import cn.dagongniu.oax.utils.ToastUtil;

/**
 * 委托
 */
public class EntrustFragment extends OAXBaseFragment implements XHLoadingView.OnReTryClickListener, EntrustIView, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.ent_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_ll_cz)
    TextView tvLlCz;
    @BindView(R.id.lv_loading)
    XHLoadingView mLoadingView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    EntrustOrderAdapter adapter;
    private Activity mActivity;
    private EntrustPresenter mEntrustPresenter;
    private int canclePosition;
    ClassicsFooter mClassicsFooter;
    ClassicsHeader mClassicsHeader;
    private int mMarketId = 0;
    private CurrentEntrustBean mCurrentEntrustBean;

    @Override
    protected int getLayoutId() {
        return R.layout.entrust_fragment_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        mEntrustPresenter = new EntrustPresenter(this);
        tvLlCz.setText(R.string.entrust_order_cz);
        initSmartRefresh();
        initRecyc();
        initEmpty();
        mActivity = getActivity();
        if (mActivity != null) {
            Intent intent = mActivity.getIntent();
            mMarketId = intent.getIntExtra(UrlParams.marketId, 0);
        }
    }

    private void initSmartRefresh() {
        /**
         * 事件处理
         */
        mClassicsFooter = (ClassicsFooter) mRefreshLayout.getRefreshFooter();
        mClassicsHeader = (ClassicsHeader) mRefreshLayout.getRefreshHeader();
        LanguageUtils.setFooterLanguage(mClassicsFooter, getActivity());
        LanguageUtils.setHeaderLanguage(mClassicsHeader, getActivity());
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
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

    @Override
    protected void initData() {
        super.initData();
        getCurrentData();
    }

    private void getCurrentData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(UrlParams.pageNo, 1);
        map.put(UrlParams.pageSize, Constant.PAGESIZE);
        map.put(UrlParams.status, "0,1,2");
        mEntrustPresenter.getCurrentEntrust(map, RequestState.STATE_ALL_SCREEN_AND_DIALOG, false, false);
    }

    /**
     * 数据适配器
     */
    private void initRecyc() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new EntrustOrderAdapter(EntrustOrderAdapter.TYPE_ENTRUST, mContext);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                canclePosition = position;
                CurrentEntrustBean.ListBean item = (CurrentEntrustBean.ListBean) adapter.getItem(position);
                if (item != null) {
                    mEntrustPresenter.cancellations(item.getId(), RequestState.STATE_DIALOG);
                }
            }
        });
    }


    @Override
    public void onRetry(View view) {
        getCurrentData();
    }

    @Override
    public void onTopicTradeListData(EntrustInfoBean bean) {

    }

    @Override
    public void onCancellationsState(CommonJsonToBean<String> state) {
        try {
            if (state.getSuccess()) {
                adapter.remove(canclePosition);
            }
            List<CurrentEntrustBean.ListBean> data = adapter.getData();
            if (data == null || data.size() == 0) {
                OAXStateBaseUtils.isNull(this, RequestState.STATE_ALL_SCREEN_AND_DIALOG, "暂无数据");
            }
            if (!TextUtils.isEmpty(state.getMsg())) {
                ToastUtil.ShowToast(state.getMsg(), mActivity);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onCurrentEnstrust(CommonJsonToBean<CurrentEntrustBean> bean) {
        try {
            mLoadingView.setVisibility(View.GONE);
            if (bean != null) {
                mCurrentEntrustBean = bean.getData();
                List<CurrentEntrustBean.ListBean> list = bean.getData().getList();
                adapter.setNewData(list);
            }
        } catch (Exception e) {

        }

    }

    @Override
    public void onRefreshCurrentEnstrust(CommonJsonToBean<CurrentEntrustBean> bean) {
        try {
            mLoadingView.setVisibility(View.GONE);
            mRefreshLayout.finishRefresh();
            mRefreshLayout.setNoMoreData(false);
            if (bean != null) {
                mCurrentEntrustBean = bean.getData();
                List<CurrentEntrustBean.ListBean> list = bean.getData().getList();
                adapter.setNewData(list);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onLoadMoreCurrentEnstrust(CommonJsonToBean<CurrentEntrustBean> bean) {
        try {
            mLoadingView.setVisibility(View.GONE);
            if (bean != null) {
                mCurrentEntrustBean = bean.getData();
                List<CurrentEntrustBean.ListBean> list = bean.getData().getList();
                if (list == null || list.size() == 0) {
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadMore();
                }
                adapter.addData(list);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void showToast(String msg) {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadMore();
        }
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.ShowToast(msg, mActivity);
        }
    }

    @Override
    public void setXState(LoadingState xState, String msg) {
        if (mLoadingView != null) {
            mLoadingView.setState(xState, msg);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(UrlParams.pageNo, 1);
        map.put(UrlParams.pageSize, 10);
        map.put(UrlParams.status, "0,1,2");
//        map.put(UrlParams.marketId, mMarketId);
        mEntrustPresenter.getCurrentEntrust(map, RequestState.STATE_REFRESH, true, false);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        HashMap<String, Object> map = new HashMap<>();
        int num = 1;
        if (mCurrentEntrustBean != null) {
            num = mCurrentEntrustBean.getPageNum() + 1;
        }
        map.put(UrlParams.pageNo, num);
        map.put(UrlParams.pageSize, 10);
        map.put(UrlParams.status, "0,1,2");
//        map.put(UrlParams.marketId, mMarketId);
        mEntrustPresenter.getCurrentEntrust(map, RequestState.STATE_LOADMORE, false, true);
    }
}
