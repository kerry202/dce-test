package cn.dagongniu.oax.assets.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zhy.autolayout.AutoRelativeLayout;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.adapter.SelectRedPacketCoinAdapter;
import cn.dagongniu.oax.assets.bean.RedPacketIndexBean;
import cn.dagongniu.oax.customview.WrapContentLinearLayoutManager;
import cn.dagongniu.oax.customview.popwindow.BasePopupWindow;
import cn.dagongniu.oax.trading.adapter.SelectMarketAdapter;
import cn.dagongniu.oax.trading.adapter.SelectMarketCategoryAdapter;
import cn.dagongniu.oax.trading.bean.MarketCategoryBean;
import cn.dagongniu.oax.utils.DensityUtils;

/**
 * 底部选中币种
 */
public class SelectCurrencyBott extends BasePopupWindow {

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

    private SelectRedPacketCoinAdapter selectRedPacketCoinAdapter;

    OnMarketNameClickListener onMarketNameClickListener;


    public SelectCurrencyBott(Context context, RedPacketIndexBean redPacketIndexBean,
                              OnMarketNameClickListener onMarketNameClickListener) {
        super(context);

        List<RedPacketIndexBean.DataBean.UserCoinListBean> userCoinListBeans = redPacketIndexBean.getData().getUserCoinList();
        List<RedPacketIndexBean.DataBean.CoinListBean> coinListBeanList = redPacketIndexBean.getData().getCoinList();

        this.onMarketNameClickListener = onMarketNameClickListener;
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_select_market, null);

        setContentView(view);
        TextView tvLeft = view.findViewById(R.id.tv_left);
        tvLeft.setText(context.getResources().getString(R.string.select_coin));
        int[] wg = DensityUtils.getWidthHeight(context);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ButterKnife.bind(this, view);

        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        recycleView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                String marketName = coinListBeanList.get(position).getCoinName();
//                BigDecimal banlance = userCoinListBeans.get(position).getBanlance();
//                BigDecimal banlanceScale = banlance.setScale(8, BigDecimal.ROUND_DOWN);
//
//                String banlanceStr = banlanceScale.toPlainString() + marketName;
                int coinId = coinListBeanList.get(position).getCoinId();

                onMarketNameClickListener.OnGetMarKetName(marketName, position,coinId, redPacketIndexBean);

            }
        });

        selectRedPacketCoinAdapter = new SelectRedPacketCoinAdapter();
        selectRedPacketCoinAdapter.setNewData(coinListBeanList);
        recycleView.setAdapter(selectRedPacketCoinAdapter);

    }


    @OnClick({R.id.tv_right, R.id.rl_mian})
    public void onClicked(View view) {
        this.dismiss();
    }

    public interface OnMarketNameClickListener {
        /**
         * 点击回调数据
         *
         * @param market           币种名称
         * @param position         下标
         * @param redPacketIndexBean
         */
        public void OnGetMarKetName(String market, int position,int id, RedPacketIndexBean redPacketIndexBean);
    }

    public void showPop(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
    }
}
