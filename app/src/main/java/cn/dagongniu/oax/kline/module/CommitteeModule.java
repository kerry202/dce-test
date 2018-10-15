package cn.dagongniu.oax.kline.module;

import android.app.Activity;

import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.HashMap;

import cn.dagongniu.oax.base.OAXBaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.WebSocketsManager;
import cn.dagongniu.oax.kline.bean.CommitteeBean;
import cn.dagongniu.oax.trading.bean.TradeListAndMarketOrdersBean;
import cn.dagongniu.oax.utils.Logs;
import io.reactivex.disposables.Disposable;
import ua.naiksoftware.stomp.client.StompClient;

public class CommitteeModule extends OAXBaseModule<HashMap<String, String>, CommitteeBean> {

    private StompClient tradeListAndMarketOrders;
    private WebSocketsManager.OnCreateStompClientListener onCreateStompClientListener;
    private Disposable mTopic;

    public CommitteeModule(Activity activity) {
        super(activity);
    }

    public void getTradeListAndMarketOrders(String marketId, OnTradeListAndMarketOrders listener) {
        if (tradeListAndMarketOrders == null) {
            onCreateStompClientListener = new WebSocketsManager.OnCreateStompClientListener() {
                @Override
                public void onOpened() {
                    if (tradeListAndMarketOrders != null) {
                        mTopic = WebSocketsManager.getInstance().topic(tradeListAndMarketOrders, Http.TOPIC_TRADELISTANDMARKETORDERS + marketId, new WebSocketsManager.OnTopicListener() {
                            @Override
                            public void onNewData(String data) {

                                Logs.s("      交易列表onNewData ：   "+data);
                                Logs.s("      交易列表marketId ：   "+marketId);
                                KLog.d("data = " + data);
                                try {
                                    TradeListAndMarketOrdersBean bean = new Gson().fromJson(data, TradeListAndMarketOrdersBean.class);
                                    listener.newData(bean);
                                } catch (Exception e) {
                                    KLog.d("data Exception = " + e);
                                }
                            }
                        });
                    }
                }

                @Override
                public void onError() {
                    Logs.s("      交易列表onError ：   ");
                }

                @Override
                public void onClosed() {
                    Logs.s("      交易列表onClosed ：   ");
                }
            };
            tradeListAndMarketOrders = WebSocketsManager.getInstance().createStompClient(onCreateStompClientListener);
        }
    }

    public void unTopicTradeListAndMarketOrders() {
        if (tradeListAndMarketOrders != null) {
//            WebSocketsManager.getInstance().disconnect(tradeListAndMarketOrders);
            if (mTopic != null && !mTopic.isDisposed()) {
                mTopic.dispose();
                mTopic = null;
            }
            tradeListAndMarketOrders.disconnect();
            tradeListAndMarketOrders = null;
            onCreateStompClientListener = null;
        }
    }

    public interface OnTradeListAndMarketOrders {
        void newData(TradeListAndMarketOrdersBean bean);
    }
}
