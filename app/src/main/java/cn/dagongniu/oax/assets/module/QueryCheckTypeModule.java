package cn.dagongniu.oax.assets.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.assets.bean.CoinAddressListBean;
import cn.dagongniu.oax.assets.bean.QueryCheckTypeBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 查询需要的验证类型 module
 */
public class QueryCheckTypeModule extends BaseModule<String, QueryCheckTypeBean> {

    public QueryCheckTypeModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<QueryCheckTypeBean> onBaseDataListener, String... parm) {
    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<QueryCheckTypeBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String username = parm[0];
        hashMap.put("username", username);          //账号

        HttpUtils.getInstance().postLang(Http.USER_QUERYCHECKTYPE, hashMap, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    QueryCheckTypeBean queryCheckTypeBean = new Gson().fromJson(data, QueryCheckTypeBean.class);
                    onBaseDataListener.onNewData(queryCheckTypeBean);
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
