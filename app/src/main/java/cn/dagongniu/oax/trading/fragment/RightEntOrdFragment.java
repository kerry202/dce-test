package cn.dagongniu.oax.trading.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.OaxRegisteredActivity;
import cn.dagongniu.oax.base.BaseDialogFragment;
import cn.dagongniu.oax.customview.GridRadioGroup;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.customview.RadioGroupEx;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.trading.bean.MarketCategoryBean;
import cn.dagongniu.oax.trading.bean.OrderSelectNoticeBean;
import cn.dagongniu.oax.trading.presenter.MarketCategoryPresenter;
import cn.dagongniu.oax.trading.view.IMarketCategoryView;
import cn.dagongniu.oax.trading.view.SelectCoinPopWindow;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.MyEvents;

/**
 * 订单的筛选 右边
 */
public class RightEntOrdFragment extends BaseDialogFragment implements IMarketCategoryView,
        SelectCoinPopWindow.OnMarketNameClickListener, SelectCoinPopWindow.OnMarketCategoryClickListener {

    private static final String TAG = "RightEntOrdFragment";

    @BindView(R.id.ll_all)
    AutoRelativeLayout llAll;
    @BindView(R.id.ll_all_market)
    AutoRelativeLayout llAllMarket;
    @BindView(R.id.ll_bottom)
    AutoLinearLayout ll_Bottom;
    @BindView(R.id.rl_reset)
    RelativeLayout rlReset;
    @BindView(R.id.radiogroup_time)
    RadioGroupEx radioGroupTime;
    @BindView(R.id.radiogroup_lx)
    RadioGroup radioGroupLx;
    @BindView(R.id.tv_marketname)
    TextView tvMarketName;
    @BindView(R.id.tv_categoryname)
    TextView tvCategoryname;
    @BindView(R.id.rb_day)
    RadioButton rbDay;
    @BindView(R.id.rb_weeks)
    RadioButton rbWeeks;
    @BindView(R.id.rb_month)
    RadioButton rbMonth;
    @BindView(R.id.rb_three_month)
    RadioButton rbThreeMonth;
    @BindView(R.id.rb_all)
    RadioButton rbAll;
    @BindView(R.id.commit)
    RelativeLayout commit;
    @BindView(R.id.RadioButton_buy_sell)
    RadioButton radioButtonBuyAndSell;
    @BindView(R.id.RadioButton_buy)
    RadioButton radioButtonBuy;
    @BindView(R.id.RadioButton_sell)
    RadioButton radioButtonSell;

    public MyEvents myEvents = new MyEvents();
    public EventBus eventBus = OAXApplication.getmEventBus();
    MarketCategoryPresenter marketCategoryPresenter;
    MarketCategoryBean marketCategoryBean;
    SelectCoinPopWindow liveLinePopupWindow;
    OrderSelectNoticeBean orderSelectNoticeBean;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar;

    public void setOrderSelectNoticeBean(OrderSelectNoticeBean orderSelectNoticeBean) {
        this.orderSelectNoticeBean = orderSelectNoticeBean;
    }

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.TOP | Gravity.END);
        mWindow.setWindowAnimations(R.style.RightDialog);
        mWindow.setLayout((int) mWidth, mHeight);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.right_entrust_order_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        calendar = Calendar.getInstance();
        if (orderSelectNoticeBean == null) {
            orderSelectNoticeBean = new OrderSelectNoticeBean();
        }
        lastDefaultData();
        marketCategoryPresenter = new MarketCategoryPresenter(this, RequestState.STATE_REFRESH);
        marketCategoryPresenter.getMarketCategoryListModule();


        radioGroupTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_day:
                        if (rbDay.isChecked()) {
                            //过去一天
                            calendar.setTime(new Date());
                            calendar.add(Calendar.DATE, -1);
                            Date d = calendar.getTime();
                            String day = format.format(d);
                            Log.e(TAG, "过去一天：" + day);
                            orderSelectNoticeBean.setBeginDate(day);
                            orderSelectNoticeBean.setEndDate(format.format(date));
                            orderSelectNoticeBean.setDateType(1);
                        }
                        break;
                    case R.id.rb_weeks:
                        if (rbWeeks.isChecked()) {
                            //过去七天
                            calendar.setTime(new Date());
                            calendar.add(Calendar.DATE, -7);
                            Date d7 = calendar.getTime();
                            String day7 = format.format(d7);
                            Log.e(TAG, "过去七天：" + day7);
                            orderSelectNoticeBean.setBeginDate(day7);
                            orderSelectNoticeBean.setEndDate(format.format(date));
                            orderSelectNoticeBean.setDateType(2);
                        }
                        break;
                    case R.id.rb_month:

                        if (rbMonth.isChecked()) {
                            //过去一月
                            calendar.setTime(new Date());
                            calendar.add(Calendar.MONTH, -1);
                            Date m = calendar.getTime();
                            String mon = format.format(m);
                            Log.e(TAG, "过去一个月：" + mon);
                            orderSelectNoticeBean.setBeginDate(mon);
                            orderSelectNoticeBean.setEndDate(format.format(date));
                            orderSelectNoticeBean.setDateType(3);
                        }
                        break;
                    case R.id.rb_three_month:
                        if (rbThreeMonth.isChecked()) {
                            //过去三个月
                            calendar.setTime(new Date());
                            calendar.add(Calendar.MONTH, -3);
                            Date m3 = calendar.getTime();
                            String mon3 = format.format(m3);
                            Log.e(TAG, "过去三个月：" + mon3);
                            orderSelectNoticeBean.setBeginDate(mon3);
                            orderSelectNoticeBean.setEndDate(format.format(date));
                            orderSelectNoticeBean.setDateType(4);
                        }
                        break;
                    case R.id.rb_all:
                        orderSelectNoticeBean.setBeginDate("");
                        orderSelectNoticeBean.setEndDate("");
                        orderSelectNoticeBean.setDateType(5);
                        break;
                }
            }
        });

        radioGroupLx.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.RadioButton_buy_sell:
                        orderSelectNoticeBean.setType("");
                        orderSelectNoticeBean.setBuySellType(-1);
                        break;
                    case R.id.RadioButton_buy:
                        if (radioButtonBuy.isChecked()) {
                            orderSelectNoticeBean.setType("1");
                            orderSelectNoticeBean.setBuySellType(1);
                        }
                        break;
                    case R.id.RadioButton_sell:
                        if (radioButtonSell.isChecked()) {
                            orderSelectNoticeBean.setType("2");
                            orderSelectNoticeBean.setBuySellType(2);
                        }
                        break;
                }
            }
        });
    }

    /**
     * 还原上一次选中的值
     */
    private void lastDefaultData() {
        switch (orderSelectNoticeBean.getDateType()) {
            case 1:
                rbDay.setChecked(true);
                break;
            case 2:
                rbWeeks.setChecked(true);
                break;
            case 3:
                rbMonth.setChecked(true);
                break;
            case 4:
                rbThreeMonth.setChecked(true);
                break;
            case 5:
                rbAll.setChecked(true);
                break;
        }

        switch (orderSelectNoticeBean.getBuySellType()) {
            case -1:
                radioButtonBuyAndSell.setChecked(true);
                break;
            case 1:
                radioButtonBuy.setChecked(true);
                break;
            case 2:
                radioButtonSell.setChecked(true);
                break;
        }
        if (TextUtils.isEmpty(orderSelectNoticeBean.getMarKetName())) {
            tvCategoryname.setText(getContext().getResources().getString(R.string.entrust_order_market));
        } else {
            tvCategoryname.setText(orderSelectNoticeBean.getMarKetName());
        }
        if (TextUtils.isEmpty(orderSelectNoticeBean.getCoinName())) {
            tvMarketName.setText(getContext().getResources().getString(R.string.entrust_order_bz));
        } else {
            tvMarketName.setText(orderSelectNoticeBean.getCoinName());
        }

    }

    @OnClick({R.id.ll_all, R.id.rl_reset, R.id.ll_all_market, R.id.commit, R.id.rl_close})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_all:
                if (marketCategoryBean.getData() != null) {
                    liveLinePopupWindow = new SelectCoinPopWindow(getContext(), marketCategoryBean.getData(), this);
                    liveLinePopupWindow.setAnimationStyle(R.style.PPwin_AnimBottom);

                    liveLinePopupWindow.setBackgroundDrawable(null);
                    liveLinePopupWindow.showPop(ll_Bottom);
                }

                break;
            case R.id.ll_all_market:
                if (!TextUtils.isEmpty(tvCategoryname.getText().toString()) &&
                        !tvCategoryname.getText().toString().equals(getContext().getResources().getString(R.string.entrust_order_market))) {
                    liveLinePopupWindow = new SelectCoinPopWindow(getContext(),
                            marketCategoryBean.getData().get(orderSelectNoticeBean.getPosition()).getMarketCategoryList(),
                            this);
                    liveLinePopupWindow.setAnimationStyle(R.style.PPwin_AnimBottom);

                    liveLinePopupWindow.setBackgroundDrawable(null);
                    liveLinePopupWindow.showPop(ll_Bottom);
                } else {
                    ToastUtil.ShowToast(getActivity().getResources().getString(R.string.please_market_show_hint), getActivity());
                }
                break;
            case R.id.rl_reset://重置
                orderSelectNoticeBean = new OrderSelectNoticeBean();
                OAXApplication.getInstance().setOrderSelectNoticeBean(orderSelectNoticeBean);
                rbDay.setChecked(false);
                rbWeeks.setChecked(false);
                rbMonth.setChecked(false);
                rbThreeMonth.setChecked(false);
                rbAll.setChecked(false);
                radioGroupLx.clearCheck();

                tvMarketName.setText(getContext().getResources().getString(R.string.entrust_order_bz));
                tvCategoryname.setText(getContext().getResources().getString(R.string.entrust_order_market));
                tvMarketName.setTextColor(getContext().getResources().getColor(R.color.df_999999));
                tvCategoryname.setTextColor(getContext().getResources().getColor(R.color.df_999999));

                break;
            case R.id.commit://确认
                OAXApplication.getInstance().setOrderSelectNoticeBean(orderSelectNoticeBean);
                //发送订单筛选通知
                myEvents.status = MyEvents.status_pass;
                myEvents.status_type = MyEvents.Order_Select_Notice;
                myEvents.errmsg = "筛选订单过来回调";
                myEvents.something = OAXApplication.getInstance().getOrderSelectNoticeBean();
                eventBus.post(myEvents);
                Logger.e(TAG, "发送订单筛选通知!");
                dismiss();
                break;
            case R.id.rl_close:
                this.dismiss();
                break;
        }

    }

    /**
     * 回调数据
     *
     * @param marketCategoryData
     */
    @Override
    public void setMarketCategoryData(MarketCategoryBean marketCategoryData) {
        this.marketCategoryBean = marketCategoryData;
    }

    @Override
    public void showToask(String str) {
        ToastUtil.ShowToast(str);
    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void toOtherActivity(Class<?> cls) {

    }

    @Override
    public void toFinishActivity() {

    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void setXState(LoadingState xState, String msg) {

    }

    @Override
    public void setRefresh(Object obj) {

    }

    @Override
    public void setLoadMore(Object obj) {
    }


    /**
     * 市场
     *
     * @param market
     * @param position
     */
    @Override
    public void OnGetMarKetName(String market, int position) {
        tvCategoryname.setText(market);
        //取当前市场里面下标为零的币种
        if (marketCategoryBean.getData() != null && marketCategoryBean.getData().size() != 0) {
            if (marketCategoryBean.getData().get(position).getMarketCategoryList() != null && marketCategoryBean.getData().get(position).getMarketCategoryList().size() != 0) {
                String coinName = marketCategoryBean.getData().get(position).getMarketCategoryList().get(0).getMarketMame();
                String substringCoinName = coinName.substring(0, coinName.lastIndexOf("/"));
                tvMarketName.setText(substringCoinName);
                orderSelectNoticeBean.setCoinName(substringCoinName);
                orderSelectNoticeBean.setPosition(position);
                orderSelectNoticeBean.setMarKetName(market);
                orderSelectNoticeBean.setMarketId(marketCategoryBean.getData().get(position).getMarketCategoryList().get(0).getMarketId() + "");
                tvMarketName.setTextColor(getContext().getResources().getColor(R.color.df_gray_666));
                tvCategoryname.setTextColor(getContext().getResources().getColor(R.color.df_gray_666));
                liveLinePopupWindow.dismiss();
                return;
            }
        }
        orderSelectNoticeBean.setPosition(position);
        tvMarketName.setText("");
        liveLinePopupWindow.dismiss();
    }


    /**
     * 币种
     *
     * @param marketName
     * @param marketId
     */
    @Override
    public void OnGetMarketCategoryName(String marketName, String marketId,int Position) {
        tvMarketName.setTextColor(getContext().getResources().getColor(R.color.df_gray_666));
        orderSelectNoticeBean.setCoinName(marketName);
        orderSelectNoticeBean.setMarketId(marketId);
        tvMarketName.setText(marketName);
        liveLinePopupWindow.dismiss();
    }
}
