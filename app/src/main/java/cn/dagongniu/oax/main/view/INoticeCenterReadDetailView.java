package cn.dagongniu.oax.main.view;

import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.main.bean.BannerBean;
import cn.dagongniu.oax.main.bean.NoticeCenterReadDetailBean;

public interface INoticeCenterReadDetailView extends IView {

    String getReadDetail();//公告ID

    void setNoticeCenterReadDetailData(NoticeCenterReadDetailBean noticeCenterReadDetailData);

}
