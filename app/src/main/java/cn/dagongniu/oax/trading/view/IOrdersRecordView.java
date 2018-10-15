package cn.dagongniu.oax.trading.view;

import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.trading.bean.OrdersRecordBean;
import cn.dagongniu.oax.trading.bean.TransactionIndexBean;

public interface IOrdersRecordView extends IView {

    String getBeginDate();//根据时间查询的开始日期 必须是‘yyyy-

    String getEndDate();//根据时间查询的结束 必须是‘yyyy-MM-dd’格

    String getMarketId();//交易对id

    String getType();//1买入 2 卖出

    String getPageNo();//第N页 表示页码

    String getPageSize();//每页显示数据数条

    void goLogin(String msg);

    void isNull(OrdersRecordBean ordersRecordBean);

    void setOrdersRecordData(OrdersRecordBean ordersRecordBean);

    void setDataErrer(String msg);

    void setOrdersRecordLoadMoreData(OrdersRecordBean ordersRecordBean);

    void setDataLoadErrer(String msg);
}
