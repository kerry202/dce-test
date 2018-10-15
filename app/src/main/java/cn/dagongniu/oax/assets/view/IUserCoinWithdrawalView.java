package cn.dagongniu.oax.assets.view;

import cn.dagongniu.oax.assets.bean.UserCoinTopBean;
import cn.dagongniu.oax.assets.bean.UserCoinWithdrawalBean;
import cn.dagongniu.oax.base.IView;

public interface IUserCoinWithdrawalView extends IView {


    void setUserCoinWithdrawalData(UserCoinWithdrawalBean userCoinTopBean);

    String getCoinId();

    void goLogin(String msg);

}
