package cn.dagongniu.oax.account.view;

import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.https.HttpBaseBean;

public interface IBindPhoneEmailView extends IView {

    String getChooseCountries();//

    String getPhoneOrEmail(); //手机号码或者邮箱

    String getCode();

    void successful(String msg);//成功
    void failure(String msg);//失败
}
