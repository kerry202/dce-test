package cn.dagongniu.oax.account.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;

import java.util.ArrayList;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.adapter.TotalFeedbackAdapter;
import cn.dagongniu.oax.account.bean.TotalBounsDataBean;
import cn.dagongniu.oax.account.bean.TotalBounsItemBean;
import cn.dagongniu.oax.account.bean.TotalFeedbackDataBean;
import cn.dagongniu.oax.account.bean.TotalFeedbackItemBean;
import cn.dagongniu.oax.account.presenter.TotalFeedbackPresenter;
import cn.dagongniu.oax.base.OAXBaseFragment;
import cn.dagongniu.oax.base.OAXIViewBean;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.customview.XHLoadingView;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.language.LanguageUtils;

public class TotalFeedbackFragment extends OAXBaseFragment implements OAXIViewBean<TotalFeedbackDataBean>, OnRefreshListener, XHLoadingView.OnReTryClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_loading)
    XHLoadingView mLoadingView;

    private ClassicsHeader mClassicsHeader;
    private TotalFeedbackAdapter mAdapter;
    private TotalFeedbackPresenter mPresenter;
    private double mCount;
    protected boolean isFirstLoad = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_total_feedback;
    }

    @Override
    protected void initView() {
        super.initView();
        initSmartRefresh();
        initEmpty();
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
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
        mAdapter = new TotalFeedbackAdapter(mContext);
        recyclerview.setAdapter(mAdapter);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (isFirstLoad) {
            refreshLayout.autoRefresh();
            isFirstLoad = false;
        }

    }

    @Override
    public void onRetry(View view) {
        refreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getData();
    }

    private void getData() {
        if (mPresenter == null) {
            mPresenter = new TotalFeedbackPresenter(this);
        }
        mPresenter.getData(null, RequestState.STATE_REFRESH);
    }

    @Override
    public void setData(CommonJsonToBean<TotalFeedbackDataBean> data) {
        try {
            mLoadingView.setVisibility(View.GONE);
            refreshLayout.finishRefresh();
            TotalFeedbackDataBean dataBean = data.getData();

            if (dataBean == null) {
                return;
            }
            //防止下拉刷新时设置边框颜色崩溃
            ArrayList<TotalFeedbackItemBean> list = new ArrayList<>();
//            mAdapter.setNewData(list);
            if (mCount == 0) {
                mAdapter.isFromReflesh(false);
            } else {
                mAdapter.isFromReflesh(true);
            }
            mCount++;


            //重新组合数据
            //1.今日待分配收入折合
            TotalFeedbackItemBean feedBack = new TotalFeedbackItemBean();
            feedBack.setDes(getResources().getString(R.string.today_FeedBack_Sum));
            feedBack.setFeedBackETH(dataBean.getTodayFeedBackSum().getFeedBackETH());
            feedBack.setFeedBackX(dataBean.getTodayFeedBackSum().getFeedBackX());

            //2.今日补偿待分配收入折合
            TotalFeedbackItemBean compensationBean = new TotalFeedbackItemBean();
            compensationBean.setDes(getResources().getString(R.string.today_Compensation_FeedBack));
            compensationBean.setFeedBackETH(dataBean.getTodayCompensationFeedBack().getFeedBackETH());
            compensationBean.setFeedBackX(dataBean.getTodayCompensationFeedBack().getFeedBackX());

            //3.今日交易分配收入
            TotalFeedbackItemBean trade = new TotalFeedbackItemBean();
            trade.setDes(getResources().getString(R.string.today_Trade_FeedBack));
            trade.setFeedBackETH(dataBean.getTodayTradeFeedBack().getFeedBackETH());
            trade.setFeedBackX(dataBean.getTodayTradeFeedBack().getFeedBackX());


            list.add(feedBack);
            list.add(compensationBean);
            list.add(trade);

            mAdapter.setNewData(list);
        } catch (Exception e) {
            KLog.d("setData Exception = " + e.getMessage());
        }
    }

    @Override
    public void setRefresh(CommonJsonToBean<TotalFeedbackDataBean> data) {

    }

    @Override
    public void setLoadMore(CommonJsonToBean<TotalFeedbackDataBean> data) {

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
        } catch (Exception e) {
            KLog.d("setXState Exception = " + e.getMessage());
        }

    }
}
