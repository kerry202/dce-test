package cn.dagongniu.oax.account.view;

import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.https.HttpBaseBean;

public interface ILoginCheckTypeView extends IView {

    String getEmailAndPhone(); //邮箱或者手机号码 用户名


    void setILoginCheckType(HttpBaseBean data);//检查验证回调

}
