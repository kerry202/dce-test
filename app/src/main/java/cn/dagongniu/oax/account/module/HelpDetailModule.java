package cn.dagongniu.oax.account.module;

import android.app.Activity;

import java.util.HashMap;

import cn.dagongniu.oax.account.bean.HelpBean;
import cn.dagongniu.oax.account.bean.HelpDetailInfoBean;
import cn.dagongniu.oax.base.OAXBaseModule;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;

public class HelpDetailModule extends OAXBaseModule<HashMap<String, String>, HelpDetailInfoBean> {

    public HelpDetailModule(Activity activity) {
        super(activity);
    }


    @Override
    public void requestServerData(final OnDataListener<HelpDetailInfoBean> onBaseDataListener, RequestState state, HashMap<String, Object> params) {
        HttpUtils.getInstance().commonPost(Http.HELP_CENTER_READDETAIL, params, activity, new OnDataListener<HelpDetailInfoBean>() {

            @Override
            public void onNewData(CommonJsonToBean<HelpDetailInfoBean> data) {
                onBaseDataListener.onNewData(data);
            }

            @Override
            public void onError(String code) {
                onBaseDataListener.onError(code);
            }
        }, HelpDetailInfoBean.class, state);
    }
}
