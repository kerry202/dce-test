package cn.dagongniu.oax.kline.module;

import android.app.Activity;

import java.util.HashMap;

import cn.dagongniu.oax.base.OAXBaseModule;
import cn.dagongniu.oax.https.CommonJsonToList;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnDataListListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.kline.bean.KlineInfoBean;

public class KLineFragmentModule extends OAXBaseModule<String, KlineInfoBean> {
    public KLineFragmentModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataList(OnDataListListener<KlineInfoBean> dataListListener, RequestState state, HashMap<String, Object> params) {
        HttpUtils.getInstance().commonPostList(Http.KLINE_FINDLISTBYMARKETID, params, activity, new OnDataListListener<KlineInfoBean>() {
            @Override
            public void onNewData(CommonJsonToList<KlineInfoBean> data) {
                dataListListener.onNewData(data);
            }

            @Override
            public void onError(String msg) {
                dataListListener.onError(msg);
            }
        }, KlineInfoBean.class, state);
    }
}
