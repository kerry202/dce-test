package cn.dagongniu.oax.account.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.account.bean.LoginBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 身份认证
 */
public class UserIdentityAuthenModule extends BaseModule<String, HttpBaseBean> {

    public UserIdentityAuthenModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<HttpBaseBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<HttpBaseBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String idName = parm[0];
        String cardType = parm[1];
        String cardNo = parm[2];
        String idImageA = parm[3];
        String idImageB = parm[4];
        String country = parm[5];

        hashMap.put("idName", idName);                  //名称
        hashMap.put("cardType", cardType);              //证件类型 1身份证
        hashMap.put("cardNo", cardNo);                  //证件号码
        hashMap.put("idImageA", idImageA);              //正面图
        hashMap.put("idImageB", idImageB);              //反面图
        hashMap.put("country", country);                //国家
        HttpUtils.getInstance().postLangIdToken(Http.USER_IDENTITYAUTHEN, hashMap, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    HttpBaseBean loginBean = new Gson().fromJson(data, HttpBaseBean.class);
                    onBaseDataListener.onNewData(loginBean);
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
