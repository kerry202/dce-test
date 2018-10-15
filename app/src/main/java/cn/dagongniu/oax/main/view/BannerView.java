package cn.dagongniu.oax.main.view;

import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.main.bean.BannerBean;

public interface BannerView extends IView {

    void setBannerData(BannerBean bannerData);

}
