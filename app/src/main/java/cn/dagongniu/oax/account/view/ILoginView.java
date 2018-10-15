package cn.dagongniu.oax.account.view;

import cn.dagongniu.oax.account.bean.LoginBean;
import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.https.HttpBaseBean;

public interface ILoginView extends IView {

    String getEmailAndPhone(); //邮箱或者手机号码

    String getPassword(); //密码

    String getLoginType();//登录类型

    String getChooseCountries();//区号

    String getSmsCode();//短信验证码

    String getGoogleCode();//Google验证码

    void isLogin(LoginBean data);//登录成功

    void setLoginFailure(String msg);//登录失败

    boolean isEmailPhone();//邮箱还是手机

}
