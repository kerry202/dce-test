package cn.dagongniu.oax.https;


import android.text.TextUtils;

import com.socks.library.KLog;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;

public class WebSocketsManager {

    private WebSocketsManager() {
    }

    public static WebSocketsManager getInstance() {
        return WebSocketManagerInstanceHolder.instance;
    }

    private static class WebSocketManagerInstanceHolder {
        private static WebSocketsManager instance = new WebSocketsManager();
    }

    public StompClient createStompClient(OnCreateStompClientListener listener) {
        StompClient stompClient = Stomp.over(Stomp.ConnectionProvider.JWS, Http.WEBSOCKET_ROOT);//
        stompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            KLog.d("Stomp connection opened");
                            listener.onOpened();
                            break;
                        case ERROR:
                            KLog.d("Stomp connection error");
                            listener.onError();
                            break;
                        case CLOSED:
                            KLog.d("Stomp connection closed");
                            listener.onClosed();
                            break;
                    }
                });
        stompClient.connect();
        return stompClient;
    }

    public Disposable topic(StompClient client, String url, OnTopicListener listener) {
        KLog.d("topic = " + url);
        if (client == null) {
            return null;
        }
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        if (listener == null) {
            return null;
        }
        Disposable subscribe = client.topic(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    if (topicMessage != null && !TextUtils.isEmpty(topicMessage.getPayload())) {
                        listener.onNewData(topicMessage.getPayload());
                    }
                });
        return subscribe;
    }


    public void send(StompClient client, String url) {
        if (client == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!client.isConnected()) {
            return;
        }
        try {
            client.send(url).subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(Disposable d) {
                    KLog.d("send = " + "onSubscribe" + url);
                }

                @Override
                public void onComplete() {
                    KLog.d("send = " + "onComplete" + url);
                }

                @Override
                public void onError(Throwable e) {
                    KLog.d("send = " + "onError" + url);
                }
            });
        } catch (Exception e) {
            KLog.d("Exception send = " + e.getMessage());
        }

    }

    public void disconnect(StompClient stompClient) {
        KLog.d("disconnect");
        if (stompClient != null) {
            stompClient.disconnect();
        }
    }

    public interface OnCreateStompClientListener {
        void onOpened();

        void onError();

        void onClosed();
    }

    public interface OnTopicListener {
        void onNewData(String data);
    }
}
