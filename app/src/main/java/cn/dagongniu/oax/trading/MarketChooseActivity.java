package cn.dagongniu.oax.trading;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.main.adapter.HomesViewPagerAdapter;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.presenter.IndexPagePresenter;
import cn.dagongniu.oax.main.view.IndexPageView;
import cn.dagongniu.oax.trading.fragment.TradingAllMarketFragmnet;
import cn.dagongniu.oax.trading.fragment.TradingZXMarketFragmnet;
import cn.dagongniu.oax.utils.Logs;
import cn.dagongniu.oax.utils.SPUtils;

/**
 * 所有市场
 */
public class MarketChooseActivity extends BaseActivity implements View.OnClickListener, IndexPageView {

    @BindView(R.id.magic_indicator_market)
    MagicIndicator magicIndicatorMarket;
    @BindView(R.id.view_pager_market)
    ViewPager otherMarketViewPager;
    @BindView(R.id.rl_close)
    RelativeLayout rl_close;

    TradingZXMarketFragmnet zxMarketFragmnet = new TradingZXMarketFragmnet();
    HomesViewPagerAdapter allMaketAdapter = null;
    IndexPagePresenter indexPagePresenter;
    List<Fragment> fragmentsMarKet = new ArrayList<>();
    List<String> listTitle = new ArrayList<>();
    List<TradingAllMarketFragmnet> listAllMarketFragmnets = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_market_choose;
    }

    @Override
    protected void initView() {
        super.initView();
        setMarKet();
        Logs.s("    市场接口   initView  ");
    }

    @Override
    protected void initData() {
        super.initData();
        Logs.s("    市场接口   initData  ");
        indexPagePresenter = new IndexPagePresenter(this, RequestState.STATE_DIALOG);
        indexPagePresenter.getIndexPageModule();
    }

    /**
     * 市场情况
     */
    private void setMarKet() {
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void setIndexPageData(IndexPageBean allMaketList) {
        listTitle.clear();
        fragmentsMarKet.clear();
        listAllMarketFragmnets.clear();

        String userid = SPUtils.getParamString(this, SPConstant.USER_ID, null);
        //判断自选
        //更具id判断自选是否出现
        if (userid != null) {
            fragmentsMarKet.add(zxMarketFragmnet);
            //判断是否有值 有值在set过去
            if (allMaketList.getData().getUserMaketList().size() > 0) {
                zxMarketFragmnet.setUserMaketList(allMaketList.getData().getUserMaketList());
            }
            listTitle.add(getContext().getResources().getString(R.string.home_optional));
        }


        //遍历各个市场
        for (int subscript = 0; subscript < allMaketList.getData().getAllMaketList().size(); subscript++) {
            //新增一个title
            listTitle.add(allMaketList.getData().getAllMaketList().get(subscript).getCategoryName());
            TradingAllMarketFragmnet allMarketFragmnet = new TradingAllMarketFragmnet();
            allMarketFragmnet.setAllMaketListList(allMaketList.getData().getAllMaketList().get(subscript).getMarketList());
            listAllMarketFragmnets.add(allMarketFragmnet);
            fragmentsMarKet.add(allMarketFragmnet);
        }
        //市场页面适配器
        allMaketAdapter = new HomesViewPagerAdapter(this.getSupportFragmentManager(), fragmentsMarKet);
        otherMarketViewPager.setAdapter(allMaketAdapter);
        otherMarketViewPager.setOffscreenPageLimit(listTitle.size());
        initAllMaketListIndicator(listTitle);
    }

    /**
     * 设置所以市场的指标标识点
     */
    private void initAllMaketListIndicator(final List<String> mDataList) {

        CommonNavigator commonNavigator = new CommonNavigator(this);
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
        magicIndicatorMarket.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicatorMarket, otherMarketViewPager);
        otherMarketViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    /**
     * -------------------
     *
     * @param indexPageData
     */
    @Override
    public void setRefreshIndexPageData(IndexPageBean indexPageData) {

    }

    @Override
    public void setSilentRefreshIndexPageData(IndexPageBean indexPageData) {

    }

    @Override
    public void setRefreshIndexPageDataErrer(String indexPageData) {

    }

    @Override
    public void refreshErrer() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.rl_close)
    public void onClick() {
        finish();
    }
}
