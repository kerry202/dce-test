package cn.dagongniu.oax.assets.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.assets.bean.CoinAddressListBean;
import cn.dagongniu.oax.assets.bean.UserCoinWithdrawalBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 提币地址列表 module
 */
public class CoinAddressListModule extends BaseModule<String, CoinAddressListBean> {

    public CoinAddressListModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<CoinAddressListBean> onBaseDataListener, String... parm) {
    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<CoinAddressListBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String pageIndex = parm[0];
        String pageSize = parm[1];
        String coinId = parm[2];
        hashMap.put("pageIndex", pageIndex);                  //开始页
        hashMap.put("pageSize", pageSize);                  //每页多少条
        hashMap.put("coinId", coinId);                  //币种id

        HttpUtils.getInstance().postLangIdToken(Http.COINADDRESS_LIST, hashMap, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    CoinAddressListBean coinAddressListBean = new Gson().fromJson(data, CoinAddressListBean.class);
                    onBaseDataListener.onNewData(coinAddressListBean);
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
