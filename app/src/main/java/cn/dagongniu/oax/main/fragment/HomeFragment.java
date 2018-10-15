package cn.dagongniu.oax.main.fragment;


import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.customview.ClassicsFooter;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.customview.MyToolbar;
import cn.dagongniu.oax.customview.XHLoadingView;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.language.LanguageUtils;
import cn.dagongniu.oax.main.adapter.HomeRecyclerAdapter;
import cn.dagongniu.oax.main.bean.TestBean;
import cn.dagongniu.oax.main.presenter.HomePresenter;
import es.dmoral.toasty.Toasty;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment implements OnRefreshListener,
        OnLoadMoreListener,
        XHLoadingView.OnReTryClickListener,
        IView {

    private static final String TAG = "HomeFragment";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.home_toolbar)
    MyToolbar homeToolbar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_loading)
    XHLoadingView mLoadingView;

    ClassicsFooter mClassicsFooter;
    HomeRecyclerAdapter homeRecyclerAdapter;
    private HomePresenter homePresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_frament;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
//        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).init();
        mImmersionBar
                .titleBar(homeToolbar)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initView() {
        super.initView();
        homeToolbar.setmIvTitleLeftMoreGone();

        /**
         * 事件处理
         */
        mClassicsFooter = (ClassicsFooter) refreshLayout.getRefreshFooter();
        LanguageUtils.setFooterLanguage(mClassicsFooter, getActivity());
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);

        /**
         * 模拟重试  消息
         */
        homeToolbar.setRightImgClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePresenter = new HomePresenter(HomeFragment.this, RequestState.STATE_ALL_SCREEN_AND_DIALOG);
                homePresenter.gethomeData();
            }
        });

        initEmpty();

        /**
         * 数据请求
         */
        homePresenter = new HomePresenter(this, RequestState.STATE_ALL_SCREEN_AND_DIALOG);
        homePresenter.gethomeData();
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
    }


    public String[] getData(int count) {
        String[] a = new String[count];
        for (int i = 0; i < count; i++) {
            a[i] = "111";
        }
        return a;
    }


    @Override
    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
        homePresenter = new HomePresenter(this, RequestState.STATE_REFRESH);
        homePresenter.gethomeData();
    }

    @Override
    public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
        homePresenter = new HomePresenter(this, RequestState.STATE_LOADMORE);
        homePresenter.gethomeData();
    }


    @Override
    public void onRetry(View view) {
        Toasty.info(getActivity(), "重新请求网络 ", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void setData(Object piazzaData) {
//        TestBean testBean = (TestBean) piazzaData.getData();
//        Toasty.info(getActivity(), "请求成功", Toast.LENGTH_SHORT, true).show();
        /**
         * 适配器
         */
        if (recyclerView instanceof RecyclerView) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            homeRecyclerAdapter = new HomeRecyclerAdapter(getData(10), getActivity());
            recyclerView.setAdapter(homeRecyclerAdapter);
        }
    }

    /**
     * 全屏状态
     *
     * @param xState
     */
    @Override
    public void setXState(LoadingState xState, String msg) {
        mLoadingView.setState(xState, msg);
    }


    @Override
    public void setRefresh(Object piazzaData) {
        Toasty.info(getActivity(), getResources().getString(R.string.refresh_ok), Toast.LENGTH_SHORT, true).show();
        homeRecyclerAdapter.setFloorList(getData(10));
        homeRecyclerAdapter.notifyDataSetChanged();
        refreshLayout.finishRefresh();
        refreshLayout.setNoMoreData(false);
    }

    @Override
    public void setLoadMore(Object piazzaData) {
        Toasty.info(getActivity(), getResources().getString(R.string.load_ok), Toast.LENGTH_SHORT, true).show();
        if (homeRecyclerAdapter.getItemCount() >= 20) {
            refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
        } else {
            homeRecyclerAdapter.setFloorList(getData(20));
            homeRecyclerAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
        }
    }
}
