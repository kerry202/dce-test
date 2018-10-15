package cn.dagongniu.oax.trading.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.trading.bean.TransactionIndexBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 交易首页的 module
 */
public class TransactionIndexModule extends BaseModule<String, TransactionIndexBean> {

    public TransactionIndexModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<TransactionIndexBean> onBaseDataListener, String... parm) {
    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<TransactionIndexBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String marketId = parm[0];
        String minType = parm[1];
        hashMap.put("marketId", marketId);              //交易对id
        hashMap.put("minType", minType);              //时间类型 查询K线数据的条件

        HttpUtils.getInstance().postLang(Http.TRANSACTIONPAGE_INDEX, hashMap, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    TransactionIndexBean indexPageBean = new Gson().fromJson(data, TransactionIndexBean.class);
                    onBaseDataListener.onNewData(indexPageBean);
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
