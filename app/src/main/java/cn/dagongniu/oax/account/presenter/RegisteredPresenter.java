package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.module.PhoneRegisteredModule;
import cn.dagongniu.oax.account.module.RegisteredModule;
import cn.dagongniu.oax.account.view.IRegisteredView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/***
 * 注册 Presenter
 */
public class RegisteredPresenter extends BasePresenter {

    private RegisteredModule registeredModule;
    private IRegisteredView iRegisteredView;
    private Activity activity;
    RequestState state;

    public RegisteredPresenter(IRegisteredView iRegisteredView, RequestState state) {
        super(iRegisteredView);
        this.state = state;
        activity = (Activity) iRegisteredView.getContext();
        this.iRegisteredView = iRegisteredView;
        registeredModule = new RegisteredModule(activity);
    }

    public void getRegisteredModule() {
        String replace = iRegisteredView.getEmailAndPhone().replace("+", "00");

        registeredModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

                                                  @Override
                                                  public void onNewData(HttpBaseBean data) {
                                                      if (data.isSuccess()) {
                                                          //响应请求数据回去
                                                          StateBaseUtils.success(iRegisteredView, state, data);
                                                      } else {
                                                          StateBaseUtils.failure(iRegisteredView, state, data.getMsg());
                                                      }
                                                      iRegisteredView.isRegistered(data);
                                                  }

                                                  @Override
                                                  public void onError(String code) {
                                                      StateBaseUtils.error(iRegisteredView, state, code);
                                                  }
                                              }, state,
                replace,
                iRegisteredView.getPassword(),
                iRegisteredView.getRepeatPassword(),
                iRegisteredView.getInvateCode(),
                iRegisteredView.getRegisteredType());
    }

}
