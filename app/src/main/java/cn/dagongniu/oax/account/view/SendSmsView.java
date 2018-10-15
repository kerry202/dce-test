package cn.dagongniu.oax.account.view;

import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.https.HttpBaseBean;

public interface SendSmsView extends IView {

    String getChooseCountries();//

    String getPhone(); //手机号码

    String getEamil();//邮箱

    String getType(); //发短信 类型 暂定 1 注册 2登陆......

    String getCode();

    String getPhoneAndEamil();//邮箱或者手机

    void setCheckPhoneSms(HttpBaseBean data);//效验手机验证码回调
    void setCheckEmailSms(HttpBaseBean data);//效验邮箱验证码回调
}
