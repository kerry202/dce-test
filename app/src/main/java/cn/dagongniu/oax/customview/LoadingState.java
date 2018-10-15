package cn.dagongniu.oax.customview;

/**
 * Created by 加载的状态
 */

public enum LoadingState {
    //状态分别为：关闭，数据为空，无网络，错误
    STATE_LOADING,//关闭
    STATE_EMPTY, //数据为空
    STATE_NO_NET, //无网络
    STATE_ERROR,//错误
    STATE_CUSTOM; //自定义
}
