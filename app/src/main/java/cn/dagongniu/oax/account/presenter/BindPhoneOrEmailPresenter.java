package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.module.BindEamilModule;
import cn.dagongniu.oax.account.module.BindPhoneModule;
import cn.dagongniu.oax.account.view.IBindPhoneEmailView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 绑定手机 Presenter
 */
public class BindPhoneOrEmailPresenter extends BasePresenter {

    private BindPhoneModule bindPhoneModule;
    private BindEamilModule bindEamilModule;
    private IBindPhoneEmailView iBindPhoneEmailView;
    private Activity activity;
    RequestState state;

    public BindPhoneOrEmailPresenter(IBindPhoneEmailView iBindPhoneEmailView, RequestState state) {
        super(iBindPhoneEmailView);
        this.state = state;
        activity = (Activity) iBindPhoneEmailView.getContext();
        this.iBindPhoneEmailView = iBindPhoneEmailView;
        bindPhoneModule = new BindPhoneModule(activity);
        bindEamilModule = new BindEamilModule(activity);
    }

    /**
     * 绑定手机
     */
    public void getBindPhoneModule() {
        String chooseCountries = iBindPhoneEmailView.getChooseCountries();
        String substring = chooseCountries.substring(1, chooseCountries.length());

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("00");
        stringBuffer.append(substring);

        bindPhoneModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

            @Override
            public void onNewData(HttpBaseBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    iBindPhoneEmailView.successful(data.getMsg());
                } else {
                    iBindPhoneEmailView.failure(data.getMsg());
                }
            }


            @Override
            public void onError(String code) {
                iBindPhoneEmailView.failure(code);
            }
        }, state, stringBuffer.toString() + iBindPhoneEmailView.getPhoneOrEmail(), iBindPhoneEmailView.getCode());
    }

    /**
     * 绑定邮箱
     */
    public void getBindEamilModule() {

        bindEamilModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

            @Override
            public void onNewData(HttpBaseBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    iBindPhoneEmailView.successful(data.getMsg());
                } else {
                    iBindPhoneEmailView.failure(data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                iBindPhoneEmailView.failure(code);
            }
        }, state, iBindPhoneEmailView.getPhoneOrEmail(), iBindPhoneEmailView.getCode());
    }

}
