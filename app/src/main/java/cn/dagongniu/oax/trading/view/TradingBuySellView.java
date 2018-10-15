package cn.dagongniu.oax.trading.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.PopupWindowCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.utils.DensityUtils;
import cn.dagongniu.oax.utils.ToastUtil;

/**
 * 交易买卖
 */
public class TradingBuySellView extends FrameLayout implements TabLayout.OnTabSelectedListener{

    private static final String TAG = "TradingBuySellView";
    
    Context context;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.RadioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.RadioButton25)
    RadioButton RadioButton25;
    @BindView(R.id.RadioButton50)
    RadioButton RadioButton50;
    @BindView(R.id.RadioButton75)
    RadioButton RadioButton75;
    @BindView(R.id.RadioButton100)
    RadioButton RadioButton100;
    @BindView(R.id.tl_fTabP)
    TabLayout tabLayout;


    private static final String[] CHANNELS = new String[]{"买入", "卖出"};
    private List<String> mDataList = Arrays.asList(CHANNELS);


    public TradingBuySellView(Context context) {
        super(context);
        this.context = context;
    }

    public TradingBuySellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public TradingBuySellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //R.layout.trading_buy_sell_layout;
        ButterKnife.bind(this);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ToastUtil.ShowToast("选中了" + checkedId);
            }
        });

        TestPopupWindow mWindow = new TestPopupWindow(context);
        //根据指定View定位
        int dimensionx = (int) getResources().getDimension(R.dimen.d80);
        int dimensiony = (int) getResources().getDimension(R.dimen.d05);
        PopupWindowCompat.showAsDropDown(mWindow, ivImg, -dimensionx, dimensiony, Gravity.START);

        try {
            settab();
            tabLayout.addOnTabSelectedListener(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

//        //或者
//        mWindow.showAsDropDown(...);
//        //又或者使用showAtLocation根据屏幕来定位
//        mWindow.showAtLocation(...);
    }

    /**
     * 选中的
     * @param tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    /**
     * 未选中的
     * @param tab
     */
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    /**
     * 复选的
     * @param tab
     */
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    /***
     * ivImg.animate().rotation(180);
     * ivImg.animate().rotation(0);
     */

    /**
     * 悬浮弹框
     */
    public class TestPopupWindow extends PopupWindow {

        public TestPopupWindow(Context context) {
            super(context);
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            setOutsideTouchable(true);
            setFocusable(true);
            setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            View contentView = LayoutInflater.from(context).inflate(R.layout.popup_show,
                    null, false);
            setContentView(contentView);
        }
    }


    /**
     * 设置tab 下划线的宽度
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void settab() throws NoSuchFieldException, IllegalAccessException {
        Class<?> tablayout = tabLayout.getClass();
        Field tabStrip = tablayout.getDeclaredField("mTabStrip");
        tabStrip.setAccessible(true);
        LinearLayout ll_tab = (LinearLayout) tabStrip.get(tabLayout);
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.setMarginStart(DensityUtils.dp2px( 30f));
            params.setMarginEnd(DensityUtils.dp2px( 30f));
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


}
