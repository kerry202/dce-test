package cn.dagongniu.oax.account;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.UserCenterBean;
import cn.dagongniu.oax.account.presenter.SendEamilCodePresenter;
import cn.dagongniu.oax.account.presenter.SendSmsPresenter;
import cn.dagongniu.oax.account.presenter.SwitchCheckPresenter;
import cn.dagongniu.oax.account.view.ISwitchCheckView;
import cn.dagongniu.oax.account.view.SendSmsView;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.customview.CommonToolbar;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.DialogUtils;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.MyEvents;

/**
 * 安全中心
 */
public class SafetyActivity extends BaseActivity implements ISwitchCheckView, SendSmsView, OnCheckedChangeListener {

    private static final String TAG = "SafetyActivity";

    @BindView(R.id.commontoolbar)
    CommonToolbar commontoolbar;
    @BindView(R.id.phone_verify)
    Switch phoneVerify;
    @BindView(R.id.ll_setting_phone_verify)
    AutoLinearLayout llSettingPhoneVerify;
    @BindView(R.id.mail_verify)
    Switch mailVerify;
    @BindView(R.id.ll_setting_mail_verify)
    AutoLinearLayout llSettingMailVerify;
    @BindView(R.id.google_verify)
    Switch googleVerify;
    @BindView(R.id.ll_setting_google_verify)
    AutoLinearLayout llSettingGoogleVerify;
    @BindView(R.id.rl_change_pwd)
    AutoRelativeLayout rlChangePwd;

    Intent intent;
    UserCenterBean userCenterBean = null;
    Bundle bundle;
    SwitchCheckPresenter switchCheckPresenter;
    SendEamilCodePresenter sendEamilCodePresenter;
    SendSmsPresenter sendSmsPresenter;
    int swithType = -1; //1 手机 2邮箱 3google

    String phoneCodeMain;
    String emailCodeMain;
    String googleCodeMain;
    boolean isCheckType = false;
    Dialog validationDialogShow = null;
    int swicthType = -1;


    // 0=未开启  1=开启
    int OpenToclose = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_safety;
    }

    @Override
    protected void initView() {
        super.initView();
        bundle = new Bundle();
        intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        switchCheckPresenter = new SwitchCheckPresenter(this, RequestState.STATE_REFRESH);
        sendEamilCodePresenter = new SendEamilCodePresenter(this, RequestState.STATE_REFRESH);
        sendSmsPresenter = new SendSmsPresenter(this, RequestState.STATE_REFRESH);
        userCenterBean = (UserCenterBean) bundle.getSerializable("UserCenterBean");
        initToolbar();
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        super.initData();
        if (userCenterBean != null) {
            //手机验证
            if (userCenterBean.getData().getUsercenter().getPhone() != null) {
                phoneVerify.setVisibility(View.VISIBLE);
                llSettingPhoneVerify.setVisibility(View.GONE);
                if (userCenterBean.getData().getUsercenter().getPhoneStatus() == 1) {
                    phoneVerify.setChecked(true);
                } else {
                    phoneVerify.setChecked(false);
                }

            } else {
                phoneVerify.setVisibility(View.GONE);
                llSettingPhoneVerify.setVisibility(View.VISIBLE);
            }
            //邮箱验证
            if (userCenterBean.getData().getUsercenter().getEmail() != null) {
                mailVerify.setVisibility(View.VISIBLE);
                llSettingMailVerify.setVisibility(View.GONE);
                if (userCenterBean.getData().getUsercenter().getEmailStatus() == 1) {
                    mailVerify.setChecked(true);
                } else {
                    mailVerify.setChecked(false);
                }
            } else {
                mailVerify.setVisibility(View.GONE);
                llSettingMailVerify.setVisibility(View.VISIBLE);
            }
            //google验证
            if (userCenterBean.getData().getUsercenter().getGoogleKey() != null) {
                googleVerify.setVisibility(View.VISIBLE);
                llSettingGoogleVerify.setVisibility(View.GONE);
                if (userCenterBean.getData().getUsercenter().getGoogleStatus() == 1) {
                    googleVerify.setChecked(true);
                } else {
                    googleVerify.setChecked(false);
                }
            } else {
                googleVerify.setVisibility(View.GONE);
                llSettingGoogleVerify.setVisibility(View.VISIBLE);
            }
        }
        initSwitch();
    }

    /**
     * 检查开启验证的状态 省最后一种则弹框提示 (1=手机，2=邮箱，3=google)
     */
    public boolean checkBindingToDialog(int type) {
        boolean isCheck = true;
        if (userCenterBean != null) {
            switch (type) {
                case 1://手机
                    if (OAXApplication.getInstance().getUserGoogleState() == 0
                            && OAXApplication.getInstance().getUserEmailState() == 0) {
                        DialogUtils.getRemindDialog(mContext, R.string.safety_remind_title, R.string.safety_remind_des, R.string.safety_remind_sure);
                        return isCheck;
                    }
                    break;
                case 2://邮箱
                    if (OAXApplication.getInstance().getUserGoogleState() == 0
                            && OAXApplication.getInstance().getUserPhoneState() == 0) {
                        DialogUtils.getRemindDialog(mContext, R.string.safety_remind_title, R.string.safety_remind_des, R.string.safety_remind_sure);
                        return isCheck;
                    }
                    break;
                case 3://google
                    if (OAXApplication.getInstance().getUserEmailState() == 0
                            && OAXApplication.getInstance().getUserPhoneState() == 0) {
                        DialogUtils.getRemindDialog(mContext, R.string.safety_remind_title, R.string.safety_remind_des, R.string.safety_remind_sure);
                        return isCheck;
                    }
                    break;
            }
        }
        return false;
    }

    /**
     * 开启关闭事件处理
     */
    private void initSwitch() {
        phoneVerify.setOnCheckedChangeListener(this);
        mailVerify.setOnCheckedChangeListener(this);
        googleVerify.setOnCheckedChangeListener(this);
    }

    /**
     * 关闭开启
     *
     * @param type        类型（邮箱 手机 google）
     * @param ischeck
     * @param openToclose 开启1 or 关闭0
     * @return
     */
    public boolean dialogSms(int type, boolean ischeck, int openToclose) {

        boolean isEmail = false;
        boolean isPhone = false;
        boolean isGoogle = false;
        if (TextUtils.isEmpty(OAXApplication.getInstance().getUserEmail())) {
            isEmail = true;
        }
        if (TextUtils.isEmpty(OAXApplication.getInstance().getUserPhone())) {
            isPhone = true;
        }
        if (TextUtils.isEmpty(OAXApplication.getInstance().getUserGoogleKe())) {
            isGoogle = true;
        }

        this.isCheckType = ischeck;
        this.swicthType = type;
        this.OpenToclose = openToclose;
        String EmailStr = userCenterBean.getData().getUsercenter().getEmail();
        String PhoneStr = userCenterBean.getData().getUsercenter().getPhone();
        String PhoneStrSub = "";
        if (!TextUtils.isEmpty(PhoneStr)) {
            PhoneStrSub = "******" + PhoneStr.substring(PhoneStr.length() - 4);
        }

        validationDialogShow = DialogUtils.getValidationDialog(this, isEmail, isPhone, isGoogle, EmailStr, PhoneStrSub, new DialogUtils.onCommitListener() {
            @Override
            public void onCommit(String phoneCode, String emailCode, String googleCode) {
                phoneCodeMain = phoneCode;
                emailCodeMain = emailCode;
                googleCodeMain = googleCode;
                switchCheckPresenter.getSwitchCheckModule();
            }
        }, new DialogUtils.onClickGetEmailListener() {
            @Override
            public void onClickGetEmail() {
                sendEamilCodePresenter.getSendEamilCodeModule();
            }
        }, new DialogUtils.onClickGetPhoneListener() {
            @Override
            public void onClickPhone() {
                sendSmsPresenter.getSendSmsModule(userCenterBean.getData().getUsercenter().getPhone());
            }
        });

        Window win = getWindow();
        WindowManager.LayoutParams params = win.getAttributes();
        win.setSoftInputMode(params.SOFT_INPUT_ADJUST_NOTHING);

        validationDialogShow.show();
        //什么状态过来反向出去
        if (ischeck) {
            return false;
        } else {
            return true;
        }

    }

    private void initToolbar() {
        commontoolbar.setTitleText(getResources().getString(R.string.safety_canter));
        commontoolbar.setOnCommonToolbarLeftClickListener(new CommonToolbar.CommonToolbarLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }


    @OnClick({R.id.ll_setting_phone_verify, R.id.ll_setting_mail_verify, R.id.ll_setting_google_verify, R.id.rl_change_pwd})
    public void onClicked(View view) {
        Intent intentCC = null;
        switch (view.getId()) {
            case R.id.ll_setting_phone_verify://设置手机验证
                intentCC = new Intent(this, VerifyActivity.class);
                bundle.putInt(Constant.VERIFY_TYPE, Constant.VERIFY_TYPE_PHONE);
                intentCC.putExtras(bundle);
                startActivityForResult(intentCC, 1);
                break;
            case R.id.ll_setting_mail_verify://设置邮箱验证
                intentCC = new Intent(this, VerifyActivity.class);
                bundle.putInt(Constant.VERIFY_TYPE, Constant.VERIFY_TYPE_MAIL);
                intentCC.putExtras(bundle);
                startActivityForResult(intentCC, 1);
                break;
            case R.id.ll_setting_google_verify:
                intentCC = new Intent(this, GoogleVerifyActivity.class);
                startActivityForResult(intentCC, 1);
                break;
            case R.id.rl_change_pwd:
                openActivity(ChangeLoginPwdActivity.class);
                break;

        }
    }

    /**
     * 绑定手机号码以及邮箱成功的回来   显示开关
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 300://绑定邮箱手机回来
                Bundle bundle = data.getExtras();
                int mType = bundle.getInt("bindType");
                if (mType == Constant.VERIFY_TYPE_PHONE) {
                    phoneVerify.setVisibility(View.VISIBLE);
                    llSettingPhoneVerify.setVisibility(View.GONE);
                } else if (mType == Constant.VERIFY_TYPE_MAIL) {
                    mailVerify.setVisibility(View.VISIBLE);
                    llSettingMailVerify.setVisibility(View.GONE);
                }
                break;
            case 400://google验证回来
                googleVerify.setVisibility(View.VISIBLE);
                llSettingGoogleVerify.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    /**
     * 开启成功
     */
    @Override
    public void successful() {
        validationDialogShow.dismiss();
        /**
         * 发送开启验证的通知 ， 刷新我的
         */
        myEvents.status = MyEvents.status_ok;
        myEvents.status_type = MyEvents.Account_Check_Refresh;
        myEvents.errmsg = this.getResources().getString(R.string.refresh_ok);
        eventBus.post(myEvents);
        Logger.e(TAG, "发送开启验证通知刷新");
        switch (swicthType) {
            case 1:
                phoneVerify.setOnCheckedChangeListener(null);
                phoneVerify.setChecked(isCheckType);
                phoneVerify.setOnCheckedChangeListener(this);
                break;
            case 2:
                mailVerify.setOnCheckedChangeListener(null);
                mailVerify.setChecked(isCheckType);
                mailVerify.setOnCheckedChangeListener(this);
                break;
            case 3:
                googleVerify.setOnCheckedChangeListener(null);
                googleVerify.setChecked(isCheckType);
                googleVerify.setOnCheckedChangeListener(this);
                break;
        }
    }

    /**
     * 开启失败
     *
     * @param msg
     */
    @Override
    public void failure(String msg) {
        validationDialogShow.dismiss();
        ToastUtil.ShowToast(msg);
        switch (swicthType) {
            case 1:
                phoneVerify.setOnCheckedChangeListener(null);
                phoneVerify.setChecked(!isCheckType);
                phoneVerify.setOnCheckedChangeListener(this);
                break;
            case 2:
                mailVerify.setOnCheckedChangeListener(null);
                mailVerify.setChecked(!isCheckType);
                mailVerify.setOnCheckedChangeListener(this);
                break;
            case 3:
                googleVerify.setOnCheckedChangeListener(null);
                googleVerify.setChecked(!isCheckType);
                googleVerify.setOnCheckedChangeListener(this);
                break;
        }
    }

    /**
     * --------------------------------开启验证 一系列方法
     *
     * @return
     */
    @Override
    public String getEmailCode() {
        return emailCodeMain;
    }

    @Override
    public String getSmsCode() {
        return phoneCodeMain;
    }

    @Override
    public String getGoogleCode() {
        return googleCodeMain;
    }

    @Override
    public int getCheckType() {
        return swithType;
    }

    @Override
    public int getStatus() {
        return OpenToclose;
    }

    /**
     * -------------------- 发送验证码邮箱 手机 一些列方法
     *
     * @return
     */

    @Override
    public String getChooseCountries() {
        return null;
    }

    @Override
    public String getPhone() {
        return userCenterBean.getData().getUsercenter().getPhone();
    }

    @Override
    public String getEamil() {
        return userCenterBean.getData().getUsercenter().getEmail();
    }

    @Override
    public String getPhoneAndEamil() {
        return userCenterBean.getData().getUsercenter().getEmail();
    }

    @Override
    public String getType() {
        return AppConstants.SMS_REGISTERED;
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


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.phone_verify:
                swithType = 1;
                if (isChecked == false) {//判断关闭
                    boolean phoneV = checkBindingToDialog(1);
                    phoneVerify.setChecked(phoneV);
                    if (phoneV == false) {
                        phoneVerify.setChecked(dialogSms(1, isChecked, 0));
                    }
                } else {
                    phoneVerify.setChecked(dialogSms(1, isChecked, 1));
                }
                break;
            case R.id.mail_verify:
                swithType = 2;
                if (isChecked == false) {
                    boolean emailV = checkBindingToDialog(2);
                    mailVerify.setChecked(emailV);
                    if (emailV == false) {
                        mailVerify.setChecked(dialogSms(2, isChecked, 0));
                    }
                } else {
                    mailVerify.setChecked(dialogSms(2, isChecked, 1));
                }
                break;
            case R.id.google_verify:
                swithType = 3;
                if (isChecked == false) {
                    boolean googleV = checkBindingToDialog(3);
                    googleVerify.setChecked(googleV);
                    if (googleV == false) {
                        googleVerify.setChecked(dialogSms(3, isChecked, 0));
                    }
                } else {
                    googleVerify.setChecked(dialogSms(3, isChecked, 1));
                }
                break;
        }
    }
}
