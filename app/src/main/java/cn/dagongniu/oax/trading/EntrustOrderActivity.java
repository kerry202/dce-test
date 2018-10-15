package cn.dagongniu.oax.trading;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.base.OAXBaseActivity;
import cn.dagongniu.oax.main.adapter.FragAdapter;
import cn.dagongniu.oax.trading.fragment.EntrustFragment;
import cn.dagongniu.oax.trading.fragment.OrderFragment;
import cn.dagongniu.oax.trading.fragment.RightEntOrdFragment;
import cn.dagongniu.oax.trading.view.SelectCoinPopWindow;
import cn.dagongniu.oax.utils.events.KLineCurrentItemInfoEvent;

/**
 * 委托/订单
 */
public class EntrustOrderActivity extends OAXBaseActivity implements View.OnClickListener {

    @BindView(R.id.ent_ord_pager)
    ViewPager entOrdPager;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.rl_close)
    RelativeLayout rlClose;
    @BindView(R.id.rl_screening)
    RelativeLayout rlScreening;
    @BindView(R.id.im_right)
    ImageView im_right;

    List<Fragment> fragmentsEntOrd = new ArrayList<>();

    private String[] CHANNELS = null;
    private List<String> mDataList = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_entrust_order;
    }

    @Override
    protected void initView() {
        super.initView();
        setEntOrdTitle();
        rlClose.setOnClickListener(this);
        rlScreening.setOnClickListener(this);
        //默认隐藏
        rlScreening.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void initData() {
        super.initData();
    }

    /**
     * 主流市场
     */
    private void setEntOrdTitle() {
        CHANNELS = getResources().getStringArray(R.array.entrustnadorder);
        mDataList = Arrays.asList(CHANNELS);

        fragmentsEntOrd.add(new EntrustFragment());
        fragmentsEntOrd.add(new OrderFragment());
        FragAdapter adapter = new FragAdapter(this.getSupportFragmentManager(), fragmentsEntOrd);
        //设定适配器
        entOrdPager.setAdapter(adapter);

        initMagicIndicator();

    }

    private void initMagicIndicator() {
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Color.parseColor("#999999"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#333333"));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(getResources().getDimension(R.dimen.d18));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        entOrdPager.setCurrentItem(index);
                        if (index == 0)
                            isRlScreeningVi(true);
                        else
                            isRlScreeningVi(false);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(Color.parseColor("#F8BE13"));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, entOrdPager);

        entOrdPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0)
                    isRlScreeningVi(true);
                else
                    isRlScreeningVi(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    /**
     * 是否隐藏   ture=隐藏  iv_false=显示
     */
    public void isRlScreeningVi(boolean is) {
        if (is) {
            rlScreening.setVisibility(View.INVISIBLE);
            //im_right.setVisibility(View.INVISIBLE);
        } else {
            rlScreening.setVisibility(View.VISIBLE);
            //im_right.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_close:
                finish();
                break;
            case R.id.rl_screening://排序
                //如果弹出的dialog里有输入框并且activity里设置了keyboardEnable为true的话，
                //当弹出Dialog的时候，要把activity的keyboardEnable方法设置为false，
                //当dialog关闭时，要把keyboardEnable设置为打开之前的状态
                RightEntOrdFragment rightEntOrdFragment = new RightEntOrdFragment();
                rightEntOrdFragment.setOrderSelectNoticeBean(OAXApplication.getInstance().getOrderSelectNoticeBean());
                rightEntOrdFragment.show(getSupportFragmentManager(), "RightEntOrdFragment");
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OAXApplication.getInstance().setOrderSelectNoticeBean(null);
    }
}
