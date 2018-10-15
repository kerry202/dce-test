package cn.dagongniu.oax.assets.view;

import cn.dagongniu.oax.assets.bean.PropertyShowBean;
import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.oax.base.IView;

public interface IPropertyShowView extends IView {


    void setRefreshPropertyShowData(PropertyShowBean propertyShowBean);

    String getCoinId();

    void goLogin(String msg);

}
