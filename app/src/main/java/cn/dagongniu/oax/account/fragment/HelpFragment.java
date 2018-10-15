package cn.dagongniu.oax.account.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import cn.dagongniu.oax.account.HelpDetailActivity;
import cn.dagongniu.oax.account.adapter.HelpAdapter;
import cn.dagongniu.oax.account.bean.HelpBean;
import cn.dagongniu.oax.account.presenter.HelpPresenter;
import cn.dagongniu.oax.base.OAXBaseFragment;
import cn.dagongniu.oax.base.OAXIViewBean;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.customview.ClassicsFooter;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.customview.XHLoadingView;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.UrlParams;
import cn.dagongniu.oax.language.LanguageUtils;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.ViewUtils;

public class HelpFragment extends OAXBaseFragment implements OAXIViewBean<HelpBean>, OnLoadMoreListener, OnRefreshListener, XHLoadingView.OnReTryClickListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_loading)
    XHLoadingView mLoadingView;

    private HelpAdapter mHelpAdapter;
    private int mType;
    private HelpPresenter mHelpPresenter;
    private ClassicsHeader mClassicsHeader;
    private ClassicsFooter mClassicsFooter;
    private HashMap<String, Object> mMap;
    private HelpBean mInfoBean;


    public static HelpFragment instance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        HelpFragment helpFragment = new HelpFragment();
        helpFragment.setArguments(bundle);
        return helpFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_help;
    }

    @Override
    protected void initView() {
        super.initView();
        initSmartRefresh();
        initEmpty();
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.addItemDecoration(ViewUtils.getRecyclerViewDividerLine(mContext));
        initAdapter();
    }


    private void initSmartRefresh() {
        /**
         * 事件处理
         */
        mClassicsFooter = (ClassicsFooter) refreshLayout.getRefreshFooter();
        mClassicsHeader = (ClassicsHeader) refreshLayout.getRefreshHeader();
        LanguageUtils.setFooterLanguage(mClassicsFooter, getActivity());
        LanguageUtils.setHeaderLanguage(mClassicsHeader, getActivity());
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

    @Override
    protected void lazyLoad() {
        Bundle bundle = getArguments();
        mType = bundle.getInt("type");
        if (mType != Constant.FAQ && isFirstLoad) {
            getHelpData();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        if (mType == Constant.FAQ) {
            getHelpData();
        }
    }

    private void getHelpData() {
        mHelpPresenter = new HelpPresenter(this);
        initParams();
//        mHelpPresenter.getData(mMap, RequestState.STATE_ALL_SCREEN_AND_DIALOG, false);
        refreshLayout.autoRefresh();
        isFirstLoad = false;
    }

    private void initParams() {
        if (mMap == null) {
            mMap = new HashMap<>();
        }
        mMap.clear();
        mMap.put(UrlParams.type, mType);//类型
        mMap.put(UrlParams.pageIndex, 1);//页数
        mMap.put(UrlParams.pageSize, Constant.PAGESIZE);//每页多少条数据
    }

    private void initAdapter() {
        mHelpAdapter = new HelpAdapter();
        recyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                HashMap<String, Integer> map = new HashMap<>();
                map.put(UrlParams.id, ((HelpBean.ListBean) adapter.getData().get(position)).getId());
                SkipActivityUtil.skipAnotherActivity(getActivity(), HelpDetailActivity.class, map, false);
            }
        });
        recyclerview.setAdapter(mHelpAdapter);
    }

    @Override
    public void showToast(String str) {

    }

    @Override
    public void setData(CommonJsonToBean<HelpBean> obj) {
        mLoadingView.setVisibility(View.GONE);
        mInfoBean = obj.getData();
        List<HelpBean.ListBean> listBeans = mInfoBean.getList();
        mHelpAdapter.addData(listBeans);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore();
        if (mInfoBean != null) {
            mMap.put(UrlParams.pageIndex, mInfoBean.getPageNum() + 1);//页数
            mHelpPresenter.getData(mMap, RequestState.STATE_LOADMORE, true);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore();
        refreshLayout.setNoMoreData(false);
        initParams();
        mHelpPresenter.getData(mMap, RequestState.STATE_REFRESH, false);
    }

    @Override
    public void setLoadMore(CommonJsonToBean<HelpBean> obj) {
        mLoadingView.setVisibility(View.GONE);
        mInfoBean = obj.getData();
        List<HelpBean.ListBean> listBeans = mInfoBean.getList();
        if (listBeans != null && listBeans.size() > 0) {
            mHelpAdapter.addData(listBeans);
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }


    }

    @Override
    public void setRefresh(CommonJsonToBean<HelpBean> obj) {
        mLoadingView.setVisibility(View.GONE);
        mInfoBean = obj.getData();
        if (mInfoBean != null) {
            mHelpAdapter.setNewData(mInfoBean.getList());
        }
        refreshLayout.finishRefresh();
    }

    @Override
    public void onRetry(View view) {
        getHelpData();
    }

    @Override
    public void setXState(LoadingState xState, String msg) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        mLoadingView.setState(xState, msg);
    }
}
