package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.module.LoginCheckTypeModule;
import cn.dagongniu.oax.account.view.ILoginCheckTypeView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;
import cn.dagongniu.oax.main.module.BannerModule;
import cn.dagongniu.oax.main.view.BannerView;

/**
 * 登录查询需要的验证类型  Presenter
 */
public class LoginCheckTypePresenter extends BasePresenter {

    private LoginCheckTypeModule loginCheckTypeModule;
    private ILoginCheckTypeView iLoginCheckTypeView;
    private Activity activity;
    RequestState state;

    public LoginCheckTypePresenter(ILoginCheckTypeView iLoginCheckTypeView, RequestState state) {
        super(iLoginCheckTypeView);
        this.state = state;
        activity = (Activity) iLoginCheckTypeView.getContext();
        this.iLoginCheckTypeView = iLoginCheckTypeView;
        loginCheckTypeModule = new LoginCheckTypeModule(activity);
    }

    public void getLoginCheckTypeModule() {

        loginCheckTypeModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

            @Override
            public void onNewData(HttpBaseBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iLoginCheckTypeView, state, data);
                } else {
                    StateBaseUtils.failure(iLoginCheckTypeView, state, data.getMsg());
                }
                data.getData();

            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(iLoginCheckTypeView, state, code);
            }
        }, state, iLoginCheckTypeView.getEmailAndPhone());
    }

}
