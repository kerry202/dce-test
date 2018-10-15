package cn.dagongniu.oax.utils.events;

import cn.dagongniu.oax.kline.bean.KlineInfoBean;
import cn.dagongniu.oax.kline.bean.TradingInfoBean;

public class KLineCurrentItemInfoEvent {
    public boolean isVisible;
    public TradingInfoBean.KlineListBean bean;
    public float ma5;
    public float ma10;
    public float ma30;
    public float ma60;

    public KLineCurrentItemInfoEvent(boolean isVisible, TradingInfoBean.KlineListBean bean, float ma5, float ma10, float ma30, float ma60) {
        this.isVisible = isVisible;
        this.bean = bean;
        this.ma5 = ma5;
        this.ma10 = ma10;
        this.ma30 = ma30;
        this.ma60 = ma60;
    }
}
