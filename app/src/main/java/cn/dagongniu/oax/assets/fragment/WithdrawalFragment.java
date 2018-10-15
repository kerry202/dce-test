package cn.dagongniu.oax.assets.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.adapter.TopUpRecordAdapter;
import cn.dagongniu.oax.assets.adapter.WTRecordAdapter;
import cn.dagongniu.oax.assets.adapter.WithdrawalRecordAdapter;
import cn.dagongniu.oax.assets.bean.PropertyRechargeBean;
import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.oax.assets.presenter.PropertyRechargePresenter;
import cn.dagongniu.oax.assets.presenter.PropertyWithdrawPresenter;
import cn.dagongniu.oax.assets.view.IPropertyRechargeView;
import cn.dagongniu.oax.assets.view.IPropertyWithdrawView;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.customview.ClassicsFooter;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.kline.bean.CommitteeBean;
import cn.dagongniu.oax.language.LanguageUtils;
import cn.dagongniu.oax.utils.SortingUtils;
import cn.dagongniu.oax.utils.ToastUtil;


/**
 * 提现 Fragment
 */
public class WithdrawalFragment extends BaseFragment implements IPropertyWithdrawView, OnRefreshListener,
        OnLoadMoreListener {


    @BindView(R.id.ent_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.im_sorting_time)
    ImageView imSortingTime;//时间排序
    @BindView(R.id.im_sorting_market)
    ImageView imSortingMarket;//币种
    @BindView(R.id.im_sorting_count)
    ImageView imSortingCount;//数量排序
    @BindView(R.id.rl_time)
    RelativeLayout rl_time;
    @BindView(R.id.rl_market)
    RelativeLayout rl_market;
    @BindView(R.id.rl_count)
    RelativeLayout rl_count;
    @BindView(R.id.rl_no_data)
    LinearLayout rlNodata;

    int iRltime = -1;
    int iRlmarket = -1;
    int iRlcount = -1;

    WithdrawalRecordAdapter withdrawalRecordAdapter;
    PropertyWithdrawPresenter propertyWithdrawPresenter;
    List<PropertyWithdrawBean.DataBean.ListBean> listBeanList;
    List<PropertyWithdrawBean.DataBean.ListBean> listBeanListDefault = null;
    int page = 1;
    Bundle bundle;

    ClassicsFooter mClassicsFooter;
    ClassicsHeader mClassicsHeader;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.topup_withdrawal_fragment_layout;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    protected void initView() {
        super.initView();
        initSmartRefresh();
        propertyWithdrawPresenter = new PropertyWithdrawPresenter(this, RequestState.STATE_REFRESH);
        mRefreshLayout.autoRefresh();
        withdrawalRecordAdapter = new WithdrawalRecordAdapter(getContext());
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

    @Override
    protected void initData() {
        super.initData();
    }


    @Override
    public String getCoinName() {
        return null;
    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public int getPropertyWithdrawPageIndex() {
        return page;
    }

    @Override
    public int getPropertyWithdrawPageSize() {
        return Constant.PAGESIZE;
    }

    /**
     * 数据回调
     *
     * @param PropertyWithdrawBean
     */
    @Override
    public void setPropertyWithdrawData(PropertyWithdrawBean PropertyWithdrawBean) {
        rlNodata.setVisibility(View.GONE);
        iRltime = -1;
        iRlmarket = -1;
        iRlcount = -1;
        sortingImg();
        listBeanList = PropertyWithdrawBean.getData().getList();
        listBeanListDefault = new ArrayList<>(listBeanList);
        mRefreshLayout.finishRefresh();
        mRefreshLayout.setNoMoreData(false);
        if (PropertyWithdrawBean.getData().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        withdrawalRecordAdapter.setNewData(PropertyWithdrawBean.getData().getList());
        mRecyclerView.setAdapter(withdrawalRecordAdapter);
    }

    @Override
    public void setPropertyWithdrawDataNull(PropertyWithdrawBean PropertyWithdrawBean) {
        withdrawalRecordAdapter.setNewData(null);
        withdrawalRecordAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh();
        rlNodata.setVisibility(View.VISIBLE);
        if (PropertyWithdrawBean.getData().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    /**
     * 参数有误-去登陆
     *
     * @param msg
     */
    @Override
    public void goLogin(String msg) {
        ToastUtil.ShowToast(msg);
    }

    /**
     * 加载更多回调
     *
     * @param PropertyWithdrawBean
     */
    @Override
    public void setRefreshPropertyWithdrawMoreData(PropertyWithdrawBean PropertyWithdrawBean) {
        for (int i = 0; i < PropertyWithdrawBean.getData().getList().size(); i++) {
            listBeanListDefault.add(PropertyWithdrawBean.getData().getList().get(i));
        }
        iRltime = -1;
        iRlmarket = -1;
        iRlcount = -1;
        sortingImg();
        if (PropertyWithdrawBean.getData().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
        withdrawalRecordAdapter.addData(PropertyWithdrawBean.getData().getList());
        withdrawalRecordAdapter.notifyDataSetChanged();
    }

    /**
     * 加载更多错误回调
     *
     * @param noticeCenterMoreData
     */
    @Override
    public void setRefreshPropertyWithdrawLoadMoreErrer(String noticeCenterMoreData) {
        mRefreshLayout.finishLoadMore();
        ToastUtil.ShowToast(noticeCenterMoreData);
    }

    @Override
    public void refreshErrer() {
        mRefreshLayout.finishRefresh();
    }

    /**
     * 加载
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        propertyWithdrawPresenter = new PropertyWithdrawPresenter(this, RequestState.STATE_LOADMORE);
        propertyWithdrawPresenter.getPropertyWithdrawLoadModule();
    }

    /**
     * 刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        propertyWithdrawPresenter = new PropertyWithdrawPresenter(this, RequestState.STATE_REFRESH);
        propertyWithdrawPresenter.getPropertyWithdrawModule();
    }


    @OnClick({R.id.rl_time, R.id.rl_market, R.id.rl_count})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_time:
                if (iRltime == -1) {
                    iRltime = 0;
                } else if (iRltime == 0) {
                    iRltime = 1;
                } else if (iRltime == 1) {
                    iRltime = -1;
                }
                iRlmarket = -1;
                iRlcount = -1;
                sortingImg();
                sortingTime(iRltime);
                break;
            case R.id.rl_market:
                if (iRlmarket == -1) {
                    iRlmarket = 0;
                } else if (iRlmarket == 0) {
                    iRlmarket = 1;
                } else if (iRlmarket == 1) {
                    iRlmarket = -1;
                }

                iRltime = -1;
                iRlcount = -1;
                sortingImg();
                sortingRlmarket(iRlmarket);
                break;
            case R.id.rl_count:
                if (iRlcount == -1) {
                    iRlcount = 0;
                } else if (iRlcount == 0) {
                    iRlcount = 1;
                } else if (iRlcount == 1) {
                    iRlcount = -1;
                }
                iRltime = -1;
                iRlmarket = -1;
                sortingImg();
                sortingCount(iRlcount);
                break;
        }
    }

    private void sortingImg() {
        if (iRltime == 0) {
            imSortingTime.setImageDrawable(getContext().getResources().getDrawable(R.drawable.sorting_shang_log));
        } else if (iRltime == -1) {
            imSortingTime.setImageDrawable(getContext().getResources().getDrawable(R.drawable.sorting_log));
        } else if (iRltime == 1) {
            imSortingTime.setImageDrawable(getContext().getResources().getDrawable(R.drawable.sorting_xia_log));
        }

        if (iRlmarket == 0) {
            imSortingMarket.setImageDrawable(getContext().getResources().getDrawable(R.drawable.sorting_shang_log));
        } else if (iRlmarket == -1) {
            imSortingMarket.setImageDrawable(getContext().getResources().getDrawable(R.drawable.sorting_log));
        } else if (iRlmarket == 1) {
            imSortingMarket.setImageDrawable(getContext().getResources().getDrawable(R.drawable.sorting_xia_log));
        }

        if (iRlcount == 0) {
            imSortingCount.setImageDrawable(getContext().getResources().getDrawable(R.drawable.sorting_shang_log));
        } else if (iRlcount == -1) {
            imSortingCount.setImageDrawable(getContext().getResources().getDrawable(R.drawable.sorting_log));
        } else if (iRlcount == 1) {
            imSortingCount.setImageDrawable(getContext().getResources().getDrawable(R.drawable.sorting_xia_log));
        }
    }

    /**
     * 时间排序
     *
     * @param iRltime
     */
    public void sortingTime(int iRltime) {
        if (listBeanList == null || listBeanList.size() <= 0) {
            return;
        }
        if (iRltime == 0) {
            withdrawalRecordAdapter.setNewData(SortingUtils.sortDataShangW(listBeanList));
            withdrawalRecordAdapter.notifyDataSetChanged();
        } else if (iRltime == -1) {
            withdrawalRecordAdapter.setNewData(listBeanListDefault);
            withdrawalRecordAdapter.notifyDataSetChanged();
        } else if (iRltime == 1) {
            withdrawalRecordAdapter.setNewData(SortingUtils.sortDataXiaW(listBeanList));
            withdrawalRecordAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 币种排序
     *
     * @param iRlmarket
     */
    public void sortingRlmarket(int iRlmarket) {
        if (listBeanList == null || listBeanList.size() <= 0) {
            return;
        }
        if (iRlmarket == 0) {
            withdrawalRecordAdapter.setNewData(SortingUtils.AZWithdrawSorting(listBeanList));
            withdrawalRecordAdapter.notifyDataSetChanged();
        } else if (iRlmarket == -1) {
            withdrawalRecordAdapter.setNewData(listBeanListDefault);
            withdrawalRecordAdapter.notifyDataSetChanged();
        } else if (iRlmarket == 1) {
            List<PropertyWithdrawBean.DataBean.ListBean> listBeans = SortingUtils.AZWithdrawSorting(listBeanList);
            //list反过来
            Collections.reverse(listBeans);
            withdrawalRecordAdapter.setNewData(listBeans);
            withdrawalRecordAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 数量排序
     *
     * @param iRlcount
     */
    public void sortingCount(int iRlcount) {
        if (listBeanList == null || listBeanList.size() <= 0) {
            return;
        }
        if (iRlcount == 0) {
            withdrawalRecordAdapter.setNewData(SortingUtils.sortCountShangW(listBeanList));
            withdrawalRecordAdapter.notifyDataSetChanged();
        } else if (iRlcount == -1) {
            withdrawalRecordAdapter.setNewData(listBeanListDefault);
            withdrawalRecordAdapter.notifyDataSetChanged();
        } else if (iRlcount == 1) {
            withdrawalRecordAdapter.setNewData(SortingUtils.sortCountXiaW(listBeanList));
            withdrawalRecordAdapter.notifyDataSetChanged();
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
