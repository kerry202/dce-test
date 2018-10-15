package cn.dagongniu.oax.main.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.main.bean.BannerBean;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 首页banner加载 module
 */
public class BannerModule extends BaseModule<String, BannerBean> {

    public BannerModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<BannerBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<BannerBean> onBaseDataListener, RequestState state, String... parm) {

        HttpUtils.getInstance().getLang(Http.main_banner + "/" + AppConstants.SOURCE_PC_APP, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    BannerBean bannerBean = new Gson().fromJson(data, BannerBean.class);
                    onBaseDataListener.onNewData(bannerBean);
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
