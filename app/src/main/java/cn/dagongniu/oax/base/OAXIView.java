package cn.dagongniu.oax.base;

import android.content.Context;

import cn.dagongniu.oax.customview.LoadingState;

public interface OAXIView {
    //Toast
    void showToast(String msg);

    /**
     * 获取Context
     *
     * @return
     */
    Context getContext();

    /**
     * 请求状态返回，全屏
     *
     * @param xState
     * @param msg
     */
    void setXState(LoadingState xState, String msg);
}
