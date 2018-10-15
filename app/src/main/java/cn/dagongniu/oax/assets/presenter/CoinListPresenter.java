package cn.dagongniu.oax.assets.presenter;

import android.app.Activity;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.CoinListBean;
import cn.dagongniu.oax.assets.bean.PropertyShowBean;
import cn.dagongniu.oax.assets.module.CoinListModule;
import cn.dagongniu.oax.assets.module.PropertyShowModule;
import cn.dagongniu.oax.assets.view.ICoinListView;
import cn.dagongniu.oax.assets.view.IPropertyShowView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 币种列表 Presenter
 */
public class CoinListPresenter extends BasePresenter {

    private CoinListModule coinListModule;
    private ICoinListView iCoinListView;
    private Activity activity;
    RequestState state;

    public CoinListPresenter(ICoinListView iCoinListView, RequestState state) {
        super(iCoinListView);
        this.state = state;
        activity = (Activity) iCoinListView.getContext();
        this.iCoinListView = iCoinListView;
        coinListModule = new CoinListModule(activity);
    }

    public void getCoinListModule() {

        coinListModule.requestServerDataOne(new OnBaseDataListener<CoinListBean>() {

            @Override
            public void onNewData(CoinListBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iCoinListView, state, data);
                    iCoinListView.setCoinListBeanData(data);
                } else {
                    StateBaseUtils.failure(iCoinListView, state, data.getMsg());
                    iCoinListView.refreshErrer();
                }
            }

            @Override
            public void onError(String code) {
                iCoinListView.refreshErrer();
                StateBaseUtils.error(iCoinListView, state, code);
            }
        }, state);
    }


}
