package cn.dagongniu.oax.account;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.fragment.HelpFragment;
import cn.dagongniu.oax.base.OAXBaseActivity;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.customview.CommonToolbar;
import cn.dagongniu.oax.main.adapter.FragAdapter;

/**
 * 帮助中心
 */
public class HelpActivity extends OAXBaseActivity {
    @BindView(R.id.commontoolbar)
    CommonToolbar commontoolbar;
    @BindView(R.id.magicindicator)
    MagicIndicator magicindicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();
        initFragment();
        initAdapter();
        initIndicator();
    }

    private void initIndicator() {
        titles.add(getResources().getString(R.string.FAQ));
        titles.add(getResources().getString(R.string.information_announcement));
        titles.add(getResources().getString(R.string.articles));
        titles.add(getResources().getString(R.string.about_us));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles == null ? 0 : titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.df_999999));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.df_font));
                simplePagerTitleView.setText(titles.get(index));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewpager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(getResources().getColor(R.color.df_theme));
                return linePagerIndicator;
            }
        });
        magicindicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicindicator, viewpager);
    }

    private void initAdapter() {
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);
        //设定适配器
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(3);
    }

    private void initFragment() {
        fragments.add(HelpFragment.instance(Constant.FAQ));
        fragments.add(HelpFragment.instance(Constant.INFORMATION));
        fragments.add(HelpFragment.instance(Constant.ARTICLES));
        fragments.add(HelpFragment.instance(Constant.ABOUT_US));
    }

    private void initToolbar() {
        commontoolbar.setTitleText(getResources().getString(R.string.help_center));
        commontoolbar.setOnCommonToolbarLeftClickListener(new CommonToolbar.CommonToolbarLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }
}
