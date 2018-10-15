package cn.dagongniu.oax.assets.view;

import cn.dagongniu.oax.assets.bean.AssetsPropertyListBean;
import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.main.bean.NoticeCenterMoreBean;
import cn.dagongniu.oax.main.bean.NoticeCenterReadDetailBean;

public interface IAssetsPropertyListView extends IView {

    String getCoinName();//币种名称

    void setIAssetsPropertyData(AssetsPropertyListBean noticeCenterReadDetailBean);//回调数据

    void setIAssetsPropertyDataNull(AssetsPropertyListBean noticeCenterReadDetailBean);//回调数据

    void refreshErrer();

    void goLogin(String msg);

    int getType();//类型 1 显示所有币种资产 2显示有余额




}
