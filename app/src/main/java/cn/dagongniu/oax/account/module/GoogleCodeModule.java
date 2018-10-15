package cn.dagongniu.oax.account.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.account.bean.GoogleCodeBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * google验证查询 module
 */
public class GoogleCodeModule extends BaseModule<String, GoogleCodeBean> {

    public GoogleCodeModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<GoogleCodeBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<GoogleCodeBean> onBaseDataListener, RequestState state, String... parm) {


        HttpUtils.getInstance().getLangIdToKen(Http.USER_GETGOOGLEQRBARCODEURL, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    GoogleCodeBean googleCodeBean = new Gson().fromJson(data, GoogleCodeBean.class);
                    onBaseDataListener.onNewData(googleCodeBean);
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
