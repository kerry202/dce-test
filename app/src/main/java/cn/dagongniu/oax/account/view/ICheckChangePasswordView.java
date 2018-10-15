package cn.dagongniu.oax.account.view;

import cn.dagongniu.oax.base.IView;

public interface ICheckChangePasswordView extends IView {

    void isSuccess();

    void setShowCheckPasswordErrer(String msg);

    String getPassword();

    String getUsername();


}
