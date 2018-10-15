package cn.dagongniu.oax.account.view;

import cn.dagongniu.oax.account.bean.LoginBean;
import cn.dagongniu.oax.account.bean.MyInvateBean;
import cn.dagongniu.oax.assets.bean.UserCoinTopBean;
import cn.dagongniu.oax.base.IView;

public interface IMyInvateView extends IView {

    void goLogin(String msg);

    void setMyInvateData(MyInvateBean myInvateBean);

}
