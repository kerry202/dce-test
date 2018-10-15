package cn.dagongniu.oax.account.module;

import android.app.Activity;

import java.util.HashMap;

import cn.dagongniu.oax.account.bean.EarningsOverviewDataBean;
import cn.dagongniu.oax.base.OAXBaseModule;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;

public class EarningsOverviewModule extends OAXBaseModule<HashMap<String, String>, EarningsOverviewDataBean> {

    public EarningsOverviewModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerData(OnDataListener<EarningsOverviewDataBean> onDataListener, RequestState state, HashMap<String, Object> params) {
        HttpUtils.getInstance().commonPost(Http.overview, params, activity, new OnDataListener<EarningsOverviewDataBean>() {

            @Override
            public void onNewData(CommonJsonToBean<EarningsOverviewDataBean> data) {
                onDataListener.onNewData(data);
            }

            @Override
            public void onError(String code) {
                onDataListener.onError(code);
            }
        }, EarningsOverviewDataBean.class, state);
    }
}
