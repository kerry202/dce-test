package cn.dagongniu.oax.main.view;

import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.main.bean.BannerBean;
import cn.dagongniu.oax.main.bean.IndexPageBean;

public interface IndexPageView extends IView {

    void setIndexPageData(IndexPageBean indexPageData);

    void setRefreshIndexPageData(IndexPageBean indexPageData);

    void setSilentRefreshIndexPageData(IndexPageBean indexPageData);

    void setRefreshIndexPageDataErrer(String indexPageData);

    void refreshErrer();

}
