package cn.dagongniu.oax.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gyf.barlibrary.ImmersionBar;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.language.MultiLanguageUtil;
import cn.dagongniu.oax.utils.AppManager;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.MyEvents;
import cn.dagongniu.oax.utils.um.UMManager;


/**
 * @describe activity的基类
 */

public abstract class BaseActivity extends AutoLayoutActivity implements IView {

    private static final String TAG = "BaseActivity";

    protected Context mContext;
    public EventBus eventBus = OAXApplication.getmEventBus();
    public static Activity baseActivity;
    private Unbinder unbinder;
    protected ImmersionBar mImmersionBar;
    public MyEvents myEvents = new MyEvents();

    @Override
    public void showToask(String str) {
        ToastUtil.ShowToast(str);
    }


    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void toOtherActivity(Class<?> cls) {
        openActivity_(cls);
    }

    @Override
    public void toFinishActivity() {
        finish();
    }


    @Override
    public Context getContext() {
        return this;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        eventBus.register(this);
        baseActivity = this;
        if (isScreenPortrait()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        unbinder = ButterKnife.bind(this);
        AppManager.activityCreated(this);
        //view与数据绑定
        initView();
        //初始化数据
        initData();
        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();

        //关闭友盟自动统计和确定场景类型
        UMManager.openActivityDurationTrack(this);
    }


    /**
     * 在BaseActivity里初始化
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(newBase));
    }

    protected abstract int getLayoutId();

    protected void initView() {
        mContext = this;
        hideSoftKeyboard();

    }


    protected void initData() {

    }

    @Subscribe
    public void onEvent(MyEvents event) {

    }


    @Override
    protected void onDestroy() {
        AppManager.activityDestroyed(this);
        super.onDestroy();
        unbinder.unbind();
        eventBus.unregister(this);
        ImmersionBar.with(this).destroy();
    }


    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    /**
     * activity 跳转
     *
     * @param cls
     */
    protected void openActivity_(Class<?> cls) {
        hideSoftKeyboard();
        Intent i = new Intent(mContext, cls);
        startActivity(i);
    }

    protected void openActivity(Class<?> cls, Bundle bundle) {
        Intent i = new Intent(mContext, cls);
        i.putExtras(bundle);
        startActivity(i);
    }

    protected void openActivityForResult(Class<?> cls, int requestCode) {
        Intent i = new Intent(mContext, cls);
        startActivityForResult(i, requestCode);
    }

    protected void openActivityForResult(Class<?> cls, int requestCode, Bundle bundle) {
        Intent i = new Intent(mContext, cls);
        i.putExtras(bundle);
        startActivityForResult(i, requestCode);
    }

    public void openActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 是否竖屏，可在子类中重写
     *
     * @return
     */
    public boolean isScreenPortrait() {
        return true;
    }

    @Override
    public void setData(Object obj) {

    }

    @Override
    public void setXState(LoadingState xState, String msg) {

    }

    @Override
    public void setRefresh(Object obj) {

    }

    @Override
    public void setLoadMore(Object obj) {

    }

}
