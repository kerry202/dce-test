package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.HashMap;

import cn.dagongniu.oax.account.bean.HelpDetailInfoBean;
import cn.dagongniu.oax.account.module.HelpDetailModule;
import cn.dagongniu.oax.base.OAXBasePresenter;
import cn.dagongniu.oax.base.OAXIViewBean;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.OAXStateBaseUtils;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;

public class HelpDetailPresenter extends OAXBasePresenter {
    private HelpDetailModule mHelpModule;
    private Activity activity;
    OAXIViewBean view;
    RequestState mState;

    public HelpDetailPresenter(OAXIViewBean view) {
        super(view);
        activity = (Activity) view.getContext();
        this.view = view;
        mHelpModule = new HelpDetailModule(activity);
    }

    public void getData(HashMap<String, Object> params, RequestState state) {
        this.mState = state;
        mHelpModule.requestServerData(new OnDataListener<HelpDetailInfoBean>() {

            @Override
            public void onNewData(CommonJsonToBean<HelpDetailInfoBean> data) {
                KLog.d("onNewData == " + new Gson().toJson(data));
                if (data != null && data.getSuccess()) {
                    if (data.getData() == null || data.getData().getContent().isEmpty()) {
                        OAXStateBaseUtils.isNull(view, mState, data.getMsg());
                    } else {
                        OAXStateBaseUtils.successBean(view, mState, data);
                    }
                } else {
                    OAXStateBaseUtils.failure(view, mState, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                OAXStateBaseUtils.error(view, mState, code);

            }
        }, state, params);
    }
}
