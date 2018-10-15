package cn.dagongniu.oax.main.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.GridView;

import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.customview.HorizontalListView;
import cn.dagongniu.oax.kline.bean.CommitteeBean;
import cn.dagongniu.oax.main.adapter.HomeCentreAdapter;

/**
 * 首页指标 第一页
 */
public class HomeIndicatorsOneFragment  extends BaseFragment {



    @Override
    protected int getLayoutId() {
        return R.layout.activity_oax_home_one_frament;

    }

    @Override
    protected void initView() {
        super.initView();


    }

    /**
     * 如果要限制GridView滑动，可以自定义一个Gridview重写dispatchTouchEvent方法
     */
    public class MyGridview extends GridView {
        public MyGridview(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyGridview(Context context) {
            super(context);
        }

        public MyGridview(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }
        //通过重写dispatchTouchEvent方法来禁止滑动
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if(ev.getAction() == MotionEvent.ACTION_MOVE){
                return true;//禁止Gridview进行滑动
            }
            return super.dispatchTouchEvent(ev);

        }
    }


}
