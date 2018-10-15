package cn.dagongniu.oax.account.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import cn.dagongniu.oax.account.bean.FileUpdoadBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 文件上传
 */
public class FileUpdoadModule extends BaseModule<String, FileUpdoadBean> {


    public FileUpdoadModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<FileUpdoadBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<FileUpdoadBean> onBaseDataListener, RequestState state, String... parm) {

    }

    public void requestServerDataOne(OnBaseDataListener<FileUpdoadBean> onBaseDataListener, Activity activity, List<File> list, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String fileName = parm[0];
        String name = parm[1];

        hashMap.put("fileName", fileName);
        hashMap.put("name", name);

        HttpUtils.getInstance().postFile(Http.FILEUPDATE_UPDATEPIC, hashMap, activity, state, list, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                try {
                    FileUpdoadBean httpBaseBean = new Gson().fromJson(data, FileUpdoadBean.class);
                    onBaseDataListener.onNewData(httpBaseBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String code) {
                onBaseDataListener.onError(code);
            }
        });

    }
}
