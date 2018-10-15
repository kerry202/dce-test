package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.module.CheckEmailSmsModule;
import cn.dagongniu.oax.account.module.CheckSmsModule;
import cn.dagongniu.oax.account.view.SendSmsView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 效验邮箱验证码 Presenter
 */
public class CheckEmailSmsPresenter extends BasePresenter {

    private CheckEmailSmsModule checkEmailSmsModule;
    private SendSmsView sendSmsView;
    private Activity activity;
    RequestState state;

    public CheckEmailSmsPresenter(SendSmsView sendSmsView, RequestState state) {
        super(sendSmsView);
        this.state = state;
        activity = (Activity) sendSmsView.getContext();
        this.sendSmsView = sendSmsView;
        checkEmailSmsModule = new CheckEmailSmsModule(activity);
    }

    /***
     * 效验短信验证码
     */
    public void getCheckEmailSmsModule() {

        checkEmailSmsModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

            @Override
            public void onNewData(HttpBaseBean data) {
                sendSmsView.setCheckEmailSms(data);
            }


            @Override
            public void onError(String code) {
                StateBaseUtils.error(sendSmsView, state, code);
            }
        }, state, sendSmsView.getPhoneAndEamil(), sendSmsView.getCode());
    }

}
