package cn.dagongniu.oax.base;

import android.app.Activity;



import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;

/**
 * 模型 base
 */
public abstract class BaseModule<T, V> {
    public Activity activity;

    public BaseModule(Activity activity) {
        this.activity = activity;
    }

    public abstract void requestServerDataOne(OnBaseDataListener<V> onBaseDataListener, T... parm);

    public abstract void requestServerDataOne(OnBaseDataListener<V> onBaseDataListener, RequestState state, T... parm);

    public void requestServerDataTwo(OnBaseDataListener<V> onBaseDataListener, T... parm) {
    }


    public void requestServerDataThree(OnBaseDataListener<V> onBaseDataListener, V... parm) {

    }

    public void requestServerDataString(OnBaseDataListener<String> onBaseDataListener, String... parm) {

    }
}
