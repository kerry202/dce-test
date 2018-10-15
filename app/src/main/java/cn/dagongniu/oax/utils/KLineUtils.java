package cn.dagongniu.oax.utils;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.socks.library.KLog;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.kline.bean.KlineInfoBean;
import cn.dagongniu.oax.kline.bean.TradingInfoBean;
import cn.dagongniu.oax.main.bean.IndexPageBean;

public class KLineUtils {

    /**
     * Prevent class instantiation.
     */
    private KLineUtils() {
    }

    public static String getVolUnit(float num) {

        int e = (int) Math.floor(Math.log10(num));

        if (e >= 8) {
            return "亿手";
        } else if (e >= 4) {
            return "万手";
        } else {
            return "手";
        }
    }

    public static float getVolmax(List<TradingInfoBean.KlineListBean> datas) {
        if (datas == null) {
            return 0;
        }
        float volMax = 0f;
        for (int i = 0; i < datas.size(); i++) {
            volMax = Math.max(Float.valueOf(datas.get(i).getQty().toPlainString()), volMax);
        }

        return volMax;
    }

    public static float getMacdmax(List<BarEntry> macdData, List<Entry> deaData, List<Entry> difData) {
        if (macdData == null || deaData == null || difData == null) {
            return 0;
        }
        float volMax = 0f;
        for (int i = 0; i < macdData.size(); i++) {
            volMax = Math.max(macdData.get(i).getVal(), volMax);
        }
        for (int i = 0; i < deaData.size(); i++) {
            volMax = Math.max(deaData.get(i).getVal(), volMax);
        }
        for (int i = 0; i < difData.size(); i++) {
            volMax = Math.max(difData.get(i).getVal(), volMax);
        }
        KLog.d("volMax = " + volMax);
        return volMax;
    }

    public static float getMacdmin(List<BarEntry> macdData, List<Entry> deaData, List<Entry> difData) {
        if (macdData == null || deaData == null || difData == null) {
            return 0;
        }
        float volMin = 0f;
        for (int i = 0; i < macdData.size(); i++) {
            volMin = Math.min(macdData.get(i).getVal(), volMin);
        }
        for (int i = 0; i < deaData.size(); i++) {
            volMin = Math.min(deaData.get(i).getVal(), volMin);
        }
        for (int i = 0; i < difData.size(); i++) {
            volMin = Math.min(difData.get(i).getVal(), volMin);
        }
        KLog.d("volMin = " + volMin);
        return volMin;
    }

    public static float getKdjmax(List<BarEntry> barDatasKDJ, List<Entry> kData, List<Entry> dData, List<Entry> jData) {
        if (barDatasKDJ == null || kData == null || dData == null || jData == null) {
            return 0;
        }
        float volMax = 0f;
        for (int i = 0; i < barDatasKDJ.size(); i++) {
            volMax = Math.max(barDatasKDJ.get(i).getVal(), volMax);
        }
        for (int i = 0; i < kData.size(); i++) {
            volMax = Math.max(kData.get(i).getVal(), volMax);
        }
        for (int i = 0; i < dData.size(); i++) {
            volMax = Math.max(dData.get(i).getVal(), volMax);
        }
        for (int i = 0; i < jData.size(); i++) {
            volMax = Math.max(jData.get(i).getVal(), volMax);
        }
        KLog.d("volMax = " + volMax);
        return volMax;
    }


    public static float getKdjmin(List<BarEntry> barDatasKDJ, List<Entry> kData, List<Entry> dData, List<Entry> jData) {
        if (barDatasKDJ == null || kData == null || dData == null || jData == null) {
            return 0;
        }
        float volMin = 0f;
        for (int i = 0; i < barDatasKDJ.size(); i++) {
            volMin = Math.min(barDatasKDJ.get(i).getVal(), volMin);
        }
        for (int i = 0; i < kData.size(); i++) {
            volMin = Math.min(kData.get(i).getVal(), volMin);
        }
        for (int i = 0; i < dData.size(); i++) {
            volMin = Math.min(dData.get(i).getVal(), volMin);
        }
        for (int i = 0; i < jData.size(); i++) {
            volMin = Math.min(jData.get(i).getVal(), volMin);
        }
        KLog.d("volMin = " + volMin);
        return volMin;
    }

    public static float getRsimax(List<BarEntry> barDatasRSI, List<Entry> rsiData6, List<Entry> rsiData12, List<Entry> rsiData24) {
        if (barDatasRSI == null || rsiData6 == null || rsiData12 == null || rsiData24 == null) {
            return 0;
        }
        float volMax = 0f;
        for (int i = 0; i < barDatasRSI.size(); i++) {
            volMax = Math.max(barDatasRSI.get(i).getVal(), volMax);
        }
        for (int i = 0; i < rsiData6.size(); i++) {
            volMax = Math.max(rsiData6.get(i).getVal(), volMax);
        }
        for (int i = 0; i < rsiData12.size(); i++) {
            volMax = Math.max(rsiData12.get(i).getVal(), volMax);
        }
        for (int i = 0; i < rsiData24.size(); i++) {
            volMax = Math.max(rsiData24.get(i).getVal(), volMax);
        }
        KLog.d("volMax = " + volMax);
        return volMax;
    }

    public static float getRsimin(List<BarEntry> barDatasRSI, List<Entry> rsiData6, List<Entry> rsiData12, List<Entry> rsiData24) {
        if (barDatasRSI == null || rsiData6 == null || rsiData12 == null || rsiData24 == null) {
            return 0;
        }
        float volMin = 0f;
        for (int i = 0; i < barDatasRSI.size(); i++) {
            volMin = Math.min(barDatasRSI.get(i).getVal(), volMin);
        }
        for (int i = 0; i < rsiData6.size(); i++) {
            volMin = Math.min(rsiData6.get(i).getVal(), volMin);
        }
        for (int i = 0; i < rsiData12.size(); i++) {
            volMin = Math.min(rsiData12.get(i).getVal(), volMin);
        }
        for (int i = 0; i < rsiData24.size(); i++) {
            volMin = Math.min(rsiData24.get(i).getVal(), volMin);
        }
        KLog.d("volMin = " + volMin);
        return volMin;
    }

    public static String formatFloat(float num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }

    /**
     * 确定k线左边的Marker的格式化精度
     */
    public static String confirmLeftMarkerPattern(int marketId) {
        String pattern = "#0.";
        IndexPageBean.DataBean.AllMaketListBean.MarketListBean bean = OAXApplication.coinsInfoMap.get(marketId);
        if (bean != null) {
            int priceDecimal = bean.getPriceDecimals();
            for (int i = 0; i < priceDecimal; i++) {
                pattern = pattern + "0";
            }
        } else {
            pattern = "#0.0000";
        }
        KLog.d("pattern = " + pattern);
        return pattern;
    }
}
