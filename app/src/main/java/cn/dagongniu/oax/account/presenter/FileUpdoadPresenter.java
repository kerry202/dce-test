package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import cn.dagongniu.oax.account.bean.FileUpdoadBean;
import cn.dagongniu.oax.account.module.FileUpdoadModule;
import cn.dagongniu.oax.account.view.IFileUpdoadView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;

/**
 * 文件上传
 */
public class FileUpdoadPresenter extends BasePresenter {
    private FileUpdoadModule fileUpdoadModule;
    private IFileUpdoadView mView;
    private Activity activity;
    RequestState state;

    public FileUpdoadPresenter(IFileUpdoadView iView, RequestState state) {
        super(iView);
        activity = (Activity) iView.getContext();
        mView = iView;
        this.state = state;
        fileUpdoadModule = new FileUpdoadModule(activity);
    }


    /**
     * @param list
     * @param fileName
     * @param name
     * @param type     正反面 0=正面Full 1=反面Reverse
     */
    public void UploadImg(List<File> list, String fileName, String name, int type) {
        fileUpdoadModule.requestServerDataOne(new OnBaseDataListener<FileUpdoadBean>() {
            @Override
            public void onNewData(FileUpdoadBean data) {
                if (type == 0) {
                    if (data.isSuccess()) {
                        mView.setFullFaceContainerSuccess(data.getData().get(0));
                    } else {
                        mView.setFullFaceContainerfailure(data.getMsg());
                    }
                } else if (type == 1) {
                    if (data.isSuccess()) {
                        mView.setReverseFaceContainerSuccess(data.getData().get(0));
                    } else {
                        mView.setReverseContainerfailure(data.getMsg());
                    }
                }
            }

            @Override
            public void onError(String code) {
                mView.showToask(code);
                if (type == 0) {
                    mView.setFullFaceContainerfailure(code);
                } else if (type == 1) {
                    mView.setReverseContainerfailure(code);
                }
            }
        }, activity, list, state, fileName, name);


    }
}
