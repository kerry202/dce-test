package cn.dagongniu.oax.main.presenter;

import android.app.Activity;
import android.content.pm.PackageManager;

import java.util.HashMap;

import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.base.OAXBasePresenter;
import cn.dagongniu.oax.base.OAXIViewBean;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.UrlParams;
import cn.dagongniu.oax.main.bean.VersionInfoBean;
import cn.dagongniu.oax.main.module.AppVersionModule;

public class MainPresenter extends OAXBasePresenter {
    private AppVersionModule mAppVersionModule;
    private Activity activity;
    OAXIViewBean view;
    RequestState mState;

    public MainPresenter(OAXIViewBean view) {
        super(view);
        activity = (Activity) view.getContext();
        this.view = view;
        mAppVersionModule = new AppVersionModule(activity);
    }

    public void checkAppUpdateInfo(RequestState state) {
        this.mState = state;
        String versionName = "";
        try {
            versionName = OAXApplication.getInstance().getPackageManager().getPackageInfo
                    (OAXApplication.getInstance().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put(UrlParams.type, Constant.ANDROID_TYPE);
        map.put(UrlParams.version, versionName);
        mAppVersionModule.requestServerData(new OnDataListener<VersionInfoBean>() {
            @Override
            public void onNewData(CommonJsonToBean<VersionInfoBean> data) {
                if (view!=null){
                    view.setData(data);
                }

            }

            @Override
            public void onError(String msg) {
                if (view!=null){
                    view.setXState(LoadingState.STATE_ERROR, msg);
                }
            }
        }, state, map);
    }

    public void onDestroy() {
        mAppVersionModule = null;
        activity = null;
        view = null;
    }
}
