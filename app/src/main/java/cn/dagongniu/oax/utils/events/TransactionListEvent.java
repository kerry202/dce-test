package cn.dagongniu.oax.utils.events;

import cn.dagongniu.oax.kline.bean.TradingInfoBean;
import cn.dagongniu.oax.kline.bean.TransactionBean;

public class TransactionListEvent {
    public TradingInfoBean bean;

    public TransactionListEvent(TradingInfoBean bean) {
        this.bean = bean;
    }
}
