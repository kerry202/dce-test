package cn.dagongniu.oax.kline.fragment;

import cn.dagongniu.oax.base.OAXIView;
import cn.dagongniu.oax.kline.bean.TradingInfoBean;
import cn.dagongniu.oax.trading.bean.TradeListAndMarketOrdersBean;

public interface CommitteeIView extends OAXIView {
    void onNewCommitteeList(TradeListAndMarketOrdersBean bean);
}
