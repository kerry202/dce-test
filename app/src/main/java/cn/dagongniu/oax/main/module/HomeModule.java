package cn.dagongniu.oax.main.module;

import android.app.Activity;

import com.google.gson.Gson;

import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.OkHttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.main.bean.TestBean;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 首页
 */

public class HomeModule extends BaseModule<String, TestBean> {

    public HomeModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<TestBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<TestBean> onBaseDataListener, RequestState state, String... parm) {
        OkHttpUtils.getInstance().get(Http.TEST_ROOT, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    TestBean piazzaBean = new Gson().fromJson(data, TestBean.class);
                    onBaseDataListener.onNewData(piazzaBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String code) {
                onBaseDataListener.onError(code);
            }
        },state);
    }




}
