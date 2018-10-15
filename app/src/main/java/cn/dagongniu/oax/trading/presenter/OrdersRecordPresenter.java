package cn.dagongniu.oax.trading.presenter;

import android.app.Activity;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.PropertyShowBean;
import cn.dagongniu.oax.assets.module.PropertyShowModule;
import cn.dagongniu.oax.assets.view.IPropertyShowView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;
import cn.dagongniu.oax.trading.bean.OrdersRecordBean;
import cn.dagongniu.oax.trading.module.OrdersRecordModule;
import cn.dagongniu.oax.trading.view.IOrdersRecordView;

/**
 * 成交订单 Presenter
 */
public class OrdersRecordPresenter extends BasePresenter {

    private OrdersRecordModule ordersRecordModule;
    private IOrdersRecordView iOrdersRecordView;
    private Activity activity;
    RequestState state;

    public OrdersRecordPresenter(IOrdersRecordView iOrdersRecordView, RequestState state) {
        super(iOrdersRecordView);
        this.state = state;
        activity = (Activity) iOrdersRecordView.getContext();
        this.iOrdersRecordView = iOrdersRecordView;
        ordersRecordModule = new OrdersRecordModule(activity);
    }

    public void getOrdersRecordModule() {

        ordersRecordModule.requestServerDataOne(new OnBaseDataListener<OrdersRecordBean>() {

            @Override
            public void onNewData(OrdersRecordBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    if (data.getData().getList() != null && data.getData().getList().size() > 0) {
                        iOrdersRecordView.setOrdersRecordData(data);
                    } else {
                        iOrdersRecordView.isNull(data);
                    }
                } else {
                    iOrdersRecordView.setDataErrer(data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                if (activity.getResources().getString(R.string.to_login_go).equals(code)) {//前往登录
                    iOrdersRecordView.goLogin(code);
                } else {
                    iOrdersRecordView.setDataErrer(code);
                }
            }
        }, state, iOrdersRecordView.getBeginDate(), iOrdersRecordView.getEndDate(), iOrdersRecordView.getMarketId(), iOrdersRecordView.getType(), iOrdersRecordView.getPageNo(), iOrdersRecordView.getPageSize());
    }

    public void getOrdersRecordLoadModule() {
        ordersRecordModule.requestServerDataOne(new OnBaseDataListener<OrdersRecordBean>() {
            @Override
            public void onNewData(OrdersRecordBean data) {
                if (data.isSuccess()) {
                    iOrdersRecordView.setOrdersRecordLoadMoreData(data);
                } else {
                    iOrdersRecordView.setDataLoadErrer(data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                iOrdersRecordView.setDataLoadErrer(code);
            }
        }, state, iOrdersRecordView.getBeginDate(), iOrdersRecordView.getEndDate(), iOrdersRecordView.getMarketId(), iOrdersRecordView.getType(), iOrdersRecordView.getPageNo(), iOrdersRecordView.getPageSize());
    }


}
