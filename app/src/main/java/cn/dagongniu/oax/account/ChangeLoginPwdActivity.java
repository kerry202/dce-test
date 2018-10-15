package cn.dagongniu.oax.account;

import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.presenter.CheckLoginPasswordPresenter;
import cn.dagongniu.oax.account.presenter.SendEamilCodePresenter;
import cn.dagongniu.oax.account.presenter.SendSmsPresenter;
import cn.dagongniu.oax.account.presenter.UserUpdateLoginPwPresenter;
import cn.dagongniu.oax.account.view.ICheckChangePasswordView;
import cn.dagongniu.oax.account.view.IUserLoginLoginPwView;
import cn.dagongniu.oax.account.view.SendSmsView;
import cn.dagongniu.oax.assets.bean.QueryCheckTypeBean;
import cn.dagongniu.oax.assets.presenter.QueryCheckTypePresenter;
import cn.dagongniu.oax.assets.view.IQueryCheckTypeView;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.customview.CommonToolbar;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.AccountValidatorUtil;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.AppManager;
import cn.dagongniu.oax.utils.DialogUtils;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.MyEvents;

/**
 * 修改登陆密码
 */
public class ChangeLoginPwdActivity extends BaseActivity implements IQueryCheckTypeView, SendSmsView, IUserLoginLoginPwView, ICheckChangePasswordView {

    private static final String TAG = "ChangeLoginPwdActivity";

    @BindView(R.id.commontoolbar)
    CommonToolbar commontoolbar;
    @BindView(R.id.old_pwd)
    EditText oldPwd;
    @BindView(R.id.new_pwd)
    EditText newPwd;
    @BindView(R.id.confirmed_new_pwd)
    EditText confirmedNewPwd;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private Dialog validationDialog;
    String smsPhoneCode = null;
    String smsGoogleCode = null;
    String smsEamilCode = null;
    private String oldPwdText = "";
    private String newPwdText = "";
    private String confirmedPwdText = "";
    String accountEmail;
    String accountPhone;

    QueryCheckTypePresenter queryCheckTypePresenter;
    SendSmsPresenter sendSmsPresenter;
    SendEamilCodePresenter sendEamilCodePresenter;
    UserUpdateLoginPwPresenter userUpdateLoginPwPresenter;
    CheckLoginPasswordPresenter checkLoginPasswordPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_loginpwd;
    }

    @Override
    protected void initView() {
        super.initView();
        tvCommit.setEnabled(false);
        initToolbar();
        initOldPwdTextWatcher();
        initNewPwdTextWatcher();
        initConfirmedPwdTextWatcher();
    }

    @Override
    protected void initData() {
        super.initData();
        accountEmail = (String) SPUtils.getParam(this, SPConstant.USER_EMAIL, "");
        accountPhone = (String) SPUtils.getParam(this, SPConstant.USER_PHONE, "");
        queryCheckTypePresenter = new QueryCheckTypePresenter(this, RequestState.STATE_DIALOG);
        sendSmsPresenter = new SendSmsPresenter(this, RequestState.STATE_REFRESH);
        sendEamilCodePresenter = new SendEamilCodePresenter(this, RequestState.STATE_REFRESH);
        userUpdateLoginPwPresenter = new UserUpdateLoginPwPresenter(this, RequestState.STATE_DIALOG);
        checkLoginPasswordPresenter = new CheckLoginPasswordPresenter(this, RequestState.STATE_DIALOG);
    }

    private void initToolbar() {
        commontoolbar.setTitleText(getResources().getString(R.string.change_pwd));
        commontoolbar.setOnCommonToolbarLeftClickListener(new CommonToolbar.CommonToolbarLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }


    private void initOldPwdTextWatcher() {
        oldPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeCommitState();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initNewPwdTextWatcher() {
        newPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeCommitState();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //失去焦点事件
        newPwd.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    if (!AccountValidatorUtil.isPassword(newPwd.getText().toString())) {
                        ToastUtil.ShowToast(ChangeLoginPwdActivity.this.getResources().getString(R.string.registered_pasd_errer));
                    }
                }
            }
        });
    }

    private void initConfirmedPwdTextWatcher() {
        confirmedNewPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeCommitState();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 效验合法
     */
    private void changeCommitState() {
        if (!TextUtils.isEmpty(oldPwd.getText().toString()) &&
                AccountValidatorUtil.isPassword(newPwd.getText().toString()) &&
                newPwd.getText().toString().equals(confirmedNewPwd.getText().toString())) {
            tvCommit.setEnabled(true);
            tvCommit.setBackgroundColor(getResources().getColor(R.color.df_F5BE23));
        } else {
            tvCommit.setEnabled(false);
            tvCommit.setBackgroundColor(getResources().getColor(R.color.df_cccccc));
        }
    }


    @OnClick(R.id.tv_commit)
    public void onClicked() {
        checkLoginPasswordPresenter.getCheckChangePasswordModule();
    }

    /**
     * 效验账户密码匹配成功
     */
    @Override
    public void isSuccess() {
        queryCheckTypePresenter.getQueryCheckTypeModule();
    }

    /**
     * 效验错误
     *
     * @param msg
     */
    @Override
    public void setShowCheckPasswordErrer(String msg) {

        DialogUtils.showDialog(this, R.drawable.errer_icon, msg);
    }

    /**
     * 密码
     *
     * @return
     */
    @Override
    public String getPassword() {
        return oldPwd.getText().toString();
    }

    /**
     * 获取账户名
     *
     * @return
     */
    @Override
    public String getUsername() {
        String Username = (String) SPUtils.getParam(this, SPConstant.USER_ACCOUNT, "");
        return Username;
    }

    /**
     * 查询成功回调
     *
     * @param queryCheckTypeData
     */
    @Override
    public void setQueryCheckTypeData(QueryCheckTypeBean queryCheckTypeData) {
        boolean isEmail = true;
        //1 需要 0不需要
        if (queryCheckTypeData.getData().getEmailStatus() == 1) {
            isEmail = false;
        }
        boolean isGoogle = true;
        if (queryCheckTypeData.getData().getGoogleStatus() == 1) {
            isGoogle = false;
        }
        boolean isPhone = true;
        if (queryCheckTypeData.getData().getPhoneStatus() == 1) {
            isPhone = false;
        }
        showDialog(isEmail, isPhone, isGoogle);
    }


    public void showDialog(boolean isEmail, boolean isPhone, boolean isGoogle) {

        String stringPhone = accountPhone;
        if (!TextUtils.isEmpty(accountPhone)) {
            if (stringPhone.length() > 4) {
                stringPhone = "******" + stringPhone.substring(stringPhone.length() - 4);
            }
        }

        validationDialog = DialogUtils.getValidationDialog(this, isEmail, isPhone, isGoogle,
                accountEmail, stringPhone,
                new DialogUtils.onCommitListener() {
                    @Override
                    public void onCommit(String phoneCode, String emailCode, String googleCode) {
                        smsPhoneCode = phoneCode;
                        smsGoogleCode = googleCode;
                        smsEamilCode = emailCode;
                        userUpdateLoginPwPresenter.getUserUpdateLoginPwModule();
                    }
                }, new DialogUtils.onClickGetEmailListener() {
                    @Override
                    public void onClickGetEmail() {
                        sendEamilCodePresenter.getSendEamilCodeModule();
                    }
                }, new DialogUtils.onClickGetPhoneListener() {
                    @Override
                    public void onClickPhone() {
                        sendSmsPresenter.getSendSmsModule(accountPhone);
                    }
                });
        validationDialog.show();
    }

    /**
     * ---------------------发送验证码一系列方法
     *
     * @return
     */
    @Override
    public String getChooseCountries() {
        return null;
    }

    @Override
    public String getPhone() {
        return accountPhone;
    }

    @Override
    public String getEamil() {
        return accountEmail;
    }

    @Override
    public String getPhoneAndEamil() {
        return accountEmail;
    }


    @Override
    public String getType() {
        return "1";
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public void setCheckPhoneSms(HttpBaseBean data) {
    }

    @Override
    public void setCheckEmailSms(HttpBaseBean data) {
    }

    /**
     * ---------------------------修改密码一些列方法
     */
    /**
     * 修改成功回调
     */
    @Override
    public void UpdateSuccess() {
        validationDialog.dismiss();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(AppConstants.LOGIN_FAILURE, false);
        intent.putExtra(AppConstants.LOGINTYPE, AppConstants.LOGINPHONE);
        this.startActivity(intent);
        SPUtils.remove(this, SPConstant.LOGIN_STATE);
        SPUtils.remove(this, SPConstant.USER_ID);
        SPUtils.remove(this, SPConstant.USER_TOKEN);
        SPUtils.remove(this, SPConstant.USER_ACCOUNT);
        SPUtils.remove(this, SPConstant.USER_CHOOSECOUNTRIES);

        //发送修改密码通知
        EventBus eventBus = OAXApplication.getmEventBus();
        MyEvents myEvents = new MyEvents();
        myEvents.status = MyEvents.status_ok;
        myEvents.status_type = MyEvents.User_Update_Passwrod_Success;
        eventBus.post(myEvents);
        Logger.e(TAG, "发送修改密码重新登录通知!");

        ToastUtil.ShowToast(this.getResources().getString(R.string.update_login_success));
        AppManager.remove(SafetyActivity.class.getSimpleName());
        finish();

    }

    @Override
    public String getOldPassword() {
        return oldPwd.getText().toString();
    }

    @Override
    public String getNewPassword() {
        return newPwd.getText().toString();
    }

    @Override
    public String getRepeatPassword() {
        return confirmedNewPwd.getText().toString();
    }

    @Override
    public String getGoogleCode() {
        return smsGoogleCode;
    }

    @Override
    public String getSmsCode() {
        return smsPhoneCode;
    }

    @Override
    public String getEmailCode() {
        return smsEamilCode;
    }
}
