package cn.dagongniu.oax.account.module;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import cn.dagongniu.oax.account.bean.CountryCodeBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.DebugUtils;
import cn.dagongniu.oax.utils.Logger;

/**
 * 国家区号 module
 */
public class CountryCodeModule extends BaseModule<String, CountryCodeBean> {

    public CountryCodeModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<CountryCodeBean> onBaseDataListener, String... parm) {
    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<CountryCodeBean> onBaseDataListener, RequestState state, String... parm) {

        HttpUtils.getInstance().postLang(Http.user_countryCode, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    CountryCodeBean httpBaseBean = new Gson().fromJson(data, CountryCodeBean.class);
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
