package cn.dagongniu.oax.trading.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.customview.WrapContentLinearLayoutManager;
import cn.dagongniu.oax.customview.popwindow.BasePopupWindow;
import cn.dagongniu.oax.trading.adapter.SelectMarketAdapter;
import cn.dagongniu.oax.trading.adapter.SelectMarketCategoryAdapter;
import cn.dagongniu.oax.trading.bean.MarketCategoryBean;
import cn.dagongniu.oax.utils.DensityUtils;

public class SelectCoinPopWindow extends BasePopupWindow {

    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_title)
    AutoRelativeLayout rlTitle;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.rl_mian)
    AutoRelativeLayout rl_mian;

    private SelectMarketAdapter selectMarketAdapter;
    private SelectMarketCategoryAdapter selectMarketCategoryAdapter;

    OnMarketNameClickListener onMarketNameClickListener;
    OnMarketCategoryClickListener onMarketCategoryClickListener;


    public SelectCoinPopWindow(Context context, List<MarketCategoryBean.DataBean> dataBeanList, OnMarketNameClickListener onMarketNameClickListener) {
        super(context);

        this.onMarketNameClickListener = onMarketNameClickListener;
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_select_market, null);

        setContentView(view);
        TextView tvLeft = view.findViewById(R.id.tv_left);
        tvLeft.setText(context.getResources().getString(R.string.select_market));
        int[] wg = DensityUtils.getWidthHeight(context);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //setHeight(DensityUtils.dp2px(context.getResources().getDimension(R.dimen.d320)));
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ButterKnife.bind(this, view);

        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        recycleView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                String marketName = dataBeanList.get(position).getCategoryName();

                onMarketNameClickListener.OnGetMarKetName(marketName, position);

            }
        });

        selectMarketAdapter = new SelectMarketAdapter();
        selectMarketAdapter.setNewData(dataBeanList);
        recycleView.setAdapter(selectMarketAdapter);

    }

    public SelectCoinPopWindow(Context context, List<MarketCategoryBean.DataBean.MarketCategoryListBean> dataBeanList, OnMarketCategoryClickListener onMarketCategoryClickListener) {
        super(context);

        this.onMarketCategoryClickListener = onMarketCategoryClickListener;
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_select_market, null);
        setContentView(view);
        TextView tvLeft = view.findViewById(R.id.tv_left);
        tvLeft.setText(context.getResources().getString(R.string.select_coin));
        int[] wg = DensityUtils.getWidthHeight(context);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //setHeight(DensityUtils.dp2px(context.getResources().getDimension(R.dimen.d320)));
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ButterKnife.bind(this, view);

        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        recycleView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                String marketName = dataBeanList.get(position).getMarketMame().substring(0, dataBeanList.get(position).getMarketMame().lastIndexOf("/"));
                String marketId = String.valueOf(dataBeanList.get(position).getMarketId());
                onMarketCategoryClickListener.OnGetMarketCategoryName(marketName, marketId,position);

            }
        });

        selectMarketCategoryAdapter = new SelectMarketCategoryAdapter();
        selectMarketCategoryAdapter.setNewData(dataBeanList);
        recycleView.setAdapter(selectMarketCategoryAdapter);

    }


    @OnClick({R.id.tv_right, R.id.rl_mian})
    public void onClicked(View view) {
        this.dismiss();
    }

    public interface OnMarketNameClickListener {
        public void OnGetMarKetName(String market, int position);
    }

    public interface OnMarketCategoryClickListener {
        public void OnGetMarketCategoryName(String marketName, String marketId,int position);
    }

    public void showPop(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
    }
}
