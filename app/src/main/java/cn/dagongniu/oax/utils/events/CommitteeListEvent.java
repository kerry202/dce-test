package cn.dagongniu.oax.utils.events;

import cn.dagongniu.oax.kline.bean.TradingInfoBean;

public class CommitteeListEvent {
    public TradingInfoBean bean;

    public CommitteeListEvent(TradingInfoBean bean) {
        this.bean = bean;
    }
}
