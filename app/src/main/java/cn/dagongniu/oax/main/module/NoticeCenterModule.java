package cn.dagongniu.oax.main.module;

import android.app.Activity;

import com.google.gson.Gson;

import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.main.bean.BannerBean;
import cn.dagongniu.oax.main.bean.NoticeCenterBean;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 首页公告 module
 */
public class NoticeCenterModule extends BaseModule<String, NoticeCenterBean> {

    public NoticeCenterModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<NoticeCenterBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<NoticeCenterBean> onBaseDataListener, RequestState state, String... parm) {

        HttpUtils.getInstance().getLang(Http.main_noticeCenter, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    NoticeCenterBean noticeCenterBean = new Gson().fromJson(data, NoticeCenterBean.class);
                    onBaseDataListener.onNewData(noticeCenterBean);
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
