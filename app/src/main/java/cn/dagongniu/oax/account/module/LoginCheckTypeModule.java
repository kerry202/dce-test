package cn.dagongniu.oax.account.module;

import android.app.Activity;

import com.google.gson.Gson;

import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 登录查询需要的验证类型 module
 */
public class LoginCheckTypeModule extends BaseModule<String, HttpBaseBean> {

    public LoginCheckTypeModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<HttpBaseBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<HttpBaseBean> onBaseDataListener, RequestState state, String... parm) {
        String username = parm[0];//账号

        HttpUtils.getInstance().getLang(Http.user_queryCheckType + "/" + username, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    HttpBaseBean httpBaseBean = new Gson().fromJson(data, HttpBaseBean.class);
                    onBaseDataListener.onNewData(httpBaseBean);
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
