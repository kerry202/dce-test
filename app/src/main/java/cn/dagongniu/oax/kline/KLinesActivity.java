package cn.dagongniu.oax.kline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.socks.library.KLog;
import com.zhy.autolayout.AutoLinearLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.LoginActivity;
import cn.dagongniu.oax.base.OAXBaseActivity;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.customview.CustomViewPager;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.UrlParams;
import cn.dagongniu.oax.kline.bean.DataParse;
import cn.dagongniu.oax.kline.bean.TradingInfoBean;
import cn.dagongniu.oax.kline.fragment.CommitteeFragment;
import cn.dagongniu.oax.kline.fragment.TransactionFragment;
import cn.dagongniu.oax.kline.kchart.CoupleChartGestureListener;
import cn.dagongniu.oax.kline.kchart.MyBottomMarkerView;
import cn.dagongniu.oax.kline.kchart.MyCombinedChart;
import cn.dagongniu.oax.kline.kchart.MyLeftMarkerView;
import cn.dagongniu.oax.kline.kchart.MyRightMarkerView;
import cn.dagongniu.oax.kline.presenter.KLineActivityPresenter;
import cn.dagongniu.oax.kline.view.CurrentItemInfoView;
import cn.dagongniu.oax.kline.view.CurrentTransactionPriceView;
import cn.dagongniu.oax.kline.view.KLineDaysSelectPopupWindow;
import cn.dagongniu.oax.kline.view.KLineHoursSelectPopupWindow;
import cn.dagongniu.oax.kline.view.KLineMinutesSelectPopupWindow;
import cn.dagongniu.oax.kline.view.KLineTabView;
import cn.dagongniu.oax.kline.view.KLineToolbar;
import cn.dagongniu.oax.main.adapter.FragAdapter;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.trading.fragment.OaxTradingIView;
import cn.dagongniu.oax.utils.KLineUtils;
import cn.dagongniu.oax.utils.Logs;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.KLineBuyOrSellEvent;
import cn.dagongniu.oax.utils.events.RefreshHomeFragmentEvent;
import cn.dagongniu.oax.utils.events.TransactionListEvent;
import cn.dagongniu.oax.utils.events.UpdateTradingInfoEvent;

public class KLinesActivity extends OAXBaseActivity implements OaxTradingIView {


    @BindView(R.id.kline_toolbar)
    KLineToolbar klineToolbar;
    @BindView(R.id.full_screen)
    ImageView fullScreen;
    @BindView(R.id.current_transaction_price)
    CurrentTransactionPriceView currentTransactionPrice;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.main_market_view_pager)
    CustomViewPager viewPager;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.tv_sell)
    TextView tvSell;
    @BindView(R.id.currentiteminfoview)
    CurrentItemInfoView mCurrentItemInfoView;
    @BindView(R.id.combinedchart)
    MyCombinedChart kChart;
    @BindView(R.id.barchart)
    MyCombinedChart volChart;

    @BindView(R.id.tab_minute)
    KLineTabView mTabMinute;
    @BindView(R.id.tab_minutes)
    KLineTabView mTabMinutes;
    @BindView(R.id.tab_hour)
    KLineTabView mTabHour;
    @BindView(R.id.tab_day)
    KLineTabView mTabDay;
    @BindView(R.id.tab_mouth)
    KLineTabView mTabMouth;


    KLineActivityPresenter mKLinePresenter;
    List<Fragment> transactionFragments = new ArrayList<>();
    String[] indicatorTitles = new String[2];
    XAxis xAxisVol, xAxisK;
    YAxis axisLeftVol, axisLeftK;
    YAxis axisRightVol, axisRightK;
    @BindView(R.id.ll_tab_minute)
    AutoLinearLayout llTabMinute;
    @BindView(R.id.ll_tab_minutes)
    AutoLinearLayout llTabMinutes;
    @BindView(R.id.ll_tab_hour)
    AutoLinearLayout llTabHour;
    @BindView(R.id.ll_tab_day)
    AutoLinearLayout llTabDay;
    @BindView(R.id.ll_tab_mouth)
    AutoLinearLayout llTabMouth;
    private DataParse mData;
    private int mBarSpacePercent = 35;

    private int marketId = -1;
    private int minType = Constant.KLINE_MINTYPE_1;
    BarDataSet barDataSet;
    private int mkType = Constant.KLINE_KLINE;
    private boolean isDay = false;
    private int delayMillisMinutes = 20;
    private KLineMinutesSelectPopupWindow mMinutesSelectPopuWindow;
    private KLineHoursSelectPopupWindow mKLineHoursSelectPopupWindow;
    private KLineDaysSelectPopupWindow mKLineDaysSelectPopupWindow;
    final float mXscaleCombin = 20;
    final float mYscaleCombin = 1;
    private KProgressHUD dialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            notifyDataChange();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_klines;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar
                .titleBar(klineToolbar)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent.hasExtra(UrlParams.marketId)) {
            marketId = intent.getIntExtra(UrlParams.marketId, 0);
        }
        indicatorTitles[0] = getResources().getString(R.string.kline_committee);
        indicatorTitles[1] = getResources().getString(R.string.kline_transaction);
        initFragments();
        initViewPager();
        initMagicIndicator();
        if (klineToolbar != null) {
            IndexPageBean.DataBean.AllMaketListBean.MarketListBean bean = OAXApplication.coinsInfoMap.get(marketId);
            if (bean != null) {
                klineToolbar.setTvTitle(bean.getCoinName() + "/" + bean.getMarketCoinName());
            }
            klineToolbar.setOnKLineToolbarClickListener(new KLineToolbar.OnKLineToolbarClickListener() {
                @Override
                public void onLeftClick() {
                    finish();
                }

                @Override
                public void onRightClick() {
                    String userId = SPUtils.getParamString(mContext, SPConstant.USER_ID, null);
                    if (userId != null) {
                        if (OAXApplication.collectCoinsMap.containsKey(marketId)) {
                            mKLinePresenter.cancelCollectMarket(marketId, RequestState.STATE_DIALOG);
                        } else {
                            mKLinePresenter.collectMarket(marketId, RequestState.STATE_DIALOG);
                        }
                    } else {
                        SkipActivityUtil.skipAnotherActivity(mContext, LoginActivity.class);
                    }

                }

                @Override
                public void onTvTitleClick() {

                }
            });
        }
        changeCurrentTransactionPrice();
        initCollect();
        initChart();
    }

    private void initFragments() {
        CommitteeFragment committeeFragment = new CommitteeFragment();
        TransactionFragment transactionFragment = new TransactionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(UrlParams.marketId, marketId);
        committeeFragment.setArguments(bundle);
        transactionFragment.setArguments(bundle);
        transactionFragments.add(committeeFragment);
        transactionFragments.add(transactionFragment);
    }

    /**
     * 初始化图表
     */
    private void initChart() {
        volChart.setDrawBorders(true);
        volChart.setBorderWidth(1);
        volChart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        volChart.setDescription("");
        volChart.setDragEnabled(true);
        volChart.setScaleYEnabled(false);

        Legend barChartLegend = volChart.getLegend();
        barChartLegend.setEnabled(false);

        //bar x y轴
        xAxisVol = volChart.getXAxis();
        xAxisVol.setDrawLabels(false);
        xAxisVol.setDrawGridLines(false);
        xAxisVol.setDrawAxisLine(false);
        xAxisVol.setTextColor(getResources().getColor(R.color.kline_buy_bg));
        xAxisVol.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisVol.setGridColor(getResources().getColor(R.color.minute_grayLine));

        axisLeftVol = volChart.getAxisLeft();
        axisLeftVol.setDrawGridLines(false);
        axisLeftVol.setDrawAxisLine(false);
        axisLeftVol.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisLeftVol.setTextColor(getResources().getColor(R.color.kline_buy_bg));
        axisLeftVol.setDrawLabels(true);
        axisLeftVol.setSpaceTop(10);
        axisLeftVol.setSpaceBottom(0);
        axisLeftVol.setShowOnlyMinMax(true);

        axisRightVol = volChart.getAxisRight();
        axisRightVol.setDrawLabels(false);
        axisRightVol.setDrawGridLines(false);
        axisRightVol.setDrawAxisLine(false);


        /****************************************************************/
        kChart.setDrawBorders(true);
        kChart.setBorderWidth(1);
        kChart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        kChart.setDescription("");
        kChart.setDragEnabled(true);
        kChart.setScaleYEnabled(false);

        Legend combinedchartLegend = kChart.getLegend();
        combinedchartLegend.setEnabled(false);

        //K线 x y轴
        xAxisK = kChart.getXAxis();
        xAxisK.setDrawLabels(true);
        xAxisK.setDrawGridLines(false);
        xAxisK.setDrawAxisLine(false);
        xAxisK.setTextColor(getResources().getColor(R.color.kline_buy_bg));
        xAxisK.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisK.setGridColor(getResources().getColor(R.color.minute_grayLine));
        xAxisK.setAvoidFirstLastClipping(true);

        axisLeftK = kChart.getAxisLeft();
        axisLeftK.setDrawGridLines(true);
        axisLeftK.setDrawAxisLine(false);
        axisLeftK.setDrawLabels(true);
        axisLeftK.setTextColor(getResources().getColor(R.color.kline_buy_bg));
        axisLeftK.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftK.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisLeftK.setLabelCount(5, true);
        axisLeftK.setSpaceTop(10);

        axisRightK = kChart.getAxisRight();
        axisRightK.setDrawLabels(false);
        axisRightK.setDrawGridLines(false);
        axisRightK.setDrawAxisLine(false);
        axisRightK.setGridColor(getResources().getColor(R.color.minute_grayLine));

        kChart.setDragDecelerationEnabled(true);
        volChart.setDragDecelerationEnabled(true);

        kChart.setDragDecelerationFrictionCoef(0.2f);
        volChart.setDragDecelerationFrictionCoef(0.2f);
        setHighLight();
    }

    private void setHighLight() {
        // 将K线控的滑动事件传递给交易量控件
        kChart.setOnChartGestureListener(new CoupleChartGestureListener(kChart, new Chart[]{volChart}));
        // 将交易量控件的滑动事件传递给K线控件
        volChart.setOnChartGestureListener(new CoupleChartGestureListener(volChart, new Chart[]{kChart}));

        volChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                int index = h.getXIndex();
//                kChart.setHighlightValue(new Highlight(index, dataSetIndex));
                kChart.setHighlightValue(new Highlight(index, Float.NaN, 1, dataSetIndex));
                setCurrentItemInfo(index);
            }

            @Override
            public void onNothingSelected() {
                kChart.highlightValue(null);
                postCurrentItemInfo(false, null, 0, 0, 0, 0);
            }
        });
        kChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                int index = h.getXIndex();
                volChart.setHighlightValue(new Highlight(index, dataSetIndex));
                setCurrentItemInfo(index);
            }

            @Override
            public void onNothingSelected() {
                KLog.d("onNothingSelected");
                volChart.highlightValue(null);
                postCurrentItemInfo(false, null, 0, 0, 0, 0);
            }
        });
    }

    private void setCurrentItemInfo(int index) {
        if (mData != null) {
            float ma5 = 0;
            float ma10 = 0;
            float ma30 = 0;
            float ma60 = 0;
            if (mData.getMa5Vols().containsKey(index)) {
                ma5 = mData.getMa5Vols().get(index);
            }
            if (mData.getMa10Vols().containsKey(index)) {
                ma10 = mData.getMa10Vols().get(index);
            }
            if (mData.getMa30Vols().containsKey(index)) {
                ma30 = mData.getMa30Vols().get(index);
            }
            if (mData.getMa60Vols().containsKey(index)) {
                ma60 = mData.getMa60Vols().get(index);
            }
            postCurrentItemInfo(true, mData.getKLineDatas().get(index), ma5, ma10, ma30, ma60);
        }
    }

    private void postCurrentItemInfo(boolean isVisible, TradingInfoBean.KlineListBean bean, float ma5, float ma10, float ma30, float ma60) {
        if (isVisible) {
            mCurrentItemInfoView.setVisibility(View.VISIBLE);
            mCurrentItemInfoView.dataChange(bean, ma5, ma10, ma30, ma60);
        } else {
            mCurrentItemInfoView.setVisibility(View.GONE);
        }
    }

    private void refreshKlineData() {
        showDialog(this);
        volChart.clear();
        kChart.clear();
        kChart.fitScreen();
        volChart.fitScreen();
        HashMap<String, Object> map = new HashMap<>();
        map.put(UrlParams.marketId, marketId);
        map.put(UrlParams.minType, minType);
        mKLinePresenter.getData(map, RequestState.STATE_REFRESH);
    }

    private void initViewPager() {
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), transactionFragments);
        viewPager.setAdapter(adapter);
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return indicatorTitles == null ? 0 : indicatorTitles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.df_font_hui));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.df_font));
                simplePagerTitleView.setText(indicatorTitles[index]);
                simplePagerTitleView.setTextSize(getResources().getDimension(R.dimen.d14));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(getResources().getColor(R.color.minute_yellow));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }


    @OnClick({R.id.full_screen, R.id.tv_buy, R.id.tv_sell, R.id.ll_tab_minute, R.id.ll_tab_minutes, R.id.ll_tab_hour, R.id.ll_tab_day, R.id.ll_tab_mouth})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.full_screen:
                Intent intent = new Intent(mContext, KLinesFullScreenActivity.class);
                intent.putExtra(UrlParams.marketId, marketId);
                intent.putExtra(UrlParams.minType, minType);
                intent.putExtra("kType", mkType);
                startActivityForResult(intent, 2);
                break;
            case R.id.tv_buy:
                String userId =  SPUtils.getParamString(mContext, SPConstant.USER_ID, null);
                if (userId != null) {
                    EventBus.getDefault().post(new KLineBuyOrSellEvent(marketId, minType, true));
                    finish();
                } else {
                    SkipActivityUtil.skipAnotherActivity(mContext, LoginActivity.class);
                }
                break;
            case R.id.tv_sell:
                String Id = SPUtils.getParamString(mContext, SPConstant.USER_ID, null);
                if (Id != null) {
                    EventBus.getDefault().post(new KLineBuyOrSellEvent(marketId, minType, false));
                    finish();
                } else {
                    SkipActivityUtil.skipAnotherActivity(mContext, LoginActivity.class);
                }
                break;
            case R.id.ll_tab_minute:
                setMinuteClick();
                break;
            case R.id.ll_tab_minutes:
                initMinutesSelect();
                break;
            case R.id.ll_tab_hour:
                initHoursSelect();
                break;
            case R.id.ll_tab_day:
                initDaysSelect();
                break;
            case R.id.ll_tab_mouth:
                setMouthClick();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (requestCode == 2) {
                mkType = data.getIntExtra("kType", Constant.KLINE_KLINE);
                minType = data.getIntExtra(UrlParams.minType, Constant.KLINE_MINTYPE_1);
                initSelect();
            }
        }
    }

    private void initSelect() {
        if (mkType == Constant.KLINE_MINUTE) {
            setMinuteClick();
        } else {
            switch (minType) {
                case Constant.KLINE_MINTYPE_1:
                    setMinutesClick(minType, getResources().getString(R.string.kline_one_min));
                    break;
                case Constant.KLINE_MINTYPE_5:
                    setMinutesClick(minType, getResources().getString(R.string.kline_five_min));
                    break;
                case Constant.KLINE_MINTYPE_10:
                    setMinutesClick(minType, getResources().getString(R.string.kline_ten_min));
                    break;
                case Constant.KLINE_MINTYPE_30:
                    setMinutesClick(minType, getResources().getString(R.string.kline_thirty_min));
                    break;
                case Constant.KLINE_MINTYPE_60:
                    setHourClick(minType, getResources().getString(R.string.kline_one_hour));
                    break;
                case Constant.KLINE_MINTYPE_240:
                    setHourClick(minType, getResources().getString(R.string.kline_four_hour));
                    break;
                case Constant.KLINE_MINTYPE_480:
                    setHourClick(minType, getResources().getString(R.string.kline_eight_hour));
                    break;
                case Constant.KLINE_MINTYPE_1440:
                    setDayClick(minType, getResources().getString(R.string.kline_one_day));
                    break;
                case Constant.KLINE_MINTYPE_10080:
                    setDayClick(minType, getResources().getString(R.string.kline_seven_day));
                    break;
                case Constant.KLINE_MINTYPE_43200:
                    setMouthClick();
                    break;
            }
        }
    }

    private void initDaysSelect() {
        if (mKLineDaysSelectPopupWindow == null) {
            mKLineDaysSelectPopupWindow = new KLineDaysSelectPopupWindow(this);
            mKLineDaysSelectPopupWindow.setOnItemClickListener(new KLineDaysSelectPopupWindow.KLineDaysSelectPopupWindowClickListener() {
                @Override
                public void oneDay() {
                    setDayClick(Constant.KLINE_MINTYPE_1440, getResources().getString(R.string.kline_one_day));
                }

                @Override
                public void sevenDay() {
                    setDayClick(Constant.KLINE_MINTYPE_10080, getResources().getString(R.string.kline_seven_day));
                }
            });
        }
        mKLineDaysSelectPopupWindow.showAsDropDown(llTabDay);
    }

    private void initHoursSelect() {
        if (mKLineHoursSelectPopupWindow == null) {
            mKLineHoursSelectPopupWindow = new KLineHoursSelectPopupWindow(this);
            mKLineHoursSelectPopupWindow.setOnItemClickListener(new KLineHoursSelectPopupWindow.KLineHoursSelectPopupWindowClickListener() {
                @Override
                public void oneHour() {
                    setHourClick(Constant.KLINE_MINTYPE_60, getResources().getString(R.string.kline_one_hour));
                }

                @Override
                public void fourHour() {
                    setHourClick(Constant.KLINE_MINTYPE_240, getResources().getString(R.string.kline_four_hour));
                }

                @Override
                public void eightHour() {
                    setHourClick(Constant.KLINE_MINTYPE_480, getResources().getString(R.string.kline_eight_hour));
                }
            });
        }
        mKLineHoursSelectPopupWindow.showAsDropDown(llTabHour);
    }

    private void initMinutesSelect() {
        if (mMinutesSelectPopuWindow == null) {
            mMinutesSelectPopuWindow = new KLineMinutesSelectPopupWindow(this);
            mMinutesSelectPopuWindow.setOnItemClickListener(new KLineMinutesSelectPopupWindow.KLineMinutesSelectPopupWindowClickListener() {
                @Override
                public void oneMin() {
                    setMinutesClick(Constant.KLINE_MINTYPE_1, getResources().getString(R.string.kline_one_min));
                }

                @Override
                public void fiveMin() {
                    setMinutesClick(Constant.KLINE_MINTYPE_5, getResources().getString(R.string.kline_five_min));
                }

                @Override
                public void tenMin() {
                    setMinutesClick(Constant.KLINE_MINTYPE_10, getResources().getString(R.string.kline_ten_min));
                }

                @Override
                public void fifteenMin() {
                    setMinutesClick(Constant.KLINE_MINTYPE_15, getResources().getString(R.string.kline_fifteen_min));
                }

                @Override
                public void thirtyMin() {
                    setMinutesClick(Constant.KLINE_MINTYPE_30, getResources().getString(R.string.kline_thirty_min));
                }
            });
        }
        mMinutesSelectPopuWindow.showAsDropDown(llTabMinutes);
    }

    private void setMouthClick() {
        mCurrentItemInfoView.setVisibility(View.GONE);
        isDay = true;
        mkType = Constant.KLINE_KLINE;
        this.minType = Constant.KLINE_MINTYPE_43200;
        mTabMinute.setSelectState(false, "");
        mTabMinutes.setSelectState(false, getResources().getString(R.string.kline_minutes));
        mTabHour.setSelectState(false, getResources().getString(R.string.hour));
        mTabDay.setSelectState(false, getResources().getString(R.string.kline_daily_line));
        mTabMouth.setSelectState(true, "");
        refreshKlineData();
    }

    private void setDayClick(int minType, String name) {
        mCurrentItemInfoView.setVisibility(View.GONE);
        isDay = true;
        mkType = Constant.KLINE_KLINE;
        this.minType = minType;
        mTabMinute.setSelectState(false, "");
        mTabMinutes.setSelectState(false, getResources().getString(R.string.kline_minutes));
        mTabHour.setSelectState(false, getResources().getString(R.string.hour));
        mTabDay.setSelectState(true, name);
        mTabMouth.setSelectState(false, "");
        refreshKlineData();
    }

    private void setHourClick(int minType, String name) {
        mCurrentItemInfoView.setVisibility(View.GONE);
        isDay = false;
        mkType = Constant.KLINE_KLINE;
        this.minType = minType;
        mTabMinute.setSelectState(false, "");
        mTabMinutes.setSelectState(false, getResources().getString(R.string.kline_minutes));
        mTabHour.setSelectState(true, name);
        mTabDay.setSelectState(false, getResources().getString(R.string.kline_daily_line));
        mTabMouth.setSelectState(false, "");
        refreshKlineData();
    }

    private void setMinutesClick(int minType, String name) {
        mCurrentItemInfoView.setVisibility(View.GONE);
        isDay = false;
        mkType = Constant.KLINE_KLINE;
        this.minType = minType;
        mTabMinute.setSelectState(false, "");
        mTabMinutes.setSelectState(true, name);
        mTabHour.setSelectState(false, getResources().getString(R.string.hour));
        mTabDay.setSelectState(false, getResources().getString(R.string.kline_daily_line));
        mTabMouth.setSelectState(false, "");
        refreshKlineData();
    }

    private void setMinuteClick() {
        mCurrentItemInfoView.setVisibility(View.GONE);
        isDay = false;
        mkType = Constant.KLINE_MINUTE;
        minType = Constant.KLINE_MINTYPE_1;
        mTabMinute.setSelectState(true, "");
        mTabMinutes.setSelectState(false, getResources().getString(R.string.kline_minutes));
        mTabHour.setSelectState(false, getResources().getString(R.string.hour));
        mTabDay.setSelectState(false, getResources().getString(R.string.kline_daily_line));
        mTabMouth.setSelectState(false, "");
        refreshKlineData();
    }

    @Override
    protected void initData() {
        super.initData();
        mKLinePresenter = new KLineActivityPresenter(this);
        setMinutesClick(minType, getResources().getString(R.string.kline_one_min));
    }

    private void setKLineDatas() {
        setMarkerView(mData.getKLineDatas());
        //成交量
        setVolData();
        //MA
        setMaData();
        setOffset();
        handler.sendEmptyMessageDelayed(0, delayMillisMinutes);
    }

    /**
     * 设置ma（5 10 30 60 ）和蜡烛图 或者 分时线
     */
    private void setMaData() {
        if (kChart == null) {
            return;
        }
        if (mData == null) {
            return;
        }
        int size = mData.getKLineDatas().size();   //点的个数
        CandleDataSet candleDataSet = new CandleDataSet(mData.getCandleEntries(), "KLine");
        if (mkType == Constant.KLINE_MINUTE) {
            candleDataSet.setVisible(false);
            candleDataSet.setHighlightEnabled(false);
            candleDataSet.setDrawHorizontalHighlightIndicator(false);
            candleDataSet.setDrawVerticalHighlightIndicator(false);
        } else {
            candleDataSet.setVisible(true);
            candleDataSet.setHighlightEnabled(true);
            candleDataSet.setDrawHorizontalHighlightIndicator(true);
            candleDataSet.setDrawVerticalHighlightIndicator(true);
        }
        candleDataSet.setHighLightColor(Color.WHITE);
        candleDataSet.setDecreasingColor(getResources().getColor(R.color.kline_sell_bg));//设置开盘价高于收盘价的颜色
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setIncreasingColor(getResources().getColor(R.color.kline_buy_bg));//设置开盘价地狱收盘价的颜色
        candleDataSet.setIncreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setNeutralColor(getResources().getColor(R.color.kline_buy_bg));//设置开盘价等于收盘价的颜色    可在CandleStickChartRenderer drawDataSet（）中改规则
        candleDataSet.setShadowColorSameAsCandle(true);
        candleDataSet.setValueTextSize(10f);
        candleDataSet.setDrawValues(false);
        candleDataSet.setShadowWidth(1f);
        candleDataSet.setBarSpace(0.2f);
        candleDataSet.setShadowColor(Color.DKGRAY);
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        CandleData candleData = new CandleData(mData.getXVals(), candleDataSet);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        mData.initKLineMA(mData.getKLineDatas());
        /******此处修复如果显示的点的个数达不到MA均线的位置所有的点都从0开始计算最小值的问题******************************/
        if (mkType == Constant.KLINE_KLINE) {
            if (size >= 60) {
                sets.add(setMaLine(5, mData.getXVals(), mData.getMa5DataL()));
                sets.add(setMaLine(10, mData.getXVals(), mData.getMa10DataL()));
                sets.add(setMaLine(30, mData.getXVals(), mData.getMa30DataL()));
                sets.add(setMaLine(60, mData.getXVals(), mData.getMa60DataL()));
            } else if (size >= 30 && size < 60) {
                sets.add(setMaLine(5, mData.getXVals(), mData.getMa5DataL()));
                sets.add(setMaLine(10, mData.getXVals(), mData.getMa10DataL()));
                sets.add(setMaLine(30, mData.getXVals(), mData.getMa30DataL()));
            } else if (size >= 10 && size < 30) {
                sets.add(setMaLine(5, mData.getXVals(), mData.getMa5DataL()));
                sets.add(setMaLine(10, mData.getXVals(), mData.getMa10DataL()));
            } else if (size >= 5 && size < 10) {
                sets.add(setMaLine(5, mData.getXVals(), mData.getMa5DataL()));
            }
        } else if (mkType == Constant.KLINE_MINUTE) {
            sets.add(setMinuteLine(mData.getMinuteLineEntries()));
        }
        CombinedData combinedData = new CombinedData(mData.getXVals());
        LineData lineData = new LineData(mData.getXVals(), sets);
        combinedData.setData(candleData);
        combinedData.setData(lineData);

        Logs.s("    k线图实体数据类1    "+mData.toString());
        kChart.setData(combinedData);

        final ViewPortHandler viewPortHandlerCombin = kChart.getViewPortHandler();
        viewPortHandlerCombin.setMaximumScaleX(culcMaxscale(mData.getXVals().size()));
        Matrix matrixCombin = viewPortHandlerCombin.getMatrixTouch();
        matrixCombin.postScale(mXscaleCombin, mYscaleCombin);
        kChart.moveViewToX(mData.getKLineDatas().size() - 1);
    }

    /**
     * MA线
     *
     * @param lineEntries
     * @return
     */
    @NonNull
    private LineDataSet setMaLine(int ma, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {

        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + ma);
        lineDataSetMa.setHighlightEnabled(false);
        lineDataSetMa.setDrawHorizontalHighlightIndicator(false);
        lineDataSetMa.setDrawVerticalHighlightIndicator(false);
        lineDataSetMa.setDrawValues(false);
        if (ma == 5) {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_ma5));
        } else if (ma == 10) {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_ma10));
        } else if (ma == 30) {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_ma30));
        } else {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_ma60));
        }
        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        return lineDataSetMa;
    }

    /**
     * 分时线
     *
     * @param lineEntries
     * @return
     */
    @NonNull
    private LineDataSet setMinuteLine(ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMinute = new LineDataSet(lineEntries, "minute");
        lineDataSetMinute.setHighlightEnabled(true);
        lineDataSetMinute.setDrawHorizontalHighlightIndicator(true);
        lineDataSetMinute.setDrawVerticalHighlightIndicator(true);
        lineDataSetMinute.setHighLightColor(Color.WHITE);
        lineDataSetMinute.setDrawValues(false);
        lineDataSetMinute.setColor(getResources().getColor(R.color.minute_blue));
        lineDataSetMinute.setDrawFilled(true);
        lineDataSetMinute.setLineWidth(1f);
        lineDataSetMinute.setDrawCircles(false);
        lineDataSetMinute.setAxisDependency(YAxis.AxisDependency.LEFT);
        return lineDataSetMinute;
    }

    /*设置量表对齐*/
    private void setOffset() {
        float lineLeft = kChart.getViewPortHandler().offsetLeft();
        float barLeft = volChart.getViewPortHandler().offsetLeft();
        float lineRight = kChart.getViewPortHandler().offsetRight();
        float barRight = volChart.getViewPortHandler().offsetRight();
        float barBottom = volChart.getViewPortHandler().offsetBottom();
        float offsetLeft, offsetRight;
        float transLeft = 0, transRight = 0;
        /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (barLeft < lineLeft) {
           /* offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
            volChart.setExtraLeftOffset(offsetLeft);*/
            transLeft = lineLeft;
        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
            kChart.setExtraLeftOffset(offsetLeft);
            transLeft = barLeft;
        }
        /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (barRight < lineRight) {
          /*  offsetRight = Utils.convertPixelsToDp(lineRight);
            volChart.setExtraRightOffset(offsetRight);*/
            transRight = lineRight;
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            kChart.setExtraRightOffset(offsetRight);
            transRight = barRight;
        }
        volChart.setViewPortOffsets(transLeft, 1, transRight, barBottom);
    }

    private void notifyDataChange() {
        if (volChart == null) {
            return;
        }
        if (kChart == null) {
            return;
        }

        volChart.setAutoScaleMinMaxEnabled(true);
        kChart.setAutoScaleMinMaxEnabled(true);

        kChart.notifyDataSetChanged();
        volChart.notifyDataSetChanged();

        kChart.invalidate();
        volChart.invalidate();

        dismissDialog();
    }

    /**
     * 设置成交量数据
     */
    private void setVolData() {
        if (volChart == null) {
            return;
        }
        if (mData == null) {
            return;
        }
        mData.initBaseDatas(mData.getKLineDatas(), isDay);
        barDataSet = new BarDataSet(mData.getBarEntries(), "成交量");
        barDataSet.setBarSpacePercent(mBarSpacePercent); //bar空隙
        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightAlpha(255);
        barDataSet.setHighLightColor(Color.WHITE);
        barDataSet.setDrawValues(false); //BarChartRenderer drawDataSet（）中修改规则
        barDataSet.setColor(Color.RED);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.kline_buy_bg));
        colors.add(getResources().getColor(R.color.kline_sell_bg));
        barDataSet.setColors(colors);
        BarData barData = new BarData(mData.getXVals(), barDataSet);
        CombinedData combinedBarData = new CombinedData(mData.getXVals());
        combinedBarData.setData(barData);
        volChart.setData(combinedBarData);

        final ViewPortHandler viewPortHandlerBar = volChart.getViewPortHandler();

        viewPortHandlerBar.setMaximumScaleX(culcMaxscale(mData.getXVals().size()));

        Matrix touchmatrix = viewPortHandlerBar.getMatrixTouch();
        touchmatrix.postScale(mXscaleCombin, mYscaleCombin);
        volChart.moveViewToX(mData.getKLineDatas().size() - 1);

    }

    private void setMarkerView(List<TradingInfoBean.KlineListBean> mData) {
        if (kChart == null) {
            return;
        }
        if (volChart == null) {
            return;
        }
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(mContext, R.layout.mymarkerview, KLineUtils.confirmLeftMarkerPattern(marketId));
//        MyRightMarkerView rightMarkerView = new MyRightMarkerView(mContext, R.layout.mymarkerview);
        MyBottomMarkerView bottomMarkerView = new MyBottomMarkerView(mContext, R.layout.mymarkerview);
        kChart.setMarker(leftMarkerView, bottomMarkerView, null, mData);
        volChart.setMarker(null, null, null, mData);
    }

    private float culcMaxscale(float count) {
        float max = 1;
        max = count / 127 * 5;
        return max;
    }

    /**
     * 收藏状态
     */
    private void initCollect() {
        KLog.d("marketId = " + marketId);
        if (OAXApplication.collectCoinsMap.containsKey(marketId)) {
            klineToolbar.setIvRightState(true);
        } else {
            klineToolbar.setIvRightState(false);
        }
    }

    /**
     * 实时价格 涨跌幅
     */
    private void changeCurrentTransactionPrice() {
        if (OAXApplication.coinsInfoMap.containsKey(marketId)) {
            IndexPageBean.DataBean.AllMaketListBean.MarketListBean bean = OAXApplication.coinsInfoMap.get(marketId);
            KLog.d("changeCurrentTransactionPrice = " + new Gson().toJson(bean));
            currentTransactionPrice.setDataChange(bean);
        }
    }

    /**
     * k线数据回调
     *
     * @param data
     */
    @Override
    public void setTradingInfoData(CommonJsonToBean<TradingInfoBean> data) {
        if (kChart == null) {
            return;
        }
        if (volChart == null) {
            return;
        }
        EventBus.getDefault().post(new TransactionListEvent(data.getData()));
        List<TradingInfoBean.KlineListBean> klineList = data.getData().getKlineList();
        if (klineList != null && klineList.size() > 0) {
            mData = new DataParse();
            mData.setKLine(klineList);
            setKLineDatas();
        } else {
            String s = getResources().getString(R.string.no_data_available);
            if (kChart != null) {
                kChart.setNoDataText(s);
            }
            if (volChart != null) {
                volChart.setNoDataText(s);
            }
            dismissDialog();
        }
    }

    @Override
    public void setBuyOrSellState(CommonJsonToBean<String> data) {

    }

    public void showDialog(Activity activity) {
        if (dialog == null || !dialog.isShowing()) {
            dialog = KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void showToast(String str) {
        super.showToast(str);
    }

    /**
     * 收藏回调
     *
     * @param bean
     */
    @Override
    public void collectMarketState(CommonJsonToBean<String> bean) {
        ToastUtil.ShowToast(bean.getMsg(), this);
        klineToolbar.setIvRightState(bean.getSuccess());
        OAXApplication.addCollectCoinsMap(marketId);
        EventBus.getDefault().post(new RefreshHomeFragmentEvent());
    }

    /**
     * 取消收藏回调
     *
     * @param bean
     */
    @Override
    public void cancelCollectMarket(CommonJsonToBean<String> bean) {
        ToastUtil.ShowToast(bean.getMsg(), this);
        klineToolbar.setIvRightState(!bean.getSuccess());
        OAXApplication.collectCoinsMap.remove(marketId);
        EventBus.getDefault().post(new RefreshHomeFragmentEvent());
    }

    @Override
    public void setTopicKlineData(List<TradingInfoBean.KlineListBean> data) {

    }


    /**
     * 改变实时价格 涨跌幅
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UpdateTradingInfoEvent event) {
        KLog.d("UpdateTradingInfoEvent");
        changeCurrentTransactionPrice();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler = null;
    }
}
