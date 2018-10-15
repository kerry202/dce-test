package cn.dagongniu.oax.main.view;

import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.bean.NoticeCenterMoreBean;
import cn.dagongniu.oax.main.bean.NoticeCenterReadDetailBean;

public interface INoticeCenterMorelView extends IView {

    String getNoticeCenterMoreType();//公告类型 4新币上线 5最新公告

    int getNoticeCenterPageIndex();//页码

    int getNoticeCenterPageSize();//	每页显示条数

    void setNoticeCenterMoreData(NoticeCenterMoreBean noticeCenterMoreData);

    void setRefreshNoticeCenterMoreData(NoticeCenterMoreBean noticeCenterMoreData);

    void setRefreshNoticeCenterMoreErrer(String noticeCenterMoreData);

    void setRefreshNoticeCenterLoadMoreData(NoticeCenterMoreBean noticeCenterMoreData);

    void setRefreshNoticeCenterLoadMoreErrer(String noticeCenterMoreData);

}
