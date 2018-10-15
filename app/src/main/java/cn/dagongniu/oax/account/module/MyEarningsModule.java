package cn.dagongniu.oax.account.module;

import android.app.Activity;

import java.util.HashMap;

import cn.dagongniu.oax.account.bean.MyEarningsDataBean;
import cn.dagongniu.oax.base.OAXBaseModule;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;

public class MyEarningsModule extends OAXBaseModule<HashMap<String, String>, MyEarningsDataBean> {

    public MyEarningsModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerData(OnDataListener<MyEarningsDataBean> onDataListener, RequestState state, HashMap<String, Object> params) {
        HttpUtils.getInstance().commonPost(Http.MyEarnings, params, activity, new OnDataListener<MyEarningsDataBean>() {

            @Override
            public void onNewData(CommonJsonToBean<MyEarningsDataBean> data) {
                onDataListener.onNewData(data);
            }

            @Override
            public void onError(String code) {
                onDataListener.onError(code);
            }
        }, MyEarningsDataBean.class, state);
    }
}
