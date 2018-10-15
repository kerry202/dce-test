package cn.dagongniu.oax.account.view;

import cn.dagongniu.oax.account.bean.GoogleCodeBean;
import cn.dagongniu.oax.base.IView;

public interface IFileUpdoadView extends IView {

    void setFullFaceContainerSuccess(String data);

    void setReverseFaceContainerSuccess(String data);

    void setFullFaceContainerfailure(String data);

    void setReverseContainerfailure(String data);

}
