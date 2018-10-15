package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.bean.LoginBean;
import cn.dagongniu.oax.account.module.LoginModule;
import cn.dagongniu.oax.account.module.UserIdentityAuthenModule;
import cn.dagongniu.oax.account.view.ILoginView;
import cn.dagongniu.oax.account.view.IUserIdentityAuthenView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/***
 * 身份认证 Presenter
 */
public class UserIdentityAuthenPresenter extends BasePresenter {

    private UserIdentityAuthenModule userIdentityAuthenModule;
    private IUserIdentityAuthenView iUserIdentityAuthenView;
    private Activity activity;
    RequestState state;

    public UserIdentityAuthenPresenter(IUserIdentityAuthenView iUserIdentityAuthenView, RequestState state) {
        super(iUserIdentityAuthenView);
        this.state = state;
        activity = (Activity) iUserIdentityAuthenView.getContext();
        this.iUserIdentityAuthenView = iUserIdentityAuthenView;
        userIdentityAuthenModule = new UserIdentityAuthenModule(activity);
    }

    public void getUserIdentityAuthenModule() {

        userIdentityAuthenModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

            @Override
            public void onNewData(HttpBaseBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    iUserIdentityAuthenView.isSueecss();
                } else {
                    iUserIdentityAuthenView.isfailure(data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                iUserIdentityAuthenView.isfailure(code);
            }
        }, state, iUserIdentityAuthenView.getIdName(), iUserIdentityAuthenView.getCardType(), iUserIdentityAuthenView.getCardNo(), iUserIdentityAuthenView.getIdImageA(), iUserIdentityAuthenView.getIdImageB(), iUserIdentityAuthenView.getCountry());
    }

}
