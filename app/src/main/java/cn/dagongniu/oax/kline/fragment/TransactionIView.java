package cn.dagongniu.oax.kline.fragment;

import java.util.List;

import cn.dagongniu.oax.base.OAXIView;
import cn.dagongniu.oax.kline.bean.TradingInfoBean;

public interface TransactionIView extends OAXIView {
    void onNewTransactionList(List<TradingInfoBean.MarketTradeListBean> beans);
}
