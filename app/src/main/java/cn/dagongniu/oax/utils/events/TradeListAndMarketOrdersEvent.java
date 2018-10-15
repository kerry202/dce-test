package cn.dagongniu.oax.utils.events;

import cn.dagongniu.oax.trading.bean.TradeListAndMarketOrdersBean;

public class TradeListAndMarketOrdersEvent {
    public TradeListAndMarketOrdersEvent(TradeListAndMarketOrdersBean bean) {
        mBean = bean;
    }

    public TradeListAndMarketOrdersBean mBean;
}
