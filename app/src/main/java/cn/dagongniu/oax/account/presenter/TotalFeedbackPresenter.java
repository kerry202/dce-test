package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.HashMap;

import cn.dagongniu.oax.account.bean.TotalFeedbackDataBean;
import cn.dagongniu.oax.account.module.TotalFeedbackModule;
import cn.dagongniu.oax.base.OAXBasePresenter;
import cn.dagongniu.oax.base.OAXIViewBean;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;

public class TotalFeedbackPresenter extends OAXBasePresenter {

    private TotalFeedbackModule mModule;
    private Activity activity;
    OAXIViewBean view;
    RequestState mState;

    public TotalFeedbackPresenter(OAXIViewBean view) {
        super(view);
        activity = (Activity) view.getContext();
        this.view = view;
        mModule = new TotalFeedbackModule(activity);
    }

    public void getData(HashMap<String, Object> params, RequestState state) {
        this.mState = state;
        mModule.requestServerData(new OnDataListener<TotalFeedbackDataBean>() {
            @Override
            public void onNewData(CommonJsonToBean<TotalFeedbackDataBean> data) {
                KLog.d("onNewData == " + new Gson().toJson(data));
                if (data.getSuccess()) {
                    view.setData(data);
                } else {
                    view.setXState(LoadingState.STATE_ERROR, data.getMsg());
                }
            }

            @Override
            public void onError(String msg) {
                view.setXState(LoadingState.STATE_ERROR, msg);
            }
        }, state, params);
    }
}
