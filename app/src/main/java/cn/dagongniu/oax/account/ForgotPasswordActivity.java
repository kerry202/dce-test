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
 * 忘记密码和找回密码
 */
public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener, SendSmsView, ICaptchaView, CheckEmailPhoneView {

    private static final String TAG = "ForgotPasswordActivity";

    @BindView(R.id.forgot_close)
    ImageView forgotClose;
    @BindView(R.id.forgot_title)
    TextView forgotTitle;
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
    @BindView(R.id.et_cede)
    EditText etCede;
    @BindView(R.id.tv_cede)
    TextView tvCode;
    @BindView(R.id.rl_get_code)
    RelativeLayout rlGetCode;
    @BindView(R.id.rl_forgot)
    RelativeLayout rlForgot;
    @BindView(R.id.tv_get_forgot)
    TextView tvGetForgot;
    @BindView(R.id.ll_get_forgot)
    LinearLayout llGetForgot;

    String goLoginType = null;
    String LoginType = AppConstants.LOGINPHONE;
    Intent intent;
    private String dfShortName = "CN";//默认中国
    private PhoneNumberUtil utilPhoneNumber = null;
    Bundle bundle = new Bundle();
    String Validate = "";

    SendEamilCodePresenter sendEamilCodePresenter;
    SendSmsPresenter sendSmsPresenter;
    CheckSmsPresenter checkSmsPresenter;
    CheckEmailSmsPresenter checkEmailSmsPresenter;
    CaptchaPresenter captchaPresenter;
    CheckPhoneAndEmailPresenter checkPhoneAndEmailPresenter;

    OaxCaptcha mOaxCaptcha = null;
    private CaptchaTask mLoginTask = null;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void initView() {
        super.initView();
        rlForgot.setEnabled(false);
        initEvent();
        intent = getIntent();
        LoginType = intent.getStringExtra(AppConstants.LOGINTYPE);
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
     * edt 改变事件
     */
    private void initEvent() {
        utilPhoneNumber = PhoneNumberUtil.createInstance(getApplicationContext());
        etEmailPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    rlX.setVisibility(View.GONE);
                    etCede.setText("");
                } else {
                    rlX.setVisibility(View.VISIBLE);
                }
                String cede = etCede.getText().toString();

                if (LoginType.equals(AppConstants.LOGINEAMIL)) {
                    if (AccountValidatorUtil.isEmail(etEmailPhone.getText().toString()) && cede.length() == 6) {
                        rlForgot.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                        rlForgot.setEnabled(true);
                    } else {
                        rlForgot.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                        rlForgot.setEnabled(false);
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
                        rlForgot.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                        rlForgot.setEnabled(true);
                    } else {
                        rlForgot.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                        rlForgot.setEnabled(false);
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

        etCede.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (LoginType.equals(AppConstants.LOGINEAMIL)) {
                    if (AccountValidatorUtil.isEmail(etEmailPhone.getText().toString()) && s.length() == 6) {
                        rlForgot.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                        rlForgot.setEnabled(true);
                    } else {
                        rlForgot.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                        rlForgot.setEnabled(false);
                    }
                } else if (LoginType.equals(AppConstants.LOGINPHONE)) {
                    if (isPhoneNumber() && s.length() == 6) {
                        rlForgot.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                        rlForgot.setEnabled(true);
                    } else {
                        rlForgot.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                        rlForgot.setEnabled(false);
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
            Phonenumber.PhoneNumber phoneNumber = utilPhoneNumber.parse(etEmailPhone.getText().toString(), dfShortName);
            validNumber = utilPhoneNumber.isValidNumber(phoneNumber);
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        return validNumber;
    }

    /**
     * 初始化页面
     */
    private void initLayout() {
        if (LoginType.equals(AppConstants.LOGINEAMIL)) {
            initializeData(R.string.forgot_email_show,
                    getResources().getDrawable(R.drawable.email), true, R.string.login_email_hint,
                    R.string.forgot_phone_show, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            goLoginType = AppConstants.LOGINPHONE;
        } else if (LoginType.equals(AppConstants.LOGINPHONE)) {
            initializeData(R.string.forgot_phone_show,
                    getResources().getDrawable(R.drawable.phone), false, R.string.login_phone_hint,
                    R.string.forgot_email_show, InputType.TYPE_CLASS_PHONE);
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
        forgotTitle.setText(titleName);
        imEmailPhoneIcon.setImageDrawable(emailPhoneIcon);
        if (isRlInternationalization)
            rlInternationalization.setVisibility(View.GONE);
        else
            rlInternationalization.setVisibility(View.VISIBLE);
        etEmailPhone.setHint(hintName);
        tvGetForgot.setText(goLogin);
        etEmailPhone.setInputType(textType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.forgot_close, R.id.rl_x, R.id.ll_get_forgot, R.id.rl_get_code, R.id.rl_forgot, R.id.rl_internationalization})
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.forgot_close:
                finish();
                break;
            case R.id.rl_x:
                etEmailPhone.setText("");
                rlX.setVisibility(View.GONE);
                break;
            case R.id.ll_get_forgot:
                finish();
                bundle.putString(AppConstants.LOGINTYPE, goLoginType);
                openActivity(ForgotPasswordActivity.class, bundle);
                break;
            case R.id.rl_get_code://获取验证码
                if (LoginType.equals(AppConstants.LOGINEAMIL)) {
                    checkPhoneAndEmailPresenter.getCheckEmailModule();
                } else if (LoginType.equals(AppConstants.LOGINPHONE)) {
                    checkPhoneAndEmailPresenter.getCheckPhoneModule();
                }
                break;
            case R.id.rl_forgot://下一步
                toNext();
                break;
            case R.id.rl_internationalization://国际区号
                Intent intentCC = new Intent(this, ChooseCountriesActivity.class);
                startActivityForResult(intentCC, 1);
                break;
        }
    }

    /**
     * 下一步
     */
    private void toNext() {

        if (LoginType.equals(AppConstants.LOGINEAMIL)) {
            //效验邮箱验证码
            checkEmailSmsPresenter.getCheckEmailSmsModule();
        } else if (LoginType.equals(AppConstants.LOGINPHONE)) {
            //效验手机验证码
            checkSmsPresenter.getCheckSmsModule();
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
                    //ToastUtil.ShowToast(ForgotPasswordActivity.this.getResources().getString(R.string.captcha_successful));
                    Logger.e(TAG, "验证成功：result = " + result + ", validate = " + validate + ", message = " + message);
                } else {
                    ToastUtil.ShowToast(ForgotPasswordActivity.this.getResources().getString(R.string.captcha_failure));
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
        return etCede.getText().toString();
    }

    /**
     * 手机短信验证码检查回调
     *
     * @param data
     */
    @Override
    public void setCheckPhoneSms(HttpBaseBean data) {
        if (data.isSuccess()) {
            bundle.putString(AppConstants.LOGINTYPE, AppConstants.LOGINPHONE);
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
            bundle.putString(AppConstants.LOGINTYPE, AppConstants.LOGINEAMIL);
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
     * 检查手机邮箱是否存在方法 ----------------------
     */

    @Override
    public void setCheckEmailSuccess(HttpBaseBean data) {
        if (!data.isSuccess()) {
            startCaptcha();
        } else {
            DialogUtils.showDialog(this, R.drawable.errer_icon, getContext().getResources().getString(R.string.show_hint_email_thereisno));
        }
    }

    @Override
    public void setCheckEmailFailure(String data) {
        DialogUtils.showDialog(this, R.drawable.errer_icon, data);
    }

    @Override
    public void setCheckPhoneSuccess(HttpBaseBean data) {
        if (!data.isSuccess()) {
            startCaptcha();
        } else {
            DialogUtils.showDialog(this, R.drawable.errer_icon, getContext().getResources().getString(R.string.show_hint_phone_thereisno));
        }
    }

    @Override
    public void setCheckPhoneFailure(String data) {
        DialogUtils.showDialog(this, R.drawable.errer_icon, data);
    }

}
