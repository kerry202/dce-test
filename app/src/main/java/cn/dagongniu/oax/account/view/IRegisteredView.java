package cn.dagongniu.oax.account.view;

import android.content.Context;

import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.https.HttpBaseBean;

public interface IRegisteredView extends IView {

    String getEmailAndPhone(); //邮箱或者手机号码

    String getPassword(); //密码

    String getRepeatPassword();//重复密码

    String getInvateCode(); //邀请码

    String getRegisteredType();//注册类型

    void isRegistered(HttpBaseBean data);//注册回调

    void isForgetPassword(HttpBaseBean data);//修改回调
}
