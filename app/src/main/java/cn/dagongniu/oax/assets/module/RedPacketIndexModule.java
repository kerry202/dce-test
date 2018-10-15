package cn.dagongniu.oax.assets.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.assets.bean.AwardRedPacketBean;
import cn.dagongniu.oax.assets.bean.RedPacketIndexBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 红包首页 module
 */
public class RedPacketIndexModule extends BaseModule<String, RedPacketIndexBean> {

    public RedPacketIndexModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<RedPacketIndexBean> onBaseDataListener, String... parm) {
    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<RedPacketIndexBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String type = parm[0];


        HttpUtils.getInstance().getLangIdToKen(Http.RED_INDEX + type, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    RedPacketIndexBean redPacketIndexBean = new Gson().fromJson(data, RedPacketIndexBean.class);
                    onBaseDataListener.onNewData(redPacketIndexBean);
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
