package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.HashMap;

import cn.dagongniu.oax.account.bean.EarningsOverviewDataBean;
import cn.dagongniu.oax.account.module.EarningsOverviewModule;
import cn.dagongniu.oax.base.OAXBasePresenter;
import cn.dagongniu.oax.base.OAXIViewBean;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;

public class EarningsOverviewPresenter extends OAXBasePresenter {

    private EarningsOverviewModule mModule;
    private Activity activity;
    OAXIViewBean view;
    RequestState mState;

    public EarningsOverviewPresenter(OAXIViewBean view) {
        super(view);
        activity = (Activity) view.getContext();
        this.view = view;
        mModule = new EarningsOverviewModule(activity);
    }

    public void getData(HashMap<String, Object> params, RequestState state) {
        this.mState = state;
        mModule.requestServerData(new OnDataListener<EarningsOverviewDataBean>() {

            @Override
            public void onNewData(CommonJsonToBean<EarningsOverviewDataBean> data) {
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
