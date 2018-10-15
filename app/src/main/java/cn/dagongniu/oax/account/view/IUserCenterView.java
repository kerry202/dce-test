package cn.dagongniu.oax.account.view;

import cn.dagongniu.oax.account.bean.UserCenterBean;
import cn.dagongniu.oax.base.IView;

public interface IUserCenterView extends IView {

    void setUserCenterData(UserCenterBean userCenterData);//成功

    void setUserCenterFailure(String msg);//失败


}
