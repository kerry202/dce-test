package cn.dagongniu.oax.assets.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.assets.bean.CoinAddressListBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 添加提币地址 module
 */
public class CoinAddressAddModule extends BaseModule<String, CoinAddressListBean> {

    public CoinAddressAddModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<CoinAddressListBean> onBaseDataListener, String... parm) {
    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<CoinAddressListBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String coinId = parm[0];
        String address = parm[1];
        String remark = parm[2];
        String smsCode = parm[3];
        String emailCode = parm[4];
        String googleCode = parm[5];
        hashMap.put("coinId", coinId);                  //币种id
        hashMap.put("address", address);                //地址
        hashMap.put("remark", remark);                  //备注
        hashMap.put("smsCode", smsCode);
        hashMap.put("emailCode", emailCode);
        hashMap.put("googleCode", googleCode);

        HttpUtils.getInstance().postLangIdToken(Http.COINADDRESS_ADD, hashMap, activity, new OnBaseDataListener<String>() {
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
