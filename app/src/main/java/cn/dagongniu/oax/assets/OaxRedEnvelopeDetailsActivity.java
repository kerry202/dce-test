package cn.dagongniu.oax.assets;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.adapter.TakeRedPacketDetailsAdapter;
import cn.dagongniu.oax.assets.bean.TakeRedPacketDetailsBean;
import cn.dagongniu.oax.assets.presenter.TakeRedPacketDetailsPresenter;
import cn.dagongniu.oax.assets.view.CustomShareListener;
import cn.dagongniu.oax.assets.view.ITakeRedPacketDetailsView;
import cn.dagongniu.oax.assets.view.SharePopWind;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.customview.ClassicsFooter;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.customview.MyTradingToolbar;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.language.LanguageUtils;
import cn.dagongniu.oax.utils.AccountValidatorUtil;
import cn.dagongniu.oax.utils.PageToolUtils;
import cn.dagongniu.oax.utils.PermissionsUtils;
import cn.dagongniu.oax.utils.ToastUtil;

/**
 * 大公牛红包详情
 */
public class OaxRedEnvelopeDetailsActivity extends BaseActivity implements ITakeRedPacketDetailsView, OnRefreshListener,
        OnLoadMoreListener, View.OnClickListener {

    @BindView(R.id.assets_toolbar)
    MyTradingToolbar myTradingToolbar;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rl_no_data)
    LinearLayout rlNodata;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    TextView tv_red_count;
    RelativeLayout rl_send_red;
    LinearLayout ll_title_share;
    TextView tv_surplusCoinQty;

    SharePopWind sharePopWind;
    Intent intent;
    int expireFlag;
    int takeFlag;
    int id;
    ClassicsFooter mClassicsFooter;
    ClassicsHeader mClassicsHeader;
    int page = 1;
    TakeRedPacketDetailsPresenter takeRedPacketDetailsPresenter;
    TakeRedPacketDetailsAdapter takeRedPacketDetailsAdapter;
    TakeRedPacketDetailsBean takeRedPacketDetails;
    private UMShareListener mShareListener;

    View inflate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oax_red_envelope_details;
    }

    @Override
    protected void initView() {
        super.initView();
        initToobar();
        initSmartRefresh();
        inflate = LayoutInflater.from(this).inflate(R.layout.oax_red_envelope_details_header_layout, null);
        tv_surplusCoinQty = inflate.findViewById(R.id.tv_surplusCoinQty);
        ll_title_share = inflate.findViewById(R.id.ll_title_share);
        rl_send_red = inflate.findViewById(R.id.rl_send_red);
        rl_send_red.setOnClickListener(this);
        tv_red_count = inflate.findViewById(R.id.tv_red_count);

        mShareListener = new CustomShareListener(this, myEvents, eventBus);
        intent = this.getIntent();
        expireFlag = intent.getIntExtra("expireFlag", -1);//红包是否已过期标识 0表示未过期 1表示已过期
        takeFlag = intent.getIntExtra("takeFlag", -1);//是否已领完标识 0未领取玩 1已领取完
        id = intent.getIntExtra("id", -1);

        if (expireFlag == 0 && takeFlag == 0) {
            ll_title_share.setVisibility(View.VISIBLE);
        } else {
            ll_title_share.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initData() {
        super.initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        takeRedPacketDetailsAdapter = new TakeRedPacketDetailsAdapter(this);
        takeRedPacketDetailsAdapter.addHeaderView(inflate);
        mRecyclerView.setAdapter(takeRedPacketDetailsAdapter);
        takeRedPacketDetailsPresenter = new TakeRedPacketDetailsPresenter(this, RequestState.STATE_REFRESH);
        mRefreshLayout.autoRefresh();

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

        myTradingToolbar.setTitleNameText(R.string.red_oax);

        myTradingToolbar.setRightTvColor(getContext().getResources().getColor(R.color.df_333333));
        myTradingToolbar.setLeftMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getTakeRedPacketDetailsPageIndex() {
        return page;
    }

    @Override
    public int getTakeRedPacketDetailsPageSize() {
        return Constant.PAGESIZE;
    }

    @Override
    public int getTakeRedPacketDetailsId() {
        return id;
    }

    @Override
    public void setTakeRedPacketDetailsData(TakeRedPacketDetailsBean takeRedPacketDetailsData) {
        rlNodata.setVisibility(View.GONE);
        mRefreshLayout.finishRefresh();
        mRefreshLayout.setNoMoreData(false);

        String coinName = takeRedPacketDetailsData.getData().getRedPacketInfo().getCoinName();//币种名称

        setTitleData(takeRedPacketDetailsData, coinName);

        if (takeRedPacketDetailsData.getData().getRedPacketLogDetailsPageInfo().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }

        takeRedPacketDetailsAdapter.setMarketName(coinName);
        takeRedPacketDetailsAdapter.setNewData(takeRedPacketDetailsData.getData().getRedPacketLogDetailsPageInfo().getList());
        mRecyclerView.setAdapter(takeRedPacketDetailsAdapter);

    }

    public void setTitleData(TakeRedPacketDetailsBean takeRedPacketDetailsData, String coinName) {
        this.takeRedPacketDetails = takeRedPacketDetailsData;
        /**
         * 剩余红包币数
         */
        BigDecimal surplusCoinQty = takeRedPacketDetailsData.getData().getRedPacketInfo().getSurplusCoinQty();
        BigDecimal bigDecimal = surplusCoinQty.setScale(8, BigDecimal.ROUND_DOWN);
        tv_surplusCoinQty.setText(bigDecimal.toPlainString() + coinName);

        //说明
        int totalPacketQty = takeRedPacketDetailsData.getData().getRedPacketInfo().getTotalPacketQty();//红包个数
        int grabPacketQty = takeRedPacketDetailsData.getData().getRedPacketInfo().getGrabPacketQty();//红包已领取个数

        BigDecimal totalCoinQty = takeRedPacketDetailsData.getData()
                .getRedPacketInfo().getTotalCoinQty().setScale(8, BigDecimal.ROUND_DOWN);//红包的币种金额
        String totalCoinQtyStr = AccountValidatorUtil.subZeroAndDot(totalCoinQty.toPlainString());

        BigDecimal grabcoinQty = takeRedPacketDetailsData.getData()
                .getRedPacketInfo().getGrabCoinQty().setScale(8, BigDecimal.ROUND_DOWN);//已领取红包金额
        String grabcoinQtyStr = AccountValidatorUtil.subZeroAndDot(grabcoinQty.toPlainString());


        String hint = "";
        if (expireFlag == 0 && takeFlag == 0) {//未领完，未过期
            hint = "已领取" + grabPacketQty + "/" + totalPacketQty + "个红包,共" + grabcoinQtyStr + coinName + "/" + totalCoinQtyStr + coinName;
        } else if (takeFlag == 0 && expireFlag == 1) {//为领完，已过期
            hint = "红包已过期，已领取" + grabPacketQty + "/" + totalPacketQty + "个,共" + grabcoinQtyStr + coinName + "/" + totalCoinQtyStr + coinName;
        } else {//已领完
            hint = totalPacketQty + "个红包共" + totalCoinQtyStr + coinName + ",已被领完";
        }
        tv_red_count.setText(hint);
    }

    @Override
    public void setTakeRedPacketDetailsNull(TakeRedPacketDetailsBean takeRedPacketDetailsNull) {
        takeRedPacketDetailsAdapter.setNewData(null);
        takeRedPacketDetailsAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh();
        rlNodata.setVisibility(View.VISIBLE);
        String coinName = takeRedPacketDetailsNull.getData().getRedPacketInfo().getCoinName();//币种名称
        setTitleData(takeRedPacketDetailsNull, coinName);
        if (takeRedPacketDetailsNull.getData().getRedPacketLogDetailsPageInfo().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void setTakeRedPacketDetailsMoreData(TakeRedPacketDetailsBean takeRedPacketDetailsMoreData) {
        if (takeRedPacketDetailsMoreData.getData().getRedPacketLogDetailsPageInfo().isHasNextPage()) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
        takeRedPacketDetailsAdapter.addData(takeRedPacketDetailsMoreData.getData().getRedPacketLogDetailsPageInfo().getList());
        takeRedPacketDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void setTakeRedPacketDetailsMoreErrer(String noticeCenterMoreData) {
        mRefreshLayout.finishLoadMore();
        ToastUtil.ShowToast(noticeCenterMoreData);
    }

    @Override
    public void setRefreshErrer() {
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        takeRedPacketDetailsPresenter.getTakeRedPacketDetailsLoadModule();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        takeRedPacketDetailsPresenter.getTakeRedPacketDetailsModule();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 分享
     *
     * @param url
     * @param title
     * @param desc
     * @param var1
     */
    public void shareGo(String url, String title, String desc, SHARE_MEDIA var1) {
        sharePopWind.dismiss();
        UMWeb web = new UMWeb(url);
        web.setTitle(title);
        web.setThumb(new UMImage(this, R.drawable.redpacket_icon));
        web.setDescription(desc);
        new ShareAction(this).withMedia(web)
                .setPlatform(var1)
                .setCallback(mShareListener).share();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_send_red:

                if (takeRedPacketDetails == null || takeRedPacketDetails.getData() == null || takeRedPacketDetails.getData().getRedPacketInfo() == null) {
                    return;
                }
                String url = takeRedPacketDetails.getData().getRedPacketInfo().getUrl();
                String title = takeRedPacketDetails.getData().getRedPacketInfo().getTitle();
                String desc = takeRedPacketDetails.getData().getRedPacketInfo().getDesc();

                if (Build.VERSION.SDK_INT >= 23) {
                    String[] mPermissionList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (PermissionsUtils.lacksPermissions(this, mPermissionList)) {
                        ActivityCompat.requestPermissions(this, mPermissionList, 123);
                    }
                }

                sharePopWind = new SharePopWind(this, new SharePopWind.OnShareClickListener() {
                    @Override
                    public void OnShare(SHARE_MEDIA var1) {
                        shareGo(url, title, desc, var1);
                    }
                }, new SharePopWind.OnShareCopyClickListener() {
                    @Override
                    public void OnShareCopy() {
                        TextView tvurl = new TextView(OaxRedEnvelopeDetailsActivity.this);
                        tvurl.setText(title + desc + url);
                        PageToolUtils.CopyText(OaxRedEnvelopeDetailsActivity.this, tvurl);
                    }
                });
                sharePopWind.setAnimationStyle(R.style.PPwin_AnimBottom);
                sharePopWind.setBackgroundDrawable(null);
                sharePopWind.showPop(mRefreshLayout);
                break;
        }
    }
}
