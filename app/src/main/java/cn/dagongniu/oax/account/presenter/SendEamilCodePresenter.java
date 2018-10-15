package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.module.SendEamilCodeModule;
import cn.dagongniu.oax.account.module.SendSmsModule;
import cn.dagongniu.oax.account.view.SendSmsView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 邮箱验证码 Presenter
 */
public class SendEamilCodePresenter extends BasePresenter {

    private SendEamilCodeModule sendEamilCodeModule;
    private SendSmsView sendSmsView;
    private Activity activity;
    RequestState state;

    public SendEamilCodePresenter(SendSmsView sendSmsView, RequestState state) {
        super(sendSmsView);
        this.state = state;
        activity = (Activity) sendSmsView.getContext();
        this.sendSmsView = sendSmsView;
        sendEamilCodeModule = new SendEamilCodeModule(activity);
    }

    /***
     * 邮箱验证
     */
    public void getSendEamilCodeModule() {
        sendEamilCodeModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

            @Override
            public void onNewData(HttpBaseBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(sendSmsView, state, data);
                } else {
                    StateBaseUtils.failure(sendSmsView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(sendSmsView, state, code);
            }
        }, state, sendSmsView.getEamil());
    }

}
