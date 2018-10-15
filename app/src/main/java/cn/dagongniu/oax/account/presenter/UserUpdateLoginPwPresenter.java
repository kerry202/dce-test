package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.module.CheckSmsModule;
import cn.dagongniu.oax.account.module.UserUpdateLoginPwModule;
import cn.dagongniu.oax.account.view.IUserLoginLoginPwView;
import cn.dagongniu.oax.account.view.SendSmsView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 修改登录密码 Presenter
 */
public class UserUpdateLoginPwPresenter extends BasePresenter {

    private UserUpdateLoginPwModule userUpdateLoginPwModule;
    private IUserLoginLoginPwView iUserLoginLoginPwView;
    private Activity activity;
    RequestState state;

    public UserUpdateLoginPwPresenter(IUserLoginLoginPwView iUserLoginLoginPwView, RequestState state) {
        super(iUserLoginLoginPwView);
        this.state = state;
        activity = (Activity) iUserLoginLoginPwView.getContext();
        this.iUserLoginLoginPwView = iUserLoginLoginPwView;
        userUpdateLoginPwModule = new UserUpdateLoginPwModule(activity);
    }


    public void getUserUpdateLoginPwModule() {

        userUpdateLoginPwModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {
                                                         @Override
                                                         public void onNewData(HttpBaseBean data) {
                                                             if (data.isSuccess()) {
                                                                 //响应请求数据回去
                                                                 iUserLoginLoginPwView.UpdateSuccess();
                                                             } else {
                                                                 StateBaseUtils.failure(iUserLoginLoginPwView, state, data.getMsg());
                                                             }
                                                         }

                                                         @Override
                                                         public void onError(String code) {
                                                             StateBaseUtils.error(iUserLoginLoginPwView, state, code);
                                                         }
                                                     },
                state,
                iUserLoginLoginPwView.getOldPassword(),
                iUserLoginLoginPwView.getNewPassword(),
                iUserLoginLoginPwView.getRepeatPassword(),
                iUserLoginLoginPwView.getSmsCode(),
                iUserLoginLoginPwView.getEmailCode(),
                iUserLoginLoginPwView.getGoogleCode());
    }

}
