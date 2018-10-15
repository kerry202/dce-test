package cn.dagongniu.oax.assets.presenter;

import android.app.Activity;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.CoinAddressListBean;
import cn.dagongniu.oax.assets.bean.CoinListBean;
import cn.dagongniu.oax.assets.module.CoinAddressListModule;
import cn.dagongniu.oax.assets.module.CoinListModule;
import cn.dagongniu.oax.assets.view.ICoinAddressListView;
import cn.dagongniu.oax.assets.view.ICoinListView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 提币地址列表 Presenter
 */
public class CoinAddressListPresenter extends BasePresenter {

    private CoinAddressListModule coinAddressListModule;
    private ICoinAddressListView iCoinAddressListView;
    private Activity activity;
    RequestState state;

    public CoinAddressListPresenter(ICoinAddressListView iCoinAddressListView, RequestState state) {
        super(iCoinAddressListView);
        this.state = state;
        activity = (Activity) iCoinAddressListView.getContext();
        this.iCoinAddressListView = iCoinAddressListView;
        coinAddressListModule = new CoinAddressListModule(activity);
    }

    public void getCoinAddressListModule() {
        coinAddressListModule.requestServerDataOne(new OnBaseDataListener<CoinAddressListBean>() {
            @Override
            public void onNewData(CoinAddressListBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iCoinAddressListView, state, data);
                    iCoinAddressListView.setCoinAddressListData(data);
                } else {
                    StateBaseUtils.failure(iCoinAddressListView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                if (activity.getResources().getString(R.string.to_login_go).equals(code)) {//前往登录
                    iCoinAddressListView.goLogin(code);
                } else {
                    StateBaseUtils.error(iCoinAddressListView, state, code);
                }
            }
        },state, iCoinAddressListView.getCoinAddressListIndex() + "", iCoinAddressListView.getCoinAddressListPageSize() + "", iCoinAddressListView.getCoinId());
    }


}
