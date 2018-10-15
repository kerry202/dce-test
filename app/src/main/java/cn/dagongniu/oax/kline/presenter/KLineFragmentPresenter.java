package cn.dagongniu.oax.kline.presenter;

import android.app.Activity;

import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import cn.dagongniu.oax.base.OAXBasePresenter;
import cn.dagongniu.oax.base.OAXIViewList;
import cn.dagongniu.oax.https.CommonJsonToList;
import cn.dagongniu.oax.https.OAXStateBaseUtils;
import cn.dagongniu.oax.https.OnDataListListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.kline.bean.KlineInfoBean;
import cn.dagongniu.oax.kline.module.ConstantTest;
import cn.dagongniu.oax.kline.module.KLineFragmentModule;

public class KLineFragmentPresenter extends OAXBasePresenter {

    private KLineFragmentModule mModule;
    private OAXIViewList mView;
    private Activity mActivity;
    private RequestState mState;

    public KLineFragmentPresenter(OAXIViewList iView, RequestState state) {
        super(iView);
        this.mState = state;
        mActivity = (Activity) iView.getContext();
        mView = iView;
        mModule = new KLineFragmentModule(mActivity);
    }

    public void getData(HashMap<String, Object> params, RequestState state) {
        this.mState = state;
        mModule.requestServerDataList(new OnDataListListener<KlineInfoBean>() {
            @Override
            public void onNewData(CommonJsonToList<KlineInfoBean> data) {
                List<KlineInfoBean> beans = data.getData();
                KLog.d("onNewData size()== " + beans.size());
                if (beans.size() == 0) {
                    OAXStateBaseUtils.isNull(mView, mState, data.getMsg());
                } else {
                    OAXStateBaseUtils.successList(mView, mState, data);
                }
            }

            @Override
            public void onError(String code) {
                OAXStateBaseUtils.error(mView, state, code);
            }
        }, state, params);
    }
}
