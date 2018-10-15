package cn.dagongniu.oax.assets.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.assets.bean.CoinListBean;
import cn.dagongniu.oax.assets.bean.PropertyShowBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 币种列表 module
 */
public class CoinListModule extends BaseModule<String, CoinListBean> {

    public CoinListModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<CoinListBean> onBaseDataListener, String... parm) {
    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<CoinListBean> onBaseDataListener, RequestState state, String... parm) {


        HttpUtils.getInstance().getLang(Http.USERCOIN_LIST, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    CoinListBean coinListBean = new Gson().fromJson(data, CoinListBean.class);
                    onBaseDataListener.onNewData(coinListBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String code) {
                onBaseDataListener.onError(code);
            }
        }, state);

    }
}
