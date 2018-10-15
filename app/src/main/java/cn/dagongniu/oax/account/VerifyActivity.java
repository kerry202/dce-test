package cn.dagongniu.oax.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.presenter.BindPhoneOrEmailPresenter;
import cn.dagongniu.oax.account.presenter.SendEamilCodePresenter;
import cn.dagongniu.oax.account.presenter.SendSmsPresenter;
import cn.dagongniu.oax.account.view.IBindPhoneEmailView;
import cn.dagongniu.oax.account.view.SendSmsView;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.customview.CommonToolbar;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.AccountValidatorUtil;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.DialogUtils;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.PhoneCodeUtils;
import cn.dagongniu.oax.utils.events.MyEvents;
import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

/**
 * 邮箱验证 手机验证
 */
public class VerifyActivity extends BaseActivity implements SendSmsView, IBindPhoneEmailView {

    private static final String TAG = "VerifyActivity";

    @BindView(R.id.commontoolbar)
    CommonToolbar commontoolbar;
    @BindView(R.id.phone_or_mail)
    EditText phoneOrMail;
    @BindView(R.id.verification_code)
    EditText verificationCode;
    @BindView(R.id.tv_remind)
    TextView tvRemind;
    @BindView(R.id.tv_bind)
    TextView tvBind;
    @BindView(R.id.rl_get_code)
    RelativeLayout rlGetCode;
    @BindView(R.id.tv_cede)
    TextView tvCode;
    @BindView(R.id.rl_internationalization)
    RelativeLayout rlInternationalization;
    @BindView(R.id.tv_internationalization)
    TextView tvInternationalization;
    private int mType;
    private String phoneOrMailText = "";
    private String verificationCodeText = "";
    private String dfShortName = "CN";//默认中国
    private PhoneNumberUtil utilPhoneNumber = null;

    SendEamilCodePresenter sendEamilCodePresenter;
    SendSmsPresenter sendSmsPresenter;
    BindPhoneOrEmailPresenter bindPhoneOrEmailPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify;
    }

    @Override
    protected void initView() {
        super.initView();
        tvBind.setEnabled(false);
        mType = getIntent().getIntExtra(Constant.VERIFY_TYPE, 0);
        initToolbar();
        initText();
        initEvent();
    }

    @Override
    protected void initData() {
        super.initData();
        sendEamilCodePresenter = new SendEamilCodePresenter(this, RequestState.STATE_REFRESH);
        sendSmsPresenter = new SendSmsPresenter(this, RequestState.STATE_REFRESH);
        bindPhoneOrEmailPresenter = new BindPhoneOrEmailPresenter(this, RequestState.STATE_DIALOG);
    }

    private void initText() {
        if (mType == Constant.VERIFY_TYPE_PHONE) {
            phoneOrMail.setHint(getResources().getString(R.string.please_input_phone));
            tvRemind.setText(getResources().getString(R.string.please_input_phone_remind));
            rlInternationalization.setVisibility(View.VISIBLE);
            phoneOrMail.setInputType(InputType.TYPE_CLASS_PHONE);
        } else if (mType == Constant.VERIFY_TYPE_MAIL) {
            phoneOrMail.setHint(getResources().getString(R.string.please_input_mail));
            tvRemind.setText(getResources().getString(R.string.please_input_mail_remind));
            rlInternationalization.setVisibility(View.GONE);
            phoneOrMail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
    }

    private void initToolbar() {
        if (mType == Constant.VERIFY_TYPE_PHONE) {
            commontoolbar.setTitleText(getResources().getString(R.string.phone_verify));
        } else if (mType == Constant.VERIFY_TYPE_MAIL) {
            commontoolbar.setTitleText(getResources().getString(R.string.mail_verify));
        }
        commontoolbar.setOnCommonToolbarLeftClickListener(new CommonToolbar.CommonToolbarLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    /**
     * edt 改变事件
     */
    private void initEvent() {
        utilPhoneNumber = PhoneNumberUtil.createInstance(getApplicationContext());
        phoneOrMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String cede = verificationCode.getText().toString();

                if (mType == Constant.VERIFY_TYPE_MAIL) {
                    if (AccountValidatorUtil.isEmail(phoneOrMail.getText().toString()) && cede.length() == 6) {
                        tvBind.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                        tvBind.setEnabled(true);
                    } else {
                        tvBind.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                        tvBind.setEnabled(false);
                    }

                    if (AccountValidatorUtil.isEmail(phoneOrMail.getText().toString())) {
                        rlGetCode.setEnabled(true);
                        rlGetCode.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                    } else {
                        rlGetCode.setEnabled(false);
                        rlGetCode.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                    }

                } else if (mType == Constant.VERIFY_TYPE_PHONE) {
                    if (isPhoneNumber() && cede.length() == 6) {
                        tvBind.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                        tvBind.setEnabled(true);
                    } else {
                        tvBind.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                        tvBind.setEnabled(false);
                    }

                    if (isPhoneNumber()) {
                        rlGetCode.setEnabled(true);
                        rlGetCode.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                    } else {
                        rlGetCode.setEnabled(false);
                        rlGetCode.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                    }
                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        verificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mType == Constant.VERIFY_TYPE_MAIL) {
                    if (AccountValidatorUtil.isEmail(phoneOrMail.getText().toString()) && s.length() == 6) {
                        tvBind.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                        tvBind.setEnabled(true);
                    } else {
                        tvBind.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                        tvBind.setEnabled(false);
                    }
                } else if (mType == Constant.VERIFY_TYPE_PHONE) {
                    if (isPhoneNumber() && s.length() == 6) {
                        tvBind.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                        tvBind.setEnabled(true);
                    } else {
                        tvBind.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                        tvBind.setEnabled(false);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    /**
     * 效验国际化手机
     *
     * @return
     */
    private boolean isPhoneNumber() {
        if (dfShortName.contains("+"))
            dfShortName = dfShortName.replace("+", "");
        boolean validNumber = false;
        try {
            Phonenumber.PhoneNumber phoneNumber = utilPhoneNumber.parse(phoneOrMail.getText().toString(), dfShortName);
            validNumber = utilPhoneNumber.isValidNumber(phoneNumber);
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        return validNumber;
    }


    @OnClick({R.id.tv_bind, R.id.rl_internationalization, R.id.rl_get_code})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bind:
                if (mType == Constant.VERIFY_TYPE_PHONE) {
                    bindPhoneOrEmailPresenter.getBindPhoneModule();
                } else if (mType == Constant.VERIFY_TYPE_MAIL) {
                    bindPhoneOrEmailPresenter.getBindEamilModule();
                }
                break;
            case R.id.rl_internationalization://国际区号
                Intent intentCC = new Intent(this, ChooseCountriesActivity.class);
                startActivityForResult(intentCC, 1);
                break;
            case R.id.rl_get_code://获取验证码
                getCodeSms();
        }
    }

    /**
     * 跳转回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                Bundle bundle = data.getExtras();
                String info = bundle.getString("numberType");
                dfShortName = bundle.getString("shortName");
                phoneOrMail.setText("");
                tvInternationalization.setText(info);
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     */
    public void getCodeSms() {
        if (mType == Constant.VERIFY_TYPE_MAIL) {
            PhoneCodeUtils.getInstance().setDownTime(60, rlGetCode, tvCode);
            sendEamilCodePresenter.getSendEamilCodeModule();
        } else if (mType == Constant.VERIFY_TYPE_PHONE) {
            PhoneCodeUtils.getInstance().setDownTime(60, rlGetCode, tvCode);
            sendSmsPresenter.getSendSmsModule();
        }
    }

    /**
     * 成功
     *
     * @param msg
     */
    @Override
    public void successful(String msg) {
        DialogUtils.showDialog(this, R.drawable.correct, msg);
        //发送登录成功通知
        myEvents.status = MyEvents.status_ok;
        myEvents.status_type = MyEvents.Bind_PhoneOrEmail_Success;
        myEvents.errmsg = this.getResources().getString(R.string.bind_phone_email_seuccss);
        eventBus.post(myEvents);
        Logger.e(TAG, "发送绑定号码或邮箱成功消息!");

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intentRegistered = new Intent();
                intentRegistered.putExtra("bindType", mType);
                VerifyActivity.this.setResult(300, intentRegistered);
                VerifyActivity.this.finish();
            }
        }, 1500);

    }

    /**
     * 失败
     *
     * @param msg
     */
    @Override
    public void failure(String msg) {
        DialogUtils.showDialog(this, R.drawable.errer_icon, msg);
    }

    /**
     * ------------------------------------- 发送验证码一系列操作以及view的数据获取
     *
     * @return
     */
    @Override
    public String getChooseCountries() {
        return tvInternationalization.getText().toString();
    }

    @Override
    public String getPhone() {
        return phoneOrMail.getText().toString();
    }

    @Override
    public String getEamil() {
        return phoneOrMail.getText().toString();
    }

    @Override
    public String getPhoneOrEmail() {
        return phoneOrMail.getText().toString();
    }

    @Override
    public String getPhoneAndEamil() {
        return phoneOrMail.getText().toString();
    }

    @Override
    public String getType() {
        return AppConstants.SMS_REGISTERED;
    }

    @Override
    public String getCode() {
        return verificationCode.getText().toString();
    }

    @Override
    public void setCheckPhoneSms(HttpBaseBean data) {
    }

    @Override
    public void setCheckEmailSms(HttpBaseBean data) {
    }
}
