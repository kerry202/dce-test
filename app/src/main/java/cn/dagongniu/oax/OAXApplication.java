package cn.dagongniu.oax;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
//import com.taobao.sophix.PatchStatus;
//import com.taobao.sophix.SophixManager;
//import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.umeng.socialize.PlatformConfig;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import cn.dagongniu.oax.broadcast.ScreenStatusReceiver;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.language.MultiLanguageUtil;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.bean.OaxMarketBean;
import cn.dagongniu.oax.trading.bean.OrderSelectNoticeBean;
import cn.dagongniu.oax.utils.Logs;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.um.UMManager;
import cn.jpush.android.api.JPushInterface;

/**
 * 入口
 */
public class OAXApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "OAXApplication";

    private static OAXApplication instance;
    public static Context sContext;
    public static int defaultMarketId = -1;
    private ScreenStatusReceiver mScreenStatusReceiver;
    public static boolean isScreenOn = true;

    public static HashMap<Integer, IndexPageBean.DataBean.AllMaketListBean.MarketListBean> coinsInfoMap = new HashMap<>();//marketId对应的交易对
    public static HashMap<Integer, IndexPageBean.DataBean.UserMaketListBean> collectCoinsMap = new HashMap<>();//用户收藏的交易对

    public OrderSelectNoticeBean orderSelectNoticeBean;//订单筛选条件模型类
    public static boolean mIsBackground;

    public static OAXApplication getInstance() {
        return instance;
    }

    private static EventBus mEventBus;

    public static Context getContext() {
        return sContext;
    }

    public String MarketName = "";
    public String MarketId = "";
    public int type = -1;

    public String UserEmail;
    public String UserPhone;
    public String UserGoogleKe;
    public int UserEmailState;
    public int UserPhoneState;
    public int UserGoogleState;

    //缓存的搜索历史列表
    public static List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> coinsInfoHistory = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化内存泄漏检测
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);

        MultiLanguageUtil.init(this);
        sContext = getApplicationContext();
        initOkHttp();
        mEventBus = EventBus.getDefault();

        registSreenStatusReceiver();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static EventBus getmEventBus() {
        return mEventBus;
    }

    public static boolean getAppLog() {
        // TODO　是否开启log模式 (true开启  false关闭)
        return false;
    }



    /**
     * 获取版本号
     *
     * @return
     */
    public String getAppVersion() {
        String appVersion;
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion = "1.0.0";
        }
        return appVersion;
    }

    private void initOkHttp() {
        //必须调用初始化
        OkGo.init(this);
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
//            HttpHeaders customHeader = new HttpHeaders();
//            customHeader.put(HttpHeaders.HEAD_KEY_CONTENT_TYPE, "application/json;charset=utf-8");

            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("okGo---", Level.INFO, true)
                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(10000)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间
                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                    .setRetryCount(3)
                    //.addCommonHeaders(customHeader)
                    .setCookieStore(new PersistentCookieStore())
                    .setCertificates();                               //方法一：信任所有证书,不安全有风险
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存的搜索历史记录
     *
     * @return
     */
    public static List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> getCoinsInfoHistory() {
        String coinsInfoHistoryJsonStr = (String) SPUtils.getParam(getContext(), SPConstant.COINSINFO_HISTORY_LIST, "");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean>>() {
        }.getType();
        List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> list = gson.fromJson(coinsInfoHistoryJsonStr, listType);
        coinsInfoHistory.clear();
        if (list != null) {
            coinsInfoHistory.addAll(list);
        }
        return coinsInfoHistory;
    }

    /**
     * 设置缓存历史记录
     *
     * @param coinsInfoHistoryBean
     */
    public static void setCoinsInfoHistory(IndexPageBean.DataBean.AllMaketListBean.MarketListBean coinsInfoHistoryBean) {
        //缓存已存在则不存
        List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> coinsInfoHistory = getCoinsInfoHistory();
        for (IndexPageBean.DataBean.AllMaketListBean.MarketListBean bean : coinsInfoHistory) {
            if (bean.getMarketId() == coinsInfoHistoryBean.getMarketId()) {
                return;
            }
        }
        //只缓存六条数据
        if (OAXApplication.coinsInfoHistory.size() == 6) {
            OAXApplication.coinsInfoHistory.remove(0);
        }
        OAXApplication.coinsInfoHistory.add(coinsInfoHistoryBean);
        //转json存
        Gson gson = new Gson();
        String coinsInfoHistoryJson = gson.toJson(OAXApplication.coinsInfoHistory);

        SPUtils.setParam(getContext(), SPConstant.COINSINFO_HISTORY_LIST, coinsInfoHistoryJson);
    }

    /**
     * 所有交易对信息
     *
     * @param beans
     */
    public static void setCoinsInfo(List<IndexPageBean.DataBean.AllMaketListBean> beans) {
        if (beans != null) {
            for (int i = 0; i < beans.size(); i++) {
                IndexPageBean.DataBean.AllMaketListBean allMaketListBean = beans.get(i);
                List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> marketList = allMaketListBean.getMarketList();
                if (marketList != null) {
                    for (int j = 0; j < marketList.size(); j++) {
                        IndexPageBean.DataBean.AllMaketListBean.MarketListBean marketListBean = marketList.get(j);
                        int marketId = marketListBean.getMarketId();
                        if (!coinsInfoMap.containsKey(marketId)) {
                            coinsInfoMap.put(marketId, marketListBean);
                        }
                        if (i == 0 && j == 0) {
                            defaultMarketId = marketListBean.getMarketId();
                            KLog.d("defaultMarketId = " + defaultMarketId);
                        }
                    }
                }
            }
        }
        KLog.d("coinsInfoMap = " + new Gson().toJson(coinsInfoMap));
    }

    /**
     * 所有交易对信息
     *
     * @param beans
     */
    public static void setCoinsInfoFromRecommend(List<IndexPageBean.DataBean.RecommendMarketListBean> beans) {
        Gson gson = new Gson();
        if (beans != null) {
            for (int i = 0; i < beans.size(); i++) {
                IndexPageBean.DataBean.RecommendMarketListBean recommendMarketListBean = beans.get(i);
                IndexPageBean.DataBean.RecommendMarketListBean.MarketCoinBean marketCoin = recommendMarketListBean.getMarketCoin();
                if (marketCoin != null) {
                    IndexPageBean.DataBean.AllMaketListBean.MarketListBean marketListBean = gson.fromJson(gson.toJson(marketCoin), IndexPageBean.DataBean.AllMaketListBean.MarketListBean.class);
                    int marketId = marketListBean.getMarketId();
                    Logs.s("     marketListBean    "+marketListBean);

                    if (!coinsInfoMap.containsKey(marketId)) {
                        coinsInfoMap.put(marketId, marketListBean);
                    }
                }
            }
        }
        KLog.d("coinsInfoMap = " + new Gson().toJson(coinsInfoMap));
    }


    /**
     * APP 所有All市场
     *
     * @param beans
     */
    public static void updateCoinsInfo(List<OaxMarketBean> beans) {
        if (beans != null) {
            for (int i = 0; i < beans.size(); i++) {
                OaxMarketBean oaxMarketBean = beans.get(i);
                int marketId = oaxMarketBean.getMarketId();
                try {
                    IndexPageBean.DataBean.AllMaketListBean.MarketListBean bean = new Gson().fromJson(new Gson().toJson(oaxMarketBean), IndexPageBean.DataBean.AllMaketListBean.MarketListBean.class);
                    coinsInfoMap.put(marketId, bean);
                } catch (Exception e) {
                    KLog.d("updateCoinsInfo Exception == " + e.getMessage());
                }
            }
        }
        KLog.d("coinsInfoMap = " + new Gson().toJson(coinsInfoMap));
    }

    /**
     * 设置收藏交易对信息
     *
     * @param beans
     */
    public static void setCollectCoinsMap(List<IndexPageBean.DataBean.UserMaketListBean> beans) {
        collectCoinsMap.clear();
        if (beans != null) {
            for (int i = 0; i < beans.size(); i++) {
                IndexPageBean.DataBean.UserMaketListBean userMaketListBean = beans.get(i);
                if (userMaketListBean != null) {
                    int marketId = userMaketListBean.getMarketId();
                    if (!collectCoinsMap.containsKey(marketId)) {
                        collectCoinsMap.put(marketId, userMaketListBean);
                    }
                }
            }
        }
        KLog.d("collectCoinsMap setCollectCoinsMap = " + new Gson().toJson(collectCoinsMap));
    }


    /**
     * 添加收藏交易对信息
     *
     * @param marketId
     */
    public static void addCollectCoinsMap(int marketId) {
        if (coinsInfoMap.containsKey(marketId)) {
            IndexPageBean.DataBean.AllMaketListBean.MarketListBean bean = coinsInfoMap.get(marketId);
            if (bean != null) {
                Gson gson = new Gson();
                String s = gson.toJson(bean);
                try {
                    IndexPageBean.DataBean.UserMaketListBean userMaketListBean = gson.fromJson(s, IndexPageBean.DataBean.UserMaketListBean.class);
                    collectCoinsMap.put(marketId, userMaketListBean);
                } catch (Exception e) {
                    KLog.d("collectCoinsMap Exception = " + e.getMessage());
                }
            }
        }
        KLog.d("collectCoinsMap addCollectCoinsMap = " + new Gson().toJson(collectCoinsMap));
    }

    /**
     * Map 转 List  所有市场
     *
     * @return
     */
    public static List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> toMapCoinsInfoList() {
        List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> coinsInfoList = new ArrayList<>();

        Iterator iterKey = coinsInfoMap.entrySet().iterator();  //获得map的Iterator

        while (iterKey.hasNext()) {
            Entry entry = (Entry) iterKey.next();
            coinsInfoList.add((IndexPageBean.DataBean.AllMaketListBean.MarketListBean) entry.getValue());
        }
        return coinsInfoList;
    }

    public OrderSelectNoticeBean getOrderSelectNoticeBean() {
        return orderSelectNoticeBean;
    }

    public void setOrderSelectNoticeBean(OrderSelectNoticeBean orderSelectNoticeBean) {
        this.orderSelectNoticeBean = orderSelectNoticeBean;
    }

    private void registSreenStatusReceiver() {
        mScreenStatusReceiver = new ScreenStatusReceiver();
        IntentFilter screenStatusIF = new IntentFilter();
        screenStatusIF.addAction(Intent.ACTION_SCREEN_ON);
        screenStatusIF.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenStatusReceiver, screenStatusIF);
    }


    public void setUserEmailState(int userEmailState) {
        UserEmailState = userEmailState;
    }

    public void setUserPhoneState(int userPhoneState) {
        UserPhoneState = userPhoneState;
    }

    public void setUserGoogleState(int userGoogleState) {
        UserGoogleState = userGoogleState;
    }

    public int getUserEmailState() {
        return UserEmailState;
    }

    public int getUserPhoneState() {
        return UserPhoneState;
    }

    public int getUserGoogleState() {
        return UserGoogleState;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public String getUserGoogleKe() {
        return UserGoogleKe;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public void setUserGoogleKe(String userGoogleKe) {
        UserGoogleKe = userGoogleKe;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        mIsBackground = false;
        KLog.d("mIsBackground = " + mIsBackground);
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        mIsBackground = true;
        KLog.d("mIsBackground = " + mIsBackground);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
