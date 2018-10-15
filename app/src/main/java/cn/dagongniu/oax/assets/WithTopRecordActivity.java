package cn.dagongniu.oax.assets;

import android.content.Context;
import android.graphics.Color;
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
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.fragment.TopUpFragment;
import cn.dagongniu.oax.assets.fragment.WithdrawalFragment;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.main.adapter.FragAdapter;

/**
 * 历史记录 充值提现
 */
public class WithTopRecordActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ent_ord_pager)
    ViewPager entOrdPager;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.rl_close)
    RelativeLayout rlClose;

    List<Fragment> fragmentsEntOrd = new ArrayList<>();

    private String[] CHANNELS = null;
    private List<String> mDataList = null;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_with_top_record;
    }

    @Override
    protected void initView() {
        super.initView();
        setRecordTitle();
        rlClose.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }


    /**
     * 主流市场
     */
    private void setRecordTitle() {
        CHANNELS = getResources().getStringArray(R.array.assets_record);
        mDataList = Arrays.asList(CHANNELS);

        fragmentsEntOrd.add(new TopUpFragment());
        fragmentsEntOrd.add(new WithdrawalFragment());
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_close:
                finish();
                break;
        }
    }
}
