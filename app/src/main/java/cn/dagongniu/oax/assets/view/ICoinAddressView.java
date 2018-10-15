package cn.dagongniu.oax.assets.view;

import cn.dagongniu.oax.assets.bean.CoinAddressListBean;
import cn.dagongniu.oax.base.IView;

public interface ICoinAddressView extends IView {

    String getCoinId();//币种id

    String getAddress();//地址

    String getRemark();//备注

    void isSuccess();

    void goLogin(String msg);

    String getSmsCode();

    String getEmailCode();

    String getGoogleCode();

}
