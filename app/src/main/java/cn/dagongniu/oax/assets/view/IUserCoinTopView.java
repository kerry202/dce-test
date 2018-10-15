package cn.dagongniu.oax.assets.view;

import cn.dagongniu.oax.assets.bean.PropertyShowBean;
import cn.dagongniu.oax.assets.bean.UserCoinTopBean;
import cn.dagongniu.oax.base.IView;

public interface IUserCoinTopView extends IView {


    void setUserCoinTopData(UserCoinTopBean userCoinTopBean);

    String getCoinId();

    void goLogin(String msg);

}
