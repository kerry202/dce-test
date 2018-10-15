package cn.dagongniu.oax.trading.fragment;


import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;
import com.umeng.commonsdk.debug.E;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.LoginActivity;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.constant.UMConstant;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.customview.TradingFragmentToolbar;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.UrlParams;
import cn.dagongniu.oax.kline.adapter.CommitteeAdapter;
import cn.dagongniu.oax.kline.bean.TradingInfoBean;
import cn.dagongniu.oax.kline.fragment.CommitteeIView;
import cn.dagongniu.oax.kline.presenter.CommitteePresenter;
import cn.dagongniu.oax.kline.presenter.KLineActivityPresenter;
import cn.dagongniu.oax.kline.view.TabEntity;
import cn.dagongniu.oax.language.LanguageUtils;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.presenter.IndexPagePresenter;
import cn.dagongniu.oax.main.view.IndexPageView;
import cn.dagongniu.oax.trading.EntrustOrderActivity;
import cn.dagongniu.oax.trading.MarketChooseActivity;
import cn.dagongniu.oax.trading.bean.CurrentEntrustBean;
import cn.dagongniu.oax.trading.bean.EntrustInfoBean;
import cn.dagongniu.oax.trading.bean.TradeListAndMarketOrdersBean;
import cn.dagongniu.oax.trading.presenter.EntrustPresenter;
import cn.dagongniu.oax.utils.AppManager;
import cn.dagongniu.oax.utils.Logs;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.ViewUtils;
import cn.dagongniu.oax.utils.events.KLineBuyOrSellEvent;
import cn.dagongniu.oax.utils.events.MyEvents;
import cn.dagongniu.oax.utils.events.RefreshHomeFragmentEvent;
import cn.dagongniu.oax.utils.events.SelectTradingCoinEvent;
import cn.dagongniu.oax.utils.um.UMManager;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;

/**
 * 交易首页
 * <p>
 * 已在 TabView 的 initTabChildView() 中预加载
 */
public class OaxTradingFragment extends BaseFragment implements OaxTradingIView, CommitteeIView, EntrustIView, IndexPageView, OnRefreshListener {

    @BindView(R.id.mytrading_toolbar)
    TradingFragmentToolbar mytradingToolbar;
    @BindView(R.id.buy_and_sell_tab_layout)
    CommonTabLayout buyAndSellTabLayout;
    @BindView(R.id.bt_trading_buy_sell)
    Button btTradingBuySell;
    @BindView(R.id.buy_recyclerview)
    RecyclerView buyRecyclerview;
    @BindView(R.id.sell_recyclerview)
    RecyclerView sellRecyclerview;
    @BindView(R.id.tv_trading_price)
    TextView tvTradingPrice;//最新成交价
    @BindView(R.id.tv_rmb_price)
    TextView tvRmbPrice;//人民币价格
    @BindView(R.id.tv_trading_limit)
    TextView tvTradingLimit;//涨跌幅
    @BindView(R.id.tv_buy_or_sell)
    TextView tvBuyOrSell;
    @BindView(R.id.et_buy_sell_price)
    EditText etBuySellPrice;
    @BindView(R.id.iv_price_minus)
    ImageView ivPriceMinus;
    @BindView(R.id.iv_price_add)
    ImageView ivPriceAdd;
    @BindView(R.id.et_buy_sell_volume)
    EditText etBuySellVolume;
    @BindView(R.id.iv_volume_minus)
    ImageView ivVolumeMinus;
    @BindView(R.id.iv_volume_add)
    ImageView ivVolumeAdd;
    @BindView(R.id.RadioButton25)
    RadioButton RadioButton25;
    @BindView(R.id.RadioButton50)
    RadioButton RadioButton50;
    @BindView(R.id.RadioButton75)
    RadioButton RadioButton75;
    @BindView(R.id.RadioButton100)
    RadioButton RadioButton100;
    @BindView(R.id.tv_total_prices)
    TextView tvTotalPrices;
    @BindView(R.id.tv_feeRate)
    TextView tvFeeRate;
    @BindView(R.id.tv_available)
    TextView tvAvailable;
    @BindView(R.id.RadioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    private KLineActivityPresenter mKLinePresenter;
    private CommitteePresenter mCommitteePresenter;
    private IndexPagePresenter indexPagePresenter;
    private EntrustPresenter mEntrustPresenter;

    private CommitteeAdapter mBuyAdapter;
    private CommitteeAdapter mSellAdapter;
    private int mMarketId = -1;
    private int minType = 60;
    private TradingInfoBean.AllMaketListBean.MarketListBean currentCoin = null;
    private double mPricePlusOrMinusLimit = 0.001;
    private double mVolumePlusOrMinusLimit = 0.001;
    private EntrustInfoBean mEntrustInfoBean;//余额 委托列表等
    private int mQtyDecimals = 4;//数量精度
    private int mPriceDecimals = 8;//价格精度
    private String mEtBuyPriceText = "";
    private String mEtBuyVolumeText = "";
    private String mEtSellPriceText = "";
    private String mEtSellVolumeText = "";
    private boolean mIsBuy = true;
    private int checkBuyId = 0;
    private int checkSellId = 0;

    private int beforeDot = 20;

    ClassicsHeader mClassicsHeader;
    private boolean isRefreshError = false;
    private int mGreen;
    private int mGray;
    private int mRed;
    private int delay = 10000;
    private int msg_what = 101;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            topicTradeList();
            mHandler.removeMessages(msg_what);
            mHandler.sendEmptyMessageDelayed(msg_what, delay);
        }
    };
    private boolean mIsBackgroundOrUnScreenOn;
    private boolean mIsTopicTradeList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oax_trading_frament;
    }

    @Override
    protected void initView() {
        super.initView();
        KLog.d("OaxTradingFragment initView");
        mGreen = getResources().getColor(R.color.kline_buy_bg);
        mGray = getResources().getColor(R.color.df_gray_666);
        mRed = getResources().getColor(R.color.kline_sell_bg);
        //尺寸拉伸
        mClassicsHeader = (ClassicsHeader) mRefreshLayout.getRefreshHeader();
        mClassicsHeader.setSpinnerStyle(SpinnerStyle.Scale);
        mRefreshLayout.setOnRefreshListener(this);
        LanguageUtils.setHeaderLanguage(mClassicsHeader, getActivity());
        buyRecyclerview.setLayoutManager(new LinearLayoutManager(mContext) {

            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });
        buyRecyclerview.setNestedScrollingEnabled(false);

        sellRecyclerview.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });
        sellRecyclerview.setNestedScrollingEnabled(false);
        initToolbar();
        initBuyAdapter();
        initSellAdapter();

        initPriceWatcher();
        initVolumeWatcher();
        initTabLayout();
    }

    private void initTabLayout() {
        buyAndSellTabLayout.setIndicatorColor(getResources().getColor(R.color.df_F8BE13));
        buyAndSellTabLayout.setTextSelectColor(getResources().getColor(R.color.df_333333));
        buyAndSellTabLayout.setTextUnselectColor(getResources().getColor(R.color.df_9B9B9B));
        buyAndSellTabLayout.setIndicatorWidth(getResources().getDimension(R.dimen.d30));
        ArrayList<CustomTabEntity> list = new ArrayList();
        final String[] timeSelectIndicatorTitles = {getResources().getString(R.string.buy), getResources().getString(R.string.sell)};
        for (int i = 0; i < timeSelectIndicatorTitles.length; i++) {
            list.add(new TabEntity(timeSelectIndicatorTitles[i], 0, 0));
        }
        buyAndSellTabLayout.setTabData(list);
        buyAndSellTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                setTabLayoutSelect(position);
            }

            @Override
            public void onTabReselect(int position) {
                setTabLayoutSelect(position);
            }
        });
    }

    private void setTabLayoutSelect(int position) {
        if (position == 0) {
            mIsBuy = true;
            setBuySellVolumeText(mEtBuyVolumeText);
            setEtBuyPriceText(mEtBuyPriceText);
            setRightAvailable();
            radioGroup.clearCheck();
            if (checkBuyId != 0) {
                radioGroup.check(checkBuyId);
            }
            btTradingBuySell.setBackgroundResource(R.color.button_bu);
            btTradingBuySell.setText(R.string.buy);
        } else if (position == 1) {
            mIsBuy = false;
            setBuySellVolumeText(mEtSellVolumeText);
            setEtBuyPriceText(mEtSellPriceText);
            setLeftAvailable();
            radioGroup.clearCheck();
            if (checkSellId != 0) {
                radioGroup.check(checkSellId);
            }
            btTradingBuySell.setBackgroundResource(R.color.df_E17F5C);
            btTradingBuySell.setText(R.string.sell);
        }
    }

    private void initToolbar() {
        mytradingToolbar.setLeftImg(ContextCompat.getDrawable(mContext, R.mipmap.toolbar_trading_right));
        mytradingToolbar.setRightImgVisibility(true);
        mytradingToolbar.setLeftImgVisibility(true);
        mytradingToolbar.setRightNameText(R.string.entrust_order);
        mytradingToolbar.setLeftTvText(R.string.trding_k_line);
        mytradingToolbar.setSjVisibility(false);
        mytradingToolbar.setNameClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOtherActivity(MarketChooseActivity.class);
                UMManager.onEvent(mContext, UMConstant.OaxTradingFragment, UMConstant.select_market);
            }
        });
        mytradingToolbar.setTvLeftVisibility(false);
        mytradingToolbar.setRightTvColor(getContext().getResources().getColor(R.color.df_theme));
        mytradingToolbar.setLeftTvColor(getContext().getResources().getColor(R.color.df_theme));


        mytradingToolbar.setRightTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = (String) SPUtils.getParamString(mContext, SPConstant.USER_ID, null);
                if (userId != null) {
                    //委托 订单
                    HashMap<String, Object> map = new HashMap<>();
                    map.put(UrlParams.marketId, mMarketId);
                    SkipActivityUtil.skipAnotherActivity(getActivity(), EntrustOrderActivity.class, map, false);
                } else {
                    SkipActivityUtil.skipAnotherActivity(mContext, LoginActivity.class);
                }
                UMManager.onEvent(mContext, UMConstant.OaxTradingFragment, UMConstant.Entrust_orders);
            }
        });
        mytradingToolbar.setLeftTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //K线
                SkipActivityUtil.skipToKLineActivity(mMarketId, minType, getActivity());
                UMManager.onEvent(mContext, UMConstant.OaxTradingFragment, UMConstant.kline);
            }
        });

    }

    private void initBuyAdapter() {
        mBuyAdapter = new CommitteeAdapter(mContext, CommitteeAdapter.TYPE_BUY);
        mBuyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        buyRecyclerview.setAdapter(mBuyAdapter);
        buyRecyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                TradeListAndMarketOrdersBean.BuyOrSellListBean bean = (TradeListAndMarketOrdersBean.BuyOrSellListBean) adapter.getItem(position);
                BigDecimal decimal = bean.getPrice();
                if (decimal != null) {
                    String price = decimal.setScale(mPriceDecimals, BigDecimal.ROUND_DOWN).toPlainString();
                    etBuySellPrice.setText(price);
                }

            }
        });
    }

    private void initSellAdapter() {
        mSellAdapter = new CommitteeAdapter(mContext, CommitteeAdapter.TYPE_SELL);
        mSellAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        sellRecyclerview.setAdapter(mSellAdapter);
        sellRecyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                TradeListAndMarketOrdersBean.BuyOrSellListBean bean = (TradeListAndMarketOrdersBean.BuyOrSellListBean) adapter.getItem(position);
                BigDecimal decimal = bean.getPrice();
                if (decimal != null) {
                    String price = decimal.setScale(mPriceDecimals, BigDecimal.ROUND_DOWN).toPlainString();
                    etBuySellPrice.setText(price);
                }

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        indexPagePresenter = new IndexPagePresenter(this, RequestState.STATE_REFRESH);
        indexPagePresenter.getIndexPageModule();
    }

    private void updateCurrentCoin() {
        IndexPageBean.DataBean.AllMaketListBean.MarketListBean marketListBean = OAXApplication.coinsInfoMap.get(mMarketId);
        if (marketListBean != null) {
            if (currentCoin == null) {
                currentCoin = new TradingInfoBean.AllMaketListBean.MarketListBean();
            }
            currentCoin.setCnyPrice(marketListBean.getCnyPrice());
            currentCoin.setCoinId(marketListBean.getCoinId());
            currentCoin.setCoinName(marketListBean.getCoinName());
            currentCoin.setIncRate(marketListBean.getIncRate());
            currentCoin.setLastTradePrice(marketListBean.getLastTradePrice());
            currentCoin.setMarketCoinId(marketListBean.getMarketCoinId());
            currentCoin.setMarketCoinName(marketListBean.getMarketCoinName());
            currentCoin.setMarketId(marketListBean.getMarketId());
            currentCoin.setMaxPrice(marketListBean.getMaxPrice());
            currentCoin.setMinPrice(marketListBean.getMinPrice());
            currentCoin.setPriceDecimals(marketListBean.getPriceDecimals());
            currentCoin.setQtyDecimals(marketListBean.getQtyDecimals());
            currentCoin.setTotalAmount(marketListBean.getTotalAmount());
            currentCoin.setTradeQty(marketListBean.getTradeQty());
            mQtyDecimals = currentCoin.getQtyDecimals();
            mPriceDecimals = currentCoin.getPriceDecimals();
            KLog.d("currentCoin = " + new Gson().toJson(currentCoin));
        }
    }

    private void getData() {
        //基本数据
        HashMap<String, Object> map = new HashMap<>();
        map.put(UrlParams.marketId, mMarketId);
        map.put(UrlParams.minType, minType);
        mKLinePresenter = new KLineActivityPresenter(this);
        mKLinePresenter.getData(map, RequestState.STATE_REFRESH);
        //个人数据
        String userId = (String) SPUtils.getParamString(mContext, SPConstant.USER_ID, null);
        if (userId != null) {
            if (mEntrustPresenter == null) {
                mEntrustPresenter = new EntrustPresenter(this);
            }
            mEntrustPresenter.getEntrustInfo(mMarketId, RequestState.STATE_REFRESH);//接口获取 用户余额 订单信息
            mEntrustPresenter.topicTradeList(mMarketId, userId);//订阅 用户余额 订单信息
            mIsTopicTradeList = true;
            mHandler.sendEmptyMessageDelayed(msg_what, delay);
        }
        initBuyAndSellWebSocket();
    }

    private void initBuyAndSellWebSocket() {
        //webSocket数据
        if (mCommitteePresenter == null) {
            mCommitteePresenter = new CommitteePresenter(this);
        }
        mCommitteePresenter.getTradeListAndMarketOrders(mMarketId + "");//买卖列表
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar
                .titleBar(mytradingToolbar)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    public void setTradingInfoData(CommonJsonToBean<TradingInfoBean> data) {
        KLog.d("setTradingInfoData  = " + new Gson().toJson(data));
        //设置买卖列表 费率
        if (data != null) {
            TradingInfoBean.MarketOrdersMapBean bean = data.getData().getMarketOrdersMap();
            String feeRate = data.getData().getFeeRate();
            KLog.d("feeRate = " + feeRate);
            if (!TextUtils.isEmpty(feeRate)) {
                if (tvFeeRate == null) {
                    return;
                }
                try {
                    BigDecimal decimal = new BigDecimal(feeRate);
                    BigDecimal h = new BigDecimal(100);
                    tvFeeRate.setText(subZeroAndDot(decimal.multiply(h).toPlainString()) + "%");
                } catch (Exception e) {
                    tvFeeRate.setText("");
                }
            } else {
                if (tvFeeRate != null) {
                    tvFeeRate.setText("");
                }
            }
            if (bean != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<TradeListAndMarketOrdersBean.BuyOrSellListBean>>() {
                }.getType();
                List<TradeListAndMarketOrdersBean.BuyOrSellListBean> sellList = null;
                List<TradeListAndMarketOrdersBean.BuyOrSellListBean> buyList = null;
                try {
                    sellList = gson.fromJson(new Gson().toJson(bean.getSellList()), type);
                    buyList = gson.fromJson(new Gson().toJson(bean.getBuyList()), type);
                } catch (Exception e) {
                    KLog.d("setTradingInfoData Exception = " + e.getMessage());
                }

                mBuyAdapter.isEmptyData(false);
                mSellAdapter.isEmptyData(false);

                mBuyAdapter.setNewData(buyList);

                if (sellList != null && sellList.size() > 1) {
                    Collections.reverse(sellList);
                }
                mSellAdapter.setNewData(sellList);
            }
        }
        //确定当前的交易对
        List<TradingInfoBean.AllMaketListBean> allMaketList = data.getData().getAllMaketList();
        if (allMaketList != null) {
            for (int i = 0; i < allMaketList.size(); i++) {
                TradingInfoBean.AllMaketListBean allMaketListBean = allMaketList.get(i);
                List<TradingInfoBean.AllMaketListBean.MarketListBean> marketList = allMaketListBean.getMarketList();
                if (marketList != null) {
                    for (int j = 0; j < marketList.size(); j++) {
                        TradingInfoBean.AllMaketListBean.MarketListBean marketListBean = marketList.get(j);
                        if (mMarketId == marketListBean.getMarketId()) {
                            currentCoin = marketListBean;
                            KLog.d("currentCoin = " + new Gson().toJson(currentCoin));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setBuyOrSellState(CommonJsonToBean<String> data) {
        KLog.d("setBuyOrSellState = " + data.toJson(String.class));
        ToastUtil.ShowToast(data.getMsg(), getActivity());
        if (data.getSuccess()) {
            etBuySellVolume.setText("");
            etBuySellPrice.setText("");
            mEtBuyPriceText = "";
            mEtBuyVolumeText = "";
            mEtSellPriceText = "";
            mEtSellVolumeText = "";
        }
        topicTradeList();
    }

    @Override
    public void collectMarketState(CommonJsonToBean<String> data) {

    }

    @Override
    public void cancelCollectMarket(CommonJsonToBean<String> data) {

    }

    @Override
    public void setTopicKlineData(List<TradingInfoBean.KlineListBean> data) {

    }

    /**
     * 点对点获取 余额 订单信息
     */
    private void topicTradeList() {
        if (!mIsTopicTradeList) {
            return;
        }
        String userId = (String) SPUtils.getParamString(mContext, SPConstant.USER_ID, null);
        if (userId != null) {
            mEntrustPresenter.sendTradeList(mMarketId, Integer.parseInt(userId));
        }
    }

    @Override
    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        ToastUtil.ShowToast(msg, getActivity());
    }

    /**
     * 买卖列表
     */
    @Override
    public void onNewCommitteeList(TradeListAndMarketOrdersBean bean) {
        KLog.d("onNewCommitteeList" + new Gson().toJson(bean));
        if (bean != null) {
            List<TradeListAndMarketOrdersBean.BuyOrSellListBean> buyList = bean.getBuyList();
            List<TradeListAndMarketOrdersBean.BuyOrSellListBean> sellList = bean.getSellList();
            mBuyAdapter.isEmptyData(false);
            mSellAdapter.isEmptyData(false);
            //刷新买卖列表
            mBuyAdapter.setNewData(buyList);
            if (sellList != null && sellList.size() > 1) {
                Collections.reverse(sellList);
            }
            mSellAdapter.setNewData(sellList);
        }
    }


    private void setCurrentCoin() {
        updateCurrentCoin();
        if (currentCoin != null) {
            mytradingToolbar.setTitleNameText(currentCoin.getCoinName() + "/" + currentCoin.getMarketCoinName());
            mBuyAdapter.setDecimals(mPriceDecimals, mQtyDecimals);
            mSellAdapter.setDecimals(mPriceDecimals, mQtyDecimals);
            //设置价格变化
            setPriceChange();
//            updateLastCoin();
            changBuyOrSellInfo();
        }
        getData();
        setNoAvailable();
    }

    private void changBuyOrSellInfo() {
        KLog.d("changBuyOrSellInfo = " + currentCoin.getLastTradePrice());
        BigDecimal lastTradePrice = new BigDecimal(currentCoin.getLastTradePrice()).setScale(currentCoin.getPriceDecimals(), BigDecimal.ROUND_DOWN);
        String s = lastTradePrice.toPlainString();
        confirmPricePlusOrMinusLimit();
        confirmVolumePlusOrMinusLimit();
        etBuySellPrice.setHint(getResources().getString(R.string.unit_price) + "(" + currentCoin.getMarketCoinName() + ")");
        setEtBuyPriceText(s);
        etBuySellVolume.setHint(getResources().getString(R.string.committee_amount) + "(" + currentCoin.getCoinName() + ")");
    }

    /**
     * 确定数量的加减幅度
     */
    private void confirmVolumePlusOrMinusLimit() {
        String s = "0.";
        int qtyDecimals = mQtyDecimals;
        for (int i = 0; i < qtyDecimals; i++) {
            if (i == qtyDecimals - 1) {
                s = s + "1";
            } else {
                s = s + "0";
            }
        }
        try {
            mVolumePlusOrMinusLimit = new BigDecimal(s).doubleValue();
        } catch (Exception e) {
        }
        KLog.d("mVolumePlusOrMinusLimit = " + mVolumePlusOrMinusLimit);
    }

    /**
     * 确定价格的加减幅度
     */
    private void confirmPricePlusOrMinusLimit() {
        int priceDecimal = currentCoin.getPriceDecimals();
        String s = "0.";
        for (int i = 0; i < priceDecimal; i++) {
            if (i == priceDecimal - 1) {
                s = s + "1";
            } else {
                s = s + "0";
            }
        }
        try {
            mPricePlusOrMinusLimit = new BigDecimal(s).doubleValue();
        } catch (Exception e) {
        }

        KLog.d("mPricePlusOrMinusLimit = " + mPricePlusOrMinusLimit);
    }

    private void setPriceChange() {
        //最近成交价
        try {
            BigDecimal lastTradePrice = new BigDecimal(currentCoin.getLastTradePrice());
            BigDecimal decimal = lastTradePrice.setScale(currentCoin.getPriceDecimals(), BigDecimal.ROUND_DOWN);
            tvTradingPrice.setText(decimal.toPlainString());
        } catch (Exception e) {

        }

        //人民币价格
        try {
            BigDecimal cny = currentCoin.getCnyPrice().multiply(new BigDecimal(currentCoin.getLastTradePrice()))
                    .setScale(2, BigDecimal.ROUND_DOWN);
            KLog.d("CnyPrice = " + currentCoin.getCnyPrice().multiply(new BigDecimal(currentCoin.getLastTradePrice())).toPlainString());
            tvRmbPrice.setText("≈¥" + cny.toPlainString());
        } catch (Exception e) {

        }

        //涨跌幅
        double incRate = 0;
        try {
            incRate = Double.parseDouble(currentCoin.getIncRate());
            BigDecimal incRateBigDecimal = new BigDecimal(currentCoin.getIncRate()).setScale(2, BigDecimal.ROUND_DOWN);
            if (incRate > 0) {
                tvTradingLimit.setText("+" + incRateBigDecimal.toPlainString() + "%");
            } else {
                tvTradingLimit.setText(incRateBigDecimal.toPlainString() + "%");
            }
        } catch (Exception e) {
        }
        if (incRate > 0) {
            tvTradingPrice.setTextColor(mGreen);
            tvTradingLimit.setTextColor(mGreen);
        } else if (incRate == 0) {
            tvTradingPrice.setTextColor(mGray);
            tvTradingLimit.setTextColor(mGray);
        } else {
            tvTradingPrice.setTextColor(mRed);
            tvTradingLimit.setTextColor(mRed);
        }
    }

    /**
     * 去掉小数后的0
     *
     * @param s
     * @return
     */
    public String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    private void showNoDefaultId() {
        ViewUtils.showKProgressHUD(mContext, R.drawable.errer_icon, R.string.network_request_failed);
    }

    @OnClick({R.id.bt_trading_buy_sell, R.id.tv_left_name, R.id.iv_price_minus, R.id.iv_price_add, R.id.iv_volume_minus, R.id.iv_volume_add, R.id.RadioButton25, R.id.RadioButton50, R.id.RadioButton75, R.id.RadioButton100})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_trading_buy_sell:
                String userId = (String) SPUtils.getParamString(mContext, SPConstant.USER_ID, null);
                Logs.s("     userId    "+userId);
                if (userId != null) {
                    checkBuyOrSellData();
                } else {
                    SkipActivityUtil.skipAnotherActivity(mContext, LoginActivity.class);
                }
                if (mIsBuy) {
                    UMManager.onEvent(mContext, UMConstant.OaxTradingFragment, UMConstant.buy);
                } else {
                    UMManager.onEvent(mContext, UMConstant.OaxTradingFragment, UMConstant.sell);
                }
                break;
            case R.id.iv_price_minus:
                priceMinus();
                break;
            case R.id.iv_price_add:
                priceAdd();
                break;
            case R.id.iv_volume_minus:
                volumeMinus();
                break;
            case R.id.iv_volume_add:
                volumeAdd();
                break;
            case R.id.RadioButton25:
                setVolumeRate("0.25", R.id.RadioButton25);
                UMManager.onEvent(mContext, UMConstant.OaxTradingFragment, UMConstant.percent_25);
                break;
            case R.id.RadioButton50:
                setVolumeRate("0.50", R.id.RadioButton50);
                UMManager.onEvent(mContext, UMConstant.OaxTradingFragment, UMConstant.percent_50);
                break;
            case R.id.RadioButton75:
                setVolumeRate("0.75", R.id.RadioButton75);
                UMManager.onEvent(mContext, UMConstant.OaxTradingFragment, UMConstant.percent_75);
                break;
            case R.id.RadioButton100:
                setVolumeRate("1.00", R.id.RadioButton100);
                UMManager.onEvent(mContext, UMConstant.OaxTradingFragment, UMConstant.percent_100);
                break;
        }
    }

    private void checkBuyOrSellData() {
        KLog.d("checkBuyOrSellData");
        String price = etBuySellPrice.getText().toString();
        if (TextUtils.isEmpty(price)) {
            ToastUtil.ShowToast(getResources().getString(R.string.please_input_price), getActivity());
            return;
        }
        String volume = etBuySellVolume.getText().toString();
        if (TextUtils.isEmpty(volume)) {
            ToastUtil.ShowToast(getResources().getString(R.string.please_input_volume), getActivity());
            return;
        }
        BigDecimal priceDecimal = new BigDecimal(price);
        KLog.d("checkBuyOrSellData priceDecimal = " + priceDecimal.toPlainString());
        if (priceDecimal.compareTo(BigDecimal.ZERO) == 0) {
            ToastUtil.ShowToast(getResources().getString(R.string.price_can_not_be_zero), getActivity());
            return;
        }
        BigDecimal volumeDecimal = new BigDecimal(volume);
        KLog.d("checkBuyOrSellData priceDecimal = " + volumeDecimal.toPlainString());
        if (volumeDecimal.compareTo(BigDecimal.ZERO) == 0) {
            ToastUtil.ShowToast(getResources().getString(R.string.volume_can_not_be_zero), getActivity());
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put(UrlParams.marketId, mMarketId);
        map.put(UrlParams.price, price);
        map.put(UrlParams.qty, volume);

        Logs.s("    交易参数  mMarketId  :   "+mMarketId);
        Logs.s("    交易参数  price  :   "+price);
        Logs.s("    交易参数  volume  :   "+volume);

        if (mIsBuy) {
            map.put(UrlParams.type, "1");
        } else {
            map.put(UrlParams.type, "2");
        }
        mKLinePresenter.sendBuyOrSellData(map, RequestState.STATE_DIALOG);
    }

    private void setVolumeRate(String rate, int id) {
        if (mIsBuy) {
            checkBuyId = id;
        } else {
            checkSellId = id;
        }
        if (mEntrustInfoBean != null) {
            BigDecimal accountDecimal = null;
            if (mIsBuy) {
                try {
                    accountDecimal = mEntrustInfoBean.getCoinBalance().getRightCoinBalance().divide(new BigDecimal(etBuySellPrice.getText().toString()), mPriceDecimals, BigDecimal.ROUND_DOWN);
                } catch (Exception e) {
                    KLog.d("accountDecimal = " + e.getMessage());
                    accountDecimal = new BigDecimal("0");
                }
            } else {
                accountDecimal = mEntrustInfoBean.getCoinBalance().getLeftCoinBalance();
            }
            BigDecimal rateDecimal = new BigDecimal(rate);

            String s = accountDecimal.multiply(rateDecimal).setScale(mQtyDecimals, BigDecimal.ROUND_DOWN).toPlainString();
            setBuySellVolumeText(s);
        }
    }

    private void setBuySellVolumeText(String s) {
        etBuySellVolume.setText(s);
        if (!TextUtils.isEmpty(s)) {
            try {
                etBuySellVolume.setSelection(s.length());
            } catch (Exception e) {

            }
        }
    }

    private void setEtBuyPriceText(String s) {
        etBuySellPrice.setText(s);
        if (!TextUtils.isEmpty(s)) {
            try {
                etBuySellPrice.setSelection(s.length());
            } catch (Exception e) {

            }
        }
    }

    private void volumeAdd() {
        String volume = etBuySellVolume.getText().toString();
        if (TextUtils.isEmpty(volume)) {
            String s = new BigDecimal(mVolumePlusOrMinusLimit).setScale(mQtyDecimals, BigDecimal.ROUND_DOWN).toPlainString();
            setBuySellVolumeText(s);
        } else {
            try {
                BigDecimal etVolume = new BigDecimal(volume);
                BigDecimal plusOrMinusLimit = new BigDecimal(subZeroAndDot(Double.toString(mVolumePlusOrMinusLimit)));
                int up = getScale(1);
                setBuySellVolumeText(etVolume.add(plusOrMinusLimit).setScale(up, BigDecimal.ROUND_HALF_UP).toPlainString());
            } catch (Exception e) {

            }
        }
    }

    private void volumeMinus() {
        String volume = etBuySellVolume.getText().toString();
        if (TextUtils.isEmpty(volume)) {
            BigDecimal pricePlusOrMinusLimit = new BigDecimal(mVolumePlusOrMinusLimit);
            setBuySellVolumeText(String.valueOf(pricePlusOrMinusLimit.subtract(pricePlusOrMinusLimit).doubleValue()));
        } else {
            try {
                BigDecimal etVolume = new BigDecimal(volume);
                double c = etVolume.doubleValue();
                if (c == 0) {
                    return;
                }
                BigDecimal plusOrMinusLimit = new BigDecimal(mVolumePlusOrMinusLimit);
                int up = getScale(1);
                setBuySellVolumeText(etVolume.subtract(plusOrMinusLimit).setScale(up, BigDecimal.ROUND_HALF_UP).toPlainString());
            } catch (Exception e) {

            }
        }

    }

    private void priceMinus() {
        String price = etBuySellPrice.getText().toString();
        if (TextUtils.isEmpty(price)) {
            BigDecimal pricePlusOrMinusLimit = new BigDecimal(mPricePlusOrMinusLimit);
            setEtBuyPriceText(String.valueOf(pricePlusOrMinusLimit.subtract(pricePlusOrMinusLimit).doubleValue()));
        } else {
            try {
                BigDecimal etPrice = new BigDecimal(price);
                double c = etPrice.doubleValue();
                if (c == 0) {
                    return;
                }
                BigDecimal plusOrMinusLimit = new BigDecimal(mPricePlusOrMinusLimit);
                int up = getScale(2);
                setEtBuyPriceText(etPrice.subtract(plusOrMinusLimit).setScale(up, BigDecimal.ROUND_HALF_UP).toPlainString());
            } catch (Exception e) {

            }
        }
    }

    private void priceAdd() {
        String price = etBuySellPrice.getText().toString();
        if (TextUtils.isEmpty(price)) {
            BigDecimal decimal = new BigDecimal(mPricePlusOrMinusLimit);
            String s = decimal.setScale(mPriceDecimals, BigDecimal.ROUND_DOWN).toPlainString();
            setEtBuyPriceText(s);
        } else {
            try {
                BigDecimal etPrice = new BigDecimal(price);
                BigDecimal plusOrMinusLimit = new BigDecimal(mPricePlusOrMinusLimit);
                int up = getScale(2);
                setEtBuyPriceText(etPrice.add(plusOrMinusLimit).setScale(up, BigDecimal.ROUND_HALF_UP).toPlainString());
            } catch (Exception e) {

            }
        }
    }


    private void initPriceWatcher() {
        etBuySellPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mIsBuy) {
                    mEtBuyPriceText = s.toString();
                    setBuyTextChange();
                } else {
                    mEtSellPriceText = s.toString();
                    setSellTextChange();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                formatText(editable, true);
            }
        });
    }

    private void formatText(Editable editable, boolean isFromPrice) {
        String temp = editable.toString();
        int posDot = temp.indexOf(".");
        //直接输入小数点的情况
        if (posDot == 0) {
            editable.insert(0, "0");
            KLog.d("editable = " + editable.toString());
            return;
        }
        //连续输入0
        if (temp.equals("00")) {
            editable.delete(1, 2);
            return;
        }
        //输入"08" 等类似情况
        if (temp.startsWith("0") && temp.length() > 1 && (posDot == -1 || posDot > 1)) {
            editable.delete(0, 1);
            return;
        }

        //不包含小数点 不限制小数点前位数
        if (posDot < 0 && beforeDot == -1) {
            //do nothing 仅仅为了理解逻辑而已
            return;
        } else if (posDot < 0 && beforeDot != -1) {
            //不包含小数点 限制小数点前位数
            if (temp.length() <= beforeDot) {
                //do nothing 仅仅为了理解逻辑而已
            } else {
                editable.delete(beforeDot, beforeDot + 1);
            }
            return;
        }
        if (isFromPrice) {
            //如果包含小数点 限制小数点后位数
            if (temp.length() - posDot - 1 > mPriceDecimals && mPriceDecimals != -1) {
                editable.delete(posDot + mPriceDecimals + 1, posDot + mPriceDecimals + 2);//删除小数点后多余位数
            }
        } else {
            if (temp.length() - posDot - 1 > mQtyDecimals && mQtyDecimals != -1) {
                editable.delete(posDot + mQtyDecimals + 1, posDot + mQtyDecimals + 2);//删除小数点后多余位数
            }
        }
    }

    private void initVolumeWatcher() {
        etBuySellVolume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mIsBuy) {
                    mEtBuyVolumeText = s.toString();
                    setBuyTextChange();
                } else {
                    mEtSellVolumeText = s.toString();
                    setSellTextChange();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                formatText(editable, false);
            }
        });
    }


    private void setSellTextChange() {
        String totalDes = getResources().getString(R.string.total_price) + ": ";
        if (TextUtils.isEmpty(mEtSellPriceText) || TextUtils.isEmpty(mEtSellVolumeText)) {
            BigDecimal decimal = new BigDecimal("0");
            if (currentCoin == null) {
                tvTotalPrices.setText(totalDes + decimal.setScale(mPriceDecimals, BigDecimal.ROUND_DOWN).toPlainString());
            } else {
                tvTotalPrices.setText(totalDes + decimal.setScale(mPriceDecimals, BigDecimal.ROUND_DOWN).toPlainString() + "(" + currentCoin.getMarketCoinName() + ")");
            }
            return;
        }
        try {
            BigDecimal price = new BigDecimal(mEtSellPriceText);
            BigDecimal volume = new BigDecimal(mEtSellVolumeText);
            if (currentCoin == null) {
                tvTotalPrices.setText(totalDes + price.multiply(volume).setScale(mPriceDecimals, BigDecimal.ROUND_DOWN).toPlainString());
            } else {
                tvTotalPrices.setText(totalDes + price.multiply(volume).setScale(mPriceDecimals, BigDecimal.ROUND_DOWN).toPlainString() + "(" + currentCoin.getMarketCoinName() + ")");
            }
        } catch (Exception e) {
            KLog.d("Exception = " + e.getMessage());
        }
    }

    private void setBuyTextChange() {
        String totalDes = getResources().getString(R.string.total_price) + ": ";
        if (TextUtils.isEmpty(mEtBuyPriceText) || TextUtils.isEmpty(mEtBuyVolumeText)) {
            BigDecimal decimal = new BigDecimal("0");
            if (currentCoin == null) {
                tvTotalPrices.setText(totalDes + decimal.setScale(mPriceDecimals, BigDecimal.ROUND_DOWN).toPlainString());
            } else {
                tvTotalPrices.setText(totalDes + decimal.setScale(mPriceDecimals, BigDecimal.ROUND_DOWN).toPlainString() + "(" + currentCoin.getMarketCoinName() + ")");
            }
            return;
        }
        try {
            BigDecimal price = new BigDecimal(mEtBuyPriceText);
            BigDecimal volume = new BigDecimal(mEtBuyVolumeText);
            if (currentCoin == null) {
                tvTotalPrices.setText(totalDes + price.multiply(volume).setScale(mPriceDecimals, BigDecimal.ROUND_DOWN).toPlainString());
            } else {
                tvTotalPrices.setText(totalDes + price.multiply(volume).setScale(mPriceDecimals, BigDecimal.ROUND_DOWN).toPlainString() + "(" + currentCoin.getMarketCoinName() + ")");
            }
        } catch (Exception e) {
            KLog.d("Exception = " + e.getMessage());
        }

    }


    /**
     * @param type 1数量精度 2价格精度
     * @return
     */
    private int getScale(int type) {
        int up = 3;
        if (type == 1) {
            if (currentCoin != null) {
                up = mQtyDecimals;
            }
        } else if (type == 2) {
            if (currentCoin != null) {
                up = mPriceDecimals;
            }
        }
        return up;
    }

    /**
     * 用户余额 订单信息
     *
     * @param bean
     */
    @Override
    public void onTopicTradeListData(EntrustInfoBean bean) {
        KLog.d("onTopicTradeListData = " + new Gson().toJson(bean));//个人数据
        String userId = (String) SPUtils.getParamString(mContext, SPConstant.USER_ID, null);
        KLog.d("userId = " + userId);
        if (userId != null) {
            mEntrustInfoBean = bean;
            if (mIsBuy) {
                setRightAvailable();
            } else {
                setLeftAvailable();
            }
        } else {
            KLog.d(" mEntrustInfoBean = null");
            mEntrustInfoBean = null;
            setNoAvailable();
        }
    }

    @Override
    public void onCancellationsState(CommonJsonToBean<String> state) {

    }

    @Override
    public void onCurrentEnstrust(CommonJsonToBean<CurrentEntrustBean> bean) {

    }

    @Override
    public void onRefreshCurrentEnstrust(CommonJsonToBean<CurrentEntrustBean> bean) {

    }

    @Override
    public void onLoadMoreCurrentEnstrust(CommonJsonToBean<CurrentEntrustBean> bean) {

    }

    private void setRightAvailable() {
        if (currentCoin != null) {
            if (mEntrustInfoBean != null) {
                EntrustInfoBean.CoinBalanceBean coinBalance = mEntrustInfoBean.getCoinBalance();
                if (coinBalance == null) {
                    setNoAvailable();
                } else {
                    BigDecimal decimal = coinBalance.getRightCoinBalance().setScale(mPriceDecimals, BigDecimal.ROUND_DOWN);
                    if (tvAvailable != null) {
                        tvAvailable.setText(decimal.toPlainString() + currentCoin.getMarketCoinName());
                    }
                }

            } else {
                setNoAvailable();
            }
        }
    }

    private void setLeftAvailable() {
        if (currentCoin != null) {
            if (mEntrustInfoBean != null) {
                EntrustInfoBean.CoinBalanceBean coinBalance = mEntrustInfoBean.getCoinBalance();
                if (coinBalance == null) {
                    setNoAvailable();
                } else {
                    BigDecimal decimal = coinBalance.getLeftCoinBalance().setScale(mPriceDecimals, BigDecimal.ROUND_DOWN);
                    if (tvAvailable != null) {
                        tvAvailable.setText(decimal.toPlainString() + currentCoin.getCoinName());
                    }
                }

            } else {
                setNoAvailable();
            }
        }
    }


    @Subscribe
    public void onEvent(MyEvents event) {
        switch (event.status_type) {
            case MyEvents.LoginSuccess://登录成功通知
                KLog.d("setCurrentCoin LoginSuccess");
                setCurrentCoin();
                break;
            case MyEvents.LoginEsc://退出登录
            case MyEvents.Token_failure://token失效通知
                mEntrustInfoBean = null;
                if (mEntrustPresenter != null) {
                    mIsTopicTradeList = false;
                    mEntrustPresenter.unTopicTradeList();
                }
                setNoAvailable();
                KLog.d("setCurrentCoin LoginEsc");
                setCurrentCoin();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(KLineBuyOrSellEvent event) {
        KLog.d("mMarketId = " + mMarketId);
        KLog.d("event.marketId = " + event.marketId);
        mIsBuy = event.isBuy;
        setEditTextEmpty();
        if (mMarketId != event.marketId) {
            currentCoin = null;
            mMarketId = event.marketId;
            if (mCommitteePresenter != null) {
                mCommitteePresenter.unTopicTradeListAndMarketOrders();
            }
            if (mEntrustPresenter != null) {
                mIsTopicTradeList = false;
                mEntrustPresenter.unTopicTradeList();
            }
            initToolbar();
            KLog.d("setCurrentCoin KLineBuyOrSellEvent");
            setCurrentCoin();
        }
        if (event.isBuy) {
            buyAndSellTabLayout.setCurrentTab(0);
            setTabLayoutSelect(0);
        } else {
            buyAndSellTabLayout.setCurrentTab(1);
            setTabLayoutSelect(1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SelectTradingCoinEvent event) {
        KLog.d("SelectTradingCoinEvent = " + mMarketId);
        KLog.d("SelectTradingCoinEvent = " + event.marketId);
        if (mMarketId != event.marketId) {
            radioGroup.clearCheck();
            currentCoin = null;
            mMarketId = event.marketId;
            if (mCommitteePresenter != null) {
                mCommitteePresenter.unTopicTradeListAndMarketOrders();
            }
            if (mEntrustPresenter != null) {
                mIsTopicTradeList = false;
                mEntrustPresenter.unTopicTradeList();
            }
            initToolbar();
            setEditTextEmpty();
            KLog.d("setCurrentCoin SelectTradingCoinEvent");
            setCurrentCoin();
        }

    }

    private void setEditTextEmpty() {
        mEtBuyPriceText = "";
        mEtBuyVolumeText = "";
        mEtSellPriceText = "";
        mEtSellVolumeText = "";
        etBuySellPrice.setText("");
        etBuySellVolume.setText("");
    }

    @Override
    public void setIndexPageData(IndexPageBean indexPageData) {
        KLog.d("setIndexPageData = " + new Gson().toJson(indexPageData));
        //所有交易对信息
        OAXApplication.setCoinsInfo(indexPageData.getData().getAllMaketList());
        OAXApplication.setCoinsInfoFromRecommend(indexPageData.getData().getRecommendMarketList());
        //用户收藏
        OAXApplication.setCollectCoinsMap(indexPageData.getData().getUserMaketList());
        mRefreshLayout.finishRefresh();
        //确定marketid
        if (indexPageData != null) {
            List<IndexPageBean.DataBean.AllMaketListBean> allMaketList = indexPageData.getData().getAllMaketList();
            if (allMaketList != null && allMaketList.size() > 0) {
                for (int i = 0; i < allMaketList.size(); i++) {
                    IndexPageBean.DataBean.AllMaketListBean allMaketListBean = allMaketList.get(i);
                    if (allMaketListBean != null) {
                        List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> marketList = allMaketListBean.getMarketList();
                        if (marketList != null && marketList.size() > 0) {
                            for (int j = 0; j < marketList.size(); j++) {
                                IndexPageBean.DataBean.AllMaketListBean.MarketListBean bean = marketList.get(j);
                                if (bean != null) {
                                    int marketId = bean.getMarketId();
                                    if (marketId > 0 && mMarketId == -1) {
                                        mMarketId = marketId;
                                        try {
                                            parseCurrentCoin(bean);
                                        } catch (Exception e) {

                                        }
                                        KLog.d("setCurrentCoin mMarketId == -1");
                                        setCurrentCoin();
                                        setIsErrorData();
                                        return;
                                    } else if (marketId == mMarketId) {
                                        try {
                                            parseCurrentCoin(bean);
                                        } catch (Exception e) {

                                        }
                                        KLog.d("setCurrentCoin marketId == mMarketId");
                                        setCurrentCoin();
                                        setIsErrorData();
                                        return;
                                    } else {
                                        KLog.d("setCurrentCoin else");
                                        setCurrentCoin();
                                        setIsErrorData();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void setIsErrorData() {
        if (isRefreshError) {
            isRefreshError = false;
            EventBus.getDefault().post(new RefreshHomeFragmentEvent());
        }
        //如果还是默认的marketID，则填充假数据
        if (mMarketId == -1) {
            setNoMarketIdState();
        }
    }

    private void parseCurrentCoin(IndexPageBean.DataBean.AllMaketListBean.MarketListBean bean) {
        Gson gson = new Gson();
        String s = gson.toJson(bean);
        currentCoin = gson.fromJson(s, TradingInfoBean.AllMaketListBean.MarketListBean.class);
    }

    private void setNoMarketIdState() {
        try {
            mytradingToolbar.setTitleNameText("--/--");
            tvTradingPrice.setText("--");
            tvRmbPrice.setText("--");
            tvTradingLimit.setText("--");
            List<TradeListAndMarketOrdersBean.BuyOrSellListBean> list = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                TradeListAndMarketOrdersBean.BuyOrSellListBean bean = new TradeListAndMarketOrdersBean.BuyOrSellListBean();
                list.add(bean);
            }
            mBuyAdapter.isEmptyData(true);
            mSellAdapter.isEmptyData(true);
            mBuyAdapter.setNewData(list);
            mSellAdapter.setNewData(list);
        } catch (Exception e) {

        }

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        indexPagePresenter.getIndexPageModule();
    }

    @Override
    public void setRefreshIndexPageData(IndexPageBean indexPageData) {

    }

    @Override
    public void setSilentRefreshIndexPageData(IndexPageBean indexPageData) {

    }

    @Override
    public void setRefreshIndexPageDataErrer(String indexPageData) {

    }

    @Override
    public void refreshErrer() {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefresh();
        }

        isRefreshError = true;
        mEntrustInfoBean = null;
        currentCoin = null;
        if (tvFeeRate != null) {
            tvFeeRate.setText("");
        }
        setNoAvailable();
        setNoMarketIdState();
    }

    private void setNoAvailable() {
        String df = "0";
        if (tvAvailable != null) {
            if (currentCoin != null) {
                if (mIsBuy) {
                    tvAvailable.setText(df + currentCoin.getMarketCoinName());
                } else {
                    tvAvailable.setText(df + currentCoin.getCoinName());
                }
            } else {
                tvAvailable.setText(df);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UMManager.onResume(mContext, UMConstant.OaxTradingFragment);
        KLog.d("WebSocketsManager initWebsocket " + AppManager.isAppIsInBackground(mContext));
        KLog.d("WebSocketsManager initWebsocket " + OAXApplication.isScreenOn);
        if (!AppManager.isAppIsInBackground(mContext) && OAXApplication.isScreenOn && mIsBackgroundOrUnScreenOn) {
            String userId = (String) SPUtils.getParamString(mContext, SPConstant.USER_ID, null);
            if (userId != null && mEntrustPresenter != null) {
                mEntrustPresenter.topicTradeList(mMarketId, userId);
                mIsTopicTradeList = true;
            }
            if (mCommitteePresenter != null) {
                initBuyAndSellWebSocket();
            }
            KLog.d("WebSocketsManager initWebsocket");
        }
    }

    @Override
    public void onStop() {
        KLog.d("mIsBackground = " + OAXApplication.mIsBackground);
        if (OAXApplication.mIsBackground || !OAXApplication.isScreenOn) {
            mIsBackgroundOrUnScreenOn = true;
            KLog.d("WebSocketsManager disconnect");
            if (mEntrustPresenter != null) {
                mIsTopicTradeList = false;
                mEntrustPresenter.unTopicTradeList();
            }
            if (mCommitteePresenter != null) {
                mCommitteePresenter.unTopicTradeListAndMarketOrders();
            }
        } else {
            mIsBackgroundOrUnScreenOn = false;
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        UMManager.onPause(mContext, UMConstant.OaxTradingFragment);
//        if (AppManager.isAppIsInBackground(mContext) || !OAXApplication.isScreenOn) {
//            mIsBackgroundOrUnScreenOn = true;
//            KLog.d("WebSocketsManager disconnect");
//            if (mEntrustPresenter != null) {
//                mEntrustPresenter.unTopicTradeList();
//            }
//            if (mCommitteePresenter != null) {
//                mCommitteePresenter.unTopicTradeListAndMarketOrders();
//            }
//        } else {
//            mIsBackgroundOrUnScreenOn = false;
//        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(msg_what);
            mHandler = null;
        }
        if (mCommitteePresenter != null) {
            mCommitteePresenter.unTopicTradeListAndMarketOrders();
        }
        if (mEntrustPresenter != null) {
            mIsTopicTradeList = false;
            mEntrustPresenter.unTopicTradeList();
        }
    }
}
