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
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 手机注册
 */
public class PhoneRegisteredModule extends BaseModule<String, HttpBaseBean> {

    public PhoneRegisteredModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<HttpBaseBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<HttpBaseBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String phone = parm[0];
        String password = parm[1];
        String repeatPassword = parm[2];
        String smsCode = parm[3];
        String invateCode = parm[4];
        hashMap.put("phone", phone);                    //手机号码
        hashMap.put("password", password);              //密码
        hashMap.put("repeatPassword", repeatPassword);  //确认密码
        hashMap.put("smsCode", smsCode);                //短信验证码
        hashMap.put("invateCode", invateCode);          //邀请码
        hashMap.put("source", AppConstants.SOURCE);     //来源 1 pc 2 ios 3 android


        HttpUtils.getInstance().postLang(Http.user_emailRegister, hashMap, activity, new OnBaseDataListener<String>() {
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
