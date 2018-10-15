package cn.dagongniu.oax.assets.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.assets.bean.AssetsPropertyListBean;
import cn.dagongniu.oax.assets.bean.PropertyRechargeBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 充值记录 module
 */
public class PropertyRechargeModule extends BaseModule<String, PropertyRechargeBean> {

    public PropertyRechargeModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<PropertyRechargeBean> onBaseDataListener, String... parm) {
    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<PropertyRechargeBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String coinName = parm[0];
        String pageIndex = parm[1];
        String pageSize = parm[2];
        //hashMap.put("coinName", coinName);              //币种名称
        hashMap.put("pageIndex", pageIndex);            //开始页
        hashMap.put("pageSize", pageSize);              //每页条数

        HttpUtils.getInstance().postLangIdToken(Http.PROPERTY_RECHARGE, hashMap, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    PropertyRechargeBean propertyRechargeBean = new Gson().fromJson(data, PropertyRechargeBean.class);
                    onBaseDataListener.onNewData(propertyRechargeBean);
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
