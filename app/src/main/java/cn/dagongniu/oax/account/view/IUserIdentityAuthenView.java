package cn.dagongniu.oax.account.view;

import cn.dagongniu.oax.account.bean.LoginBean;
import cn.dagongniu.oax.base.IView;

public interface IUserIdentityAuthenView extends IView {

    String getIdName(); //名称

    String getCardType(); //证件类型 1身份证

    String getCardNo();//证件号码

    String getIdImageA();//正面图

    String getIdImageB();//反面图

    String getCountry();//国家

    void isSueecss();//成功

    void isfailure(String msg);//失败

}
