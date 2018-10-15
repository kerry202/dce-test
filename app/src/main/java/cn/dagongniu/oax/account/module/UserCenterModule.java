package cn.dagongniu.oax.account.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.account.bean.LoginBean;
import cn.dagongniu.oax.account.bean.UserCenterBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 个人中心
 */
public class UserCenterModule extends BaseModule<String, UserCenterBean> {

    public UserCenterModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<UserCenterBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<UserCenterBean> onBaseDataListener, RequestState state, String... parm) {

        HttpUtils.getInstance().getLangIdToKenMine(Http.USER_USERCENTER, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    UserCenterBean userCenterBean = new Gson().fromJson(data, UserCenterBean.class);
                    onBaseDataListener.onNewData(userCenterBean);
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
