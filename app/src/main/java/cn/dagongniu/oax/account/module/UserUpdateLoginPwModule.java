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
 * 修改登录密码 module
 */
public class UserUpdateLoginPwModule extends BaseModule<String, HttpBaseBean> {

    public UserUpdateLoginPwModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<HttpBaseBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<HttpBaseBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String oldPassword = parm[0];
        String newPassword = parm[1];
        String repeatPassword = parm[2];
        String smsCode = parm[3];
        String emailCode = parm[4];
        String googleCode = parm[5];

        hashMap.put("oldPassword", oldPassword);                      //旧密码
        hashMap.put("newPassword", newPassword);                      //新密码
        hashMap.put("repeatPassword", repeatPassword);                //重复证码
        hashMap.put("smsCode", smsCode);                              //短信验证码
        hashMap.put("emailCode", emailCode);                          //邮箱验证码
        hashMap.put("googleCode", googleCode);                        //google验证码

        HttpUtils.getInstance().postLangIdToken(Http.USER_UPDATELOGINPASSWORD, hashMap, activity, new OnBaseDataListener<String>() {
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
