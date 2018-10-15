package cn.dagongniu.oax.account.module;

import android.app.Activity;

import java.util.HashMap;

import cn.dagongniu.oax.account.bean.TotalFeedbackDataBean;
import cn.dagongniu.oax.base.OAXBaseModule;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;

public class TotalFeedbackModule extends OAXBaseModule<HashMap<String, String>, TotalFeedbackDataBean> {

    public TotalFeedbackModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerData(OnDataListener<TotalFeedbackDataBean> onDataListener, RequestState state, HashMap<String, Object> params) {
        HttpUtils.getInstance().commonPost(Http.Total_Feedback, params, activity, new OnDataListener<TotalFeedbackDataBean>() {

            @Override
            public void onNewData(CommonJsonToBean<TotalFeedbackDataBean> data) {
                onDataListener.onNewData(data);
            }

            @Override
            public void onError(String code) {
                onDataListener.onError(code);
            }
        }, TotalFeedbackDataBean.class, state);
    }
}
