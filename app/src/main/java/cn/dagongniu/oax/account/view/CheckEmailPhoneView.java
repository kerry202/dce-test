package cn.dagongniu.oax.account.view;

import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.https.HttpBaseBean;

public interface CheckEmailPhoneView extends IView {

    String getChooseCountries();//

    String getPhoneAndEamil(); //手机号码

    void setCheckEmailSuccess(HttpBaseBean data);//效验通过

    void setCheckEmailFailure(String data);//效验失败

    void setCheckPhoneSuccess(HttpBaseBean data);//效验通过

    void setCheckPhoneFailure(String data);//效验失败


}
