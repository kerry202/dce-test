package cn.dagongniu.oax.kline.module;

import android.app.Activity;

import java.util.HashMap;

import cn.dagongniu.oax.base.OAXBaseModule;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.kline.bean.TradingInfoBean;
import cn.dagongniu.oax.utils.Logs;

public class KLineActivityModule extends OAXBaseModule<String, TradingInfoBean> {

    public KLineActivityModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerData(OnDataListener<TradingInfoBean> onBaseDataListener, RequestState state, HashMap<String, Object> params) {
        HttpUtils.getInstance().commonPost(Http.TRANSACTIONPAGE_INDEX, params, activity, new OnDataListener<TradingInfoBean>() {

            @Override
            public void onNewData(CommonJsonToBean<TradingInfoBean> data) {
                onBaseDataListener.onNewData(data);
            }

            @Override
            public void onError(String msg) {
                onBaseDataListener.onError(msg);
            }
        }, TradingInfoBean.class, state);
    }

    /**
     * 买入卖出
     *
     * @param onBaseDataListener
     * @param state
     * @param params
     */
    public void sendBuyOrSellData(OnDataListener<String> onBaseDataListener, RequestState state, HashMap<String, Object> params) {
        HttpUtils.getInstance().commonPost(Http.ORDERS_ADD, params, activity, new OnDataListener<String>() {
            @Override
            public void onNewData(CommonJsonToBean<String> data) {
                onBaseDataListener.onNewData(data);
            }

            @Override
            public void onError(String msg) {
                onBaseDataListener.onError(msg);
            }
        }, String.class, state);
    }

    /**
     * 收藏
     *
     * @param onBaseDataListener
     * @param state
     * @param marketId
     */
    public void collectMarket(OnDataListener<String> onBaseDataListener, RequestState state, int marketId) {
        HttpUtils.getInstance().commonGet(Http.USERMAKET_SAVE, marketId + "", activity, new OnDataListener<String>() {
            @Override
            public void onNewData(CommonJsonToBean<String> data) {
                Logs.s("   正式收藏数据 "+data);
                onBaseDataListener.onNewData(data);
            }

            @Override
            public void onError(String msg) {
                onBaseDataListener.onError(msg);
            }
        }, String.class, state);
    }

    /**
     * 取消收藏
     *
     * @param onBaseDataListener
     * @param state
     * @param marketId
     */
    public void cancelCollectMarket(OnDataListener<String> onBaseDataListener, RequestState state, int marketId) {
        HttpUtils.getInstance().commonGet(Http.USERMAKET_CANCEL, marketId + "", activity, new OnDataListener<String>() {
            @Override
            public void onNewData(CommonJsonToBean<String> data) {
                onBaseDataListener.onNewData(data);
            }

            @Override
            public void onError(String msg) {
                onBaseDataListener.onError(msg);
            }
        }, String.class, state);
    }

}
