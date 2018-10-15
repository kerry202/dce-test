package cn.dagongniu.oax.main.module;

import android.app.Activity;

import java.util.HashMap;

import cn.dagongniu.oax.base.OAXBaseModule;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.main.bean.VersionInfoBean;

public class AppVersionModule extends OAXBaseModule<HashMap<String, Object>, VersionInfoBean> {
    public AppVersionModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerData(final OnDataListener<VersionInfoBean> onBaseDataListener, RequestState state, HashMap<String, Object> params) {
        HttpUtils.getInstance().commonPost(Http.APP_CHECKVERSION, params, activity, new OnDataListener<VersionInfoBean>() {

            @Override
            public void onNewData(CommonJsonToBean<VersionInfoBean> data) {
                onBaseDataListener.onNewData(data);
            }

            @Override
            public void onError(String code) {
                onBaseDataListener.onError(code);
            }
        }, VersionInfoBean.class, state);
    }
}
