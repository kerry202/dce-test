package cn.dagongniu.oax.trading.module;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.HashMap;

import cn.dagongniu.oax.base.OAXBaseModule;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.WebSocketsManager;
import cn.dagongniu.oax.trading.bean.EntrustInfoBean;
import cn.dagongniu.oax.trading.bean.CurrentEntrustBean;
import cn.dagongniu.oax.utils.Logs;
import io.reactivex.disposables.Disposable;
import ua.naiksoftware.stomp.client.StompClient;

public class EntrustModule extends OAXBaseModule<HashMap<String, Object>, EntrustInfoBean> {

    private StompClient mTradeListClient;
    private boolean isOnErrorOrOnClosed = false;
    private WebSocketsManager.OnCreateStompClientListener mOnCreateStompClientListener;
    private Disposable mTopic;

    public EntrustModule(Activity activity) {
        super(activity);
    }

    public void getCommitteeList(String param, RequestState state, OnEntrustDataListener listener) {
        HttpUtils.getInstance().commonGet(Http.TRANSACTION_PAGE, param, activity, new OnDataListener<EntrustInfoBean>() {
            @Override
            public void onNewData(CommonJsonToBean<EntrustInfoBean> data) {
                listener.newData(data);
                Logs.s("  数据getCommitteeList   "+data);
            }

            @Override
            public void onError(String msg) {
                listener.onError(msg);
            }
        }, EntrustInfoBean.class, state);
    }

    public interface OnEntrustDataListener {
        void newData(CommonJsonToBean<EntrustInfoBean> bean);

        void onError(String msg);
    }

    public void topicTradeList(int marketId, String userId, OnUserEntrustInfoListener listener) {
        if (isOnErrorOrOnClosed) {
            mTradeListClient = null;
            mOnCreateStompClientListener = null;
        }
        if (mTradeListClient == null) {
            mOnCreateStompClientListener = new WebSocketsManager.OnCreateStompClientListener() {
                @Override
                public void onOpened() {
                    isOnErrorOrOnClosed = false;
                    mTopic = WebSocketsManager.getInstance().topic(mTradeListClient, Http.TOPIC_TRADELIST + marketId + "/" + userId, new WebSocketsManager.OnTopicListener() {
                        @Override
                        public void onNewData(String data) {
                            KLog.d("data = " + data);
                            if (!TextUtils.isEmpty(data)) {
                                EntrustInfoBean infoBean = new Gson().fromJson(data, EntrustInfoBean.class);
                                Logs.s("  数据getCommitteeList  2222    "+infoBean);
                                listener.onTopicTradeListData(infoBean);
                            }
                        }
                    });
                }

                @Override
                public void onError() {
                    isOnErrorOrOnClosed = true;
                }

                @Override
                public void onClosed() {
                    isOnErrorOrOnClosed = true;
                }
            };
            mTradeListClient = WebSocketsManager.getInstance().createStompClient(mOnCreateStompClientListener);
        }

    }

    public void unTopicTradeList() {
        if (mTradeListClient != null) {
//            WebSocketsManager.getInstance().disconnect(mTradeListClient);
            if (mTopic != null && !mTopic.isDisposed()) {
                mTopic.dispose();
                mTopic = null;
            }
            mTradeListClient.disconnect();
            mTradeListClient = null;
            mOnCreateStompClientListener = null;
        }
    }

    public interface OnUserEntrustInfoListener {
        void onTopicTradeListData(EntrustInfoBean bean);
    }

    public void sendTradeList(int marketId, int userId) {
        if (mTradeListClient != null) {
            WebSocketsManager.getInstance().send(mTradeListClient, Http.CHECK_TRADE + marketId + "/" + userId);
        }

    }

    public void cancellations(int id, HashMap<String, Object> map, RequestState state, OnCancellationsListener listener) {
        HttpUtils.getInstance().commonGet(Http.ORDERS_CANCEL, "" + id, activity, new OnDataListener<String>() {
            @Override
            public void onNewData(CommonJsonToBean<String> data) {
                listener.newCancellationsInfo(data);
            }

            @Override
            public void onError(String msg) {
                listener.onError(msg);
            }
        }, String.class, state);
    }

    public interface OnCancellationsListener {
        void newCancellationsInfo(CommonJsonToBean<String> data);

        void onError(String msg);
    }


    /**
     * 获取委托数据
     *
     * @param map
     * @param state
     * @param listener
     */
    public void getCurrentEntrust(HashMap<String, Object> map, RequestState state, OnDataListener listener) {
        HttpUtils.getInstance().commonPost(Http.ORDERSRECORD_FINDLISTBYPAGE, map, activity, new OnDataListener<CurrentEntrustBean>() {
            @Override
            public void onNewData(CommonJsonToBean<CurrentEntrustBean> data) {
                listener.onNewData(data);
            }

            @Override
            public void onError(String msg) {
                listener.onError(msg);
            }
        }, CurrentEntrustBean.class, state);
    }
}
