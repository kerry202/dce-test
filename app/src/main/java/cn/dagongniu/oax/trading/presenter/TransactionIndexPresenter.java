package cn.dagongniu.oax.trading.presenter;

import android.app.Activity;

import cn.dagongniu.oax.trading.bean.TransactionIndexBean;
import cn.dagongniu.oax.trading.module.TransactionIndexModule;
import cn.dagongniu.oax.trading.view.ITransactionIndexView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 交易首页的 Presenter
 */
public class TransactionIndexPresenter extends BasePresenter {

    private TransactionIndexModule transactionIndexModule;
    private ITransactionIndexView iTransactionIndexView;
    private Activity activity;
    RequestState state;

    public TransactionIndexPresenter(ITransactionIndexView iTransactionIndexView, RequestState state) {
        super(iTransactionIndexView);
        this.state = state;
        activity = (Activity) iTransactionIndexView.getContext();
        this.iTransactionIndexView = iTransactionIndexView;
        transactionIndexModule = new TransactionIndexModule(activity);
    }

    public void getTransactionIndexModule() {

        transactionIndexModule.requestServerDataOne(new OnBaseDataListener<TransactionIndexBean>() {

            @Override
            public void onNewData(TransactionIndexBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iTransactionIndexView, state, data);
                    iTransactionIndexView.setTransactionIndexBeanData(data);
                } else {
                    StateBaseUtils.failure(iTransactionIndexView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(iTransactionIndexView, state, code);
            }
        }, state, iTransactionIndexView.getMarketId(), iTransactionIndexView.getMinType());
    }

}
