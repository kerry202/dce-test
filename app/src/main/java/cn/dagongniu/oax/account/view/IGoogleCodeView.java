package cn.dagongniu.oax.account.view;

import cn.dagongniu.oax.account.bean.GoogleCodeBean;
import cn.dagongniu.oax.account.bean.MyInvateBean;
import cn.dagongniu.oax.base.IView;

public interface IGoogleCodeView extends IView {

    void setGoogleCodeData(GoogleCodeBean googleCodeData);

    void bindSuccess();

    String getGoogleKey();

    String getGoogleCode();
}
