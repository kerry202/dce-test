package cn.dagongniu.oax.account.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 切换安全验证 module
 */
public class SwitchCheckModule extends BaseModule<String, HttpBaseBean> {

    public SwitchCheckModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<HttpBaseBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<HttpBaseBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String emailCode = parm[0];
        String smsCode = parm[1];
        String googleCode = parm[2];
        String type = parm[3];
        String status = parm[4];

        hashMap.put("emailCode", emailCode);                   //emai验证码
        hashMap.put("smsCode", smsCode);                       //短信验证码
        hashMap.put("googleCode", googleCode);                 //谷歌验证码
        hashMap.put("type", type);                             //1 手机 2邮箱 3google
        hashMap.put("status", status);                         //0 关闭 1开启

        HttpUtils.getInstance().postLangIdToken(Http.USER_SWITCHCHECK, hashMap, activity, new OnBaseDataListener<String>() {
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
