package cn.dagongniu.oax.trading.presenter;

import android.app.Activity;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;
import cn.dagongniu.oax.trading.bean.MarketCategoryBean;
import cn.dagongniu.oax.trading.bean.OrdersRecordBean;
import cn.dagongniu.oax.trading.module.MarketCategoryModule;
import cn.dagongniu.oax.trading.module.OrdersRecordModule;
import cn.dagongniu.oax.trading.view.IMarketCategoryView;
import cn.dagongniu.oax.trading.view.IOrdersRecordView;

/**
 * 联动交易对信息查询 Presenter
 */
public class MarketCategoryPresenter extends BasePresenter {

    private MarketCategoryModule marketCategoryModule;
    private IMarketCategoryView iMarketCategoryView;
    private Activity activity;
    RequestState state;

    public MarketCategoryPresenter(IMarketCategoryView iMarketCategoryView, RequestState state) {
        super(iMarketCategoryView);
        this.state = state;
        activity = (Activity) iMarketCategoryView.getContext();
        this.iMarketCategoryView = iMarketCategoryView;
        marketCategoryModule = new MarketCategoryModule(activity);
    }

    public void getMarketCategoryListModule() {

        marketCategoryModule.requestServerDataOne(new OnBaseDataListener<MarketCategoryBean>() {

            @Override
            public void onNewData(MarketCategoryBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    iMarketCategoryView.setMarketCategoryData(data);

                } else {
                    StateBaseUtils.failure(iMarketCategoryView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(iMarketCategoryView, state, code);
            }
        }, state);
    }


}
