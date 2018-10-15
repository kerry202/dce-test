package cn.dagongniu.oax.base;

import android.content.Context;

import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.https.CommonJsonToBean;

/**
 * IView 接口
 */
public interface OAXIViewBean<T> extends OAXIView {

    /**
     * 请求返回实体
     */
    void setData(CommonJsonToBean<T> data);


    /**
     * 刷新
     *
     * @param data
     */
    void setRefresh(CommonJsonToBean<T> data);

    /**
     * 上拉加载
     *
     * @param data
     */
    void setLoadMore(CommonJsonToBean<T> data);


}
