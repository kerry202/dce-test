package cn.dagongniu.oax.kline.presenter;

import android.app.Activity;

import com.socks.library.KLog;

import java.util.HashMap;
import java.util.List;

import cn.dagongniu.oax.base.OAXBasePresenter;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.kline.bean.TradingInfoBean;
import cn.dagongniu.oax.kline.module.KLineActivityModule;
import cn.dagongniu.oax.trading.fragment.OaxTradingIView;
import cn.dagongniu.oax.utils.Logs;

public class KLineActivityPresenter extends OAXBasePresenter {

    private KLineActivityModule mKLineModule;
    private OaxTradingIView mView;
    private Activity mActivity;
    private RequestState mState;

    public KLineActivityPresenter(OaxTradingIView iView) {
        super(iView);
        mActivity = (Activity) iView.getContext();
        mView = iView;
    }

    public void getData(HashMap<String, Object> map, RequestState state) {
        if (mKLineModule == null) {
            mKLineModule = new KLineActivityModule(mActivity);
        }
        this.mState = state;
        mKLineModule.requestServerData(new OnDataListener<TradingInfoBean>() {
            @Override
            public void onNewData(CommonJsonToBean<TradingInfoBean> data) {
                Logs.s(" k线图数据获取成功 "+data);
                mView.setTradingInfoData(data);
            }

            @Override
            public void onError(String msg) {

                Logs.s(" k线图数据获取失败   "+msg);
                mView.showToast(msg);
            }
        }, state, map);
    }

    public void sendBuyOrSellData(HashMap<String, Object> map, RequestState state) {
        this.mState = state;
        if (mKLineModule == null) {
            mKLineModule = new KLineActivityModule(mActivity);
        }

        mKLineModule.sendBuyOrSellData(new OnDataListener<String>() {
            @Override
            public void onNewData(CommonJsonToBean<String> data) {

                Logs.s("   交易  买入请求:  " + data);
                mView.setBuyOrSellState(data);
            }

            @Override
            public void onError(String msg) {
                KLog.d("sendBuyOrSellData = " + msg);
                mView.showToast(msg);
            }
        }, state, map);
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
