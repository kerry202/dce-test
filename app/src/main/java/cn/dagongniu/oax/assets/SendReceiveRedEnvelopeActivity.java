package cn.dagongniu.oax.assets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.adapter.ReceiveRedAdapter;
import cn.dagongniu.oax.assets.adapter.SendRedPacketAdapter;
import cn.dagongniu.oax.assets.bean.GrabRedPacketRecordBean;
import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.oax.assets.bean.SendRedPacketRecordBean;
import cn.dagongniu.oax.assets.presenter.GrabPacketRecordPresenter;
import cn.dagongniu.oax.assets.presenter.SendRedPacketRecordPresenter;
import cn.dagongniu.oax.assets.view.IGrabRedPacketRecordView;
import cn.dagongniu.oax.assets.view.ISendRedPacketRecordView;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.customview.ClassicsFooter;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.customview.MyTradingToolbar;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.language.LanguageUtils;
import cn.dagongniu.oax.utils.ToastUtil;


/**
 * 收发红包页面 共用
 */
public class SendReceiveRedEnvelopeActivity extends BaseActivity implements ISendRedPacketRecordView, OnRefreshListener,
        OnLoadMoreListener, IGrabRedPacketRecordView {

    @BindView(R.id.assets_toolbar)
    MyTradingToolbar myTradingToolbar;

    View inflate;
    TextView tv_sum;
    TextView tv_sum_cmb;
    TextView tv_sum_hint;
    TextView tv_red_count;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rl_no_data)
    LinearLayout rlNodata;

    ClassicsFooter mClassicsFooter;
    ClassicsHeader mClassicsHeader;

    int redType = -1;// 0 =发出的红包   1=收到的红包
    Intent intent;
    ReceiveRedAdapter receiveRedAdapter;
    SendRedPacketAdapter sendRedPacketAdapter;
    SendRedPacketRecordPresenter sendRedPacketRecordPresenter;
    GrabPacketRecordPresenter grabPacketRecordPresenter;
    int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_receive_red_envelope;
    }

    @Override
    protected void initView() {
        super.initView();
        intent = getIntent();
        redType = intent.getIntExtra("redType", -1);
        inflate = LayoutInflater.from(this).inflate(R.layout.send_receive_red_envelope_header, null);
        tv_sum = inflate.findViewById(R.id.tv_sum);
        tv_sum_cmb = inflate.findViewById(R.id.tv_sum_cmb);
        tv_sum_hint = inflate.findViewById(R.id.tv_sum_hint);
        tv_red_count = inflate.findViewById(R.id.tv_red_count);
        initToobar();
        initSmartRefresh();
        //屏蔽滑动事件
        //TODO NestedScrollView嵌套RecyclerView时滑动不流畅问题的解决办法
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        super.initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (redType == 0) {//发出
            tv_sum.setText(R.string.red_send_sum_set);
            tv_sum_hint.setText(R.string.red_send_sum_hint);
            tv_red_count.setText(R.string.red_send_count);
            sendRedPacketRecordPresenter = new SendRedPacketRecordPresenter(this, RequestState.STATE_REFRESH);
            mRefreshLayout.autoRefresh();
            //发红包的适配器
            sendRedPacketAdapter = new SendRedPacketAdapter(getContext());
            sendRedPacketAdapter.addHeaderView(inflate);
            mRecyclerView.setAdapter(sendRedPacketAdapter);
        } else {//收到
            tv_sum.setText(R.string.red_send_sum_get);
            tv_sum_hint.setText(R.string.red_receive_sum_hint);
            tv_red_count.setText(R.string.red_receive_count);
            grabPacketRecordPresenter = new GrabPacketRecordPresenter(this, RequestState.STATE_REFRESH);
            mRefreshLayout.autoRefresh();
            //领取红包适配器
            receiveRedAdapter = new ReceiveRedAdapter(getContext());
            receiveRedAdapter.addHeaderView(inflate);
            mRecyclerView.setAdapter(receiveRedAdapter);
        }

    }

    private void initSmartRefresh() {
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

    private void initToobar() {

        myTradingToolbar.setRightImgVisibility(true);
        myTradingToolbar.setTvLeftVisibility(true);
        myTradingToolbar.setSjVisibility(true);
        myTradingToolbar.setRightTvVisibility(true);
        if (redType == 0) {
            myTradingToolbar.setTitleNameText(R.string.red_send);
        } else {
            myTradingToolbar.setTitleNameText(R.string.red_receive);
        }

        myTradingToolbar.setRightTvColor(getContext().getResources().getColor(R.color.df_333333));
        myTradingToolbar.setLeftMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    /**
     * 发出红包记录的回调
     *
     * @return
     */
    @Override
    public int getSendRedPacketRecordPageIndex() {
        return page;
    }

    @Override
    public int getSendRedPacketRecordPageSize() {
        return Constant.PAGESIZE;
    }

    /**
     * 发出数据回调
     *
     * @param sendRedPacketRecordData
     */
    @Override
    public void setSendRedPacketRecordData(SendRedPacketRecordBean sendRedPacketRecordData) {

        int size = sendRedPacketRecordData.getData().getPageInfo().getList().size();
        tv_red_count.setText("发出" + size + "个红包");

        tv_sum_cmb.setText("≈¥" + sendRedPacketRecordData.getData().getTotalCNY());
        rlNodata.setVisibility(View.GONE);
        mRefreshLayout.finishRefresh();
        mRefreshLayout.setNoMoreData(false);
        if (sendRedPacketRecordData.getData().getPageInfo().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
        sendRedPacketAdapter.setNewData(sendRedPacketRecordData.getData().getPageInfo().getList());

        mRecyclerView.setAdapter(sendRedPacketAdapter);

        sendRedPacketAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int expireFlag = sendRedPacketRecordData.getData().getPageInfo().getList().get(position).getExpireFlag();
                int takeFlag = sendRedPacketRecordData.getData().getPageInfo().getList().get(position).getTakeFlag();
                int id = sendRedPacketRecordData.getData().getPageInfo().getList().get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("expireFlag", expireFlag);
                bundle.putInt("takeFlag", takeFlag);
                bundle.putInt("id", id);
                openActivity(OaxRedEnvelopeDetailsActivity.class, bundle);
            }
        });
    }

    /**
     * 发出暂无数据
     *
     * @param sendRedPacketRecordData
     */
    @Override
    public void setSendRedPacketRecordNull(SendRedPacketRecordBean sendRedPacketRecordData) {
        sendRedPacketAdapter.setNewData(null);
        sendRedPacketAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh();
        rlNodata.setVisibility(View.VISIBLE);
        if (sendRedPacketRecordData.getData().getPageInfo().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    /**
     * 发出加载更多
     *
     * @param sendRedPacketRecordData
     */
    @Override
    public void setSendRedPacketRecordMoreData(SendRedPacketRecordBean sendRedPacketRecordData) {
        if (sendRedPacketRecordData.getData().getPageInfo().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
        sendRedPacketAdapter.addData(sendRedPacketRecordData.getData().getPageInfo().getList());
        sendRedPacketAdapter.notifyDataSetChanged();

    }

    @Override
    public void setSendRedPacketRecordMoreErrer(String noticeCenterMoreData) {
        mRefreshLayout.finishLoadMore();
        ToastUtil.ShowToast(noticeCenterMoreData);
    }


    @Override
    public int getGrabRedPacketRecordPageIndex() {
        return page;
    }

    @Override
    public int getGrabRedPacketRecordPageSize() {
        return Constant.PAGESIZE;
    }

    /**
     * 领取红包记录的回调
     *
     * @return
     */
    @Override
    public void setGrabRedPacketRecordData(GrabRedPacketRecordBean PropertyWithdrawBean) {

        int size = PropertyWithdrawBean.getData().getGrabRedPacketPageInfo().getList().size();
        tv_red_count.setText("收到" + size + "个红包");

        tv_sum_cmb.setText("≈¥" + PropertyWithdrawBean.getData().getTotalCNY());
        rlNodata.setVisibility(View.GONE);
        mRefreshLayout.finishRefresh();
        mRefreshLayout.setNoMoreData(false);
        if (PropertyWithdrawBean.getData().getGrabRedPacketPageInfo().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
        receiveRedAdapter.setNewData(PropertyWithdrawBean.getData().getGrabRedPacketPageInfo().getList());
        mRecyclerView.setAdapter(receiveRedAdapter);
    }

    /**
     * 发出暂无数据
     *
     * @param grabRedPacketRecordNull
     */
    @Override
    public void setGrabRedPacketRecordNull(GrabRedPacketRecordBean grabRedPacketRecordNull) {
        receiveRedAdapter.setNewData(null);
        receiveRedAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh();
        rlNodata.setVisibility(View.VISIBLE);
        if (grabRedPacketRecordNull.getData().getGrabRedPacketPageInfo().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    /**
     * 发出加载更多
     *
     * @param grabRedPacketRecordMoreData
     */
    @Override
    public void setGrabRedPacketRecordMoreData(GrabRedPacketRecordBean grabRedPacketRecordMoreData) {
        if (grabRedPacketRecordMoreData.getData().getGrabRedPacketPageInfo().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
        receiveRedAdapter.addData(grabRedPacketRecordMoreData.getData().getGrabRedPacketPageInfo().getList());
        receiveRedAdapter.notifyDataSetChanged();
    }

    @Override
    public void setGrabRedPacketRecordMoreErrer(String noticeCenterMoreData) {
        mRefreshLayout.finishLoadMore();
        ToastUtil.ShowToast(noticeCenterMoreData);
    }

    @Override
    public void setRefreshErrer() {
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
        if (redType == 0) {//发出
            sendRedPacketRecordPresenter.getSendRedPacketRecordLoadModule();
        } else {//收到
            grabPacketRecordPresenter.getGrabRedPacketRecordLoadModule();
        }
    }

    /**
     * 刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        if (redType == 0) {//发出
            sendRedPacketRecordPresenter.getSendRedPacketRecordModule();
        } else {//收到
            grabPacketRecordPresenter.getGrabRedPacketRecordModule();
        }
    }
}
