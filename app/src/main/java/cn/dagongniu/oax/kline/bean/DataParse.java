package cn.dagongniu.oax.kline.bean;

import android.util.SparseArray;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.dagongniu.oax.utils.DateUtils;

public class DataParse {
    private ArrayList<TradingInfoBean.KlineListBean> kDatas = new ArrayList<>();
    private ArrayList<String> xVals = new ArrayList<>();       //X轴数据
    private ArrayList<BarEntry> barEntries = new ArrayList<>();//成交量数据
    private ArrayList<CandleEntry> candleEntries = new ArrayList<>();//K线数据
    private ArrayList<Entry> minuteLineEntries = new ArrayList<>();//分时数据

    private ArrayList<Entry> ma5DataL = new ArrayList<>();
    private ArrayList<Entry> ma10DataL = new ArrayList<>();
    private ArrayList<Entry> ma30DataL = new ArrayList<>();
    private ArrayList<Entry> ma60DataL = new ArrayList<>();

    private ArrayList<Entry> ma5DataV = new ArrayList<>();
    private ArrayList<Entry> ma10DataV = new ArrayList<>();
    private ArrayList<Entry> ma20DataV = new ArrayList<>();
    private ArrayList<Entry> ma30DataV = new ArrayList<>();

    private List<BarEntry> macdData = new ArrayList<>();
    private List<Entry> deaData = new ArrayList<>();
    private List<Entry> difData = new ArrayList<>();

    private List<BarEntry> barDatasKDJ = new ArrayList<>();
    private List<Entry> kData = new ArrayList<>();
    private List<Entry> dData = new ArrayList<>();
    private List<Entry> jData = new ArrayList<>();

    private List<BarEntry> barDatasWR = new ArrayList<>();
    private List<Entry> wrData13 = new ArrayList<>();
    private List<Entry> wrData34 = new ArrayList<>();
    private List<Entry> wrData89 = new ArrayList<>();

    private List<BarEntry> barDatasRSI = new ArrayList<>();
    private List<Entry> rsiData6 = new ArrayList<>();
    private List<Entry> rsiData12 = new ArrayList<>();
    private List<Entry> rsiData24 = new ArrayList<>();

    private List<BarEntry> barDatasBOLL = new ArrayList<>();
    private List<Entry> bollDataUP = new ArrayList<>();
    private List<Entry> bollDataMB = new ArrayList<>();
    private List<Entry> bollDataDN = new ArrayList<>();

    private List<BarEntry> barDatasEXPMA = new ArrayList<>();
    private List<Entry> expmaData5 = new ArrayList<>();
    private List<Entry> expmaData10 = new ArrayList<>();
    private List<Entry> expmaData20 = new ArrayList<>();
    private List<Entry> expmaData60 = new ArrayList<>();

    private List<BarEntry> barDatasDMI = new ArrayList<>();
    private List<Entry> dmiDataDI1 = new ArrayList<>();
    private List<Entry> dmiDataDI2 = new ArrayList<>();
    private List<Entry> dmiDataADX = new ArrayList<>();
    private List<Entry> dmiDataADXR = new ArrayList<>();

    private HashMap<Integer, Float> ma5Vols = new HashMap<>();
    private HashMap<Integer, Float> ma10Vols = new HashMap<>();
    private HashMap<Integer, Float> ma30Vols = new HashMap<>();

    private HashMap<Integer, Float> ma60Vols = new HashMap<>();

    private float baseValue;
    private float permaxmin;
    private float volmax;
    private String code = "sz002081";
    private SparseArray<String> xValuesLabel = new SparseArray<>();

    /**
     * @param mData
     */
    public void setKLine(List<TradingInfoBean.KlineListBean> mData) {
        kDatas.addAll(mData);
    }

    //得到X轴数据 成交量数据 K线数据 分时数据
    public void initBaseDatas(ArrayList<TradingInfoBean.KlineListBean> datas, boolean isDay) {
        if (null == datas) {
            return;
        }
        xVals = new ArrayList<>();//X轴数据
        barEntries = new ArrayList<>();//成交量数据
        candleEntries = new ArrayList<>();//K线数据
        minuteLineEntries = new ArrayList<>();//分时数据
        for (int i = 0, j = 0; i < datas.size(); i++, j++) {
            String time = datas.get(i).getCreateTime();
            String x = "";
            if (isDay) {
                try {
                    x = time.substring(5, 10);
                } catch (Exception e) {

                }

            } else {
                try {
                    x = time.substring(11, 16);
                } catch (Exception e) {

                }
            }

            xVals.add(x);
//            barEntries.add(new BarEntry(datas.get(i).getQty().floatValue(), i, datas.get(i).getHigh().floatValue(), datas.get(i).getLow().floatValue(), datas.get(i).getOpen().floatValue(), datas.get(i).getClose().floatValue(), true));
//            candleEntries.add(new CandleEntry(i, datas.get(i).getHigh().floatValue(), datas.get(i).getLow().floatValue(), datas.get(i).getOpen().floatValue(), datas.get(i).getClose().floatValue()));
//            minuteLineEntries.add(new Entry(datas.get(i).getClose().floatValue(), i));
            barEntries.add(new BarEntry(Float.valueOf(datas.get(i).getQty().toPlainString()), i, Float.valueOf(datas.get(i).getHigh().toPlainString()), Float.valueOf(datas.get(i).getLow().toPlainString()), Float.valueOf(datas.get(i).getOpen().toPlainString()), Float.valueOf(datas.get(i).getClose().toPlainString()), true));
            candleEntries.add(new CandleEntry(i, Float.valueOf(datas.get(i).getHigh().toPlainString()), Float.valueOf(datas.get(i).getLow().toPlainString()), Float.valueOf(datas.get(i).getOpen().toPlainString()), Float.valueOf(datas.get(i).getClose().toPlainString())));
            minuteLineEntries.add(new Entry(Float.valueOf(datas.get(i).getClose().toPlainString()), i));
        }
    }

    /**
     * 初始化K线图均线
     *
     * @param datas
     */
    public void initKLineMA(ArrayList<TradingInfoBean.KlineListBean> datas) {
        if (null == datas) {
            return;
        }
        ma5DataL = new ArrayList<>();
        ma10DataL = new ArrayList<>();
        ma30DataL = new ArrayList<>();
        ma60DataL = new ArrayList<>();

        KMAEntity kmaEntity5 = new KMAEntity(datas, 5);
        KMAEntity kmaEntity10 = new KMAEntity(datas, 10);
        KMAEntity kmaEntity30 = new KMAEntity(datas, 30);
        KMAEntity kmaEntity60 = new KMAEntity(datas, 60);
        for (int i = 0; i < kmaEntity5.getMAs().size(); i++) {
            if (i >= 4)
                ma5DataL.add(new Entry(kmaEntity5.getMAs().get(i), i));
            ma5Vols.put(i, kmaEntity5.getMAs().get(i));
            if (i >= 9)
                ma10DataL.add(new Entry(kmaEntity10.getMAs().get(i), i));
            ma10Vols.put(i, kmaEntity10.getMAs().get(i));
            if (i >= 29)
                ma30DataL.add(new Entry(kmaEntity30.getMAs().get(i), i));
            ma30Vols.put(i, kmaEntity30.getMAs().get(i));
            if (i >= 59)
                ma60DataL.add(new Entry(kmaEntity60.getMAs().get(i), i));
            ma60Vols.put(i, kmaEntity60.getMAs().get(i));
        }

    }

    /**
     * 初始化成交量均线
     *
     * @param datas
     */
    public void initVlumeMA(ArrayList<TradingInfoBean.KlineListBean> datas) {
        if (null == datas) {
            return;
        }
        ma5DataV = new ArrayList<>();
        ma10DataV = new ArrayList<>();
        ma20DataV = new ArrayList<>();
        ma30DataV = new ArrayList<>();

        VMAEntity vmaEntity5 = new VMAEntity(datas, 5);
        VMAEntity vmaEntity10 = new VMAEntity(datas, 10);
        VMAEntity vmaEntity20 = new VMAEntity(datas, 20);
        VMAEntity vmaEntity30 = new VMAEntity(datas, 30);
        for (int i = 0; i < vmaEntity5.getMAs().size(); i++) {
            ma5DataV.add(new Entry(vmaEntity5.getMAs().get(i), i));
            ma10DataV.add(new Entry(vmaEntity10.getMAs().get(i), i));
            ma20DataV.add(new Entry(vmaEntity20.getMAs().get(i), i));
            ma30DataV.add(new Entry(vmaEntity30.getMAs().get(i), i));
        }

    }

    /**
     * 初始化MACD
     *
     * @param datas
     */
    public void initMACD(ArrayList<TradingInfoBean.KlineListBean> datas) {
        MACDEntity macdEntity = new MACDEntity(datas);
        macdData = new ArrayList<>();
        deaData = new ArrayList<>();
        difData = new ArrayList<>();
        for (int i = 0; i < macdEntity.getMACD().size(); i++) {
//            macdData.add(new BarEntry(macdEntity.getMACD().get(i), i));
//            macdData.add(new BarEntry(macdEntity.getMACD().get(i), i, datas.get(i).getHigh().floatValue(), datas.get(i).getLow().floatValue(), datas.get(i).getOpen().floatValue(), datas.get(i).getClose().floatValue(), false));
            macdData.add(new BarEntry(macdEntity.getMACD().get(i), i, Float.valueOf(datas.get(i).getHigh().toPlainString()), Float.valueOf(datas.get(i).getLow().toPlainString()), Float.valueOf(datas.get(i).getOpen().toPlainString()), Float.valueOf(datas.get(i).getClose().toPlainString()), false));
            deaData.add(new Entry(macdEntity.getDEA().get(i), i));
            difData.add(new Entry(macdEntity.getDIF().get(i), i));
        }
    }

    /**
     * 初始化KDJ
     *
     * @param datas
     */
    public void initKDJ(ArrayList<TradingInfoBean.KlineListBean> datas) {
        KDJEntity kdjEntity = new KDJEntity(datas, 9);

        barDatasKDJ = new ArrayList<>();
        kData = new ArrayList<>();
        dData = new ArrayList<>();
        jData = new ArrayList<>();
        for (int i = 0; i < kdjEntity.getD().size(); i++) {
            barDatasKDJ.add(new BarEntry(0, i));
            kData.add(new Entry(kdjEntity.getK().get(i), i));
            dData.add(new Entry(kdjEntity.getD().get(i), i));
            jData.add(new Entry(kdjEntity.getJ().get(i), i));
        }
    }

    /**
     * 初始化WR
     *
     * @param datas
     */
    public void initWR(ArrayList<TradingInfoBean.KlineListBean> datas) {
        WREntity wrEntity13 = new WREntity(datas, 13);
        WREntity wrEntity34 = new WREntity(datas, 34);
        WREntity wrEntity89 = new WREntity(datas, 89);

        barDatasWR = new ArrayList<>();
        wrData13 = new ArrayList<>();
        wrData34 = new ArrayList<>();
        wrData89 = new ArrayList<>();
        for (int i = 0; i < wrEntity13.getWRs().size(); i++) {
            barDatasWR.add(new BarEntry(0, i));
            wrData13.add(new Entry(wrEntity13.getWRs().get(i), i));
            wrData34.add(new Entry(wrEntity34.getWRs().get(i), i));
            wrData89.add(new Entry(wrEntity89.getWRs().get(i), i));
        }
    }

    /**
     * 初始化RSI
     *
     * @param datas
     */
    public void initRSI(ArrayList<TradingInfoBean.KlineListBean> datas) {
        RSIEntity rsiEntity6 = new RSIEntity(datas, 6);
        RSIEntity rsiEntity12 = new RSIEntity(datas, 12);
        RSIEntity rsiEntity24 = new RSIEntity(datas, 24);

        barDatasRSI = new ArrayList<>();
        rsiData6 = new ArrayList<>();
        rsiData12 = new ArrayList<>();
        rsiData24 = new ArrayList<>();

        for (int i = 0; i < rsiEntity6.getRSIs().size(); i++) {
            barDatasRSI.add(new BarEntry(0, i));
            rsiData6.add(new Entry(rsiEntity6.getRSIs().get(i), i));
            rsiData12.add(new Entry(rsiEntity12.getRSIs().get(i), i));
            rsiData24.add(new Entry(rsiEntity24.getRSIs().get(i), i));
        }
    }

    /**
     * 初始化BOLL
     *
     * @param datas
     */
    public void initBOLL(ArrayList<TradingInfoBean.KlineListBean> datas) {
        BOLLEntity bollEntity = new BOLLEntity(datas, 20);

        barDatasBOLL = new ArrayList<>();
        bollDataUP = new ArrayList<>();
        bollDataMB = new ArrayList<>();
        bollDataDN = new ArrayList<>();
        for (int i = 0; i < bollEntity.getUPs().size(); i++) {
            barDatasBOLL.add(new BarEntry(0, i));
            bollDataUP.add(new Entry(bollEntity.getUPs().get(i), i));
            bollDataMB.add(new Entry(bollEntity.getMBs().get(i), i));
            bollDataDN.add(new Entry(bollEntity.getDNs().get(i), i));
        }
    }

    /**
     * 初始化BOLL
     *
     * @param datas
     */
    public void initEXPMA(ArrayList<TradingInfoBean.KlineListBean> datas) {
        EXPMAEntity expmaEntity5 = new EXPMAEntity(datas, 5);
        EXPMAEntity expmaEntity10 = new EXPMAEntity(datas, 10);
        EXPMAEntity expmaEntity20 = new EXPMAEntity(datas, 20);
        EXPMAEntity expmaEntity60 = new EXPMAEntity(datas, 60);

        barDatasEXPMA = new ArrayList<>();
        expmaData5 = new ArrayList<>();
        expmaData10 = new ArrayList<>();
        expmaData20 = new ArrayList<>();
        expmaData60 = new ArrayList<>();
        for (int i = 0; i < expmaEntity5.getEXPMAs().size(); i++) {
            barDatasEXPMA.add(new BarEntry(0, i));
            expmaData5.add(new Entry(expmaEntity5.getEXPMAs().get(i), i));
            expmaData10.add(new Entry(expmaEntity10.getEXPMAs().get(i), i));
            expmaData20.add(new Entry(expmaEntity20.getEXPMAs().get(i), i));
            expmaData60.add(new Entry(expmaEntity60.getEXPMAs().get(i), i));
        }
    }

    /**
     * 初始化DMI
     *
     * @param datas
     */
    public void initDMI(ArrayList<TradingInfoBean.KlineListBean> datas) {
        DMIEntity dmiEntity = new DMIEntity(datas, 12, 7, 6, true);

        barDatasDMI = new ArrayList<>();
        dmiDataDI1 = new ArrayList<>();
        dmiDataDI2 = new ArrayList<>();
        dmiDataADX = new ArrayList<>();
        dmiDataADXR = new ArrayList<>();
        for (int i = 0; i < dmiEntity.getDI1s().size(); i++) {
            barDatasDMI.add(new BarEntry(0, i));
            dmiDataDI1.add(new Entry(dmiEntity.getDI1s().get(i), i));
            dmiDataDI2.add(new Entry(dmiEntity.getDI2s().get(i), i));
            dmiDataADX.add(new Entry(dmiEntity.getADXs().get(i), i));
            dmiDataADXR.add(new Entry(dmiEntity.getADXRs().get(i), i));
        }
    }

    /**
     * 得到Y轴最小值
     *
     * @return
     */
    public float getMin() {
        return baseValue - permaxmin;
    }

    /**
     * 得到Y轴最大值
     *
     * @return
     */
    public float getMax() {
        return baseValue + permaxmin;
    }

    /**
     * 得到百分百最大值
     *
     * @return
     */
    public float getPercentMax() {
        return permaxmin / baseValue;
    }

    /**
     * 得到百分比最小值
     *
     * @return
     */
    public float getPercentMin() {
        return -getPercentMax();
    }

    /**
     * 得到成交量最大值
     *
     * @return
     */
    public float getVolmax() {
        return volmax;
    }

    /**
     * 得到K线图数据
     *
     * @return
     */
    public ArrayList<TradingInfoBean.KlineListBean> getKLineDatas() {
        return kDatas;
    }

    /**
     * 得到X轴数据
     *
     * @return
     */
    public ArrayList<String> getXVals() {
        return xVals;
    }

    /**
     * 得到K线数据
     *
     * @return
     */
    public ArrayList<CandleEntry> getCandleEntries() {
        return candleEntries;
    }

    /**
     * 得到成交量数据
     *
     * @return
     */
    public ArrayList<BarEntry> getBarEntries() {
        return barEntries;
    }


    /**
     * 得到K线图5日均线
     *
     * @return
     */
    public ArrayList<Entry> getMa5DataL() {
        return ma5DataL;
    }


    /**
     * 得到K线图10日均线
     *
     * @return
     */
    public ArrayList<Entry> getMa10DataL() {
        return ma10DataL;
    }

    /**
     * 得到K线图20日均线
     *
     * @return
     */
    public ArrayList<Entry> getMa30DataL() {
        return ma30DataL;
    }

    /**
     * 得到K线图30日均线
     *
     * @return
     */
    public ArrayList<Entry> getMa60DataL() {
        return ma60DataL;
    }

    /**
     * 得到成交量5日均线
     *
     * @return
     */
    public ArrayList<Entry> getMa5DataV() {
        return ma5DataV;
    }

    /**
     * 得到成交量10日均线
     *
     * @return
     */
    public ArrayList<Entry> getMa10DataV() {
        return ma10DataV;
    }

    /**
     * 得到成交量20日均线
     *
     * @return
     */
    public ArrayList<Entry> getMa20DataV() {
        return ma20DataV;
    }

    /**
     * 得到K线图30日均线
     *
     * @return
     */
    public ArrayList<Entry> getMa30DataV() {
        return ma30DataV;
    }

    /**
     * 得到MACD bar
     *
     * @return
     */
    public List<BarEntry> getMacdData() {
        return macdData;
    }

    /**
     * 得到MACD dea
     *
     * @return
     */
    public List<Entry> getDeaData() {
        return deaData;
    }

    /**
     * 得到MACD dif
     *
     * @return
     */
    public List<Entry> getDifData() {
        return difData;
    }

    /**
     * 得到KDJ bar
     *
     * @return
     */
    public List<BarEntry> getBarDatasKDJ() {
        return barDatasKDJ;
    }

    /**
     * 得到DKJ k
     *
     * @return
     */
    public List<Entry> getkData() {
        return kData;
    }

    /**
     * 得到KDJ d
     *
     * @return
     */
    public List<Entry> getdData() {
        return dData;
    }

    /**
     * 得到KDJ j
     *
     * @return
     */
    public List<Entry> getjData() {
        return jData;
    }

    /**
     * 得到WR bar
     *
     * @return
     */
    public List<BarEntry> getBarDatasWR() {
        return barDatasWR;
    }

    /**
     * 得到WR 13
     *
     * @return
     */
    public List<Entry> getWrData13() {
        return wrData13;
    }

    /**
     * 得到WR 34
     *
     * @return
     */
    public List<Entry> getWrData34() {
        return wrData34;
    }

    /**
     * 得到WR 89
     *
     * @return
     */
    public List<Entry> getWrData89() {
        return wrData89;
    }

    /**
     * 得到RSI bar
     *
     * @return
     */
    public List<BarEntry> getBarDatasRSI() {
        return barDatasRSI;
    }

    /**
     * 得到RSI 6
     *
     * @return
     */
    public List<Entry> getRsiData6() {
        return rsiData6;
    }

    /**
     * 得到RSI 12
     *
     * @return
     */
    public List<Entry> getRsiData12() {
        return rsiData12;
    }

    /**
     * 得到RSI 24
     *
     * @return
     */
    public List<Entry> getRsiData24() {
        return rsiData24;
    }

    public List<BarEntry> getBarDatasBOLL() {
        return barDatasBOLL;
    }

    public List<Entry> getBollDataUP() {
        return bollDataUP;
    }

    public List<Entry> getBollDataMB() {
        return bollDataMB;
    }

    public List<Entry> getBollDataDN() {
        return bollDataDN;
    }

    public List<BarEntry> getBarDatasEXPMA() {
        return barDatasEXPMA;
    }

    public List<Entry> getExpmaData5() {
        return expmaData5;
    }

    public List<Entry> getExpmaData10() {
        return expmaData10;
    }

    public List<Entry> getExpmaData20() {
        return expmaData20;
    }

    public List<Entry> getExpmaData60() {
        return expmaData60;
    }

    public List<BarEntry> getBarDatasDMI() {
        return barDatasDMI;
    }

    public List<Entry> getDmiDataDI1() {
        return dmiDataDI1;
    }

    public List<Entry> getDmiDataDI2() {
        return dmiDataDI2;
    }

    public List<Entry> getDmiDataADX() {
        return dmiDataADX;
    }

    public List<Entry> getDmiDataADXR() {
        return dmiDataADXR;
    }

    public ArrayList<Entry> getMinuteLineEntries() {
        return minuteLineEntries;
    }


    public HashMap<Integer, Float> getMa5Vols() {
        return ma5Vols;
    }

    public HashMap<Integer, Float> getMa10Vols() {
        return ma10Vols;
    }

    public HashMap<Integer, Float> getMa30Vols() {
        return ma30Vols;
    }

    public HashMap<Integer, Float> getMa60Vols() {
        return ma60Vols;
    }

    @Override
    public String toString() {
        return "DataParse{" +
                "kDatas=" + kDatas +
                ", xVals=" + xVals +
                ", barEntries=" + barEntries +
                ", candleEntries=" + candleEntries +
                ", minuteLineEntries=" + minuteLineEntries +
                ", ma5DataL=" + ma5DataL +
                ", ma10DataL=" + ma10DataL +
                ", ma30DataL=" + ma30DataL +
                ", ma60DataL=" + ma60DataL +
                ", ma5DataV=" + ma5DataV +
                ", ma10DataV=" + ma10DataV +
                ", ma20DataV=" + ma20DataV +
                ", ma30DataV=" + ma30DataV +
                ", macdData=" + macdData +
                ", deaData=" + deaData +
                ", difData=" + difData +
                ", barDatasKDJ=" + barDatasKDJ +
                ", kData=" + kData +
                ", dData=" + dData +
                ", jData=" + jData +
                ", barDatasWR=" + barDatasWR +
                ", wrData13=" + wrData13 +
                ", wrData34=" + wrData34 +
                ", wrData89=" + wrData89 +
                ", barDatasRSI=" + barDatasRSI +
                ", rsiData6=" + rsiData6 +
                ", rsiData12=" + rsiData12 +
                ", rsiData24=" + rsiData24 +
                ", barDatasBOLL=" + barDatasBOLL +
                ", bollDataUP=" + bollDataUP +
                ", bollDataMB=" + bollDataMB +
                ", bollDataDN=" + bollDataDN +
                ", barDatasEXPMA=" + barDatasEXPMA +
                ", expmaData5=" + expmaData5 +
                ", expmaData10=" + expmaData10 +
                ", expmaData20=" + expmaData20 +
                ", expmaData60=" + expmaData60 +
                ", barDatasDMI=" + barDatasDMI +
                ", dmiDataDI1=" + dmiDataDI1 +
                ", dmiDataDI2=" + dmiDataDI2 +
                ", dmiDataADX=" + dmiDataADX +
                ", dmiDataADXR=" + dmiDataADXR +
                ", ma5Vols=" + ma5Vols +
                ", ma10Vols=" + ma10Vols +
                ", ma30Vols=" + ma30Vols +
                ", ma60Vols=" + ma60Vols +
                ", baseValue=" + baseValue +
                ", permaxmin=" + permaxmin +
                ", volmax=" + volmax +
                ", code='" + code + '\'' +
                ", xValuesLabel=" + xValuesLabel +
                '}';
    }
}
