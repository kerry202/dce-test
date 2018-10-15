package cn.dagongniu.oax.account.view;

import cn.dagongniu.oax.account.bean.IdentityResultBean;
import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.https.HttpBaseBean;

public interface IidentityResultView extends IView {

    void IidentityResultsuccessful(IdentityResultBean httpBaseBean);//成功

    void IidentityResultfailure(String msg);//失败

}
