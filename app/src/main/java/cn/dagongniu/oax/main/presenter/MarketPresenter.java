package cn.dagongniu.oax.main.presenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.HashMap;

import cn.dagongniu.oax.base.OAXBasePresenter;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.kline.module.KLineActivityModule;
import cn.dagongniu.oax.main.view.MarketIView;

public class MarketPresenter extends OAXBasePresenter {
    private KLineActivityModule mKLineModule;
    private MarketIView mView;
    private Activity mActivity;
    private RequestState mState;

    public MarketPresenter(MarketIView iView) {
        super(iView);
        mActivity = (Activity) iView.getContext();
        mView = iView;

    }

    public void collectMarket(int marketId, RequestState state) {
        this.mState = state;
        if (mKLineModule == null) {
            mKLineModule = new KLineActivityModule(mActivity);
        }
        mKLineModule.collectMarket(new OnDataListener<String>() {
            @Override
            public void onNewData(CommonJsonToBean<String> data) {

                mView.collectMarketState(data);
            }

            @Override
            public void onError(String msg) {
                KLog.d("collectMarket = " + msg);
                mView.showToast(msg);
            }
        }, state, marketId);
    }

    public void cancelCollectMarket(int marketId, RequestState state) {
        this.mState = state;
        if (mKLineModule == null) {
            mKLineModule = new KLineActivityModule(mActivity);
        }
        mKLineModule.cancelCollectMarket(new OnDataListener<String>() {
            @Override
            public void onNewData(CommonJsonToBean<String> data) {

                 mView.cancelCollectMarket(data);

            }

            @Override
            public void onError(String msg) {
                KLog.d("collectMarket = " + msg);
                mView.showToast(msg);
            }
        }, state, marketId);
    }

}
