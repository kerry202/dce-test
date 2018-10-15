package cn.dagongniu.oax.account;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.presenter.EamilRegisteredPresenter;
import cn.dagongniu.oax.account.presenter.ForgetPasswordPresenter;
import cn.dagongniu.oax.account.presenter.RegisteredPresenter;
import cn.dagongniu.oax.account.view.IRegisteredView;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.AccountValidatorUtil;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.AppManager;
import cn.dagongniu.oax.utils.DialogUtils;
import cn.dagongniu.oax.utils.ToastUtil;

/**
 * 设置密码 重置密码
 */
public class SetPasswordActivity extends BaseActivity implements IRegisteredView {

    @BindView(R.id.setpwd_close)
    ImageView setpwdClose;
    @BindView(R.id.setpwd_title)
    TextView setpwdTitle;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.im_open_pwd)
    ImageView imOpenPwd;
    @BindView(R.id.rl_open_pwd)
    RelativeLayout rlOpenPwd;
    @BindView(R.id.et_pwd_confirm)
    EditText etPwdConfirm;
    @BindView(R.id.rl_commit)
    RelativeLayout rlCommit;
    @BindView(R.id.tv_set_commit)
    TextView setCommit;

    Bundle bundle = new Bundle();
    String LoginType = AppConstants.LOGINPHONE;//默认手机登陆
    String userName = "";
    String registeredType = "";
    Intent intent;
    boolean isAgreement = true;

    private ForgetPasswordPresenter forgetPasswordPresenter;
    private RegisteredPresenter registeredPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_password;
    }

    @Override
    protected void initView() {
        super.initView();
        rlCommit.setEnabled(false);
        initEvent();
        intent = getIntent();
        LoginType = intent.getStringExtra(AppConstants.LOGINTYPE);
        initLayout();
    }

    @Override
    protected void initData() {
        super.initData();
        registeredPresenter = new RegisteredPresenter(this, RequestState.STATE_DIALOG);
        forgetPasswordPresenter = new ForgetPasswordPresenter(this, RequestState.STATE_DIALOG);
    }

    /**
     * 改变事件
     */
    private void initEvent() {
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (AccountValidatorUtil.isPassword(etPwdConfirm.getText().toString())
                        && AccountValidatorUtil.isPassword(s.toString())
                        && etPwdConfirm.getText().toString().equals(s.toString())) {
                    rlCommit.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                    rlCommit.setEnabled(true);
                } else {
                    rlCommit.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                    rlCommit.setEnabled(false);
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

        //失去焦点事件
        etPwd.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    if (!AccountValidatorUtil.isPassword(etPwd.getText().toString())) {
                        ToastUtil.ShowToast(SetPasswordActivity.this.getResources().getString(R.string.registered_pasd_errer));
                    }
                }
            }
        });

        etPwdConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (AccountValidatorUtil.isPassword(etPwd.getText().toString())
                        && AccountValidatorUtil.isPassword(s.toString())
                        && etPwd.getText().toString().equals(s.toString())) {
                    rlCommit.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                    rlCommit.setEnabled(true);
                } else {
                    rlCommit.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                    rlCommit.setEnabled(false);
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
            initializeData(R.string.forgot_email_title, R.string.setpwd_commit);
            userName = intent.getStringExtra(AppConstants.REGISTERED_USERNAME);
            registeredType = intent.getStringExtra(AppConstants.REGISTERED_TYPE);
        } else if (LoginType.equals(AppConstants.LOGINPHONE)) {
            initializeData(R.string.forgot_phone_title, R.string.setpwd_commit);
            userName = intent.getStringExtra(AppConstants.REGISTERED_USERNAME);
            registeredType = intent.getStringExtra(AppConstants.REGISTERED_TYPE);
        } else if (LoginType.equals(AppConstants.LOGINSET)) {
            initializeData(R.string.setpwd_the_title, R.string.registered);
            userName = intent.getStringExtra(AppConstants.REGISTERED_USERNAME);
            registeredType = intent.getStringExtra(AppConstants.REGISTERED_TYPE);
        }
    }


    /**
     * 初始化页面
     *
     * @param titleName title名字
     */
    private void initializeData(int titleName, int textCommit) {
        setpwdTitle.setText(titleName);
        setCommit.setText(textCommit);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.setpwd_close, R.id.rl_open_pwd, R.id.rl_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setpwd_close:
                finish();
                break;
            case R.id.rl_open_pwd:
                if (isAgreement) {
                    isAgreement = false;
                    imOpenPwd.setImageDrawable(getResources().getDrawable(R.drawable.yan_yes));
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    etPwdConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    isAgreement = true;
                    imOpenPwd.setImageDrawable(getResources().getDrawable(R.drawable.yan_no));
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    etPwdConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.rl_commit:
                if (LoginType.equals(AppConstants.LOGINEAMIL)) {
                    forgetPasswordPresenter.getForgetPasswordModule();
                } else if (LoginType.equals(AppConstants.LOGINPHONE)) {
                    forgetPasswordPresenter.getForgetPasswordModule();
                } else if (LoginType.equals(AppConstants.LOGINSET)) {
                    registeredPresenter.getRegisteredModule();
                }
                break;
        }
    }

    @Override
    public String getEmailAndPhone() {
        return userName;
    }

    @Override
    public String getPassword() {
        return etPwd.getText().toString();
    }

    @Override
    public String getRepeatPassword() {
        return etPwdConfirm.getText().toString();
    }

    @Override
    public String getInvateCode() {
        return "";
    }

    @Override
    public String getRegisteredType() {
        return registeredType;
    }

    /**
     * 注册回调
     *
     * @param data
     */
    @Override
    public void isRegistered(HttpBaseBean data) {
        if (data.isSuccess()) {
            ToastUtil.ShowToast(this.getResources().getString(R.string.registered_success_to_login));
            if (registeredType.equals("1")) {//跳转手机登录
                bundle.putString(AppConstants.LOGINTYPE, AppConstants.LOGINPHONE);
                openActivity(LoginActivity.class, bundle);
            } else if (registeredType.equals("2")) {//跳转邮箱登录
                bundle.putString(AppConstants.LOGINTYPE, AppConstants.LOGINEAMIL);
                openActivity(LoginActivity.class, bundle);
            }
            AppManager.remove(OaxRegisteredActivity.class.getSimpleName());
            finish();
        } else {
            DialogUtils.showDialog(this, R.drawable.errer_icon, data.getMsg());
        }
    }

    /**
     * 修改回调
     *
     * @param data
     */
    @Override
    public void isForgetPassword(HttpBaseBean data) {
        if (data.isSuccess()) {
            ToastUtil.ShowToast(this.getResources().getString(R.string.update_passwrod_success_to_login));
            if (registeredType.equals("1")) {//跳转手机登录
                bundle.putString(AppConstants.LOGINTYPE, AppConstants.LOGINPHONE);
                openActivity(LoginActivity.class, bundle);
            } else if (registeredType.equals("2")) {//跳转邮箱登录
                bundle.putString(AppConstants.LOGINTYPE, AppConstants.LOGINEAMIL);
                openActivity(LoginActivity.class, bundle);
            }
            AppManager.remove(ForgotPasswordActivity.class.getSimpleName());
            AppManager.remove(OaxRegisteredActivity.class.getSimpleName());
            finish();
        } else {
            DialogUtils.showDialog(this, R.drawable.errer_icon, data.getMsg());
        }
    }
}
