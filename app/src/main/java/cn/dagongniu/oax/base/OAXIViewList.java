package cn.dagongniu.oax.base;

import android.content.Context;

import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.CommonJsonToList;

/**
 * IView 接口
 */
public interface OAXIViewList<T> extends OAXIView {

    /**
     * 请求返回实体
     */
    void setData(CommonJsonToList<T> data);


    /**
     * 刷新
     *
     * @param data
     */
    void setRefresh(CommonJsonToList<T> data);

    /**
     * 上拉加载
     *
     * @param data
     */
    void setLoadMore(CommonJsonToList<T> data);


}
