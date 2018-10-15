package cn.dagongniu.oax.assets.module;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.assets.bean.AwardRedPacketBean;
import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 发送红包 module
 */
public class AwardRedPacketModule extends BaseModule<String, AwardRedPacketBean> {

    public AwardRedPacketModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<AwardRedPacketBean> onBaseDataListener, String... parm) {
    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<AwardRedPacketBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String coinId = parm[0];
        String type = parm[1];
        String totalCoinQty = parm[2];
        String totalPacketQty = parm[3];
        String wishWords = parm[4];

        hashMap.put("coinId", coinId); //币种id
        hashMap.put("type", type);   //红包类型
        hashMap.put("totalCoinQty", totalCoinQty); //红包总金额
        hashMap.put("totalPacketQty", totalPacketQty); //红包数量
        if (TextUtils.isEmpty(wishWords)) {
            wishWords = "红包一甩，别墅靠海！";
        }
        hashMap.put("wishWords", wishWords); //祝福语

        HttpUtils.getInstance().postLangIdToken(Http.AWARD, hashMap, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    AwardRedPacketBean awardRedPacketBean = new Gson().fromJson(data, AwardRedPacketBean.class);
                    onBaseDataListener.onNewData(awardRedPacketBean);
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
