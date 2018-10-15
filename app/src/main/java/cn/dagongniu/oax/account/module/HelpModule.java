package cn.dagongniu.oax.account.module;

import android.app.Activity;


import java.util.HashMap;

import cn.dagongniu.oax.account.bean.HelpBean;
import cn.dagongniu.oax.account.bean.HelpInfoBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.base.OAXBaseModule;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;

public class HelpModule extends OAXBaseModule<HashMap<String, String>, HelpBean> {

    public HelpModule(Activity activity) {
        super(activity);
    }
//
//    @Override
//    public void requestServerData(final OnBaseDataListener<HelpInfoBean> onBaseDataListener, RequestState state, HashMap<String, Object> params) {
//        HttpUtils.getInstance().commonPost(Http.HELP_CENTER, params, activity, new OnBaseDataListener<HelpInfoBean>() {
//
//            @Override
//            public void onNewData(HelpInfoBean data) {
//                onBaseDataListener.onNewData(data);
//            }
//
//            @Override
//            public void onError(String code) {
//                onBaseDataListener.onError(code);
//            }
//        }, HelpInfoBean.class, state);
//    }


    @Override
    public void requestServerData(final OnDataListener<HelpBean> onBaseDataListener, RequestState state, HashMap<String, Object> params) {
        HttpUtils.getInstance().commonPost(Http.HELP_CENTER, params, activity, new OnDataListener<HelpBean>() {

            @Override
            public void onNewData(CommonJsonToBean<HelpBean> data) {
                onBaseDataListener.onNewData(data);
            }

            @Override
            public void onError(String code) {
                onBaseDataListener.onError(code);
            }
        }, HelpBean.class, state);
    }
}
