package cn.dagongniu.oax.base;

import android.content.Context;

import cn.dagongniu.oax.customview.LoadingState;

/**
 * IView 接口
 */
public interface IView {

    //Toask
    void showToask(String str);

    //弹框
    void showDialog(String msg);

    //跳转Activity
    void toOtherActivity(Class<?> cls);

    /**
     * 关闭Activity
     */
    void toFinishActivity();

    /**
     * 获取Context
     *
     * @return
     */
    Context getContext();

    /**
     * 请求返回实体
     */
    void setData(Object data);


    /**
     * 请求状态返回，全屏
     *
     * @param xState
     * @param msg
     */
    void setXState(LoadingState xState, String msg);

    /**
     * 刷新
     *
     * @param obj
     */
    void setRefresh(Object obj);

    /**
     * 上拉加载
     *
     * @param obj
     */
    void setLoadMore(Object obj);


}
