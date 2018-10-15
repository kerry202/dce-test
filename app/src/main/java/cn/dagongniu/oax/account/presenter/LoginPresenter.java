package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.bean.LoginBean;
import cn.dagongniu.oax.account.module.LoginModule;
import cn.dagongniu.oax.account.module.RegisteredModule;
import cn.dagongniu.oax.account.view.ILoginView;
import cn.dagongniu.oax.account.view.IRegisteredView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/***
 * 登录 Presenter
 */
public class LoginPresenter extends BasePresenter {

    private LoginModule loginModule;
    private ILoginView iLoginView;
    private Activity activity;
    RequestState state;

    public LoginPresenter(ILoginView iLoginView, RequestState state) {
        super(iLoginView);
        this.state = state;
        activity = (Activity) iLoginView.getContext();
        this.iLoginView = iLoginView;
        loginModule = new LoginModule(activity);
    }

    public void getLoginModule() {

        StringBuffer stringBuffer = new StringBuffer();

        if (iLoginView.isEmailPhone()) {
            String chooseCountries = iLoginView.getChooseCountries();
            String substring = chooseCountries.substring(1, chooseCountries.length());
            stringBuffer.append("00");
            stringBuffer.append(substring);
            stringBuffer.append(iLoginView.getEmailAndPhone());
        } else {
            stringBuffer.append(iLoginView.getEmailAndPhone());
        }

        loginModule.requestServerDataOne(new OnBaseDataListener<LoginBean>() {

            @Override
            public void onNewData(LoginBean data) {
                iLoginView.isLogin(data);
            }

            @Override
            public void onError(String code) {
                iLoginView.setLoginFailure(code);
            }
        }, state, stringBuffer.toString(), iLoginView.getPassword(), iLoginView.getLoginType(), iLoginView.getSmsCode(), iLoginView.getGoogleCode());
    }

}
