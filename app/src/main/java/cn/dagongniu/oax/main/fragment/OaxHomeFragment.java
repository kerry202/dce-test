package cn.dagongniu.oax.main.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.customview.CustomViewPager;
import cn.dagongniu.oax.customview.MyScrollView;
import cn.dagongniu.oax.customview.ScaleCircleNavigator;
import cn.dagongniu.oax.customview.textview.AdvertView;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;
import cn.dagongniu.oax.language.LanguageUtils;
import cn.dagongniu.oax.main.adapter.FragAdapter;
import cn.dagongniu.oax.main.bean.BannerBean;
import cn.dagongniu.oax.main.presenter.BannerPresenter;
import cn.dagongniu.oax.main.view.BannerView;

/**
 * 首页
 */
public class OaxHomeFragment extends BaseFragment implements BannerView {

    private ImageView.ScaleType mScaleType = ImageView.ScaleType.CENTER_CROP;

    @BindView(R.id.banner_home_zoomCenter)
    BGABanner mContentBanner;
    @BindView(R.id.scroll_view)
    MyScrollView scrollView;
    @BindView(R.id.activity_main_advertView)
    AdvertView advertView;
    @BindView(R.id.main_market_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.main_market_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.other_market_indicator)
    MagicIndicator magicIndicatorMarket;
    @BindView(R.id.view_pager_market)
    CustomViewPager mViewPagerMarket;
    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    ClassicsHeader mClassicsHeader;
    List<Fragment> fragments = new ArrayList<>();
    List<Fragment> fragmentsMarKet = new ArrayList<>();
    private static final String[] CHANNELS = new String[]{"自选", "BTC市场", "ETH市场"};
    private List<String> mDataList = Arrays.asList(CHANNELS);

    BannerPresenter bannerPresenter;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar
                .statusBarDarkFont(true)
                .init();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oax_home_frament;
    }

    @Override
    protected void initView() {
        super.initView();
        //尺寸拉伸
        mClassicsHeader = (ClassicsHeader) mRefreshLayout.getRefreshHeader();
        mClassicsHeader.setSpinnerStyle(SpinnerStyle.Scale);
        LanguageUtils.setHeaderLanguage(mClassicsHeader, getActivity());
        setBanner();
        setTextNotice();
        setZB();
        setMarKet();
    }

    @Override
    protected void initData() {
        super.initData();

    }

    /**
     * 指标
     */
    private void setZB() {
        fragments.add(new MainMarketFirstFragment());
        FragAdapter adapter = new FragAdapter(getActivity().getSupportFragmentManager(), fragments);
        //设定适配器
        mViewPager.setAdapter(adapter);
        initMagicIndicator3();
    }

    /**
     * 市场情况
     */
    private void setMarKet() {
        fragmentsMarKet.add(new BTCMarketFragmnet());
        fragmentsMarKet.add(new ETHMarketFragmnet());
        FragAdapter adapter = new FragAdapter(getActivity().getSupportFragmentManager(), fragmentsMarKet);
        mViewPagerMarket.setAdapter(adapter);

        initMagicIndicator9();
    }

    /**
     * 指标标示点
     */
    private void initMagicIndicator3() {
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(getActivity());
        scaleCircleNavigator.setCircleCount(fragments.size());
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY);
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY);
        scaleCircleNavigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    /**
     * text 通告
     */
    private void setTextNotice() {
        final List<String> strList = new ArrayList<>();
        strList.add(getResources().getString(R.string.notice_1));
        strList.add(getResources().getString(R.string.notice_2));
        strList.add(getResources().getString(R.string.notice_3));
        //advertView.setData(strList);
        advertView.setOnItemClickListener(new AdvertView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), strList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 广告
     */
    private void setBanner() {
        List<View> views = new ArrayList<>();
        views.add(BGABannerUtil.getItemImageView(getActivity(), R.drawable.banner_df,
                new BGALocalImageSize(720, 360, 640, 320), mScaleType));
        views.add(BGABannerUtil.getItemImageView(getActivity(), R.drawable.banner_df,
                new BGALocalImageSize(720, 360, 640, 320), mScaleType));
        views.add(BGABannerUtil.getItemImageView(getActivity(), R.drawable.banner_df,
                new BGALocalImageSize(720, 360, 640, 320), mScaleType));
        mContentBanner.setData(views);
        //bannerHomeZoomCenter.setData(Arrays.asList(R.mipmap.holder, R.mipmap.holder, R.mipmap.holder), Arrays.asList("提示文字1", "提示文字2", "提示文字3"));
    }


    private void initMagicIndicator9() {
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
                        mViewPagerMarket.setCurrentItem(index);
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
        magicIndicatorMarket.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicatorMarket, mViewPagerMarket);
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 10));
                linePagerIndicator.setColors(Color.WHITE);
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(getActivity(), 15);
            }
        });

        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);
            }
        });
    }

    /**
     * 首页banner加载
     *
     */
    @Override
    public void setBannerData(BannerBean bannerData) {

    }
}
