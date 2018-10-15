package cn.dagongniu.oax.kline.bean;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by loro on 2017/3/7.
 */

public class RSIEntity {

    private ArrayList<Float> RSIs;

    /**
     * @param kLineBeens
     * @param n          几日
     */
    public RSIEntity(ArrayList<TradingInfoBean.KlineListBean> kLineBeens, int n) {
        this(kLineBeens, n, 100);
    }

    /**
     * @param kLineBeens
     * @param n          几日
     * @param defult     不足N日时的默认值
     */
    public RSIEntity(ArrayList<TradingInfoBean.KlineListBean> kLineBeens, int n, float defult) {
        RSIs = new ArrayList<>();
        float sum = 0.0f;
        float dif = 0.0f;
        float rs = 0.0f;
        float rsi = 0.0f;
        int index = n - 1;
        if (kLineBeens != null && kLineBeens.size() > 0) {
            for (int i = 0; i < kLineBeens.size(); i++) {
//                if (i > 0) {
                if (n == 0) {
                    sum = 0.0f;
                    dif = 0.0f;
                } else {
                    int k = i - n + 1;
                    Float[] wrs = getAAndB(k, i, (ArrayList<TradingInfoBean.KlineListBean>) kLineBeens);
                    sum = wrs[0];
                    dif = wrs[1];
                }
//                }
                if (dif != 0) {
//                    rs = sum / dif;
//                    float c = 100 / (1 + rs);
//                    rsi = 100 - c;

                    float h = sum + dif;
                    rsi = sum / h * 100;
                } else {
                    rsi = 100;
                }

                if (i < index) {
                    rsi = defult;
                }
                RSIs.add(rsi);
            }
        }
    }

    private Float[] getAAndB(Integer a, Integer b, ArrayList<TradingInfoBean.KlineListBean> kLineBeens) {
        if (a < 0)
            a = 0;
        float sum = 0.0f;
        float dif = 0.0f;
//        float closeT, closeY;
        BigDecimal closeT, closeY;
        Float[] abs = new Float[2];
        for (int i = a; i <= b; i++) {
            if (i > a) {
//                closeT = Float.valueOf(kLineBeens.get(i).getClose().toPlainString());
////                closeY = Float.valueOf(kLineBeens.get(i - 1).getClose().toPlainString());
                closeT = kLineBeens.get(i).getClose();
                closeY = kLineBeens.get(i - 1).getClose();

//                float c = closeT - closeY;
                float c = Float.valueOf(closeT.subtract(closeY).toPlainString());
                if (c > 0) {
                    sum = sum + c;
                } else {
                    dif = sum + c;
                }

                dif = Math.abs(dif);
            }
        }

        abs[0] = sum;
        abs[1] = dif;
        return abs;
    }

    public ArrayList<Float> getRSIs() {
        return RSIs;
    }
}
