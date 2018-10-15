package cn.dagongniu.oax.account.view;

import cn.dagongniu.oax.account.bean.GoogleCodeBean;
import cn.dagongniu.oax.base.IView;

public interface IUserLoginLoginPwView extends IView {

    void UpdateSuccess();//修改成功

    String getOldPassword();

    String getNewPassword();

    String getRepeatPassword();

    String getGoogleCode();

    String getSmsCode();

    String getEmailCode();

}
