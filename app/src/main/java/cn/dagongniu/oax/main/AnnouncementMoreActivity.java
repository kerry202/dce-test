package cn.dagongniu.oax.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.customview.ClassicsFooter;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.customview.MyTradingToolbar;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.language.LanguageUtils;
import cn.dagongniu.oax.main.adapter.AnnouncementMoreAdapter;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.bean.NoticeCenterMoreBean;
import cn.dagongniu.oax.main.presenter.NoticeCenterReadMorePresenter;
import cn.dagongniu.oax.main.view.INoticeCenterMorelView;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.ToastUtil;

/**
 * 公告详情
 */
public class AnnouncementMoreActivity extends BaseActivity implements INoticeCenterMorelView, OnRefreshListener,
        OnLoadMoreListener {

    private static final String TAG = "AnnouncementMoreActivit";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.web_toolbar)
    MyTradingToolbar tradingToolbar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    ClassicsFooter mClassicsFooter;
    ClassicsHeader mClassicsHeader;

    Intent intent;
    String ArticleType = "";
    AnnouncementMoreAdapter announcementMoreAdapter;
    NoticeCenterReadMorePresenter noticeCenterReadMorePresenter;
    int page = 1;
    Bundle bundle = new Bundle();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_announcement_more;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolber();
        noticeCenterReadMorePresenter = new NoticeCenterReadMorePresenter(this, RequestState.STATE_REFRESH);
        mRefreshLayout.autoRefresh();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        announcementMoreAdapter = new AnnouncementMoreAdapter();
        mRecyclerView.setAdapter(announcementMoreAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        intent = this.getIntent();
        ArticleType = intent.getStringExtra("ArticleType");

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<NoticeCenterMoreBean.DataBean.ListBean> listBeans = announcementMoreAdapter.getData();
                bundle.putBoolean("More", false);
                bundle.putString("ArticleID", listBeans.get(position).getId() + "");
                bundle.putString("ArticleType", listBeans.get(position).getType() + "");
                openActivity(WebActivity.class, bundle);
            }
        });

        announcementMoreAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

    }

    /**
     * 设置头部
     */
    private void initToolber() {
        tradingToolbar.setTitleNameText(R.string.announcement_tz);
        tradingToolbar.setRightImgVisibility(true);
        tradingToolbar.setRightTvVisibility(true);
        tradingToolbar.setRightTvColor(getContext().getResources().getColor(R.color.df_font));
        tradingToolbar.setLeftMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**
         * 事件处理
         */
        mClassicsFooter = (ClassicsFooter) mRefreshLayout.getRefreshFooter();
        mClassicsHeader = (ClassicsHeader) mRefreshLayout.getRefreshHeader();
        LanguageUtils.setFooterLanguage(mClassicsFooter, this);
        LanguageUtils.setHeaderLanguage(mClassicsHeader, this);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    public String getNoticeCenterMoreType() {
        return ArticleType;
    }

    @Override
    public int getNoticeCenterPageIndex() {
        return page;
    }

    @Override
    public int getNoticeCenterPageSize() {
        return Constant.PAGESIZE;
    }

    /**
     * 查看更多 回调
     *
     * @param noticeCenterMoreData
     */
    @Override
    public void setNoticeCenterMoreData(NoticeCenterMoreBean noticeCenterMoreData) {
        if (noticeCenterMoreData.getData().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
        mRecyclerView.setAdapter(announcementMoreAdapter);
        announcementMoreAdapter.setNewData(noticeCenterMoreData.getData().getList());
    }

    @Override
    public void setRefreshNoticeCenterMoreData(NoticeCenterMoreBean noticeCenterMoreData) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.setNoMoreData(false);
        if (noticeCenterMoreData.getData().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
        announcementMoreAdapter.setNewData(noticeCenterMoreData.getData().getList());

        announcementMoreAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRefreshNoticeCenterMoreErrer(String noticeCenterMoreData) {
        Logger.e(TAG, "公告查看刷新回调 刷新错误");
        mRefreshLayout.finishRefresh();
        ToastUtil.ShowToast(noticeCenterMoreData);
    }

    @Override
    public void setRefreshNoticeCenterLoadMoreData(NoticeCenterMoreBean noticeCenterMoreData) {

        if (noticeCenterMoreData.getData().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
        announcementMoreAdapter.addData(noticeCenterMoreData.getData().getList());
        announcementMoreAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRefreshNoticeCenterLoadMoreErrer(String noticeCenterMoreData) {
        mRefreshLayout.finishLoadMore();
        ToastUtil.ShowToast(noticeCenterMoreData);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        noticeCenterReadMorePresenter = new NoticeCenterReadMorePresenter(this, RequestState.STATE_LOADMORE);
        noticeCenterReadMorePresenter.getNoticeCenterReadMoreLoadModule();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        Log.e("okgo", "刷新");
        page = 1;
        noticeCenterReadMorePresenter = new NoticeCenterReadMorePresenter(this, RequestState.STATE_REFRESH);
        noticeCenterReadMorePresenter.getNoticeCenterReadMoreRefreshModule();
    }


}
