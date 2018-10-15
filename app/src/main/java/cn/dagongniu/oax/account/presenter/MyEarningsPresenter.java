package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.HashMap;

import cn.dagongniu.oax.account.bean.MyEarningsDataBean;
import cn.dagongniu.oax.account.module.MyEarningsModule;
import cn.dagongniu.oax.base.OAXBasePresenter;
import cn.dagongniu.oax.base.OAXIViewBean;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;

public class MyEarningsPresenter extends OAXBasePresenter {

    private MyEarningsModule mMyEarningsModule;
    private Activity activity;
    OAXIViewBean view;
    RequestState mState;

    public MyEarningsPresenter(OAXIViewBean view) {
        super(view);
        activity = (Activity) view.getContext();
        this.view = view;
        mMyEarningsModule = new MyEarningsModule(activity);
    }

    public void getData(HashMap<String, Object> params, RequestState state) {
        this.mState = state;
        mMyEarningsModule.requestServerData(new OnDataListener<MyEarningsDataBean>() {

            @Override
            public void onNewData(CommonJsonToBean<MyEarningsDataBean> data) {
                KLog.d("onNewData == " + new Gson().toJson(data));
                if (data.getSuccess()) {
                    view.setData(data);
                } else {
                    view.setXState(LoadingState.STATE_ERROR, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                view.setXState(LoadingState.STATE_ERROR, code);
            }
        }, state, params);
    }
}
