package cn.dagongniu.oax.assets;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.CoinAddressListBean;
import cn.dagongniu.oax.assets.bean.UserCoinWithdrawalBean;
import cn.dagongniu.oax.assets.presenter.CoinAddressListPresenter;
import cn.dagongniu.oax.assets.presenter.UserCoinWithdrawalPresenter;
import cn.dagongniu.oax.assets.presenter.WithdrawalPresenter;
import cn.dagongniu.oax.assets.view.BottomAddressFragment;
import cn.dagongniu.oax.assets.view.ICoinAddressListView;
import cn.dagongniu.oax.assets.view.IUserCoinWithdrawalView;
import cn.dagongniu.oax.assets.view.IWithdrawalView;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.customview.MyTradingToolbar;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.AppManager;
import cn.dagongniu.oax.utils.DialogUtils;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.MyEvents;

/**
 * 提现
 */
public class WithdrawalActivity extends BaseActivity implements View.OnClickListener, IUserCoinWithdrawalView, ICoinAddressListView,
        BottomAddressFragment.MyDialogFragment_Listener, IWithdrawalView {

    private static final String TAG = "WithdrawalActivity";

    @BindView(R.id.withdrawal_toolbar)
    MyTradingToolbar toolbar;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.bt_withdrawal)
    Button btWithdrawal;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_banlance)
    TextView tvBanlance;
    @BindView(R.id.tv_fee)
    TextView tvFee;
    @BindView(R.id.rl_all_withdrawal)
    RelativeLayout rlAllWithdrawal;
    @BindView(R.id.et_count)
    EditText etCount;
    @BindView(R.id.tv_actual)
    TextView tvActual;
    @BindView(R.id.tv_min_max_hint)
    TextView tvMinMaxHint;
    @BindView(R.id.tv_remaining)
    TextView tvRemaining;
    @BindView(R.id.rl_prompt)
    RelativeLayout rlPrompt;

    private KProgressHUD hud;
    UserCoinWithdrawalPresenter userCoinWithdrawalPresenter;
    CoinAddressListPresenter coinAddressListPresenter;
    WithdrawalPresenter withdrawalPresenter;
    String MarketId = null;
    String MarketName = null;
    Intent intent;
    UserCoinWithdrawalBean userCoinTopBean;
    CoinAddressListBean coinAddressListData;
    BottomAddressFragment fullDialogFragment;
    BigDecimal subtract = new BigDecimal("0");
    BigDecimal TodayMaxWithdrawal = new BigDecimal("0");
    private int beforeDot = 20;
    private int afterDot = 8;// 后八位小数点
    boolean isCheckAdd = false;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawal;
    }

    @Override
    protected void initView() {
        super.initView();
        initToobar();
        initEvent();
        fullDialogFragment = new BottomAddressFragment();
        intent = this.getIntent();
        MarketId = intent.getStringExtra("MarketId");
        MarketName = intent.getStringExtra("MarketName");
        tvName.setText(MarketName);
        tvActual.setText(subZeroAndDot(subtract.setScale(6, BigDecimal.ROUND_DOWN).toString()) + MarketName);
        tvFee.setText(subZeroAndDot(subtract.setScale(6, BigDecimal.ROUND_DOWN).toString()) + MarketName);
        String resultRemaining = String.format(getResources().getString(R.string.assets_keti_edu), "0");
        tvRemaining.setText(resultRemaining);
        String resultMinMax = String.format(getResources().getString(R.string.assets_min_max),
                subZeroAndDot(subtract.setScale(8, BigDecimal.ROUND_DOWN).toPlainString()) + MarketName,
                subZeroAndDot(subtract.setScale(8, BigDecimal.ROUND_DOWN).toPlainString()) + MarketName);
        tvMinMaxHint.setText(resultMinMax);
        userCoinWithdrawalPresenter = new UserCoinWithdrawalPresenter(this, RequestState.STATE_DIALOG);
        userCoinWithdrawalPresenter.getUserCoinWithdrawalModule();
        coinAddressListPresenter = new CoinAddressListPresenter(this, RequestState.STATE_REFRESH);
        coinAddressListPresenter.getCoinAddressListModule();
        withdrawalPresenter = new WithdrawalPresenter(this, RequestState.STATE_DIALOG);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    public String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    private void initToobar() {
        toolbar.setRightImgVisibility(true);
        toolbar.setTvLeftVisibility(true);
        toolbar.setSjVisibility(true);
        toolbar.setRightNameText(R.string.assets_record);
        toolbar.setTitleNameText(R.string.assets_withdrawal);
        toolbar.setRightTvColor(getContext().getResources().getColor(R.color.df_gray_666));
        toolbar.setRightTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //历史记录
                toOtherActivity(WithTopRecordActivity.class);
            }
        });
        toolbar.setLeftMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * edt 改变事件
     */
    private void initEvent() {
        btWithdrawal.setEnabled(false);
        etCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String temp = editable.toString();
                int posDot = temp.indexOf(".");
                //直接输入小数点的情况
                if (posDot == 0) {
                    editable.insert(0, "0");
                    return;
                }
                //连续输入0
                if (temp.equals("00")) {
                    editable.delete(1, 2);
                    return;
                }
                //输入"08" 等类似情况
                if (temp.startsWith("0") && temp.length() > 1 && (posDot == -1 || posDot > 1)) {
                    editable.delete(0, 1);
                    return;
                }

                inputWithdrawalUtils(editable);

                //不包含小数点 不限制小数点前位数
                if (posDot < 0 && beforeDot == -1) {
                    //do nothing 仅仅为了理解逻辑而已
                    return;
                } else if (posDot < 0 && beforeDot != -1) {
                    //不包含小数点 限制小数点前位数
                    if (temp.length() <= beforeDot) {
                        //do nothing 仅仅为了理解逻辑而已
                    } else {
                        editable.delete(beforeDot, beforeDot + 1);
                    }
                    return;
                }

                //如果包含小数点 限制小数点后位数
                if (temp.length() - posDot - 1 > afterDot && afterDot != -1) {
                    editable.delete(posDot + afterDot + 1, posDot + afterDot + 2);//删除小数点后多余位数
                }
            }
        });

    }

    public void inputWithdrawalUtils(Editable s) {
        if (!TextUtils.isEmpty(s.toString())) {
            //当前输入的
            BigDecimal withdrawalCount = new BigDecimal(s.toString());
            //判断是否大于可用
            BigDecimal banlance = userCoinTopBean.getData().getBanlance();
            switch (withdrawalCount.compareTo(banlance)) {
                case 1:
                    etCount.setText(banlance.setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
                    withdrawalCount = banlance;
                    etCount.setSelection(banlance.setScale(8, BigDecimal.ROUND_DOWN).toPlainString().length());
                    break;
                case 0:
                    break;
                case -1:
                    break;
            }
            //手续
            BigDecimal fee = userCoinTopBean.getData().getWithdrawFee();
            //当前输入扣除手续费不能的不能小于0 减法
            subtract = withdrawalCount.subtract(fee);
            /**
             * 计算实际到账是否大小于零
             */
            switch (subtract.compareTo(new BigDecimal("0"))) {
                case 1:
                    tvActual.setText(subZeroAndDot(subtract.setScale(8, BigDecimal.ROUND_DOWN).toPlainString()) + MarketName);
                    break;
                case 0:
                    tvActual.setText(subZeroAndDot(subtract.setScale(6, BigDecimal.ROUND_DOWN).toPlainString()) + MarketName);
                    break;
                case -1:
                    subtract = new BigDecimal("0");
                    tvActual.setText(subZeroAndDot(subtract.setScale(6, BigDecimal.ROUND_DOWN).toPlainString()) + MarketName);
                    break;
            }

            /**
             *判断实际到账必须大于等于0
             */
            if (!(subtract.compareTo(BigDecimal.ZERO) == 0) && !(subtract.compareTo(BigDecimal.ZERO) == -1) && isCheckAdd) {
                btWithdrawal.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                btWithdrawal.setEnabled(true);
            } else {
                btWithdrawal.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                btWithdrawal.setEnabled(false);
            }
        } else {
            subtract = new BigDecimal("0");
            tvActual.setText(subZeroAndDot(subtract.setScale(6, BigDecimal.ROUND_DOWN).toPlainString()) + MarketName);
            btWithdrawal.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
            btWithdrawal.setEnabled(false);
        }
    }

    @OnClick({R.id.rl_all_withdrawal, R.id.rl_address, R.id.bt_withdrawal})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_address:
                fullDialogFragment.setCoinAddressListBean(coinAddressListData, MarketId, MarketName, userCoinTopBean.getData().getType());
                fullDialogFragment.show(getSupportFragmentManager(), "BottomAddressFragment");
                break;
            case R.id.bt_withdrawal:
//                a = -1,表示bigdemical小于bigdemical2；
//                a = 0,表示bigdemical等于bigdemical2；
//                a = 1,表示bigdemical大于bigdemical2；
                BigDecimal etCountbd = new BigDecimal(etCount.getText().toString());
                BigDecimal maxOutQty = userCoinTopBean.getData().getMaxOutQty();
                BigDecimal minOutQty = userCoinTopBean.getData().getMinOutQty();

                boolean isLegal = true;
                //判断当前值是否小于最小提现额
                switch (etCountbd.compareTo(minOutQty)) {
                    case -1:
                        isLegal = false;
                        String result = String.format(getResources().getString(R.string.assets_min_show_hint),
                                subZeroAndDot(userCoinTopBean.getData().getMinOutQty().setScale(8, BigDecimal.ROUND_DOWN).toPlainString())
                                        + MarketName);
                        ToastUtil.ShowToast(result);
                        break;
                    case 0:
                        break;
                    case 1:
                        break;
                }
                //判断当前值是否大于于最大提现额
                switch (etCountbd.compareTo(maxOutQty)) {
                    case -1:
                        break;
                    case 0:
                        break;
                    case 1:
                        isLegal = false;
                        String result = String.format(getResources().getString(R.string.assets_max_show_hint),
                                subZeroAndDot(userCoinTopBean.getData().getMaxOutQty().setScale(8, BigDecimal.ROUND_DOWN).toPlainString())
                                        + MarketName);
                        ToastUtil.ShowToast(result);
                        break;
                }

                if (isLegal) {
                    withdrawalPresenter.getWithdrawalModule();
                }

                break;
            case R.id.rl_all_withdrawal://全部提现
                etCount.setText(userCoinTopBean.getData().getBanlance().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
                etCount.setSelection(userCoinTopBean.getData().getBanlance().setScale(8, BigDecimal.ROUND_DOWN).toString().length());
                break;
        }
    }


    private void scheduleDismiss() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hud.dismiss();
            }
        }, 1000);
    }

    @Subscribe
    public void onEvent(MyEvents event) {
        switch (event.status_type) {
            case MyEvents.Add_Withdrawal_Adderss_Success://新增地址成功的通知
                coinAddressListPresenter.getCoinAddressListModule();
                break;
        }
    }

    /**
     * 提现回调
     *
     * @param userCoinTopBean
     */
    @Override
    public void setUserCoinWithdrawalData(UserCoinWithdrawalBean userCoinTopBean) {
        this.userCoinTopBean = userCoinTopBean;

        BigDecimal withdrawalAmount = userCoinTopBean.getData().getWithdrawalAmount();
        BigDecimal useredWithdrawal = userCoinTopBean.getData().getUseredWithdrawal();
        TodayMaxWithdrawal = withdrawalAmount.subtract(useredWithdrawal);

        String resultRemaining = String.format(getResources().getString(R.string.assets_keti_edu), subZeroAndDot(TodayMaxWithdrawal.setScale(8, BigDecimal.ROUND_DOWN).toPlainString()));

        tvRemaining.setText(resultRemaining);

        tvTotal.setText(userCoinTopBean.getData().getTotalBanlance().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
        tvBanlance.setText(userCoinTopBean.getData().getBanlance().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());

        String resultMinMax = String.format(getResources().getString(R.string.assets_min_max),
                subZeroAndDot(userCoinTopBean.getData().getMinOutQty().setScale(8, BigDecimal.ROUND_DOWN).toPlainString()) + MarketName,
                subZeroAndDot(userCoinTopBean.getData().getMaxOutQty().setScale(8, BigDecimal.ROUND_DOWN).toPlainString()) + MarketName);

        tvMinMaxHint.setText(resultMinMax);

        String tvFeestr;
        switch (userCoinTopBean.getData().getWithdrawFee().compareTo(BigDecimal.ZERO)) {
            case 0:
                tvFeestr = "0";
                break;
            default:
                tvFeestr = userCoinTopBean.getData().getWithdrawFee().setScale(8, BigDecimal.ROUND_DOWN).toPlainString();
                break;
        }

        tvFee.setText(tvFeestr + MarketName);
    }

    @Override
    public String getCoinId() {
        return MarketId;
    }

    @Override
    public String getAddress() {
        return tvAdd.getText().toString();
    }

    @Override
    public String getRemark() {
        return "";
    }

    @Override
    public String getqty() {
        return etCount.getText().toString();
    }

    /**
     * 提现成功
     */
    @Override
    public void isSuccess() {

        ToastUtil.ShowToast(this.getResources().getString(R.string.assets_withdrawal_successful));

        //发送登录成功通知
        myEvents.status = MyEvents.status_ok;
        myEvents.status_type = MyEvents.Withdrawal_Success;
        myEvents.errmsg = this.getResources().getString(R.string.assets_withdrawal_successful);
        eventBus.post(myEvents);
        Logger.e(TAG, "发送提现成功通知!");

        AppManager.remove(AssetsDetailsActivity.class.getSimpleName());
        AppManager.remove(WithdTopSearchActivity.class.getSimpleName());
        finish();

    }

    @Override
    public int getCoinAddressListIndex() {
        return 1;
    }

    @Override
    public int getCoinAddressListPageSize() {
        return 100;
    }

    /**
     * 地址回调
     *
     * @param coinAddressListData
     */
    @Override
    public void setCoinAddressListData(CoinAddressListBean coinAddressListData) {
        this.coinAddressListData = coinAddressListData;
    }

    /**
     * 参数有误-去登陆
     *
     * @param msg
     */
    @Override
    public void goLogin(String msg) {
        ToastUtil.ShowToast(msg);
    }

    @Override
    public void getDataFrom_DialogFragment(String add) {
        if (!(subtract.compareTo(BigDecimal.ZERO) == 0)) {
            btWithdrawal.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
            btWithdrawal.setEnabled(true);
        } else {
            btWithdrawal.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
            btWithdrawal.setEnabled(false);
        }
        isCheckAdd = true;
        tvAdd.setText(add);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick(R.id.rl_prompt)
    public void onClick() {
        DialogUtils.getLvDialog(this);
    }
}
