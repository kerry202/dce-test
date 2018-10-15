package cn.dagongniu.oax.assets.view;

import cn.dagongniu.oax.base.IView;

public interface IWithdrawalView extends IView {

    String getCoinId();//币种id

    String getAddress();//地址

    String getRemark();//备注

    String getqty();//数量

    void isSuccess();

    void goLogin(String msg);


}
