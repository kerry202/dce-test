package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.module.ForgetPasswordModule;
import cn.dagongniu.oax.account.view.IRegisteredView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/***
 * 忘记密码 Presenter
 */
public class ForgetPasswordPresenter extends BasePresenter {

    private ForgetPasswordModule forgetPasswordModule;
    private IRegisteredView iRegisteredView;
    private Activity activity;
    RequestState state;

    public ForgetPasswordPresenter(IRegisteredView iRegisteredView, RequestState state) {
        super(iRegisteredView);
        this.state = state;
        activity = (Activity) iRegisteredView.getContext();
        this.iRegisteredView = iRegisteredView;
        forgetPasswordModule = new ForgetPasswordModule(activity);
    }

    public void getForgetPasswordModule() {
        String replace = iRegisteredView.getEmailAndPhone().replace("+", "00");

        forgetPasswordModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

                                                      @Override
                                                      public void onNewData(HttpBaseBean data) {
                                                          if (data.isSuccess()) {
                                                              //响应请求数据回去
                                                              StateBaseUtils.success(iRegisteredView, state, data);
                                                          } else {
                                                              StateBaseUtils.failure(iRegisteredView, state, data.getMsg());
                                                          }
                                                          iRegisteredView.isForgetPassword(data);
                                                      }

                                                      @Override
                                                      public void onError(String code) {
                                                          StateBaseUtils.error(iRegisteredView, state, code);
                                                      }
                                                  }, state,
                replace,
                iRegisteredView.getPassword(),
                iRegisteredView.getRepeatPassword());
    }

}
