package cn.dagongniu.oax.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gyf.barlibrary.ImmersionBar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.MyEvents;

/**
 * Fragment的基类
 */

public abstract class BaseFragment extends Fragment implements IView {
    protected Context mContext;
    protected Unbinder unbinder;
    protected EventBus eventBus = EventBus.getDefault();
    protected ImmersionBar mImmersionBar;
    public MyEvents myEvents = new MyEvents();

    protected boolean isFirstLoad = true;
    /**
     * Fragment当前状态是否可见 只在ViewPager+Fragment有效
     */
    protected boolean isVisible;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(mContext).inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        eventBus.register(this);
        if (isImmersionBarEnabled())
            initImmersionBar();
        initView();
        initData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 延迟加载
     */
    protected void lazyLoad() {
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    protected void initView() {
    }

    protected void initData() {
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
        /**
         * 状态栏字体颜色
         */
        if (ImmersionBar.isSupportStatusBarDarkFont())
            mImmersionBar.statusBarDarkFont(true).init();
        else
            ToastUtil.ShowToast("当前设备不支持状态栏字体变色");
    }

    public BaseFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    protected abstract int getLayoutId();


    @Subscribe
    public void onEvent(MyEvents event) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

        if(eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
    }

    protected void openActivity(Class<?> cls) {
        if (isAdded()) {
            Intent i = new Intent(mContext, cls);
            startActivity(i);
        }
    }

    protected void openActivity(Class<?> cls, Bundle bundle) {
        if (bundle == null) {
            openActivity(cls);
            return;
        }
        Intent i = new Intent(mContext, cls);
        i.putExtras(bundle);
        startActivity(i);
        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
    }

    protected void openActivityForResult(Class<?> cls, int requestCode) {
        Intent i = new Intent(mContext, cls);
        startActivityForResult(i, requestCode);
        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
    }

    protected void openActivityForResult(Class<?> cls, int requestCode, Bundle bundle) {
        Intent i = new Intent(mContext, cls);
        i.putExtras(bundle);
        startActivityForResult(i, requestCode);
        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
    }

    @Override
    public void showToask(String str) {
        ToastUtil.ShowToast(str);
    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void toOtherActivity(Class<?> cls) {
        Intent i = new Intent(mContext, cls);
        startActivity(i);
    }

    @Override
    public void toFinishActivity() {

    }

    @Override
    public Context getContext() {
        return this.getActivity();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
