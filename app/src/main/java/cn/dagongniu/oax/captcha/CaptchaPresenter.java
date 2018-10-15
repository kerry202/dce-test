package cn.dagongniu.oax.captcha;

import android.app.Activity;

import cn.dagongniu.oax.account.bean.LoginBean;
import cn.dagongniu.oax.account.module.LoginModule;
import cn.dagongniu.oax.account.view.ILoginView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/***
 * 网易云滑块验证码二次校验 Presenter
 */
public class CaptchaPresenter extends BasePresenter {

    private CaptchaModule captchaModule;
    private ICaptchaView ICaptchaView;
    private Activity activity;
    RequestState state;

    public CaptchaPresenter(ICaptchaView ICaptchaView, RequestState state) {
        super(ICaptchaView);
        this.state = state;
        activity = (Activity) ICaptchaView.getContext();
        this.ICaptchaView = ICaptchaView;
        captchaModule = new CaptchaModule(activity);
    }

    public void getCaptchaModule() {
        captchaModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

            @Override
            public void onNewData(HttpBaseBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    ICaptchaView.setSuccess(data.getMsg());
                } else {
                    ICaptchaView.setfailure(data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                ICaptchaView.setfailure(code);
            }
        }, state, ICaptchaView.getValidate());
    }

}
