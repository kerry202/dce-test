package cn.dagongniu.oax.assets;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.OaxRegisteredActivity;
import cn.dagongniu.oax.assets.bean.AwardRedPacketBean;
import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.oax.assets.bean.RedPacketIndexBean;
import cn.dagongniu.oax.assets.presenter.AwardRedPacketPresenter;
import cn.dagongniu.oax.assets.presenter.RedPacketIndexPresenter;
import cn.dagongniu.oax.assets.view.CustomShareListener;
import cn.dagongniu.oax.assets.view.IAwardRedPacketView;
import cn.dagongniu.oax.assets.view.IRedPacketIndexView;
import cn.dagongniu.oax.assets.view.RedMorePopWind;
import cn.dagongniu.oax.assets.view.SelectCurrencyBott;
import cn.dagongniu.oax.assets.view.SharePopWind;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.kline.view.TabEntity;
import cn.dagongniu.oax.trading.view.SelectCoinPopWindow;
import cn.dagongniu.oax.utils.AccountValidatorUtil;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.PageToolUtils;
import cn.dagongniu.oax.utils.PermissionsUtils;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.MyEvents;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;

/**
 * 发红包
 */
public class RedEnvelopeActivity extends BaseActivity implements IAwardRedPacketView, IRedPacketIndexView {

    private static final String TAG = "RedEnvelopeActivity";

    @BindView(R.id.ordinary_and_luck_tab_layout)
    CommonTabLayout ordinaryLockTabLayout;
    @BindView(R.id.im_left)
    ImageView imLeft;
    @BindView(R.id.tv_sele_market_count)
    TextView tvSeleMarketCount;
    @BindView(R.id.tv_hint_same)
    TextView tvHintSame;
    @BindView(R.id.rl_sele_currency)
    RelativeLayout rl_sele_currency;
    @BindView(R.id.rl_go_red)
    RelativeLayout rl_go_red;
    @BindView(R.id.rl_show_hint)
    RelativeLayout rl_show_hint;
    @BindView(R.id.tv_problem)
    TextView tv_problem;
    @BindView(R.id.rl_errer)
    RelativeLayout rl_errer;
    @BindView(R.id.et_market_count)
    EditText et_market_count;
    @BindView(R.id.tv_next_right_count)
    TextView tv_next_right_count;
    @BindView(R.id.et_red_count)
    EditText et_red_count;
    @BindView(R.id.ll_mian)
    LinearLayout ll_mian;
    @BindView(R.id.et_wishwords)
    EditText et_wishwords;
    @BindView(R.id.tv_market)
    TextView tv_market;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.tv_sum_market)
    TextView tv_sum_market;
    @BindView(R.id.tv_errer)
    TextView tv_errer;
    @BindView(R.id.tv_envelope_count)
    TextView tv_envelope_count;
    @BindView(R.id.tv_envelope_next_right_count)
    TextView tv_envelope_next_right_count;
    @BindView(R.id.tv_currency)
    TextView tv_currency;

    private UMShareListener mShareListener;

    Bundle bundle;
    SelectCurrencyBott selectCurrencyBott;
    RedMorePopWind redMorePopWind;
    SharePopWind sharePopWind;
    AwardRedPacketPresenter awardRedPacketPresenter;
    int redPacketType = 2;// 2 =随机  1=普通
    int randomIndexPosition = 0;//选中币种列表下标
    int ordinaryIndexPosition = 0;//选中币种列表下标
    RedPacketIndexPresenter redPacketIndexPresenter;

    TranslateAnimation mShowAction;
    TranslateAnimation mHiddenAction;

    RedPacketIndexBean randomIndexBean;
    RedPacketIndexBean ordinaryIndexBean;

    private int beforeDot = 20;
    private int afterDot = 8;// 后八位小数点

    boolean coinCountFocus = false;
    boolean redCountFocus = false;

    String coinCountErrerMsg = "";
    String redCountErrerMsg = "";
    boolean isCoinCountErrer = false;
    boolean isRedCountErrer = false;

    RedPacketIndexBean.DataBean.UserCoinListBean userCoinListBean;
    int randomCoinId = -1;
    int ordinaryCoinId = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_red_envelope;
    }

    @Override
    protected void initView() {
        super.initView();
        initTabLayout();
        initEvent();
        mShareListener = new CustomShareListener(this, myEvents, eventBus);
        awardRedPacketPresenter = new AwardRedPacketPresenter(this, RequestState.STATE_DIALOG);
        redPacketIndexPresenter = new RedPacketIndexPresenter(this, RequestState.STATE_DIALOG);
        redPacketIndexPresenter.getRedIndexOrdinaryModule();
        redPacketIndexPresenter.getRedIndexRandomModule();
    }

    @Override
    protected void initData() {
        super.initData();
        bundle = new Bundle();

        alphaGO(0.5F, false);
        /**
         * 出场动画
         */
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(300);
        /**
         * 关闭动画
         */
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(300);
    }

    private void initTabLayout() {
        ordinaryLockTabLayout.setIndicatorColor(getResources().getColor(R.color.white));
        ordinaryLockTabLayout.setTextSelectColor(getResources().getColor(R.color.white));
        ordinaryLockTabLayout.setTextUnselectColor(getResources().getColor(R.color.white));
        ordinaryLockTabLayout.seTextUnselectBold();
        ordinaryLockTabLayout.setIndicatorWidth(getResources().getDimension(R.dimen.d30));
        ArrayList<CustomTabEntity> list = new ArrayList();
        final String[] timeSelectIndicatorTitles = {getResources().getString(R.string.red_luck), getResources().getString(R.string.red_ordinary)};
        for (int i = 0; i < timeSelectIndicatorTitles.length; i++) {
            list.add(new TabEntity(timeSelectIndicatorTitles[i], 0, 0));
        }
        ordinaryLockTabLayout.setTabData(list);
        ordinaryLockTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                setTabLayoutSelect(position);
            }

            @Override
            public void onTabReselect(int position) {
                setTabLayoutSelect(position);
            }
        });
    }

    /**
     * tablayout 初始化以及事件处理
     *
     * @param position
     */
    private void setTabLayoutSelect(int position) {

        //防止重复点击
        if (position == 1 && redPacketType == 1) {
            return;
        } else if (position == 0 && redPacketType == 2) {
            return;
        }

        String marketCountStr = et_market_count.getText().toString();
        String redCountStr = et_red_count.getText().toString();

        if (position == 1) {
            redPacketType = 1;
            tvSeleMarketCount.setText(this.getString(R.string.red_a_single_market));
            tvHintSame.setText(this.getString(R.string.red_hint_count_same));
            setViewTextInitialize(ordinaryIndexBean, false);
        } else if (position == 0) {
            redPacketType = 2;
            tvSeleMarketCount.setText(this.getString(R.string.red_sum_count));
            tvHintSame.setText(this.getString(R.string.red_hint_count_random));
            setViewTextInitialize(randomIndexBean, false);
        }
    }

    @OnClick({R.id.im_left, R.id.im_more, R.id.rl_sele_currency, R.id.rl_go_red, R.id.tv_problem})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
            //更多
            case R.id.im_more:
                // 0 =发出的红包   1=收到的红包
                redMorePopWind = new RedMorePopWind(this,
                        new RedMorePopWind.OnSendClickListener() {
                            @Override
                            public void OnSend() {
                                bundle.putInt("redType", 0);
                                openActivity(SendReceiveRedEnvelopeActivity.class, bundle);
                            }
                        }, new RedMorePopWind.OnReceiveClickListener() {
                    @Override
                    public void OnReceive() {
                        bundle.putInt("redType", 1);
                        openActivity(SendReceiveRedEnvelopeActivity.class, bundle);
                    }
                });
                redMorePopWind.setAnimationStyle(R.style.PPwin_AnimBottom);
                redMorePopWind.setBackgroundDrawable(null);
                redMorePopWind.showPop(rl_show_hint);
                break;
            //选中币种
            case R.id.rl_sele_currency:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                RedPacketIndexBean selectIndexRedBean;
                if (redPacketType == 2) {
                    selectIndexRedBean = randomIndexBean;
                } else {
                    selectIndexRedBean = ordinaryIndexBean;
                }
                if (selectIndexRedBean.getData().getUserCoinList() == null
                        || selectIndexRedBean.getData().getCoinList() == null
                        || selectIndexRedBean.getData().getCoinList().size() <= 0) {
                    return;
                }
                selectCurrencyBott = new SelectCurrencyBott(this, selectIndexRedBean, new SelectCurrencyBott.OnMarketNameClickListener() {
                    /**
                     * 选中回调
                     * @param market
                     * @param position
                     */
                    @Override
                    public void OnGetMarKetName(String market, int position, int id, RedPacketIndexBean redPacketIndexBean) {
                        selectCurrencyBott.dismiss();
                        if (redPacketType == 2) {
                            randomCoinId = id;
                            randomIndexPosition = position;
                        } else {
                            ordinaryCoinId = id;
                            ordinaryIndexPosition = position;
                        }
                        setViewTextInitialize(redPacketIndexBean, true);
                    }
                });
                selectCurrencyBott.setAnimationStyle(R.style.PPwin_AnimBottom);
                selectCurrencyBott.setBackgroundDrawable(null);
                selectCurrencyBott.showPop(rl_show_hint);
                break;
            //塞入红包
            case R.id.rl_go_red:
//                //Android6.0权限适配
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] mPermissionList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (PermissionsUtils.lacksPermissions(this, mPermissionList)) {
                        ActivityCompat.requestPermissions(this, mPermissionList, 123);
                    }
                }

                sharePopWind = new SharePopWind(this, new SharePopWind.OnShareWxClickListener() {
                    @Override
                    public void OnShareWx() {
                        sharePopWind.dismiss();
                        awardRedPacketPresenter.getAwardRedPacketModule(IAwardRedPacketView.ShareWxType);
                    }
                }, new SharePopWind.OnSharePyqClickListener() {
                    @Override
                    public void OnSharePyq() {
                        sharePopWind.dismiss();
                        awardRedPacketPresenter.getAwardRedPacketModule(IAwardRedPacketView.SharePyqType);
                    }
                }, new SharePopWind.OnShareQQClickListener() {
                    @Override
                    public void OnShareQQ() {
                        sharePopWind.dismiss();
                        awardRedPacketPresenter.getAwardRedPacketModule(IAwardRedPacketView.ShareQqType);
                    }
                }, new SharePopWind.OnShareCopyClickListener() {
                    @Override
                    public void OnShareCopy() {
                        sharePopWind.dismiss();
                        awardRedPacketPresenter.getAwardRedPacketModule(IAwardRedPacketView.ShareCopyType);
                    }
                });
                sharePopWind.setAnimationStyle(R.style.PPwin_AnimBottom);
                sharePopWind.setBackgroundDrawable(null);
                sharePopWind.showPop(rl_show_hint);
                break;
            //遇到问题
            case R.id.tv_problem:
                openActivity(RedHelpActivity.class);
                break;
        }
    }

    private void pingStartYiAmin(LinearLayout ll_mian) {
        ObjectAnimator down = ObjectAnimator.ofFloat(ll_mian, "translationY", 0, 30);
        down.setDuration(300);
        down.start();
    }

    private void pingStopYiAmin(LinearLayout ll_mian) {
        ObjectAnimator down = ObjectAnimator.ofFloat(ll_mian, "translationY", 30, 0);
        down.setDuration(300);
        down.start();
    }

    /**
     * 按钮失效
     *
     * @param alpha     透明度
     * @param isEnabled 是否失效
     */
    private void alphaGO(float alpha, boolean isEnabled) {
        rl_go_red.setEnabled(isEnabled);
        AlphaAnimation alphaMy = new AlphaAnimation(alpha, alpha);
        alphaMy.setDuration(0);
        alphaMy.setFillAfter(true);
        rl_go_red.startAnimation(alphaMy);
    }

    /**
     * edt 改变事件
     */
    private void initEvent() {

        /**
         * 获取焦点事件
         */
        et_market_count.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    coinCountFocus = true;
                    inputUtils(et_market_count.getEditableText(), et_red_count.getEditableText());
                } else {
                    coinCountFocus = false;
                }
            }
        });
        et_red_count.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    redCountFocus = true;
                    inputUtils(et_market_count.getEditableText(), et_red_count.getEditableText());
                } else {
                    redCountFocus = false;
                }
            }
        });

        et_market_count.addTextChangedListener(new TextWatcher() {
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
                }

                //如果包含小数点 限制小数点后位数
                if (temp.length() - posDot - 1 > afterDot && afterDot != -1) {
                    editable.delete(posDot + afterDot + 1, posDot + afterDot + 2);//删除小数点后多余位数
                }

                inputUtils(editable, et_red_count.getEditableText());
            }
        });

        et_red_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //不能输入0开头的数字
                String text = editable.toString();
                int len = editable.toString().length();
                if (len > 1 && text.startsWith("0")) {
                    editable.replace(0, 1, "");
                }
                inputUtils(et_market_count.getEditableText(), editable);
            }
        });
    }

    /**
     * 错误样式设置
     */
    private void setErrerHintText() {

        //判断币数是否有错误
        if (isCoinCountErrer) {
            et_market_count.setTextColor(getResources().getColor(R.color.df_EC7151));
            tvSeleMarketCount.setTextColor(getResources().getColor(R.color.df_EC7151));
            tv_next_right_count.setTextColor(getResources().getColor(R.color.df_EC7151));
        } else {
            et_market_count.setTextColor(getResources().getColor(R.color.df_333333));
            tvSeleMarketCount.setTextColor(getResources().getColor(R.color.df_333333));
            tv_next_right_count.setTextColor(getResources().getColor(R.color.df_333333));
        }
        //判断个数是否有错误
        if (isRedCountErrer) {
            et_red_count.setTextColor(getResources().getColor(R.color.df_EC7151));
            tv_envelope_count.setTextColor(getResources().getColor(R.color.df_EC7151));
            tv_envelope_next_right_count.setTextColor(getResources().getColor(R.color.df_EC7151));
        } else {
            et_red_count.setTextColor(getResources().getColor(R.color.df_333333));
            tv_envelope_count.setTextColor(getResources().getColor(R.color.df_333333));
            tv_envelope_next_right_count.setTextColor(getResources().getColor(R.color.df_333333));
        }

        // 判断是否有错误提示
        if (isCoinCountErrer || isRedCountErrer) {
            if (rl_errer.getVisibility() == View.GONE) {
                pingStartYiAmin(ll_mian);
                rl_errer.startAnimation(mShowAction);
                rl_errer.setVisibility(View.VISIBLE);
            }

            if (coinCountFocus && isCoinCountErrer) {
                tv_errer.setText(coinCountErrerMsg);
            } else if (redCountFocus && isRedCountErrer) {
                tv_errer.setText(redCountErrerMsg);
            } else if (isCoinCountErrer) {
                tv_errer.setText(coinCountErrerMsg);
            } else {
                tv_errer.setText(redCountErrerMsg);
            }

            alphaGO(0.5F, false);
        } else {
            if (rl_errer.getVisibility() == View.VISIBLE) {
                pingStopYiAmin(ll_mian);
                rl_errer.startAnimation(mHiddenAction);
                rl_errer.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(et_market_count.getText().toString()) && !TextUtils.isEmpty(et_red_count.getText().toString())) {
                if (new BigDecimal(et_market_count.getText().toString()).compareTo(BigDecimal.ZERO) == 1 && new BigDecimal(et_red_count.getText().toString()).compareTo(BigDecimal.ZERO) == 1) {
                    //合法
                    alphaGO(1F, true);
                } else {
                    alphaGO(0.5F, false);
                }
            } else {
                alphaGO(0.5F, false);
            }
        }
    }

    /**
     * 正则效验 效验输入的币数个数是否合法
     *
     * @param cionCount
     * @param redCount
     */
    private void inputUtils(Editable cionCount, Editable redCount) {
        RedPacketIndexBean changedIndexBean;
        //获取模型，随机or普通
        switch (redPacketType) {
            case 1:
                changedIndexBean = ordinaryIndexBean;
                break;
            case 2:
                changedIndexBean = randomIndexBean;
                break;
            default:
                changedIndexBean = randomIndexBean;
                break;
        }

        String cionCountStr = cionCount.toString();
        if (TextUtils.isEmpty(cionCount)) {
            cionCountStr = "0";
        }
        String redCountStr = redCount.toString();
        if (TextUtils.isEmpty(redCountStr)) {
            redCountStr = "0";
        }

        //判断币数
        BigDecimal limitCoinQty;
        String coinName = "";
        if (redPacketType == 2) {
            limitCoinQty = changedIndexBean.getData().getCoinList().get(randomIndexPosition).getLimitCoinQty();
            coinName = changedIndexBean.getData().getCoinList().get(randomIndexPosition).getCoinName();
        } else {
            limitCoinQty = changedIndexBean.getData().getCoinList().get(ordinaryIndexPosition).getLimitCoinQty();
            coinName = changedIndexBean.getData().getCoinList().get(ordinaryIndexPosition).getCoinName();
        }
        BigDecimal banlance = userCoinListBean.getBanlance();
        BigDecimal countCoinQty = new BigDecimal(cionCountStr);
        BigDecimal redCountQty = new BigDecimal(redCountStr);

        /**
         * 随机红包
         */
        if (redPacketType == 2) {
            if (TextUtils.isEmpty(cionCount.toString())) {
                isCoinCountErrer = false;
                judgeCountCny(changedIndexBean, redCountQty, cionCountStr, redCountStr, coinName);
                return;
            } else if (new BigDecimal(cionCount.toString()).compareTo(BigDecimal.ZERO) != 1) {
                isCoinCountErrer = false;
                judgeCountCny(changedIndexBean, redCountQty, cionCountStr, redCountStr, coinName);
                return;
            }
            //红包个数不等于0 并且要大于零
            if (redCountQty.compareTo(BigDecimal.ZERO) != -1 && redCountQty.compareTo(BigDecimal.ZERO) != 0) {
                Logger.e(TAG, countCoinQty.toPlainString());
                BigDecimal divide = countCoinQty.divide(new BigDecimal(redCount.toString()), 8, BigDecimal.ROUND_DOWN);
                BigDecimal minBD = new BigDecimal("0.00000001");
                Logger.e(TAG, divide.toPlainString());
                Logger.e(TAG, minBD.toPlainString());
                if (divide.compareTo(minBD) == -1) {
                    coinCountErrerMsg = "单个红包不可低于0.00000001个" + coinName;
                    isCoinCountErrer = true;
                } else if (countCoinQty.compareTo(limitCoinQty) == 1) {//大于限制额度
                    coinCountErrerMsg = "一次红包币数不可超过" + AccountValidatorUtil.subZeroAndDot(limitCoinQty.setScale(8, BigDecimal.ROUND_DOWN).toPlainString()) + "个";
                    isCoinCountErrer = true;
                } else {
                    if (countCoinQty.compareTo(BigDecimal.ZERO) == 1 && countCoinQty.compareTo(banlance) == 1) {
                        coinCountErrerMsg = "余额不足";
                        isCoinCountErrer = true;
                    } else {
                        isCoinCountErrer = false;
                    }
                }
            } else {
                if (countCoinQty.compareTo(limitCoinQty) == 1) {//大于限制额度
                    coinCountErrerMsg = "一次红包币数不可超过" + AccountValidatorUtil.subZeroAndDot(limitCoinQty.setScale(8, BigDecimal.ROUND_DOWN).toPlainString()) + "个";
                    isCoinCountErrer = true;
                } else {
                    if (countCoinQty.compareTo(BigDecimal.ZERO) == 1 && countCoinQty.compareTo(banlance) == 1) {
                        coinCountErrerMsg = "余额不足";
                        isCoinCountErrer = true;
                    } else {
                        isCoinCountErrer = false;
                    }
                }
            }
        } else {
            /**
             * 普通红包
             */
            if (countCoinQty.compareTo(limitCoinQty) == 1) {//大于限制额度
                coinCountErrerMsg = "每个红包币数不可超过" + AccountValidatorUtil.subZeroAndDot(limitCoinQty.setScale(8, BigDecimal.ROUND_DOWN).toPlainString()) + "个";
                isCoinCountErrer = true;
            } else {
                //红包个数跟币数相乘  判断余额
                BigDecimal a = new BigDecimal(1);
                BigDecimal sum = countCoinQty;
                if (redCountQty.compareTo(a) == 1) {
                    sum = sum.multiply(redCountQty);
                }
                if (sum.compareTo(BigDecimal.ZERO) == 1 && sum.compareTo(banlance) == 1) {
                    coinCountErrerMsg = "余额不足";
                    isCoinCountErrer = true;
                } else {
                    isCoinCountErrer = false;
                }
            }
        }
        judgeCountCny(changedIndexBean, redCountQty, cionCountStr, redCountStr, coinName);

    }

    /**
     * 判断个数 人民币 以及错误集中处理
     *
     * @param changedIndexBean
     * @param redCountQty
     * @param cionCountStr
     * @param redCountStr
     * @param coinName
     */
    public void judgeCountCny(RedPacketIndexBean changedIndexBean, BigDecimal redCountQty, String cionCountStr, String redCountStr, String coinName) {
        //判断个数
        BigDecimal limitPacketQty;
        if (redPacketType == 2) {
            limitPacketQty = changedIndexBean.getData().getCoinList().get(randomIndexPosition).getLimitPacketQty();
        } else {
            limitPacketQty = changedIndexBean.getData().getCoinList().get(ordinaryIndexPosition).getLimitPacketQty();
        }

        if (redCountQty.compareTo(limitPacketQty) == 1) {
            redCountErrerMsg = "一次最多发" + limitPacketQty.setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "个红包";
            isRedCountErrer = true;
        } else {
            isRedCountErrer = false;
        }

        //人民币
        if (redPacketType == 2) {
            tv_sum_market.setText(AccountValidatorUtil.subZeroAndDot(new BigDecimal(cionCountStr).setScale(8, BigDecimal.ROUND_DOWN).toPlainString()) + coinName);
            BigDecimal cnyPrice = userCoinListBean.getCnyPrice();
            BigDecimal cionCountBD = new BigDecimal(cionCountStr);
            BigDecimal multiply = cnyPrice.multiply(cionCountBD);
            BigDecimal bigDecimal = multiply.setScale(2, BigDecimal.ROUND_DOWN);
            tv_currency.setText("≈¥" + bigDecimal.toPlainString());
        } else {
            BigDecimal cnyPrice = userCoinListBean.getCnyPrice();
            BigDecimal cionCountBD = new BigDecimal(cionCountStr);//单个币数
            BigDecimal redCountBD = new BigDecimal(redCountStr);//红包个数
            BigDecimal multiply1 = cionCountBD.multiply(redCountBD);//随机红包的总发币数
            tv_sum_market.setText(AccountValidatorUtil.subZeroAndDot(multiply1.setScale(8, BigDecimal.ROUND_DOWN).toPlainString()) + coinName);
            BigDecimal multiply = cnyPrice.multiply(multiply1);
            BigDecimal bigDecimal = multiply.setScale(2, BigDecimal.ROUND_DOWN);
            tv_currency.setText("≈¥" + bigDecimal.toPlainString());
        }

        setErrerHintText();
    }

    /**
     * 发送红包一些列V
     *
     * @return
     */
    @Override
    public String getCoinId() {
        String id = "";
        if (redPacketType == 2) {
            id = randomCoinId + "";
        } else {
            id = ordinaryCoinId + "";
        }
        return id;
    }

    @Override
    public String getType() {
        return redPacketType + "";
    }

    @Override
    public String getTotalCoinQty() {
        if (redPacketType == 2) {
            return et_market_count.getText().toString();
        } else {
            BigDecimal market = new BigDecimal(et_market_count.getText().toString());
            BigDecimal redCount = new BigDecimal(et_red_count.getText().toString());
            BigDecimal multiply = market.multiply(redCount);
            return multiply.setScale(8, BigDecimal.ROUND_DOWN).toPlainString();
        }
    }

    @Override
    public String getTotalPacketQty() {
        return et_red_count.getText().toString();
    }

    @Override
    public String getWishWords() {
        return et_wishwords.getText().toString();
    }

    //QQ与新浪不需要添加Activity，但需要在使用QQ分享或者授权的Activity中，添加：
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 发送红包成功
     *
     * @param awardRedPacketData
     */
    @Override
    public void setAwardRedPacketData(AwardRedPacketBean awardRedPacketData, int shareType) {

        et_market_count.setText("");
        et_red_count.setText("");
        et_wishwords.setText("");

        //发送红包成功通知
        myEvents.status = MyEvents.status_ok;
        myEvents.status_type = MyEvents.RedPacketSuccess;
        myEvents.errmsg = "发送红包成功";
        eventBus.post(myEvents);
        Logger.e(TAG, "发送红包成功通知!");

        String url = awardRedPacketData.getData().getUrl();
        String title = awardRedPacketData.getData().getTitle();
        String desc = awardRedPacketData.getData().getDesc();

        switch (shareType) {
            case IAwardRedPacketView.ShareWxType:
                shareGo(url, title, desc, SHARE_MEDIA.WEIXIN);
                break;
            case IAwardRedPacketView.SharePyqType:
                shareGo(url, title, desc, SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case IAwardRedPacketView.ShareQqType:
                shareGo(url, title, desc, SHARE_MEDIA.QQ);
                break;
            case IAwardRedPacketView.ShareCopyType:
                TextView tvurl = new TextView(this);
                tvurl.setText(title + desc + url);
                PageToolUtils.CopyText(this, tvurl);

                Intent intent = new Intent(this, ShareSuccessActivity.class);
                intent.putExtra("SuccessType", 1);
                startActivity(intent);
                break;
        }

        redPacketIndexPresenter.getRedIndexRefreshModule(RequestState.STATE_REFRESH);
    }

    public void shareGo(String url, String title, String desc, SHARE_MEDIA var1) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);
        web.setThumb(new UMImage(this, R.drawable.redpacket_icon));
        web.setDescription(desc);
        new ShareAction(this).withMedia(web)
                .setPlatform(var1)
                .setCallback(mShareListener).share();
    }

    @Override
    public void setsetAwardRedPacketDataErrer(AwardRedPacketBean awardRedPacketBean) {

    }

    /**
     * 发红包页面加载接口回调
     */
    @Override
    public void setOrdinaryData(RedPacketIndexBean redPacketIndexBean) {
        this.ordinaryIndexBean = redPacketIndexBean;
        setViewTextInitialize(ordinaryIndexBean, false);
    }

    /**
     * @param redPacketIndexBean
     */
    @Override
    public void setRandomData(RedPacketIndexBean redPacketIndexBean) {
        this.randomIndexBean = redPacketIndexBean;
        setViewTextInitialize(randomIndexBean, false);
    }

    boolean isRandomSuccess;
    boolean isOrDinarySuccess;

    @Override
    public void setOrdinaryRefreshData(RedPacketIndexBean redPacketIndexBean) {
        this.ordinaryIndexBean = redPacketIndexBean;
        isOrDinarySuccess = true;
        setViewTextRefresh();
    }

    @Override
    public void setRandomRefreshData(RedPacketIndexBean redPacketIndexBean) {
        this.randomIndexBean = redPacketIndexBean;
        isRandomSuccess = true;
        setViewTextRefresh();
    }

    @Override
    public void setOrdinaryDataErrer(String msg) {
    }

    @Override
    public void setRandomDataErrer(String msg) {

    }

    /**
     * 刷新成功
     */
    public void setViewTextRefresh() {
        BigDecimal banlance = new BigDecimal(0);
        String marketName = "";
        if (isRandomSuccess && isOrDinarySuccess) {
            if (redPacketType == 2) {
                for (RedPacketIndexBean.DataBean.UserCoinListBean userCoinListBeanList : randomIndexBean.getData().getUserCoinList()) {
                    if (userCoinListBeanList.getCoinId() == randomCoinId) {
                        this.userCoinListBean = userCoinListBeanList;
                        banlance = userCoinListBean.getBanlance();
                    }
                }
                for (RedPacketIndexBean.DataBean.CoinListBean coinListBean : randomIndexBean.getData().getCoinList()) {
                    if (coinListBean.getCoinId() == randomCoinId) {
                        marketName = coinListBean.getCoinName();
                    }
                }

            } else {
                for (RedPacketIndexBean.DataBean.UserCoinListBean userCoinListBeanList : ordinaryIndexBean.getData().getUserCoinList()) {
                    if (userCoinListBeanList.getCoinId() == ordinaryCoinId) {
                        this.userCoinListBean = userCoinListBeanList;
                        banlance = userCoinListBean.getBanlance();
                    }
                }
                for (RedPacketIndexBean.DataBean.CoinListBean coinListBean : ordinaryIndexBean.getData().getCoinList()) {
                    if (coinListBean.getCoinId() == ordinaryCoinId) {
                        marketName = coinListBean.getCoinName();
                    }
                }
            }

            BigDecimal banlanceScale = banlance.setScale(8, BigDecimal.ROUND_DOWN);
            String banlanceStr = banlanceScale.toPlainString() + marketName;
            tv_balance.setText(banlanceStr);

            isRandomSuccess = false;
            isOrDinarySuccess = false;
        }
    }

    /**
     * 查询是否有余额
     *
     * @param redPacketIndexBean
     */
    public boolean getUserCoinListBeanBoolean(RedPacketIndexBean redPacketIndexBean, int id) {
        for (RedPacketIndexBean.DataBean.CoinListBean coinListBean : redPacketIndexBean.getData().getCoinList()) {
            for (RedPacketIndexBean.DataBean.UserCoinListBean userCoinListBeanList : redPacketIndexBean.getData().getUserCoinList()) {
                if (userCoinListBeanList.getCoinId() == id) {
                    this.userCoinListBean = userCoinListBeanList;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 初始化数据。 view显示
     *
     * @param redPacketIndexBean
     * @param isSwitchMarket     是否切换币种
     */
    public void setViewTextInitialize(RedPacketIndexBean redPacketIndexBean, boolean isSwitchMarket) {
        if (redPacketIndexBean.getData().getUserCoinList() == null
                || redPacketIndexBean.getData().getCoinList() == null
                || redPacketIndexBean.getData().getCoinList().size() <= 0) {
            tv_market.setText("选择币种");
            tv_balance.setText("0");
            return;
        }
        String marketName = "";

        if (redPacketType == 2) {
            marketName = redPacketIndexBean.getData().getCoinList().get(randomIndexPosition).getCoinName();
            tv_market.setText(marketName);
            randomCoinId = redPacketIndexBean.getData().getCoinList().get(randomIndexPosition).getCoinId();

            if (!getUserCoinListBeanBoolean(redPacketIndexBean, randomCoinId)) {
                userCoinListBean = new RedPacketIndexBean.DataBean.UserCoinListBean();
                userCoinListBean.setBanlance(new BigDecimal(0));
                userCoinListBean.setCoinId(randomCoinId);
            }
            userCoinListBean.setCnyPrice(redPacketIndexBean.getData().getCoinList().get(randomIndexPosition).getCnyPrice());
        } else {
            marketName = redPacketIndexBean.getData().getCoinList().get(ordinaryIndexPosition).getCoinName();
            tv_market.setText(marketName);
            ordinaryCoinId = redPacketIndexBean.getData().getCoinList().get(ordinaryIndexPosition).getCoinId();
            if (!getUserCoinListBeanBoolean(redPacketIndexBean, ordinaryCoinId)) {
                userCoinListBean = new RedPacketIndexBean.DataBean.UserCoinListBean();
                userCoinListBean.setBanlance(new BigDecimal(0));
                userCoinListBean.setCoinId(randomCoinId);
            }
            userCoinListBean.setCnyPrice(redPacketIndexBean.getData().getCoinList().get(ordinaryIndexPosition).getCnyPrice());
        }

        BigDecimal banlance = userCoinListBean.getBanlance();
        BigDecimal banlanceScale = banlance.setScale(8, BigDecimal.ROUND_DOWN);
        String banlanceStr = banlanceScale.toPlainString() + marketName;
        tv_balance.setText(banlanceStr);
        tv_sum_market.setText("0" + marketName);

        if (isSwitchMarket) {
            et_market_count.setText("");
            et_red_count.setText("");
        } else {
            String marketCountStr = et_market_count.getText().toString();
            String redCountStr = et_red_count.getText().toString();
            if (redPacketType == 1) {
                //切换到普通红包处理
                if (TextUtils.isEmpty(marketCountStr)) {
                    et_market_count.setText(marketCountStr);
                } else {
                    if (TextUtils.isEmpty(redCountStr)) {
                        et_market_count.setText(AccountValidatorUtil.subZeroAndDot(marketCountStr));
                    } else {
                        BigDecimal marketCountBD = new BigDecimal(marketCountStr);
                        BigDecimal redCountBD = new BigDecimal(redCountStr);
                        if (redCountBD.compareTo(BigDecimal.ZERO) == 1) {
                            BigDecimal multiply = marketCountBD.divide(redCountBD, 8, BigDecimal.ROUND_DOWN);//除法
                            et_market_count.setText(AccountValidatorUtil.subZeroAndDot(multiply.setScale(8, BigDecimal.ROUND_DOWN).toPlainString()));
                        } else {
                            et_market_count.setText(AccountValidatorUtil.subZeroAndDot(marketCountStr));
                        }
                    }
                }
            } else if (redPacketType == 2) {
                //切换到随机红包处理
                if (TextUtils.isEmpty(marketCountStr)) {
                    et_market_count.setText(AccountValidatorUtil.subZeroAndDot(marketCountStr));
                } else {
                    if (TextUtils.isEmpty(redCountStr)) {
                        et_market_count.setText(marketCountStr);
                    } else {
                        BigDecimal marketCountBD = new BigDecimal(marketCountStr);
                        BigDecimal redCountBD = new BigDecimal(redCountStr);
                        if (redCountBD.compareTo(BigDecimal.ZERO) == 1) {
                            BigDecimal multiply = marketCountBD.multiply(redCountBD);//相乘
                            et_market_count.setText(AccountValidatorUtil.subZeroAndDot(multiply.setScale(8, BigDecimal.ROUND_DOWN).toPlainString()));
                        } else {
                            et_market_count.setText(AccountValidatorUtil.subZeroAndDot(marketCountStr));
                        }
                    }
                }
            }
            et_red_count.setText(redCountStr);
        }

    }
}
