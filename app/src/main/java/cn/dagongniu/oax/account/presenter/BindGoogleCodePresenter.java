package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.bean.GoogleCodeBean;
import cn.dagongniu.oax.account.module.BindGoogleCodeModule;
import cn.dagongniu.oax.account.module.GoogleCodeModule;
import cn.dagongniu.oax.account.view.IGoogleCodeView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 绑定或修改谷歌验证 Presenter
 */
public class BindGoogleCodePresenter extends BasePresenter {

    private BindGoogleCodeModule bindGoogleCodeModule;
    private IGoogleCodeView iGoogleCodeView;
    private Activity activity;
    RequestState state;

    public BindGoogleCodePresenter(IGoogleCodeView iGoogleCodeView, RequestState state) {
        super(iGoogleCodeView);
        this.state = state;
        activity = (Activity) iGoogleCodeView.getContext();
        this.iGoogleCodeView = iGoogleCodeView;
        bindGoogleCodeModule = new BindGoogleCodeModule(activity);
    }


    public void getBindGoogleCodeModule() {

        bindGoogleCodeModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

            @Override
            public void onNewData(HttpBaseBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iGoogleCodeView, state, data);
                    iGoogleCodeView.bindSuccess();
                } else {
                    StateBaseUtils.failure(iGoogleCodeView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(iGoogleCodeView, state, code);
            }
        }, state,iGoogleCodeView.getGoogleKey(),iGoogleCodeView.getGoogleCode());
    }



}
