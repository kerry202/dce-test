package cn.dagongniu.oax.assets.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.LoginBean;
import cn.dagongniu.oax.assets.AssetsDetailsActivity;
import cn.dagongniu.oax.assets.RedEnvelopeActivity;
import cn.dagongniu.oax.assets.WithTopRecordActivity;
import cn.dagongniu.oax.assets.WithdTopSearchActivity;
import cn.dagongniu.oax.assets.adapter.MyAssetsAdapter;
import cn.dagongniu.oax.assets.bean.AssetsPropertyListBean;
import cn.dagongniu.oax.assets.presenter.AssetsPropertyListPresenter;
import cn.dagongniu.oax.assets.view.IAssetsPropertyListView;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.constant.UMConstant;
import cn.dagongniu.oax.customview.ClassicsFooter;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.customview.MyTradingToolbar;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.kline.bean.CommitteeBean;
import cn.dagongniu.oax.language.LanguageType;
import cn.dagongniu.oax.language.LanguageUtils;
import cn.dagongniu.oax.language.MultiLanguageUtil;
import cn.dagongniu.oax.main.MainActivity;
import cn.dagongniu.oax.main.bean.NoticeCenterReadDetailBean;
import cn.dagongniu.oax.main.presenter.NoticeCenterReadMorePresenter;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.DialogUtils;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.MyEvents;
import cn.dagongniu.oax.utils.um.UMManager;


/**
 * 资产首页
 */
public class AssetsFragment extends BaseFragment implements View.OnClickListener, IAssetsPropertyListView, OnRefreshListener {

    private static final String TAG = "AssetsFragment";

    @BindView(R.id.assets_toolbar)
    MyTradingToolbar myTradingToolbar;
    @BindView(R.id.assets_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_topup)
    RelativeLayout rlTopup;
    @BindView(R.id.rl_withdrawal)
    RelativeLayout rlWithdrawal;
    @BindView(R.id.tv_assets_btc)
    TextView tvAssetsBtc;
    @BindView(R.id.tv_assets_eth)
    TextView tvAssetsEth;
    @BindView(R.id.tv_assets_cny)
    TextView tvAssetsCny;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.im_open_eyes)
    ImageView imOpenEyes;
    @BindView(R.id.rl_no_data)
    LinearLayout rlNodata;
    @BindView(R.id.rl_red)
    RelativeLayout reRed;
    @BindView(R.id.v_left_red)
    View v_left_red;

    MyAssetsAdapter myAssetsAdapter;
    AssetsPropertyListPresenter assetsPropertyListPresenter;
    Bundle bundle;
    boolean isOpenEyes = true;

    AssetsPropertyListBean assetsPropertyListBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oax_assets_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        initToobar();
        initEyes();
        initSmartRefresh();
        myAssetsAdapter = new MyAssetsAdapter(getContext(), isOpenEyes);
        bundle = new Bundle();
        rlTopup.setOnClickListener(this);
        rlWithdrawal.setOnClickListener(this);
        reRed.setOnClickListener(this);
        assetsPropertyListPresenter = new AssetsPropertyListPresenter(this, RequestState.STATE_REFRESH);

        /**
         * 隐藏红包功能
         */
        reRed.setVisibility(View.GONE);
        v_left_red.setVisibility(View.GONE);


        boolean islogin = (boolean) SPUtils.getParam(getActivity(), SPConstant.LOGIN_STATE, false);
        if (islogin) {
            mRefreshLayout.autoRefresh();
        }

    }

    private void initSmartRefresh() {
        /**
         * 事件处理
         */
        mRefreshLayout.setOnRefreshListener(this);
        imOpenEyes.setOnClickListener(this);
    }

    private void initToobar() {
        myTradingToolbar.setRightImgVisibility(true);
        myTradingToolbar.setLeftImgVisibility(true);
        myTradingToolbar.setTvLeftVisibility(true);
        myTradingToolbar.setSjVisibility(true);
        myTradingToolbar.setRightNameText(R.string.assets_record);
        myTradingToolbar.setTitleNameText(R.string.assets);
        myTradingToolbar.setRightTvColor(getContext().getResources().getColor(R.color.df_gray_666));
        myTradingToolbar.setRightTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //历史记录
                toOtherActivity(WithTopRecordActivity.class);
                UMManager.onEvent(mContext, UMConstant.AssetsFragment, UMConstant.history);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        myAssetsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AssetsPropertyListBean.DataBean.CoinListBean bean = (AssetsPropertyListBean.DataBean.CoinListBean) adapter.getData().get(position);
                bundle.putString("MarketName", bean.getShortName());
                bundle.putString("MarketId", bean.getId() + "");
                openActivity(AssetsDetailsActivity.class, bundle);
            }
        });
    }

    private void initEyes() {
        isOpenEyes = (boolean) SPUtils.getParam(getActivity(), SPConstant.ASSETS_EYES, true);
        if (isOpenEyes) {
            imOpenEyes.setImageDrawable(getContext().getResources().getDrawable(R.drawable.assets_no));
        } else {
            imOpenEyes.setImageDrawable(getContext().getResources().getDrawable(R.drawable.assets_yes));
        }
        setAssetsTopMoney(assetsPropertyListBean);
    }


    @Subscribe
    public void onEvent(MyEvents event) {
        switch (event.status_type) {
            case MyEvents.Withdrawal_Success://提现成功通知
                Logger.e(TAG, event.errmsg);
                mRefreshLayout.autoRefresh();
                break;
            case MyEvents.LoginSuccess://登陆成功通知
            case MyEvents.RedPacketSuccess://发送红包成功通知
                mRefreshLayout.autoRefresh();
                break;
            case MyEvents.ShareSuccess://红包分享成功啦
                DialogUtils.showTextDialog(getActivity(), event.errmsg);
                break;
        }


    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.rl_topup:
                bundle.putString(AppConstants.SEARCH, AppConstants.TOPUP);
                openActivity(WithdTopSearchActivity.class, bundle);
                UMManager.onEvent(mContext, UMConstant.AssetsFragment, UMConstant.TOPUP);
                break;
            case R.id.rl_withdrawal:
                bundle.putString(AppConstants.SEARCH, AppConstants.WITHDRAWAL);
                openActivity(WithdTopSearchActivity.class, bundle);
                UMManager.onEvent(mContext, UMConstant.AssetsFragment, UMConstant.WITHDRAWAL);
                break;
            case R.id.im_open_eyes:
                if (isOpenEyes) {
                    isOpenEyes = false;
                    UMManager.onEvent(mContext, UMConstant.AssetsFragment, UMConstant.hide_Assets);
                } else {
                    isOpenEyes = true;
                    UMManager.onEvent(mContext, UMConstant.AssetsFragment, UMConstant.unhide_Assets);
                }
                myAssetsAdapter.setOpenEyes(isOpenEyes);
                myAssetsAdapter.notifyDataSetChanged();
                SPUtils.setParam(getActivity(), SPConstant.ASSETS_EYES, isOpenEyes);
                initEyes();
                break;
            case R.id.rl_red:
                openActivity(RedEnvelopeActivity.class);
                break;

        }
    }

    @Override
    public String getCoinName() {
        return null;
    }


    /**
     * 设置值
     *
     * @param assetsPropertyListBean
     */
    public void setAssetsTopMoney(AssetsPropertyListBean assetsPropertyListBean) {
        KLog.d("assetsPropertyListBean = " + new Gson().toJson(assetsPropertyListBean));
        if (isOpenEyes) {
            if (assetsPropertyListBean != null) {
                if (assetsPropertyListBean.getData().getTotal() != null) {
                    BigDecimal bigDecimalBtc = new BigDecimal(assetsPropertyListBean.getData().getTotal().getBtcPrice());
                    tvAssetsBtc.setText("≈" + bigDecimalBtc.setScale(8, BigDecimal.ROUND_DOWN).toPlainString() + "BTC");

                    BigDecimal bigDecimalCny = new BigDecimal(assetsPropertyListBean.getData().getTotal().getCnyPrice());
                    tvAssetsCny.setText("≈¥ " + bigDecimalCny.setScale(2, BigDecimal.ROUND_DOWN).toPlainString());

                    BigDecimal bigDecimalEth = new BigDecimal(assetsPropertyListBean.getData().getTotal().getEthPrice());
                    tvAssetsEth.setText("≈" + bigDecimalEth.setScale(6, BigDecimal.ROUND_DOWN).toPlainString() + "ETH");
                    return;
                }
            }
            tvAssetsBtc.setText("≈0.00000000BTC");
            tvAssetsCny.setText("≈¥ 0.00");
            tvAssetsEth.setText("≈0.000000ETH");

        } else {
            tvAssetsBtc.setText("******");
            tvAssetsCny.setText("******");
            tvAssetsEth.setText("******");
        }

    }

    /**
     * 回调数据
     *
     * @param assetsPropertyListBean
     */
    @Override
    public void setIAssetsPropertyData(AssetsPropertyListBean assetsPropertyListBean) {
        rlNodata.setVisibility(View.GONE);
        this.assetsPropertyListBean = assetsPropertyListBean;
        mRefreshLayout.finishRefresh();
        setAssetsTopMoney(assetsPropertyListBean);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAssetsAdapter.setNewData(assetsPropertyListBean.getData().getCoinList());
        mRecyclerView.setAdapter(myAssetsAdapter);
    }

    /**
     * 回调数据 列表为空
     *
     * @param noticeCenterReadDetailBean
     */
    @Override
    public void setIAssetsPropertyDataNull(AssetsPropertyListBean noticeCenterReadDetailBean) {
        this.assetsPropertyListBean = noticeCenterReadDetailBean;
        setAssetsTopMoney(assetsPropertyListBean);
        myAssetsAdapter.setNewData(null);
        myAssetsAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh();
        rlNodata.setVisibility(View.VISIBLE);
    }

    /**
     * 刷新错误
     */
    @Override
    public void refreshErrer() {
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void goLogin(String msg) {
        ToastUtil.ShowToast(msg);
    }


    @Override
    public int getType() {
        return 2;
    }

    /**
     * 刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        Log.e("okgo", "刷新");
        assetsPropertyListPresenter.getAssetsPropertyListModule();
    }


    @Override
    public void onResume() {
        super.onResume();
        UMManager.onResume(mContext, UMConstant.AssetsFragment);
    }

    @Override
    public void onPause() {
        super.onPause();
        UMManager.onPause(mContext, UMConstant.AssetsFragment);
    }
}
