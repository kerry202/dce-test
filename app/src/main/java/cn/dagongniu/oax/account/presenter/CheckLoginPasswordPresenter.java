package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.module.BindGoogleCodeModule;
import cn.dagongniu.oax.account.module.CheckLoginPasswordModule;
import cn.dagongniu.oax.account.view.ICheckChangePasswordView;
import cn.dagongniu.oax.account.view.ICheckLoginPasswordView;
import cn.dagongniu.oax.account.view.IGoogleCodeView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 登陆校验密码 Presenter
 */
public class CheckLoginPasswordPresenter extends BasePresenter {

    private CheckLoginPasswordModule checkLoginPasswordModule;
    private ICheckLoginPasswordView iCheckLoginPasswordView;
    private ICheckChangePasswordView iCheckChangePasswordView;
    private Activity activity;
    RequestState state;

    public CheckLoginPasswordPresenter(ICheckChangePasswordView iCheckChangePasswordView, RequestState state) {
        super(iCheckChangePasswordView);
        this.state = state;
        activity = (Activity) iCheckChangePasswordView.getContext();
        this.iCheckChangePasswordView = iCheckChangePasswordView;
        checkLoginPasswordModule = new CheckLoginPasswordModule(activity);
    }

    public CheckLoginPasswordPresenter(ICheckLoginPasswordView iCheckLoginPasswordView, RequestState state) {
        super(iCheckLoginPasswordView);
        this.state = state;
        activity = (Activity) iCheckLoginPasswordView.getContext();
        this.iCheckLoginPasswordView = iCheckLoginPasswordView;
        checkLoginPasswordModule = new CheckLoginPasswordModule(activity);
    }

    /**
     * 登陆时 效验账户密码是否匹配
     */
    public void getCheckLoginPasswordModule() {

        StringBuffer stringBuffer = new StringBuffer();

        if (iCheckLoginPasswordView.isEmailPhone()) {
            String chooseCountries = iCheckLoginPasswordView.getChooseCountries();
            String substring = chooseCountries.substring(1, chooseCountries.length());
            stringBuffer.append("00");
            stringBuffer.append(substring);
            stringBuffer.append(iCheckLoginPasswordView.getUsername());
        } else {
            stringBuffer.append(iCheckLoginPasswordView.getUsername());
        }

        checkLoginPasswordModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

            @Override
            public void onNewData(HttpBaseBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    iCheckLoginPasswordView.isSuccess();
                } else {
                    iCheckLoginPasswordView.setShowCheckPasswordErrer(data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                iCheckLoginPasswordView.setShowCheckPasswordErrer(code);
            }
        }, state, stringBuffer.toString(), iCheckLoginPasswordView.getPassword());
    }

    /**
     * 修改登陆密码 效验账户密码是否匹配
     */
    public void getCheckChangePasswordModule() {

        checkLoginPasswordModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

            @Override
            public void onNewData(HttpBaseBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    iCheckChangePasswordView.isSuccess();
                } else {
                    iCheckChangePasswordView.setShowCheckPasswordErrer(data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                iCheckChangePasswordView.setShowCheckPasswordErrer(code);
            }
        }, state, iCheckChangePasswordView.getUsername(), iCheckChangePasswordView.getPassword());
    }


}
