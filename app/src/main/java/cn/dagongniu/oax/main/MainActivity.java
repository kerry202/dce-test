package cn.dagongniu.oax.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.LoginActivity;
import cn.dagongniu.oax.account.fragment.MineFragment;
import cn.dagongniu.oax.assets.fragment.AssetsFragment;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.base.OAXBaseActivity;
import cn.dagongniu.oax.constant.UMConstant;
import cn.dagongniu.oax.customview.tabview.TabView;
import cn.dagongniu.oax.customview.tabview.TabViewChild;
import cn.dagongniu.oax.main.fragment.OaxHomeNFragment;
import cn.dagongniu.oax.trading.fragment.OaxTradingFragment;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.KLineBuyOrSellEvent;
import cn.dagongniu.oax.utils.events.SelectTradingCoinEvent;
import cn.dagongniu.oax.utils.um.UMManager;

/**
 * 我的
 */
public class MainActivity extends OAXBaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.tabView)
    TabView tabView;

    private List<TabViewChild> tabViewChildList;
    OaxTradingFragment oaxTradingFragment;   //交易
    OaxHomeNFragment oaxHomeNFragment;      //首页
    AssetsFragment assetsFragment;          //资产
    MineFragment mineFragment;              //我的
    TabViewChild tabViewChildOaxTrading;
    TabViewChild tabViewChildOaxNHome;
    TabViewChild tabViewChildAssets;
    private TabViewChild tabViewChildMine;
    public static Context mContext;

    Bundle bundle;
    int currentPage = 0;
    int homeViewPosition = 0;//默认

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        tabView.checkFragment(3);
    }

    @Override
    protected void initView() {
        super.initView();




        initTab(homeViewPosition);
        bundle = new Bundle();

        mContext = this;
        /**
         * 语言切换
         */
        if (getIntent().getBooleanExtra("Language", false)) {
            tabView.checkFragment(3);
        }

        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int position, ImageView imageView, TextView textView) {
                if (position == 2) {
                    boolean islogin = (boolean) SPUtils.getParam(MainActivity.this, SPConstant.LOGIN_STATE, false);
                    if (!islogin) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.putExtra(AppConstants.LOGIN_FAILURE, true);
                        intent.putExtra(AppConstants.LOGINTYPE, AppConstants.LOGINPHONE);
                        MainActivity.this.startActivity(intent);
                        tabView.checkFragment(3);
                        SPUtils.remove(MainActivity.this, SPConstant.LOGIN_STATE);
                        SPUtils.remove(MainActivity.this, SPConstant.USER_ID);
                        SPUtils.remove(MainActivity.this, SPConstant.USER_TOKEN);
                        SPUtils.remove(MainActivity.this, SPConstant.USER_ACCOUNT);
                        SPUtils.remove(MainActivity.this, SPConstant.USER_CHOOSECOUNTRIES);
                    } else {
                        currentPage = position;
                    }
                } else {
                    currentPage = position;
                }
                switch (position) {
                    case 0:
                        UMManager.onEvent(MainActivity.this, UMConstant.MainActivity, UMConstant.MainActivity_home);
                        break;
                    case 1:
                        UMManager.onEvent(MainActivity.this, UMConstant.MainActivity, UMConstant.MainActivity_transition);
                        break;
                    case 2:
                        UMManager.onEvent(MainActivity.this, UMConstant.MainActivity, UMConstant.MainActivity_funds);
                        break;
                    case 3:
                        UMManager.onEvent(MainActivity.this, UMConstant.MainActivity, UMConstant.MainActivity_mine);
                        break;
                }
            }
        });

    }

    //设置tab
    private void initTab(int homeViewPosition) {
        //初始化
        tabViewChildList = new ArrayList<>();
        oaxHomeNFragment = new OaxHomeNFragment();
        oaxTradingFragment = new OaxTradingFragment();
        assetsFragment = new AssetsFragment();
        mineFragment = new MineFragment();

        tabViewChildOaxNHome = new TabViewChild(R.mipmap.tab_home_yes, R.mipmap.tab_home_no,
                getResources().getText(R.string.home).toString(), oaxHomeNFragment);
        tabViewChildOaxTrading = new TabViewChild(R.mipmap.tab_trading_yes, R.mipmap.tab_trading_no,
                getResources().getText(R.string.trading).toString(), oaxTradingFragment);
        tabViewChildAssets = new TabViewChild(R.mipmap.assets_yes, R.mipmap.assets_no,
                getResources().getText(R.string.assets).toString(), assetsFragment);
        tabViewChildMine = new TabViewChild(R.drawable.mine_yes, R.drawable.mine_no,
                getResources().getString(R.string.mine), mineFragment);

        tabViewChildList.add(tabViewChildOaxNHome);
        tabViewChildList.add(tabViewChildOaxTrading);
        tabViewChildList.add(tabViewChildAssets);
        tabViewChildList.add(tabViewChildMine);

        //设置
        tabView.setTabViewDefaultPosition(homeViewPosition);
        tabView.setTabViewChild(tabViewChildList, getSupportFragmentManager());
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UMManager.onResume(this, UMConstant.MainActivity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMManager.onResume(this, UMConstant.MainActivity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void openActivity(Class<?> cls, Bundle bundle) {
        Intent i = new Intent(mContext, cls);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(KLineBuyOrSellEvent event) {
        tabView.checkFragment(1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SelectTradingCoinEvent event) {
        tabView.checkFragment(1);
    }

}
