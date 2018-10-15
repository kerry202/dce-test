package cn.dagongniu.oax.https;

/**
 * 回调信息接口
 */
public interface OnDataListener<T> {
    public void onNewData(CommonJsonToBean<T> data);

    public void onError(String msg);
}
