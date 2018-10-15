package cn.dagongniu.oax.account;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.presenter.CheckEmailSmsPresenter;
import cn.dagongniu.oax.account.presenter.CheckPhoneAndEmailPresenter;
import cn.dagongniu.oax.account.presenter.CheckSmsPresenter;
import cn.dagongniu.oax.account.presenter.SendEamilCodePresenter;
import cn.dagongniu.oax.account.presenter.SendSmsPresenter;
import cn.dagongniu.oax.account.view.CheckEmailPhoneView;
import cn.dagongniu.oax.account.view.SendSmsView;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.captcha.Captcha;
import cn.dagongniu.oax.captcha.CaptchaListener;
import cn.dagongniu.oax.captcha.OaxCaptcha;
import cn.dagongniu.oax.captcha.CaptchaPresenter;
import cn.dagongniu.oax.captcha.CaptchaTask;
import cn.dagongniu.oax.captcha.ICaptchaView;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.AccountValidatorUtil;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.DialogUtils;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.PhoneCodeUtils;
import cn.dagongniu.oax.utils.ToastUtil;
import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

/**
 * 注册
 */
public class OaxRegisteredActivity extends BaseActivity implements SendSmsView, ICaptchaView, CheckEmailPhoneView {

    private static final String TAG = "OaxRegisteredActivity";

    @BindView(R.id.registered_close)
    ImageView registeredClose;
    @BindView(R.id.registered_title)
    TextView registeredTitle;
    @BindView(R.id.im_email_phone_icon)
    ImageView imEmailPhoneIcon;
    @BindView(R.id.tv_internationalization)
    TextView tvInternationalization;
    @BindView(R.id.rl_internationalization)
    RelativeLayout rlInternationalization;
    @BindView(R.id.et_email_phone)
    EditText etEmailPhone;
    @BindView(R.id.im_x)
    ImageView imX;
    @BindView(R.id.rl_x)
    RelativeLayout rlX;
    @BindView(R.id.im_icon_pwd)
    ImageView imIconPwd;
    @BindView(R.id.rl_pwd)
    RelativeLayout rlPwd;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.rl_get_code)
    RelativeLayout rlGetCode;
    @BindView(R.id.rl_registered)
    RelativeLayout rlRegistered;
    @BindView(R.id.tv_registered)
    TextView tvRegistered;
    @BindView(R.id.tv_get_login)
    TextView tvGetLogin;
    @BindView(R.id.ll_get_login)
    LinearLayout llGetLogin;
    @BindView(R.id.rl_agreement)
    RelativeLayout rlAgreement;
    @BindView(R.id.im_agreement)
    ImageView imAgreement;
    @BindView(R.id.tv_cede)
    TextView tvCode;
    @BindView(R.id.tv_agreement)
            TextView tvAgreement;

    Bundle bundle = new Bundle();
    String LoginType = AppConstants.LOGINPHONE;//默认手机登陆
    String goLoginType = null;
    boolean isAgreement = true;
    Intent intent;
    boolean isLogin = false;//是否登录过来
    String Validate = "";

    CheckPhoneAndEmailPresenter checkPhoneAndEmailPresenter;
    SendEamilCodePresenter sendEamilCodePresenter;
    SendSmsPresenter sendSmsPresenter;
    CheckSmsPresenter checkSmsPresenter;
    CheckEmailSmsPresenter checkEmailSmsPresenter;
    CaptchaPresenter captchaPresenter;

    private PhoneNumberUtil utilPhoneNumber = null;
    private String dfShortName = "CN";//默认中国

    OaxCaptcha mOaxCaptcha = null;
    private CaptchaTask mLoginTask = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oax_registered;
    }

    @Override
    protected void initView() {
        super.initView();
        rlRegistered.setEnabled(false);
        initEvent();
        intent = getIntent();
        LoginType = intent.getStringExtra(AppConstants.LOGINTYPE);
        isLogin = intent.getBooleanExtra(AppConstants.LOGINTYPE_ISLOGIN, false);
        initLayout();
    }

    @Override
    protected void initData() {
        super.initData();
        sendEamilCodePresenter = new SendEamilCodePresenter(this, RequestState.STATE_REFRESH);
        sendSmsPresenter = new SendSmsPresenter(this, RequestState.STATE_REFRESH);
        checkSmsPresenter = new CheckSmsPresenter(this, RequestState.STATE_DIALOG);
        checkEmailSmsPresenter = new CheckEmailSmsPresenter(this, RequestState.STATE_DIALOG);
        captchaPresenter = new CaptchaPresenter(this, RequestState.STATE_DIALOG);
        checkPhoneAndEmailPresenter = new CheckPhoneAndEmailPresenter(this, RequestState.STATE_DIALOG);
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
            Phonenumber.PhoneNumber phoneNumber = utilPhoneNumber.parse(etEmailPhone.getText().toString(), dfShortName);
            validNumber = utilPhoneNumber.isValidNumber(phoneNumber);
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        return validNumber;
    }

    /**
     * edt 改变事件
     */
    private void initEvent() {
        utilPhoneNumber = PhoneNumberUtil.createInstance(getApplicationContext());
        etEmailPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    rlX.setVisibility(View.GONE);
                    etPwd.setText("");
                } else {
                    rlX.setVisibility(View.VISIBLE);
                }
                String cede = etPwd.getText().toString();

                if (LoginType.equals(AppConstants.LOGINEAMIL)) {
                    if (AccountValidatorUtil.isEmail(etEmailPhone.getText().toString()) && cede.length() == 6) {
                        rlRegistered.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                        rlRegistered.setEnabled(true);
                    } else {
                        rlRegistered.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                        rlRegistered.setEnabled(false);
                    }

                    if (AccountValidatorUtil.isEmail(etEmailPhone.getText().toString())) {
                        rlGetCode.setEnabled(true);
                        rlGetCode.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                    } else {
                        rlGetCode.setEnabled(false);
                        rlGetCode.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                    }

                } else if (LoginType.equals(AppConstants.LOGINPHONE)) {
                    if (isPhoneNumber() && cede.length() == 6) {
                        rlRegistered.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                        rlRegistered.setEnabled(true);
                    } else {
                        rlRegistered.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                        rlRegistered.setEnabled(false);
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

        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (LoginType.equals(AppConstants.LOGINEAMIL)) {
                    if (AccountValidatorUtil.isEmail(etEmailPhone.getText().toString()) && s.length() == 6) {
                        rlRegistered.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                        rlRegistered.setEnabled(true);
                    } else {
                        rlRegistered.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                        rlRegistered.setEnabled(false);
                    }
                } else if (LoginType.equals(AppConstants.LOGINPHONE)) {
                    if (isPhoneNumber() && s.length() == 6) {
                        rlRegistered.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                        rlRegistered.setEnabled(true);
                    } else {
                        rlRegistered.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                        rlRegistered.setEnabled(false);
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
     * 初始化页面
     */
    private void initLayout() {
        if (LoginType.equals(AppConstants.LOGINEAMIL)) {
            initializeData(R.string.registered_email,
                    getResources().getDrawable(R.drawable.email), true, R.string.login_email_hint,
                    R.string.use_registered_phone_hint, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            goLoginType = AppConstants.LOGINPHONE;
        } else if (LoginType.equals(AppConstants.LOGINPHONE)) {
            initializeData(R.string.registered_phone,
                    getResources().getDrawable(R.drawable.phone), false, R.string.login_phone_hint,
                    R.string.use_registered_email_hint, InputType.TYPE_CLASS_NUMBER);
            goLoginType = AppConstants.LOGINEAMIL;
        }
    }

    /**
     * 初始化页面
     *
     * @param titleName                title名字
     * @param emailPhoneIcon           账户图标
     * @param isRlInternationalization 是否隐藏国际化 +86
     * @param hintName                 账户hint提示
     * @param goLogin                  使用***登陆
     * @param textType                 账户输入类型
     */
    private void initializeData(int titleName, Drawable emailPhoneIcon,
                                boolean isRlInternationalization,
                                int hintName, int goLogin, int textType) {
        registeredTitle.setText(titleName);
        imEmailPhoneIcon.setImageDrawable(emailPhoneIcon);
        if (isRlInternationalization)
            rlInternationalization.setVisibility(View.GONE);
        else
            rlInternationalization.setVisibility(View.VISIBLE);
        etEmailPhone.setHint(hintName);
        tvGetLogin.setText(goLogin);
        etEmailPhone.setInputType(textType);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.registered_close, R.id.rl_x, R.id.rl_agreement,
            R.id.ll_get_login, R.id.rl_get_code, R.id.rl_registered, R.id.tv_registered, R.id.rl_internationalization,R.id.tv_agreement})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registered_close:
                finish();
                break;
            case R.id.rl_x://删除
                etEmailPhone.setText("");
                rlX.setVisibility(View.GONE);
                break;
            case R.id.rl_agreement:
                if (isAgreement) {
                    isAgreement = false;
                    imAgreement.setImageDrawable(getResources().getDrawable(R.drawable.agreement_yes));
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    isAgreement = true;
                    imAgreement.setImageDrawable(getResources().getDrawable(R.drawable.agreement_no));
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.ll_get_login:
                finish();
                bundle.putString(AppConstants.LOGINTYPE, goLoginType);
                openActivity(OaxRegisteredActivity.class, bundle);
                break;
            case R.id.rl_get_code://获取验证码
                if (LoginType.equals(AppConstants.LOGINEAMIL)) {
                    checkPhoneAndEmailPresenter.getCheckEmailModule();
                } else if (LoginType.equals(AppConstants.LOGINPHONE)) {
                    checkPhoneAndEmailPresenter.getCheckPhoneModule();
                }
                break;
            case R.id.rl_registered:
                toNext();
                break;
            case R.id.tv_registered://去登陆
                if (isLogin) {
                    finish();
                } else {
                    bundle.putString(AppConstants.LOGINTYPE, LoginType);
                    openActivity(LoginActivity.class, bundle);
                }
                break;
            case R.id.rl_internationalization://国际区号
                Intent intentCC = new Intent(this, ChooseCountriesActivity.class);
                startActivityForResult(intentCC, 1);
                break;
            case R.id.tv_agreement:
                openActivity(AgreementActivity.class);
                break;
        }
    }

    /**
     * 下一步
     */
    private void toNext() {

        if (!isAgreement) {
            if (LoginType.equals(AppConstants.LOGINEAMIL)) {
                //效验邮箱验证码
                checkEmailSmsPresenter.getCheckEmailSmsModule();
            } else if (LoginType.equals(AppConstants.LOGINPHONE)) {
                //效验手机验证码
                checkSmsPresenter.getCheckSmsModule();
            }
        } else {
            ToastUtil.ShowToast(this.getResources().getString(R.string.lregistered_reading));
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
                etEmailPhone.setText("");
                tvInternationalization.setText(info);
                break;
            default:
                break;
        }
    }

    /**
     * 启动滑块
     */
    public void startCaptcha() {

        mOaxCaptcha = new OaxCaptcha(this, new CaptchaListener() {
            @Override
            public void onReady(boolean ret) {
                //该为调试接口，ret为true表示加载Sdk完成
                if (ret) {
                    Logger.e(TAG, "唤醒网易滑块成功");

                }
            }

            @Override
            public void closeWindow() {
                Logger.e(TAG, "关闭网易云滑块");
            }

            @Override
            public void onError(String errormsg) {
                Logger.e(TAG, "错误信息：" + errormsg);
            }

            @Override
            public void onValidate(String result, String validate, String message) {
                //验证结果，valiadte，可以根据返回的三个值进行用户自定义二次验证
                if (validate.length() > 0) {
                    Validate = validate;
                    captchaPresenter.getCaptchaModule();
                    //ToastUtil.ShowToast(OaxRegisteredActivity.this.getResources().getString(R.string.captcha_successful));
                    Logger.e(TAG, "验证成功：result = " + result + ", validate = " + validate + ", message = " + message);
                } else {

                    ToastUtil.ShowToast(OaxRegisteredActivity.this.getResources().getString(R.string.captcha_failure));
                    Logger.e(TAG, "验证失败：result = " + result + ", validate = " + validate + ", message = " + message);
                }
            }

            @Override
            public void onCancel() {
                Logger.e(TAG, "取消滑块线程");
                //用户取消加载或者用户取消验证，关闭异步任务，也可根据情况在其他地方添加关闭异步任务接口
                if (mLoginTask != null) {
                    if (mLoginTask.getStatus() == AsyncTask.Status.RUNNING) {
                        Log.i(TAG, "stop mLoginTask");
                        mLoginTask.cancel(true);
                    }
                }
            }
        });
        Captcha captcha = mOaxCaptcha.getmCaptcha();
        mLoginTask = new CaptchaTask(this, captcha, new CaptchaTask.CaptchaTaskBack() {
            @Override
            public void onCaptchaTashBackCancelled() {
                mLoginTask = null;
            }
        });
        mOaxCaptcha.Start(mLoginTask);
    }

    @Override
    public String getChooseCountries() {
        return tvInternationalization.getText().toString();
    }

    @Override
    public String getPhone() {
        return etEmailPhone.getText().toString();
    }

    @Override
    public String getEamil() {
        return etEmailPhone.getText().toString();
    }

    @Override
    public String getPhoneAndEamil() {
        return etEmailPhone.getText().toString();
    }

    @Override
    public String getType() {
        return AppConstants.SMS_REGISTERED;
    }

    @Override
    public String getCode() {
        return etPwd.getText().toString();
    }

    /**
     * 手机短信验证码检查回调
     *
     * @param data
     */
    @Override
    public void setCheckPhoneSms(HttpBaseBean data) {
        if (data.isSuccess()) {
            bundle.putString(AppConstants.LOGINTYPE, AppConstants.LOGINSET);
            bundle.putString(AppConstants.REGISTERED_USERNAME, tvInternationalization.getText().toString() + etEmailPhone.getText().toString());
            if (LoginType.equals(AppConstants.LOGINEAMIL)) {
                bundle.putString(AppConstants.REGISTERED_TYPE, "2");
            } else if (LoginType.equals(AppConstants.LOGINPHONE)) {
                bundle.putString(AppConstants.REGISTERED_TYPE, "1");
            }
            openActivity(SetPasswordActivity.class, bundle);
        } else {
            DialogUtils.showDialog(this, R.drawable.errer_icon, data.getMsg());
        }
    }

    /**
     * 邮箱验证码检查回调
     *
     * @param data
     */
    @Override
    public void setCheckEmailSms(HttpBaseBean data) {
        if (data.isSuccess()) {
            bundle.putString(AppConstants.LOGINTYPE, AppConstants.LOGINSET);
            bundle.putString(AppConstants.REGISTERED_USERNAME, etEmailPhone.getText().toString());
            if (LoginType.equals(AppConstants.LOGINEAMIL)) {
                bundle.putString(AppConstants.REGISTERED_TYPE, "2");
            } else if (LoginType.equals(AppConstants.LOGINPHONE)) {
                bundle.putString(AppConstants.REGISTERED_TYPE, "1");
            }
            openActivity(SetPasswordActivity.class, bundle);
        } else {
            DialogUtils.showDialog(this, R.drawable.errer_icon, data.getMsg());
        }
    }

    /**
     * 获取验证码
     */
    public void getCodeSms() {
        if (LoginType.equals(AppConstants.LOGINEAMIL)) {
            if (!etEmailPhone.getText().toString().equals("")) {
                if (AccountValidatorUtil.isEmail(etEmailPhone.getText().toString())) {
                    PhoneCodeUtils.getInstance().setDownTime(60, rlGetCode, tvCode);
                    sendEamilCodePresenter.getSendEamilCodeModule();
                } else {
                    ToastUtil.ShowToast(this.getResources().getString(R.string.registered_eamil_errer));
                }
            } else {
                ToastUtil.ShowToast(this.getResources().getString(R.string.login_email_hint));
            }

        } else if (LoginType.equals(AppConstants.LOGINPHONE)) {
            if (!etEmailPhone.getText().toString().equals("")) {
                if (isPhoneNumber()) {
                    PhoneCodeUtils.getInstance().setDownTime(60, rlGetCode, tvCode);
                    sendSmsPresenter.getSendSmsModule();
                } else {
                    ToastUtil.ShowToast(this.getResources().getString(R.string.registered_phone_errer));
                }
            } else {
                ToastUtil.ShowToast(this.getResources().getString(R.string.login_phone_hint));
            }
        }
    }

    @Override
    public String getValidate() {
        return Validate;
    }

    @Override
    public void setfailure(String data) {
        ToastUtil.ShowToast(data);
    }

    @Override
    public void setSuccess(String data) {
        getCodeSms();
    }


    /**
     * ----------------- 效验是否存在
     *
     * @param data
     */
    @Override
    public void setCheckEmailSuccess(HttpBaseBean data) {
        if (data.isSuccess()) {
            startCaptcha();
        } else {
            DialogUtils.showDialog(this, R.drawable.errer_icon, data.getMsg());
        }

    }

    @Override
    public void setCheckEmailFailure(String data) {
        DialogUtils.showDialog(this, R.drawable.errer_icon, data);
    }

    @Override
    public void setCheckPhoneSuccess(HttpBaseBean data) {
        if (data.isSuccess()) {
            startCaptcha();
        } else {
            DialogUtils.showDialog(this, R.drawable.errer_icon, data.getMsg());
        }
    }

    @Override
    public void setCheckPhoneFailure(String data) {
        DialogUtils.showDialog(this, R.drawable.errer_icon, data);
    }
}
