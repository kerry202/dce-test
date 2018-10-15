package cn.dagongniu.oax.kline.presenter;

import android.app.Activity;

import com.socks.library.KLog;

import cn.dagongniu.oax.base.OAXBasePresenter;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.kline.fragment.CommitteeIView;
import cn.dagongniu.oax.kline.module.CommitteeModule;
import cn.dagongniu.oax.trading.bean.TradeListAndMarketOrdersBean;

public class CommitteePresenter extends OAXBasePresenter {

    private CommitteeModule mModule;
    private Activity activity;
    CommitteeIView view;
    RequestState mState;

    public CommitteePresenter(CommitteeIView view) {
        super(view);
        activity = (Activity) view.getContext();
        this.view = view;
        mModule = new CommitteeModule(activity);
    }

    public void getTradeListAndMarketOrders(String marketId) {
        mModule.getTradeListAndMarketOrders(marketId, new CommitteeModule.OnTradeListAndMarketOrders() {
            @Override
            public void newData(TradeListAndMarketOrdersBean bean) {
                view.onNewCommitteeList(bean);
            }
        });
    }

    public void unTopicTradeListAndMarketOrders() {
        mModule.unTopicTradeListAndMarketOrders();
    }
}
