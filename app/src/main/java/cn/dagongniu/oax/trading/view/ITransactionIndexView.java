package cn.dagongniu.oax.trading.view;

import cn.dagongniu.oax.trading.bean.TransactionIndexBean;
import cn.dagongniu.oax.base.IView;

public interface ITransactionIndexView extends IView {

    String getMarketId();//交易对id

    String getMinType();//时间类型 查询K线数据的条件

    void setTransactionIndexBeanData(TransactionIndexBean transactionIndexBean);
}
