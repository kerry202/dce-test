package cn.dagongniu.oax.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.socks.library.KLog;
import com.umeng.analytics.MobclickAgent;

import java.util.Locale;

import cn.dagongniu.oax.BuildConfig;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.OAXIViewBean;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.constant.UMConstant;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.language.LanguageType;
import cn.dagongniu.oax.language.MultiLanguageUtil;
import cn.dagongniu.oax.main.bean.VersionInfoBean;
import cn.dagongniu.oax.main.presenter.MainPresenter;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.um.UMManager;
import customview.ConfirmDialog;
import feature.Callback;
import util.UpdateAppUtils;

public class SplashActivity extends AppCompatActivity implements OAXIViewBean<VersionInfoBean> {

    Bundle bundle;
    private MainPresenter mMainPresenter;
    String apkPath;
    private String mVersionName;
    private boolean mISLoop = false;
    private boolean mISUpdate = true;
    private boolean isGoTo = false;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mISLoop = true;
            if (!mISUpdate) {
                KLog.d("handleMessage goTo");
                goTo();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        //设置默认语言
        setDefaultLanguage();
        bundle = new Bundle();
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .transparentBar()
                .init();
        mHandler.sendEmptyMessageDelayed(1, 500);
        //关闭友盟自动统计和确定场景类型
        UMManager.openActivityDurationTrack(this);
        mMainPresenter = new MainPresenter(this);
        mMainPresenter.checkAppUpdateInfo(null);
    }

    private void goTo() {
        isGoTo = true;
        boolean isFirstLaunched = (boolean) SPUtils.getParam(this, SPConstant.IS_FIRST_LAUNCHED, true);
        if (isFirstLaunched) {
            SkipActivityUtil.skipAnotherActivity(this, MainActivity.class, true);
        } else {
            SkipActivityUtil.skipAnotherActivity(this, MainActivity.class, true);
        }
    }

    private void setDefaultLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = getResources().getConfiguration().locale;
        }
        //或者仅仅使用 locale = Locale.getDefault(); 不需要考虑接口 deprecated(弃用)问题
        String lang = locale.getLanguage();
        KLog.d("lang = " + lang);
        String LANGUAGE = (String) SPUtils.getParam(this, SPConstant.LANGUAGE, "");
        if (!TextUtils.isEmpty(LANGUAGE)) {
            if ("CN".equals(LANGUAGE)) {
                MultiLanguageUtil.getInstance().updateLanguage(LanguageType.LANGUAGE_CHINESE_SIMPLIFIED);
            } else {
                MultiLanguageUtil.getInstance().updateLanguage(LanguageType.LANGUAGE_EN);
            }
        } else {
            if ("zh".equals(lang)) {
                MultiLanguageUtil.getInstance().updateLanguage(LanguageType.LANGUAGE_CHINESE_SIMPLIFIED);
            } else {
                MultiLanguageUtil.getInstance().updateLanguage(LanguageType.LANGUAGE_EN);
            }
        }
    }

    private void checkAndUpdate() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            updateByVersionName();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                updateByVersionName();
            } else {//申请权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }


    //根据versionName判断跟新
    private void updateByVersionName() {
        UpdateAppUtils.from(this)
                .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME)
                .serverVersionName(mVersionName)
                .apkPath(apkPath)
                .downloadBy(UpdateAppUtils.DOWNLOAD_BY_APP)
                .isForce(true)
                .update();
    }

    //权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateByVersionName();
                } else {
                    new ConfirmDialog(this, new Callback() {
                        @Override
                        public void callback(int position) {
                            if (position == 1) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                                startActivity(intent);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.exit(0);
                            } else {
                                System.exit(0);
                            }
                        }
                    }).setContent(getResources().getString(R.string.PERMISSION_NO_SDK)).show();
                }
                break;
        }
    }

    @Override
    public void setData(CommonJsonToBean<VersionInfoBean> data) {
        if (data != null) {
            KLog.d("VersionInfoBean = " + data.toJson(VersionInfoBean.class));
            if (data.getSuccess()) {
                mISUpdate = true;
                apkPath = data.getData().getDownloadUrl();
                mVersionName = data.getData().getVersion();
                //检查更新
                checkAndUpdate();

            } else {
                mISUpdate = false;
                if (mISLoop && !isGoTo) {
                    KLog.d("setData goTo");
                    goTo();
                }
            }
        }
    }

    @Override
    public void setRefresh(CommonJsonToBean<VersionInfoBean> data) {

    }

    @Override
    public void setLoadMore(CommonJsonToBean<VersionInfoBean> data) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setXState(LoadingState xState, String msg) {
        ToastUtil.ShowToast(getResources().getString(R.string.connect_server_fail), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UMManager.onResume(this, UMConstant.SplashActivity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMManager.onPause(this, UMConstant.SplashActivity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        if (mHandler != null) {
            mHandler.removeMessages(1);
            mHandler = null;
        }
        if (mMainPresenter != null) {
            mMainPresenter.onDestroy();
            mMainPresenter = null;
        }
    }
}
