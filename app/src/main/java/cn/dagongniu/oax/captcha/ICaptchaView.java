package cn.dagongniu.oax.captcha;

import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.https.HttpBaseBean;

public interface ICaptchaView extends IView {

    String getValidate(); //验证码组件提交上来的NECaptchaValidate

    void setfailure(String data);

    void setSuccess(String data);

}
