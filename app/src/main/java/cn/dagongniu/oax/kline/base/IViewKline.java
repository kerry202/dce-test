package cn.dagongniu.oax.kline.base;

import android.content.Context;

import cn.dagongniu.oax.https.HttpBaseBean;

public interface IViewKline {
    /**
     * 请求返回实体
     */
    void setLineData(Object obj);

    /**
     * 获取Context
     * @return
     */
    Context getContext();

}
