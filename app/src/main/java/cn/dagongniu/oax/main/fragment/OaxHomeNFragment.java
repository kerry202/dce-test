package cn.dagongniu.oax.main.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.constant.UMConstant;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.customview.ScaleCircleNavigator;
import cn.dagongniu.oax.customview.textview.AdvertView;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.WebSocketsManager;
import cn.dagongniu.oax.language.LanguageUtils;
import cn.dagongniu.oax.main.BannerWebActivity;
import cn.dagongniu.oax.main.SearchCoinActivity;
import cn.dagongniu.oax.main.WebActivity;
import cn.dagongniu.oax.main.adapter.HomesViewPagerAdapter;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.bean.OaxMarketBean;
import cn.dagongniu.oax.main.presenter.IndexPagePresenter;
import cn.dagongniu.oax.main.view.IndexPageView;
import cn.dagongniu.oax.utils.AppManager;
import cn.dagongniu.oax.utils.DialogUtils;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.Logs;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.CollectMarketStateEvent;
import cn.dagongniu.oax.utils.events.MyEvents;
import cn.dagongniu.oax.utils.events.RefreshHomeFragmentEvent;
import cn.dagongniu.oax.utils.events.UpdateTradingInfoEvent;
import cn.dagongniu.oax.utils.um.UMManager;
import io.reactivex.disposables.Disposable;
import ua.naiksoftware.stomp.client.StompClient;

/**
 * 首页
 */
public class OaxHomeNFragment extends BaseFragment implements OnClickListener, IndexPageView,
        BGABanner.Adapter<ImageView, String>, BGABanner.Delegate<ImageView, String>,
        OnRefreshListener {

    private static final String TAG = "OaxHomeNFragment";

    private ImageView.ScaleType mScaleType = ImageView.ScaleType.CENTER_CROP;

    @BindView(R.id.banner_home_zoomCenter)
    BGABanner mContentBanner;
    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.activity_main_advertView)
    AdvertView advertView;
    @BindView(R.id.main_market_view_pager)
    ViewPager mainMarketViewPager;
    @BindView(R.id.main_market_indicator)
    MagicIndicator mainMarketIndicator;
    @BindView(R.id.other_market_indicator)
    MagicIndicator otherMarketIndicator;
    @BindView(R.id.other_market_view_pager)
    ViewPager otherMarketViewPager;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.tv_header_jg)
    TextView tvHeaderJg;
    @BindView(R.id.rl_notice)
    RelativeLayout rlNotice;

    IndexPageBean indexPageBean;
    ClassicsHeader mClassicsHeader;
    List<Fragment> fragments = new ArrayList<>();
    List<Fragment> fragmentsMarKet = new ArrayList<>();
    HomesViewPagerAdapter adapter;
    private List<String> recommendMarketListIndicators = new ArrayList<>();
    List<String> listTitle = new ArrayList<>();
    HomesViewPagerAdapter allMaketAdapter = null;
    List<AllMarketFragmnet> listAllMarketFragmnets = new ArrayList<>();
    ZXMarketFragmnet zxMarketFragmnet = new ZXMarketFragmnet();
    IndexPagePresenter indexPagePresenter;
    Bundle bundle = new Bundle();
    private StompClient mMarketCategoryClient;
    private boolean isOnErrorOrOnClosed = false;
    private WebSocketsManager.OnCreateStompClientListener mOnCreateStompClientListener;
    private Disposable mTopic;


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oax_home_n_frament;
    }

    @Override
    protected void initView() {
        super.initView();
        rlSearch.setOnClickListener(this);
        //尺寸拉伸
        mClassicsHeader = (ClassicsHeader) mRefreshLayout.getRefreshHeader();
        mClassicsHeader.setSpinnerStyle(SpinnerStyle.Scale);
        mRefreshLayout.setOnRefreshListener(this);
        LanguageUtils.setHeaderLanguage(mClassicsHeader, getActivity());
        initWebsocket();

    }

    /**
     * web socket初始化
     */
    private void initWebsocket() {

        if (isOnErrorOrOnClosed) {
            mMarketCategoryClient = null;
            mOnCreateStompClientListener = null;
        }
        if (mMarketCategoryClient == null) {
            mOnCreateStompClientListener = new WebSocketsManager.OnCreateStompClientListener() {
                @Override
                public void onOpened() {

                    isOnErrorOrOnClosed = false;
                    mTopic = WebSocketsManager.getInstance().topic(mMarketCategoryClient, Http.marketCategory_all, new WebSocketsManager.OnTopicListener() {
                        @Override
                        public void onNewData(String data) {
                            Logs.s("   WebSocketsManager 111  "+data);
                            List<OaxMarketBean> listOaxMarketBeanGson = getListOaxMarketBeanGson(data);

                            Logs.s("   WebSocketsManager 222  "+listOaxMarketBeanGson);
                            OAXApplication.updateCoinsInfo(listOaxMarketBeanGson);
                            EventBus.getDefault().post(new UpdateTradingInfoEvent());
                            myEvents.status = MyEvents.status_pass;
                            myEvents.status_type = MyEvents.Home_WebSocket_Market_List;
                            myEvents.errmsg = "首页交易对的websocket推送数据数据";
                            myEvents.listOaxMarketBeanGson = listOaxMarketBeanGson;
                            eventBus.post(myEvents);
                            Logger.e(TAG, "发送传递首页交易对的websocket推送数据数据通知!");
                            KLog.d(TAG, "发送传递首页交易对的websocket推送数据数据通知!");

                        }
                    });
                }

                @Override
                public void onError() {
                    isOnErrorOrOnClosed = true;
                }

                @Override
                public void onClosed() {
                    isOnErrorOrOnClosed = true;
                }
            };
            mMarketCategoryClient = WebSocketsManager.getInstance().createStompClient(mOnCreateStompClientListener);
        }

    }

    //通过Gson解析
    public List<OaxMarketBean> getListOaxMarketBeanGson(String jsonString) {
        List<OaxMarketBean> list = new ArrayList<OaxMarketBean>();
        Gson gson = new Gson();
        list = gson.fromJson(jsonString, new TypeToken<List<OaxMarketBean>>() {
        }.getType());
        return list;
    }

    @Override
    protected void initData() {
        super.initData();
        indexPagePresenter = new IndexPagePresenter(this, RequestState.STATE_REFRESH);
        mRefreshLayout.autoRefresh();//进入页面显示下拉刷新
    }

    /**
     * 推荐主流市场指标标示点
     */
    private void initRecommendMarketListIndicator() {

        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(getActivity());
        scaleCircleNavigator.setCircleCount(recommendMarketListIndicators.size());
        scaleCircleNavigator.setNormalCircleColor(getContext().getResources().getColor(R.color.df_D8D8D8));
        scaleCircleNavigator.setSelectedCircleColor(getContext().getResources().getColor(R.color.df_theme));
        scaleCircleNavigator.setMaxRadius(10);
        scaleCircleNavigator.setMaxRadius(10);
        scaleCircleNavigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                mainMarketViewPager.setCurrentItem(index);
            }
        });
        mainMarketIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(mainMarketIndicator, mainMarketViewPager);
    }

    @OnClick({R.id.rl_search, R.id.rl_notice})
    public void onClick(View v) {
        switch (v.getId()) {
            //搜索
            case R.id.rl_search:
                SkipActivityUtil.skipAnotherActivity(mContext, SearchCoinActivity.class);
                UMManager.onEvent(mContext, UMConstant.OaxHomeNFragment, UMConstant.search);
                break;
            case R.id.rl_notice:
                break;
        }
    }

    /**
     * banner 适配器
     *
     * @param banner
     * @param itemView
     * @param model
     * @param position
     */
    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
        Glide.with(itemView.getContext())
                .load(model)
                .apply(new RequestOptions().placeholder(R.drawable.banner_df).error(R.drawable.banner_df).dontAnimate().centerCrop())
                .into(itemView);
    }

    /**
     * banner 点击事件
     *
     * @param banner
     * @param itemView
     * @param model
     * @param position
     */
    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
        if (indexPageBean != null) {
            if (indexPageBean.getData() != null) {
                if (indexPageBean.getData().getBannerList() != null) {
                    bundle.putString("BannerUrl", indexPageBean.getData().getBannerList().get(position).getUrl());
                    openActivity(BannerWebActivity.class, bundle);
                }
            }
        }
    }

    /**
     * 设置所以市场的指标标识点
     */
    private void initAllMaketListIndicator(final List<String> mDataList) {

        //默认设置市场价格（ETH）
        String price = getContext().getResources().getString(R.string.home_price) + "(" + mDataList.get(0) + ")";
        tvHeaderJg.setText(price);

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(16);
                //simplePagerTitleView.setPadding(20,0,20,0);
                simplePagerTitleView.setNormalColor(Color.parseColor("#999999"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#333333"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        otherMarketViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                //indicator.setFillColor(Color.parseColor("#ebe4e3"));
                return indicator;
            }
        });

        otherMarketIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(otherMarketIndicator, otherMarketViewPager);
        otherMarketViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //设置市场价格（ETH）
                String price = getContext().getResources().getString(R.string.home_price) + "(" + mDataList.get(position) + ")";
                tvHeaderJg.setText(price);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        Log.e("okgo", "刷新");
        //刷新banner请求
        indexPagePresenter.getIndexPageRefreshModule();
        initWebsocket();
    }


    /**
     * 首页刷新的回调方法
     *
     * @param indexPageDataRefresh
     */
    @Override
    public void setRefreshIndexPageData(IndexPageBean indexPageDataRefresh) {
        this.indexPageBean = indexPageDataRefresh;
        Logger.e(TAG, "首页刷新回调");
        mRefreshLayout.finishRefresh();
        if (indexPageDataRefresh.isSuccess()) {
            //刷新banner
            setBannerData(indexPageDataRefresh);
            //刷新公告
            setArticleList(indexPageDataRefresh);
            //刷新推荐
            mainMarketViewPager.removeAllViews();
            mainMarketViewPager.removeAllViewsInLayout();
            setRecommendMarketList(indexPageDataRefresh);
            //刷新所有市场
            otherMarketViewPager.removeAllViews();
            otherMarketViewPager.removeAllViewsInLayout();
            setAllMaketList(indexPageDataRefresh);
        } else {
            ToastUtil.ShowToast(indexPageDataRefresh.getMsg());
        }
    }

    /**
     * 首页静默刷新的回调方法
     *
     * @param allMaketList
     */
    @Override
    public void setSilentRefreshIndexPageData(IndexPageBean allMaketList) {
        Logger.e(TAG, "首页静默刷新的回调");

        Logs.s("   正式setSilentRefreshIndexPageData :     "+allMaketList);
        mRefreshLayout.finishRefresh();
        if (allMaketList.isSuccess()) {

            //所有交易对信息
            OAXApplication.setCoinsInfo(allMaketList.getData().getAllMaketList());
            //用户收藏
            OAXApplication.setCollectCoinsMap(allMaketList.getData().getUserMaketList());

            //判断是否有值 有值在set过去
            zxMarketFragmnet.dataSilentChange();

            EventBus.getDefault().post(new UpdateTradingInfoEvent());
            myEvents.status = MyEvents.status_pass;
            myEvents.status_type = MyEvents.COLLECTION_SILENT;
            myEvents.errmsg = "收藏静默刷新";
            eventBus.post(myEvents);
            Logger.e(TAG, "发送收藏静默刷新数据通知!");

        } else {
            ToastUtil.ShowToast(allMaketList.getMsg());
        }
    }

    @Override
    public void setRefreshIndexPageDataErrer(String indexPageData) {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefresh();
        }
        Logger.e(TAG, "首页刷新回调 刷新错误");
        ToastUtil.ShowToast(indexPageData);
    }

    @Override
    public void refreshErrer() {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefresh();
        }
    }


    /**
     * 首页数据回调
     *
     * @param indexPageData
     */
    @Override
    public void setIndexPageData(final IndexPageBean indexPageData) {
        this.indexPageBean = indexPageData;
        mRefreshLayout.finishRefresh();
        Logs.s("   首页数据回调   setIndexPageData  "+indexPageData);
        setBannerData(indexPageData);
        setArticleList(indexPageData);
        setRecommendMarketList(indexPageData);
        setAllMaketList(indexPageData);
    }

    /**
     * 设置Banner
     */
    public void setBannerData(IndexPageBean bannerData) {

        List<String> listimgs = new ArrayList<>();
        List<String> liststrs = new ArrayList<>();

        if (bannerData.getData().getBannerList() != null && bannerData.getData().getBannerList().size() > 0) {
            for (IndexPageBean.DataBean.BannerListBean bean : bannerData.getData().getBannerList()) {
                listimgs.add(bean.getImage());
                liststrs.add(bean.getTitle());

                Logs.s(" getImage "+bean.getImage());
                Logs.s(" getTitle "+bean.getTitle());
            }


            //mContentBanner.setDelegate(this);
            mContentBanner.setData(listimgs, null);
            mContentBanner.setAdapter(this);
        }

    }

    /**
     * 设置公告
     */
    public void setArticleList(final IndexPageBean articleList) {
        advertView.setData(articleList.getData().getArticleList());
        advertView.setOnItemClickListener(new AdvertView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (articleList.getData().getArticleList().size() <= 0) {
                    return;
                }
                Logger.e(TAG, "点击公告：" + articleList.getData().getArticleList().get(position).getName());
                bundle.putBoolean("More", true);
                bundle.putString("ArticleID", articleList.getData().getArticleList().get(position).getId() + "");
                bundle.putString("ArticleType", articleList.getData().getArticleList().get(position).getType() + "");
                openActivity(WebActivity.class, bundle);
            }
        });
    }

    /**
     * 设置推荐交易对的k线图数据
     */
    public void setRecommendMarketList(IndexPageBean articleListMarketList) {
        fragments.clear();
        recommendMarketListIndicators.clear();
        initRecommendMarketListIndicator();
        if (articleListMarketList.getData().getRecommendMarketList().size() > 3) {
            //第一页数据
            MainMarketFirstFragment mainMarketFirstFragment1 = new MainMarketFirstFragment();
            List<IndexPageBean.DataBean.RecommendMarketListBean> list1 = new ArrayList<>();
            for (int subscript = 0; subscript < 3; subscript++) {
                list1.add(articleListMarketList.getData().getRecommendMarketList().get(subscript));
            }
            mainMarketFirstFragment1.setRecommendMarketList(list1);
            //第二页数据
            MainMarketFirstNextFragment mainMarketFirstNextFragment = new MainMarketFirstNextFragment();
            List<IndexPageBean.DataBean.RecommendMarketListBean> list2 = new ArrayList<>();
            for (int subscript = 3; subscript < articleListMarketList.getData().getRecommendMarketList().size(); subscript++) {
                list2.add(articleListMarketList.getData().getRecommendMarketList().get(subscript));
            }
            mainMarketFirstNextFragment.setRecommendMarketListNext(list2);

            recommendMarketListIndicators.add("");
            recommendMarketListIndicators.add("");
            fragments.add(mainMarketFirstFragment1);
            fragments.add(mainMarketFirstNextFragment);
        } else {
            MainMarketFirstFragment mainMarketFirstFragment1 = new MainMarketFirstFragment();
            mainMarketFirstFragment1.setRecommendMarketList(articleListMarketList.getData().getRecommendMarketList());
            fragments.add(mainMarketFirstFragment1);
        }

        /**
         * 初始化推荐主流市场页面
         */
        adapter = new HomesViewPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        //设定适配器
        mainMarketViewPager.setAdapter(adapter);
        initRecommendMarketListIndicator();

    }

    /**
     * 设置市场所有交易对数据
     *
     * @param allMaketList
     */
    public void setAllMaketList(IndexPageBean allMaketList) {
        KLog.d("setAllMaketList = " + new Gson().toJson(allMaketList));
        //所有交易对信息
        OAXApplication.setCoinsInfo(allMaketList.getData().getAllMaketList());
        OAXApplication.setCoinsInfoFromRecommend(allMaketList.getData().getRecommendMarketList());
        //用户收藏
        OAXApplication.setCollectCoinsMap(allMaketList.getData().getUserMaketList());

        listTitle.clear();
        fragmentsMarKet.clear();
        listAllMarketFragmnets.clear();

        String userid = (String) SPUtils.getParamString(getActivity(), SPConstant.USER_ID, null);
        //更具id判断自选是否出现
        if (userid != null) {
            fragmentsMarKet.add(zxMarketFragmnet);
            //判断是否有值 有值在set过去
            if (allMaketList.getData().getUserMaketList().size() > 0) {
                zxMarketFragmnet.dataChange();
            }
            listTitle.add(getContext().getResources().getString(R.string.home_optional));
        }

        //遍历各个市场
        for (int subscript = 0; subscript < allMaketList.getData().getAllMaketList().size(); subscript++) {
            //新增一个title
            listTitle.add(allMaketList.getData().getAllMaketList().get(subscript).getCategoryName());
            AllMarketFragmnet allMarketFragmnet = new AllMarketFragmnet();
            allMarketFragmnet.setAllMaketListList(allMaketList.getData().getAllMaketList().get(subscript).getMarketList());
            listAllMarketFragmnets.add(allMarketFragmnet);
            fragmentsMarKet.add(allMarketFragmnet);
        }

        //市场页面适配器
        allMaketAdapter = new HomesViewPagerAdapter(getActivity().getSupportFragmentManager(), fragmentsMarKet);
        otherMarketViewPager.setAdapter(allMaketAdapter);
        otherMarketViewPager.setOffscreenPageLimit(listTitle.size());

        initAllMaketListIndicator(listTitle);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CollectMarketStateEvent event) {
        indexPagePresenter.getIndexPageSilentRefreshModule();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshHomeFragmentEvent event) {
        KLog.d("RefreshHomeFragmentEvent");
        if (mMarketCategoryClient == null) {
            initWebsocket();
        }
        mRefreshLayout.autoRefresh();
    }

    @Subscribe
    public void onEvent(MyEvents event) {
        switch (event.status_type) {
            case MyEvents.LoginEsc://退出登录
                indexPagePresenter.getIndexPageRefreshModule();
                break;
            case MyEvents.LoginSuccess://登陆成功通知
                mRefreshLayout.autoRefresh();
                break;
            /**
             *  修改密码 重新登录
             */
            case MyEvents.User_Update_Passwrod_Success:
                indexPagePresenter.getIndexPageRefreshModule();
                break;
            /**
             * token失效 刷新
             */
            case MyEvents.Token_failure:
                indexPagePresenter.getIndexPageRefreshModule();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UMManager.onResume(mContext, UMConstant.OaxHomeNFragment);
        if (!AppManager.isAppIsInBackground(mContext) && OAXApplication.isScreenOn) {
            initWebsocket();
            KLog.d("WebSocketsManager initWebsocket");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (OAXApplication.mIsBackground || !OAXApplication.isScreenOn) {
            KLog.d("WebSocketsManager disconnect");
//            WebSocketsManager.getInstance().disconnect(mMarketCategoryClient);
            if (mTopic != null && !mTopic.isDisposed()) {
                mTopic.dispose();
                mTopic = null;
            }
            if (mMarketCategoryClient != null) {
                mMarketCategoryClient.disconnect();
            }
            mMarketCategoryClient = null;
            mOnCreateStompClientListener = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        UMManager.onPause(mContext, UMConstant.OaxHomeNFragment);
//        if (OAXApplication.mIsBackground || !OAXApplication.isScreenOn) {
//            KLog.d("WebSocketsManager disconnect");
////            WebSocketsManager.getInstance().disconnect(mMarketCategoryClient);
//            if (mTopic != null && !mTopic.isDisposed()) {
//                mTopic.dispose();
//                mTopic = null;
//            }
//            if (mMarketCategoryClient != null) {
//                mMarketCategoryClient.disconnect();
//            }
//            mMarketCategoryClient = null;
//            mOnCreateStompClientListener = null;
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMarketCategoryClient != null) {
//            WebSocketsManager.getInstance().disconnect(mMarketCategoryClient);
            if (mTopic != null && !mTopic.isDisposed()) {
                mTopic.dispose();
                mTopic = null;
            }
            mMarketCategoryClient.disconnect();
            mMarketCategoryClient = null;
            mOnCreateStompClientListener = null;
        }
    }


}
