package cn.dagongniu.oax.kline;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.OAXBaseActivity;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.UrlParams;
import cn.dagongniu.oax.kline.bean.DataParse;
import cn.dagongniu.oax.kline.bean.TradingInfoBean;
import cn.dagongniu.oax.kline.kchart.CoupleChartGestureListener;
import cn.dagongniu.oax.kline.kchart.MyBottomMarkerView;
import cn.dagongniu.oax.kline.kchart.MyCombinedChart;
import cn.dagongniu.oax.kline.kchart.MyLeftMarkerView;
import cn.dagongniu.oax.kline.kchart.MyRightMarkerView;
import cn.dagongniu.oax.kline.presenter.KLineActivityPresenter;
import cn.dagongniu.oax.kline.view.CurrentItemInfoView;
import cn.dagongniu.oax.kline.view.KLineDaysSelectPopupWindow;
import cn.dagongniu.oax.kline.view.KLineHoursSelectPopupWindow;
import cn.dagongniu.oax.kline.view.KLineMinutesSelectPopupWindow;
import cn.dagongniu.oax.kline.view.KLineTabView;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.trading.fragment.OaxTradingIView;
import cn.dagongniu.oax.utils.KLineUtils;
import cn.dagongniu.oax.utils.Logs;
import cn.dagongniu.oax.utils.events.UpdateTradingInfoEvent;

public class KLinesFullScreenActivity extends OAXBaseActivity implements OaxTradingIView {

    @BindView(R.id.kline_full_screen)
    RelativeLayout klineFullScreen;
    @BindView(R.id.tv_coin_name)
    TextView tvCoinName;
    @BindView(R.id.tv_new_price)
    TextView tvNewPrice;
    @BindView(R.id.tv_rnb)
    TextView tvRnb;
    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.full_screen)
    ImageView fullScreen;
    @BindView(R.id.rl_head_container)
    RelativeLayout rlHeadContainer;
    @BindView(R.id.currentiteminfoview)
    CurrentItemInfoView mCurrentItemInfoView;
    @BindView(R.id.right_item_container)
    AutoLinearLayout mRightitemContainer;
    @BindView(R.id.tv_ma)
    TextView tvMa;
    @BindView(R.id.tv_boll)
    TextView tvBoll;
    @BindView(R.id.tv_vol)
    TextView tvVol;
    @BindView(R.id.tv_macd)
    TextView tvMacd;
    @BindView(R.id.tv_kdj)
    TextView tvKdj;
    @BindView(R.id.tv_rsi)
    TextView tvRsi;
    @BindView(R.id.combinedchart)
    MyCombinedChart kChart;
    @BindView(R.id.barchart)
    MyCombinedChart volChart;
    @BindView(R.id.ll_bottom_container)
    LinearLayout ll_bottom_container;

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

    private int marketId = 0;
    private int minType = Constant.KLINE_MINTYPE_1;

    private int mainLastSelected = Constant.KLINE_FULLSCREEN_SELECT_MA;
    private int minorLastSelected = Constant.KLINE_FULLSCREEN_SELECT_VOL;

    XAxis xAxisVol, xAxisK;
    YAxis axisLeftVol, axisLeftK;
    YAxis axisRightVol, axisRightK;
    BarDataSet barDataSet;
    private int mKType = Constant.KLINE_MINUTE;
    KLineActivityPresenter mKLinePresenter;
    private DataParse mData;
    private int mBarSpacePercent = 35;
    private boolean isDay = false;
    private int delayMillisMinutes = 20;
    private int mGreen;
    private int mGray;
    private int mRed;

    private KLineMinutesSelectPopupWindow mMinutesSelectPopuWindow;
    private KLineHoursSelectPopupWindow mKLineHoursSelectPopupWindow;
    private KLineDaysSelectPopupWindow mKLineDaysSelectPopupWindow;
    final float mXscaleCombin = 20;
    final float mYscaleCombin = 1;
    final float spaceBottomAndTop = 10;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            notifyDataChange();
        }
    };
    private int mXOffSet;
    private int myOffSet;
    private int mUnSelectColor;
    private int mSelectColor;
    private KProgressHUD dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_klines_fullscreen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
    }

    @Override
    protected void initView() {
        super.initView();
        mXOffSet = getResources().getDimensionPixelOffset(R.dimen.full_screen_kline_tab_width);
        myOffSet = (int) (getResources().getDimensionPixelOffset(R.dimen.kline_fullscreen_bottom_item_height));
        mUnSelectColor = getResources().getColor(R.color.kline_tab_un_select);
        mSelectColor = getResources().getColor(R.color.kline_current_selected);
        mGreen = getResources().getColor(R.color.kline_buy_bg);
        mGray = getResources().getColor(R.color.white);
        mRed = getResources().getColor(R.color.kline_sell_bg);
        Intent intent = getIntent();
        if (intent.hasExtra(UrlParams.marketId)) {
            marketId = intent.getIntExtra(UrlParams.marketId, 0);
        }
        if (intent.hasExtra(UrlParams.minType)) {
            minType = intent.getIntExtra(UrlParams.minType, Constant.KLINE_MINTYPE_1);
        }
        if (intent.hasExtra("kType")) {
            mKType = intent.getIntExtra("kType", Constant.KLINE_MINUTE);
        }
        changeCurrentTransactionPrice();
        if (mKType == Constant.KLINE_MINUTE) {
            mRightitemContainer.setVisibility(View.GONE);
        } else {
            mRightitemContainer.setVisibility(View.VISIBLE);
        }
        initChart();
    }

    @Override
    public boolean isScreenPortrait() {
        return false;
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
        axisLeftVol.setTextColor(getResources().getColor(R.color.kline_buy_bg));
        axisLeftVol.setDrawLabels(true);
        axisLeftVol.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisLeftVol.setSpaceTop(spaceBottomAndTop);
        axisLeftVol.setSpaceBottom(0);//线束数量是不能为0
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
        //bar x y轴
        xAxisK = kChart.getXAxis();
        xAxisK.setDrawLabels(true);
        xAxisK.setDrawGridLines(false);
        xAxisK.setDrawAxisLine(false);
        xAxisK.setTextColor(getResources().getColor(R.color.kline_buy_bg));
        xAxisK.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisK.setGridColor(getResources().getColor(R.color.minute_grayLine));
        xAxisK.setAvoidFirstLastClipping(true);

        //        //K线左边Y轴
        axisLeftK = kChart.getAxisLeft();
        axisLeftK.setDrawGridLines(true);
        axisLeftK.setDrawAxisLine(false);
        axisLeftK.setDrawLabels(true);
        axisLeftK.setTextColor(getResources().getColor(R.color.kline_buy_bg));
        axisLeftK.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftK.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisLeftK.setLabelCount(5, true);
        axisLeftK.setSpaceTop(spaceBottomAndTop);
        axisLeftK.setSpaceBottom(spaceBottomAndTop);

        //K线右边Y轴
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

    @OnClick({R.id.full_screen, R.id.tv_ma, R.id.tv_boll, R.id.tv_vol, R.id.tv_macd,
            R.id.tv_kdj, R.id.tv_rsi, R.id.tab_minute, R.id.tab_minutes, R.id.tab_hour, R.id.tab_day, R.id.tab_mouth})
    public void onClicked(View view) {
        mCurrentItemInfoView.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.full_screen:
                setResult();
                finish();
                break;
            case R.id.tv_ma:
                if (mainLastSelected == Constant.KLINE_FULLSCREEN_SELECT_MA) {
                    return;
                }
                mainLastSelected = Constant.KLINE_FULLSCREEN_SELECT_MA;
                tvBoll.setTextColor(mUnSelectColor);
                tvMa.setTextColor(mSelectColor);
                setMaData(true);
                handler.sendEmptyMessageDelayed(0, delayMillisMinutes);
                break;
            case R.id.tv_boll:
                if (mainLastSelected == Constant.KLINE_FULLSCREEN_SELECT_BOLL) {
                    return;
                }
                tvBoll.setTextColor(mSelectColor);
                tvMa.setTextColor(mUnSelectColor);
                mainLastSelected = Constant.KLINE_FULLSCREEN_SELECT_BOLL;
                setBOLLData();
                handler.sendEmptyMessageDelayed(0, delayMillisMinutes);
                break;
            case R.id.tv_vol:
                if (minorLastSelected == Constant.KLINE_FULLSCREEN_SELECT_VOL) {
                    return;
                }
                setRightItemSelectedState(Constant.KLINE_FULLSCREEN_SELECT_VOL);
                setVolData(true);
                handler.sendEmptyMessageDelayed(0, delayMillisMinutes);
                break;
            case R.id.tv_macd:
                if (minorLastSelected == Constant.KLINE_FULLSCREEN_SELECT_MACD) {
                    return;
                }
                setRightItemSelectedState(Constant.KLINE_FULLSCREEN_SELECT_MACD);
                setMACDData();
                handler.sendEmptyMessageDelayed(0, delayMillisMinutes);
                break;
            case R.id.tv_kdj:
                if (minorLastSelected == Constant.KLINE_FULLSCREEN_SELECT_KDJ) {
                    return;
                }
                setRightItemSelectedState(Constant.KLINE_FULLSCREEN_SELECT_KDJ);
                setKDJData();
                handler.sendEmptyMessageDelayed(0, delayMillisMinutes);
                break;
            case R.id.tv_rsi:
                if (minorLastSelected == Constant.KLINE_FULLSCREEN_SELECT_RSI) {
                    return;
                }
                setRightItemSelectedState(Constant.KLINE_FULLSCREEN_SELECT_RSI);
                setRSIByChart();
                handler.sendEmptyMessageDelayed(0, delayMillisMinutes);
                break;
            case R.id.tab_minute:
                setMinuteClick();
                break;
            case R.id.tab_minutes:
                initMinutesSelect();
                break;
            case R.id.tab_hour:
                initHoursSelect();
                break;
            case R.id.tab_day:
                initDaysSelect();
                break;
            case R.id.tab_mouth:
                setMouthClick();
                break;
        }
    }

    private void setResult() {
        Intent intent = new Intent();
        // 获取用户计算后的结果
        intent.putExtra("kType", mKType);
        intent.putExtra(UrlParams.minType, minType);
        setResult(1, intent);
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
        mKLineDaysSelectPopupWindow.showAtLocation(mTabDay, Gravity.BOTTOM | Gravity.LEFT, (int) (mXOffSet * 3.15), myOffSet)
        ;
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
        mKLineHoursSelectPopupWindow.showAtLocation(mTabHour, Gravity.BOTTOM | Gravity.LEFT, (int) (mXOffSet * 2.12), myOffSet);
    }

    private void initMinutesSelect() {
        if (mMinutesSelectPopuWindow == null) {
            mMinutesSelectPopuWindow = new KLineMinutesSelectPopupWindow(this);
            mMinutesSelectPopuWindow.setOnItemClickListener(new KLineMinutesSelectPopupWindow.KLineMinutesSelectPopupWindowClickListener() {
                @Override
                public void oneMin() {
                    //1分钟
                    setMinutesClick(Constant.KLINE_MINTYPE_1, getResources().getString(R.string.kline_one_min));
                }

                @Override
                public void fiveMin() {
                    //5分钟
                    setMinutesClick(Constant.KLINE_MINTYPE_5, getResources().getString(R.string.kline_five_min));
                }

                @Override
                public void tenMin() {
                    //10分钟
                    setMinutesClick(Constant.KLINE_MINTYPE_10, getResources().getString(R.string.kline_ten_min));
                }

                @Override
                public void fifteenMin() {
                    //15分钟
                    setMinutesClick(Constant.KLINE_MINTYPE_15, getResources().getString(R.string.kline_fifteen_min));
                }

                @Override
                public void thirtyMin() {
                    //30分钟
                    setMinutesClick(Constant.KLINE_MINTYPE_30, getResources().getString(R.string.kline_thirty_min));
                }
            });
        }
        mMinutesSelectPopuWindow.showAtLocation(mTabMinutes, Gravity.BOTTOM | Gravity.LEFT, (int) (mXOffSet * 1.1), myOffSet);
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
        //k线图 数据获取
        mKLinePresenter.getData(map, RequestState.STATE_REFRESH);
    }

    private void setMouthClick() {
        mCurrentItemInfoView.setVisibility(View.GONE);
        mRightitemContainer.setVisibility(View.VISIBLE);
        isDay = true;
        mKType = Constant.KLINE_KLINE;
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
        mRightitemContainer.setVisibility(View.VISIBLE);
        isDay = true;
        mKType = Constant.KLINE_KLINE;
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
        mRightitemContainer.setVisibility(View.VISIBLE);
        isDay = false;
        mKType = Constant.KLINE_KLINE;
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
        mRightitemContainer.setVisibility(View.VISIBLE);
        isDay = false;
        mKType = Constant.KLINE_KLINE;
        this.minType = minType;
        mTabMinute.setSelectState(false, "");
        mTabMinutes.setSelectState(true, name);
        mTabHour.setSelectState(false, getResources().getString(R.string.hour));
        mTabDay.setSelectState(false, getResources().getString(R.string.kline_daily_line));
        mTabMouth.setSelectState(false, "");
        refreshKlineData();
    }

    private void setMinuteClick() {
        mRightitemContainer.setVisibility(View.GONE);
        mCurrentItemInfoView.setVisibility(View.GONE);
        isDay = false;
        mKType = Constant.KLINE_MINUTE;
        minType = Constant.KLINE_MINTYPE_1;
        mTabMinute.setSelectState(true, "");
        mTabMinutes.setSelectState(false, getResources().getString(R.string.kline_minutes));
        mTabHour.setSelectState(false, getResources().getString(R.string.hour));
        mTabDay.setSelectState(false, getResources().getString(R.string.kline_daily_line));
        mTabMouth.setSelectState(false, "");
        refreshKlineData();
    }


    private void setRightItemSelectedState(int currentState) {
        if (minorLastSelected == currentState) {
            return;
        }
        switch (currentState) {
            case Constant.KLINE_FULLSCREEN_SELECT_VOL:
                tvVol.setTextColor(mSelectColor);
                changeRightUnSelectedColor(mUnSelectColor);
                minorLastSelected = currentState;
                break;
            case Constant.KLINE_FULLSCREEN_SELECT_MACD:
                tvMacd.setTextColor(mSelectColor);
                changeRightUnSelectedColor(mUnSelectColor);
                minorLastSelected = currentState;
                break;
            case Constant.KLINE_FULLSCREEN_SELECT_KDJ:
                tvKdj.setTextColor(mSelectColor);
                changeRightUnSelectedColor(mUnSelectColor);
                minorLastSelected = currentState;
                break;
            case Constant.KLINE_FULLSCREEN_SELECT_RSI:
                tvRsi.setTextColor(mSelectColor);
                changeRightUnSelectedColor(mUnSelectColor);
                minorLastSelected = currentState;
                break;
        }
    }

    private void changeRightUnSelectedColor(int unSelectedColor) {
        switch (minorLastSelected) {
            case Constant.KLINE_FULLSCREEN_SELECT_VOL:
                tvVol.setTextColor(unSelectedColor);
                break;
            case Constant.KLINE_FULLSCREEN_SELECT_MACD:
                tvMacd.setTextColor(unSelectedColor);
                break;
            case Constant.KLINE_FULLSCREEN_SELECT_KDJ:
                tvKdj.setTextColor(unSelectedColor);
                break;
            case Constant.KLINE_FULLSCREEN_SELECT_RSI:
                tvRsi.setTextColor(unSelectedColor);
                break;
        }
    }


    @Override
    protected void initData() {
        super.initData();
        mKLinePresenter = new KLineActivityPresenter(this);
        initSelect();
    }

    private void initSelect() {
        if (mKType == Constant.KLINE_MINUTE) {
            Logs.s("  initSelect   分时图   ");
            setMinuteClick();
        } else {

            Logs.s("  initSelect   蜡烛图   ");
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

    private void setKLineDatas() {
        if (mData == null) {
            return;
        }

        mData.initBaseDatas(mData.getKLineDatas(), isDay);
        mData.initKLineMA(mData.getKLineDatas());

        setRightItemSelectedState(Constant.KLINE_FULLSCREEN_SELECT_VOL);
        mainLastSelected = Constant.KLINE_FULLSCREEN_SELECT_MA;
        tvBoll.setTextColor(mUnSelectColor);
        tvMa.setTextColor(mSelectColor);

        setMarkerView(mData.getKLineDatas());
        //成交量
        setVolData(false);
        //MA
        setMaData(true);
        setOffset();
        handler.sendEmptyMessageDelayed(0, delayMillisMinutes);
    }

    @Override
    public void showToast(String str) {
        super.showToast(str);
        dismissDialog();
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

    /**
     * 设置ma（5 10 30 60 ）和蜡烛图 或者 分时线
     */
    private void setMaData(boolean isFromClick) {
        if (kChart == null) {
            return;
        }
        if (mData == null) {
            return;
        }
        int size = mData.getKLineDatas().size();   //点的个数
        CandleDataSet candleDataSet = new CandleDataSet(mData.getCandleEntries(), "KLine");
        if (mKType == Constant.KLINE_MINUTE) {
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
        candleDataSet.setDecreasingColor(mRed);//设置开盘价高于收盘价的颜色
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setIncreasingColor(mGreen);//设置开盘价地狱收盘价的颜色
        candleDataSet.setIncreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setNeutralColor(mGreen);//设置开盘价等于收盘价的颜色    可在CandleStickChartRenderer drawDataSet（）中改规则
        candleDataSet.setShadowColorSameAsCandle(true);
        candleDataSet.setValueTextSize(10f);
        candleDataSet.setDrawValues(false);
        candleDataSet.setShadowWidth(1f);
        candleDataSet.setBarSpace(0.2f);
        candleDataSet.setShadowColor(Color.DKGRAY);
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        CandleData candleData = new CandleData(mData.getXVals(), candleDataSet);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        /******此处修复如果显示的点的个数达不到MA均线的位置所有的点都从0开始计算最小值的问题******************************/
        if (mKType == Constant.KLINE_KLINE) {
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
        } else if (mKType == Constant.KLINE_MINUTE) {
            sets.add(setMinuteLine(mData.getMinuteLineEntries()));
        }
        CombinedData combinedData = new CombinedData(mData.getXVals());
        LineData lineData = new LineData(mData.getXVals(), sets);
        combinedData.setData(candleData);
        combinedData.setData(lineData);
        kChart.setData(combinedData);
        if (!isFromClick) {
            setViewPortHandler(kChart);
            setViewPortHandler(volChart);
        }

    }

    private void setViewPortHandler(MyCombinedChart chart) {
        final ViewPortHandler viewPortHandlerCombin = chart.getViewPortHandler();
        viewPortHandlerCombin.setMaximumScaleX(culcMaxscale(mData.getXVals().size()));
        Matrix matrixCombin = viewPortHandlerCombin.getMatrixTouch();
        matrixCombin.postScale(mXscaleCombin, mYscaleCombin);
        chart.moveViewToX(mData.getKLineDatas().size() - 1);
    }

    /**
     * 设置成交量数据
     */
    private void setVolData(boolean isFromClick) {
        if (volChart == null) {
            return;
        }
        if (mData == null) {
            return;
        }
        axisLeftVol.setSpaceBottom(0);
        barDataSet = new BarDataSet(mData.getBarEntries(), "成交量");
        barDataSet.setBarSpacePercent(mBarSpacePercent); //bar空隙
        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightAlpha(255);
        barDataSet.setHighLightColor(Color.WHITE);
        barDataSet.setDrawValues(false); //BarChartRenderer drawDataSet（）中修改规则
        barDataSet.setColor(Color.RED);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(mGreen);
        colors.add(mRed);
        barDataSet.setColors(colors);
        BarData barData = new BarData(mData.getXVals(), barDataSet);
        CombinedData combinedBarData = new CombinedData(mData.getXVals());
        combinedBarData.setData(barData);
        volChart.setData(combinedBarData);

//        final ViewPortHandler viewPortHandlerBar = volChart.getViewPortHandler();
//        viewPortHandlerBar.setMaximumScaleX(culcMaxscale(mData.getXVals().size()));
//        Matrix touchmatrix = viewPortHandlerBar.getMatrixTouch();
//        touchmatrix.postScale(mXscaleCombin, mYscaleCombin);
//        volChart.moveViewToX(mData.getKLineDatas().size() - 1);
        if (!isFromClick) {
            setViewPortHandler(volChart);
            setViewPortHandler(kChart);
        }
    }

    private void setMACDData() {
        if (mData == null) {
            return;
        }
        axisLeftVol.setSpaceBottom(spaceBottomAndTop);
        mData.initMACD(mData.getKLineDatas());
        BarDataSet set = new BarDataSet(mData.getMacdData(), "BarDataSet");
        set.setBarSpacePercent(mBarSpacePercent); //bar空隙
        set.setHighlightEnabled(true);
        set.setHighLightAlpha(255);
        set.setHighLightColor(Color.WHITE);
        set.setDrawValues(false);
        set.setColor(Color.RED);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.RED);//BarChartRenderer drawDataSet（）中修改规则
        set.setColors(colors);

        BarData barData = new BarData(mData.getXVals(), set);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(setMACDMaLine(0, mData.getXVals(), (ArrayList<Entry>) mData.getDeaData()));
        sets.add(setMACDMaLine(1, mData.getXVals(), (ArrayList<Entry>) mData.getDifData()));
        LineData lineData = new LineData(mData.getXVals(), sets);
        CombinedData combinedData = new CombinedData(mData.getXVals());
        combinedData.setData(barData);
        combinedData.setData(lineData);
        volChart.setData(combinedData);


//        final ViewPortHandler viewPortHandlerBar = volChart.getViewPortHandler();
//        viewPortHandlerBar.setMaximumScaleX(culcMaxscale(mData.getXVals().size()));
//        Matrix touchmatrix = viewPortHandlerBar.getMatrixTouch();
//        touchmatrix.postScale(mXscaleCombin, mYscaleCombin);
//        volChart.moveViewToX(mData.getKLineDatas().size() - 1);
//        setViewPortHandler(volChart);
//        setViewPortHandler(kChart);
    }

    private void setBOLLData() {
        if (mData == null) {
            return;
        }
        mData.initBOLL(mData.getKLineDatas());
        CandleDataSet candleDataSet = new CandleDataSet(mData.getCandleEntries(), "");
        candleDataSet.setHighLightColor(Color.WHITE);
        candleDataSet.setDecreasingColor(mRed);//设置开盘价高于收盘价的颜色
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setIncreasingColor(mGreen);//设置开盘价地狱收盘价的颜色
        candleDataSet.setIncreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setNeutralColor(mGreen);//设置开盘价等于收盘价的颜色    可在CandleStickChartRenderer drawDataSet（）中改规则
        candleDataSet.setShadowColorSameAsCandle(true);
        candleDataSet.setValueTextSize(10f);
        candleDataSet.setDrawValues(false);
        candleDataSet.setShadowWidth(1f);
        candleDataSet.setBarSpace(0.2f);
        candleDataSet.setShadowColor(Color.DKGRAY);
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        CandleData candleData = new CandleData(mData.getXVals(), candleDataSet);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(setBollLine(0, mData.getXVals(), (ArrayList<Entry>) mData.getBollDataUP()));
        sets.add(setBollLine(1, mData.getXVals(), (ArrayList<Entry>) mData.getBollDataMB()));
        sets.add(setBollLine(2, mData.getXVals(), (ArrayList<Entry>) mData.getBollDataDN()));
        LineData lineData = new LineData(mData.getXVals(), sets);

        CombinedData combinedData = new CombinedData(mData.getXVals());
        combinedData.setData(candleData);  //蜡烛数据
//        combinedData.setData(lineData);   //线数据
        kChart.setData(combinedData);

//        setViewPortHandler(kChart);
//        setViewPortHandler(volChart);
    }

    private void setKDJData() {
        if (mData == null) {
            return;
        }
        axisLeftVol.setSpaceBottom(spaceBottomAndTop);
        mData.initKDJ(mData.getKLineDatas());

        BarDataSet set = new BarDataSet(mData.getBarDatasKDJ(), "KDJDataSet");
        set.setBarSpacePercent(mBarSpacePercent); //bar空隙
        set.setHighlightEnabled(true);
        set.setHighLightAlpha(255);
        set.setHighLightColor(getResources().getColor(R.color.white));
        set.setDrawValues(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(getResources().getColor(R.color.transparent));

        BarData barData = new BarData(mData.getXVals(), set);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(setKDJMaLine(0, mData.getXVals(), (ArrayList<Entry>) mData.getkData()));
        sets.add(setKDJMaLine(1, mData.getXVals(), (ArrayList<Entry>) mData.getdData()));
        sets.add(setKDJMaLine(2, mData.getXVals(), (ArrayList<Entry>) mData.getjData()));
        LineData lineData = new LineData(mData.getXVals(), sets);

        CombinedData combinedData = new CombinedData(mData.getXVals());
        combinedData.setData(barData);
        combinedData.setData(lineData);
        volChart.setData(combinedData);

//        final ViewPortHandler viewPortHandlerCombin = volChart.getViewPortHandler();
//        viewPortHandlerCombin.setMaximumScaleX(culcMaxscale(mData.getXVals().size()));
//        Matrix matrixCombin = viewPortHandlerCombin.getMatrixTouch();
//        matrixCombin.postScale(mXscaleCombin, mYscaleCombin);
//        volChart.moveViewToX(mData.getKLineDatas().size() - 1);
//        setViewPortHandler(volChart);
//        setViewPortHandler(kChart);
    }

    private void setRSIByChart() {
        if (mData == null) {
            return;
        }
        axisLeftVol.setSpaceBottom(spaceBottomAndTop);
        mData.initRSI(mData.getKLineDatas());
        BarDataSet set = new BarDataSet(mData.getBarDatasRSI(), "RSIDataSet");
        set.setBarSpacePercent(20); //bar空隙
        set.setHighlightEnabled(true);
        set.setHighLightAlpha(255);
        set.setHighLightColor(getResources().getColor(R.color.white));
        set.setDrawValues(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(getResources().getColor(R.color.transparent));

        BarData barData = new BarData(mData.getXVals(), set);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(setRSILine(0, mData.getXVals(), (ArrayList<Entry>) mData.getRsiData6()));
        sets.add(setRSILine(1, mData.getXVals(), (ArrayList<Entry>) mData.getRsiData12()));
        sets.add(setRSILine(2, mData.getXVals(), (ArrayList<Entry>) mData.getRsiData24()));
        LineData lineData = new LineData(mData.getXVals(), sets);

        CombinedData combinedData = new CombinedData(mData.getXVals());
        combinedData.setData(barData);
        combinedData.setData(lineData);
        volChart.setData(combinedData);

//        final ViewPortHandler viewPortHandlerCombin = volChart.getViewPortHandler();
//        viewPortHandlerCombin.setMaximumScaleX(culcMaxscale(mData.getXVals().size()));
//        Matrix matrixCombin = viewPortHandlerCombin.getMatrixTouch();
//        matrixCombin.postScale(mXscaleCombin, mYscaleCombin);
//        volChart.moveViewToX(mData.getKLineDatas().size() - 1);
//        setViewPortHandler(volChart);
//        setViewPortHandler(kChart);
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

        volChart.notifyDataSetChanged();
        kChart.notifyDataSetChanged();

        volChart.invalidate();
        kChart.invalidate();


        dismissDialog();
    }

    private float culcMaxscale(float count) {
        float max = 1;
        max = count / 127 * 5;
        return max;
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

    private LineDataSet setMACDMaLine(int type, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + type);
        lineDataSetMa.setHighlightEnabled(false);
        lineDataSetMa.setDrawValues(false);

        //DEA
        if (type == 0) {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_macd_dea));
        } else {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_macd_dif));
        }

        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);

        return lineDataSetMa;
    }


    @NonNull
    private LineDataSet setKDJMaLine(int type, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + type);
        lineDataSetMa.setHighlightEnabled(false);
        lineDataSetMa.setDrawValues(false);
        if (type == 0) {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_kdj_k));
        } else if (type == 1) {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_kdj_d));
        } else if (type == 2) {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_kdj_j));
        }

        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);

        return lineDataSetMa;
    }

    @NonNull
    private LineDataSet setRSILine(int type, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + type);
        lineDataSetMa.setHighlightEnabled(false);
        lineDataSetMa.setDrawValues(false);
        if (type == 0) {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_rsi_r));
        } else if (type == 1) {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_rsi_s));
        } else if (type == 2) {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_rsi_i));
        }

        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);

        return lineDataSetMa;
    }

    @NonNull
    private LineDataSet setBollLine(int type, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + type);
        lineDataSetMa.setHighlightEnabled(false);
        lineDataSetMa.setDrawValues(false);
        if (type == 0) {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_boll_up));
        } else if (type == 1) {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_boll_mb));
        } else if (type == 2) {
            lineDataSetMa.setColor(getResources().getColor(R.color.kline_boll_dn));
        } else {
            lineDataSetMa.setColor(Color.GREEN);
        }

        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);

        return lineDataSetMa;
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

    private void changeCurrentTransactionPrice() {
        if (OAXApplication.coinsInfoMap.containsKey(marketId)) {
            IndexPageBean.DataBean.AllMaketListBean.MarketListBean bean = OAXApplication.coinsInfoMap.get(marketId);
            KLog.d("changeCurrentTransactionPrice = " + new Gson().toJson(bean));
            tvCoinName.setText(bean.getCoinName() + "/" + bean.getMarketCoinName());
            setPriceChange(bean);
        }
    }

    private void setPriceChange(IndexPageBean.DataBean.AllMaketListBean.MarketListBean currentCoin) {
        String newest = getResources().getString(R.string.newest_price);
        String limit = getResources().getString(R.string.newest_limit);
//        if (lastCoin == null) {
        //最近成交价
        BigDecimal lastTradePrice = new BigDecimal(currentCoin.getLastTradePrice());
        BigDecimal decimal = lastTradePrice.setScale(currentCoin.getPriceDecimals(), BigDecimal.ROUND_DOWN);

        //人民币价格
        BigDecimal cny = currentCoin.getCnyPrice().multiply(new BigDecimal(currentCoin.getLastTradePrice()))
                .setScale(2, BigDecimal.ROUND_DOWN);
        tvRnb.setText("≈¥" + cny.toPlainString());
        //涨跌幅
        double incRate = Double.parseDouble(currentCoin.getIncRate());
        BigDecimal incRateBigDecimal = new BigDecimal(currentCoin.getIncRate()).setScale(2, BigDecimal.ROUND_DOWN);

        if (incRate > 0) {
            tvNewPrice.setTextColor(mGreen);
            tvLimit.setTextColor(mGreen);
        } else if (incRate == 0) {
            tvNewPrice.setTextColor(mGray);
            tvLimit.setTextColor(mGray);
        } else {
            tvNewPrice.setTextColor(mRed);
            tvLimit.setTextColor(mRed);
        }
        tvNewPrice.setText(newest + decimal.toPlainString());
        if (incRate > 0) {
            tvLimit.setText("+" + limit + incRateBigDecimal.toPlainString() + "%");
        } else {
            tvLimit.setText(limit + incRateBigDecimal.toPlainString() + "%");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UpdateTradingInfoEvent event) {
        KLog.d("UpdateTradingInfoEvent");
        changeCurrentTransactionPrice();
    }

    @Override
    public void setTradingInfoData(CommonJsonToBean<TradingInfoBean> data) {
        if (kChart == null) {
            return;
        }
        if (volChart == null) {
            return;
        }
        List<TradingInfoBean.KlineListBean> klineList = data.getData().getKlineList();
        if (klineList != null && klineList.size() > 0) {
            mData = new DataParse();
            mData.setKLine(klineList);

            Logs.s("  K线图数据:  " + klineList.toString());
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

    @Override
    public void collectMarketState(CommonJsonToBean<String> data) {

    }

    @Override
    public void cancelCollectMarket(CommonJsonToBean<String> data) {

    }

    @Override
    public void setTopicKlineData(List<TradingInfoBean.KlineListBean> data) {

    }

    @Override
    public void onBackPressed() {
        setResult();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler = null;
    }
}
