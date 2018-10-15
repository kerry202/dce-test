package cn.dagongniu.oax.assets.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.oax.assets.bean.SendRedPacketRecordBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 我发出的红包记录 module
 */
public class SendRedPacketRecordModule extends BaseModule<String, SendRedPacketRecordBean> {

    public SendRedPacketRecordModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<SendRedPacketRecordBean> onBaseDataListener, String... parm) {
    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<SendRedPacketRecordBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String pageNo = parm[0];
        String pageSize = parm[1];
        hashMap.put("pageNo", pageNo);            //开始页
        hashMap.put("pageSize", pageSize);              //每页条数

        HttpUtils.getInstance().postLangIdToken(Http.AWARDREDPACKETRECORD, hashMap, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    SendRedPacketRecordBean sendRedPacketRecordBean = new Gson().fromJson(data, SendRedPacketRecordBean.class);
                    onBaseDataListener.onNewData(sendRedPacketRecordBean);
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
