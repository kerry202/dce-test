package cn.dagongniu.oax.account.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.socks.library.KLog;
import com.umeng.socialize.ShareAction;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.Subscribe;


import butterknife.BindView;
import butterknife.OnClick;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.AuthenticateActivity;
import cn.dagongniu.oax.account.AuthenticationStateActivity;
import cn.dagongniu.oax.account.EarningsActivity;
import cn.dagongniu.oax.account.HelpActivity;
import cn.dagongniu.oax.account.LoginActivity;
import cn.dagongniu.oax.account.MineInvitationActivity;
import cn.dagongniu.oax.account.SafetyActivity;
import cn.dagongniu.oax.account.OaxRegisteredActivity;
import cn.dagongniu.oax.account.SettingActivity;
import cn.dagongniu.oax.account.bean.IdentityResultBean;
import cn.dagongniu.oax.account.bean.LoginBean;
import cn.dagongniu.oax.account.bean.UserCenterBean;
import cn.dagongniu.oax.account.presenter.IdentityResultPresenter;
import cn.dagongniu.oax.account.presenter.UserCenterPresenter;
import cn.dagongniu.oax.account.view.IUserCenterView;
import cn.dagongniu.oax.account.view.IidentityResultView;
import cn.dagongniu.oax.account.view.MineFragmentItemView;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.constant.UMConstant;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.AccountValidatorUtil;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.MyEvents;
import cn.dagongniu.oax.utils.um.UMManager;

/**
 * 我的
 */
public class MineFragment extends BaseFragment implements IUserCenterView, IidentityResultView {

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.ll_unlogin)
    AutoLinearLayout llUnlogin;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_uid)
    TextView tvUserUid;
    @BindView(R.id.iv_grade)
    ImageView ivGrade;
    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.tv_authentication)
    TextView tvAuthentication;
    @BindView(R.id.rl_head_container)
    AutoRelativeLayout rlHeadContainer;
    @BindView(R.id.tv_security_reminder)
    TextView tvSecurityReminder;
    @BindView(R.id.mine_invitation)
    MineFragmentItemView mineInvitation;
    @BindView(R.id.mine_authentication)
    MineFragmentItemView mineAuthentication;
    @BindView(R.id.mine_security)
    MineFragmentItemView mineSecurity;
    @BindView(R.id.mine_setting)
    MineFragmentItemView mineSetting;
    @BindView(R.id.mine_help)
    MineFragmentItemView mineHelp;
    @BindView(R.id.mine_login_user_data)
    RelativeLayout mineLoginUserData;
    @BindView(R.id.rl_certification)
    RelativeLayout rlCertification;

    UserCenterPresenter userCenterPresenter;
    IdentityResultPresenter identityResultPresenter;
    UserCenterBean userCenterBean = null;
    Bundle bundle;

    private ShareAction mShareAction;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        super.initView();
        bundle = new Bundle();
        if (getActivity().getIntent().getBooleanExtra("Language", false)) {
            SkipActivityUtil.skipAnotherActivity(mContext, SettingActivity.class);
        }
        identityResultPresenter = new IdentityResultPresenter(this, RequestState.STATE_DIALOG);
        userCenterPresenter = new UserCenterPresenter(this, RequestState.STATE_REFRESH);
        userCenterPresenter.getUserCenterModule();
    }

    @OnClick({R.id.tv_register, R.id.tv_login, R.id.tv_authentication, R.id.mine_invitation, R.id.mine_authentication, R.id.mine_security, R.id.mine_setting, R.id.mine_help, R.id.mine_earnings})
    public void onClicked(View view) {
        Bundle bundle = new Bundle();
        boolean islogin = (boolean) SPUtils.getParam(getActivity(), SPConstant.LOGIN_STATE, false);
        switch (view.getId()) {
            case R.id.tv_register:
                bundle.putString(AppConstants.LOGINTYPE, AppConstants.LOGINPHONE);
                bundle.putBoolean(AppConstants.LOGINTYPE_ISLOGIN, false);
                openActivity(OaxRegisteredActivity.class, bundle);
                UMManager.onEvent(mContext, UMConstant.MineFragment, UMConstant.register);
                break;
            case R.id.tv_login:
                bundle.putString(AppConstants.LOGINTYPE, AppConstants.LOGINPHONE);
                openActivity(LoginActivity.class, bundle);
                UMManager.onEvent(mContext, UMConstant.MineFragment, UMConstant.login);
                break;
            case R.id.tv_authentication:
                UMManager.onEvent(mContext, UMConstant.MineFragment, UMConstant.go_authentication);
                break;
            case R.id.mine_invitation://邀请
                if (islogin) {
                    SkipActivityUtil.skipAnotherActivity(mContext, MineInvitationActivity.class);
                } else {
                    ToastUtil.ShowToast(getActivity().getResources().getString(R.string.please_login), getActivity());
                }
                UMManager.onEvent(mContext, UMConstant.MineFragment, UMConstant.mine_invitation);
                break;
            case R.id.mine_authentication://实名认证
                if (userCenterBean != null && islogin) {
                    identityResultPresenter.getIdentityResultModule();
                } else {
                    ToastUtil.ShowToast(getActivity().getResources().getString(R.string.please_login), getActivity());
                }
                UMManager.onEvent(mContext, UMConstant.MineFragment, UMConstant.mine_authentication);
                break;
            case R.id.mine_security://安全中心
                if (userCenterBean != null && islogin) {
                    bundle.putSerializable("UserCenterBean", userCenterBean);
                    openActivity(SafetyActivity.class, bundle);
                } else {
                    ToastUtil.ShowToast(getActivity().getResources().getString(R.string.please_login), getActivity());
                }
                UMManager.onEvent(mContext, UMConstant.MineFragment, UMConstant.mine_security);
                break;
            case R.id.mine_setting:
                SkipActivityUtil.skipAnotherActivity(mContext, SettingActivity.class);
                UMManager.onEvent(mContext, UMConstant.MineFragment, UMConstant.mine_setting);
                break;
            case R.id.mine_help:
                SkipActivityUtil.skipAnotherActivity(mContext, HelpActivity.class);
                UMManager.onEvent(mContext, UMConstant.MineFragment, UMConstant.mine_help);
                break;
            case R.id.mine_earnings:
                if (islogin) {
                    SkipActivityUtil.skipAnotherActivity(mContext, EarningsActivity.class);
                } else {
                    ToastUtil.ShowToast(getActivity().getResources().getString(R.string.please_login), getActivity());
                }
                UMManager.onEvent(mContext, UMConstant.MineFragment, UMConstant.mine_invitation);
                break;
        }
    }

    @Subscribe
    public void onEvent(MyEvents event) {
        switch (event.status_type) {
            /**
             * 登录成功通知 登陆成功后刷新下我的信息
             */
            case MyEvents.LoginSuccess:
                ToastUtil.ShowToast(getResources().getString(R.string.login_success));
                LoginBean loginBean = (LoginBean) event.something;
                setLoginSuccess(loginBean);
                userCenterPresenter.getUserCenterModule();
                break;
            /**
             * 退出登录
             */
            case MyEvents.LoginEsc:
                mineLoginUserData.setVisibility(View.GONE);
                llUnlogin.setVisibility(View.VISIBLE);
                break;
            /**
             * 绑定phone or email成功刷新我的
             */
            case MyEvents.Bind_PhoneOrEmail_Success:
                userCenterPresenter.getUserCenterModule();
                break;
            /**
             * 开启验证通知我的刷新
             */
            case MyEvents.Account_Check_Refresh:
                userCenterPresenter.getUserCenterModule();
                break;
            /**
             * 绑定google成功
             */
            case MyEvents.Bind_Google_Success:
                userCenterPresenter.getUserCenterModule();
                break;
            /**
             * 身份证资料上传成功刷新
             */
            case MyEvents.User_Identity_Authen:
                userCenterPresenter.getUserCenterModule();
                break;
            /**
             * 登陆失效 初始化我的页面
             */
            case MyEvents.Token_failure:
                tokenInitializeData();
                break;
            /**
             *  修改密码 重新登录
             */
            case MyEvents.User_Update_Passwrod_Success:
                tokenInitializeData();
                break;
        }
    }

    /**
     * 登陆成功
     *
     * @param loginSuccess
     */
    public void setLoginSuccess(LoginBean loginSuccess) {
        KLog.d("loginSuccess = " + new Gson().toJson(loginSuccess));
        mineLoginUserData.setVisibility(View.VISIBLE);
        llUnlogin.setVisibility(View.GONE);
        LoginBean.DataBean data = loginSuccess.getData();
        String name = data.getName();

        if (data.getCheckStatus() == 2) {//已通过认证
            tvUserName.setText(name);
        } else {
            if (AccountValidatorUtil.isEmail(name.toString())) {
                int indexStart = name.indexOf("@");
                String toStr = name.substring(0, indexStart);
                String emailStr = "";
                if (toStr.length() > 4) {
                    emailStr = "****" + name.substring(indexStart - 4, name.length());
                } else {
                    emailStr = name;
                }
                tvUserName.setText(emailStr);
            } else {
                tvUserName.setText("****" + name.substring(name.length() - 4));
            }
        }

        tvUserUid.setText("UID：" + loginSuccess.getData().getUserId());
        tvGrade.setText("Lv " + loginSuccess.getData().getLevel());
        if (loginSuccess.getData().getCheckStatus() == 0) {
            rlCertification.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.border_focused_withd_mine_certification));
            tvAuthentication.setText(R.string.go_authenticate);
        } else {
            rlCertification.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.border_focused_withd_mine_certification));
            tvAuthentication.setText(R.string.authenticated);
        }
    }

    /**
     * 回调
     *
     * @param userCenterData
     */
    @Override
    public void setUserCenterData(UserCenterBean userCenterData) {
        KLog.d("userCenterData = " + new Gson().toJson(userCenterData));
        userCenterBean = userCenterData;
        mineLoginUserData.setVisibility(View.VISIBLE);
        llUnlogin.setVisibility(View.GONE);

        UserCenterBean.DataBean.UsercenterBean data = userCenterData.getData().getUsercenter();
        String name = data.getIdName();

        if (data.getCheckStatus() == 2) {//已通过认证
            String userName = "";
            if (!TextUtils.isEmpty(name)) {
                if (name.length() > 1) {
                    userName = "*" + name.substring(name.length() - 1, name.length());
                } else {
                    userName = name;
                }
            }
            tvUserName.setText(userName);
        } else {
            if (AccountValidatorUtil.isEmail(name.toString())) {
                int indexStart = name.indexOf("@");
                String toStr = name.substring(0, indexStart);
                String emailStr = "";
                if (toStr.length() > 4) {
                    emailStr = "****" + name.substring(indexStart - 4, name.length());
                } else {
                    emailStr = name;
                }
                tvUserName.setText(emailStr);
            } else {
                tvUserName.setText("****" + name.substring(name.length() - 4));
            }
        }

        tvUserUid.setText("UID：" + userCenterData.getData().getUsercenter().getId());

        tvGrade.setText("Lv " + userCenterData.getData().getUsercenter().getLevel());

        switch (userCenterData.getData().getUsercenter().getCheckStatus()) {
            case -1://未通过
                rlCertification.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.border_focused_withd_mine_certification));
                tvAuthentication.setText(R.string.not_through);
                break;
            case 0://未认证
                rlCertification.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.border_focused_withd_mine_certification));
                tvAuthentication.setText(R.string.unauthorized);
                break;
            case 1://待审核
                rlCertification.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.border_focused_withd_mine_certification));
                tvAuthentication.setText(R.string.to_audit);
                break;
            case 2://审核通过
                rlCertification.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.border_focused_withd_mine_certification));
                tvAuthentication.setText(R.string.approved);
                break;
        }
        /**
         * 缓存数据
         */
        SPUtils.setParam(mContext, SPConstant.USER_PHONE, userCenterData.getData().getUsercenter().getPhone() != null ? userCenterData.getData().getUsercenter().getPhone() : "");
        SPUtils.setParam(mContext, SPConstant.USER_EMAIL, userCenterData.getData().getUsercenter().getEmail() != null ? userCenterData.getData().getUsercenter().getEmail() : "");

        SPUtils.setParam(mContext, SPConstant.LEVEL1_BTC, userCenterData.getData().getLEVEL1_BTC());
        SPUtils.setParam(mContext, SPConstant.LEVEL2_BTC, userCenterData.getData().getLEVEL2_BTC());
        SPUtils.setParam(mContext, SPConstant.LEVEL3_BTC, userCenterData.getData().getLEVEL3_BTC());

        OAXApplication.getInstance().setUserEmail(userCenterData.getData().getUsercenter().getEmail());
        OAXApplication.getInstance().setUserPhone(userCenterData.getData().getUsercenter().getPhone());
        OAXApplication.getInstance().setUserGoogleKe(userCenterData.getData().getUsercenter().getGoogleKey());
        OAXApplication.getInstance().setUserEmailState(userCenterData.getData().getUsercenter().getEmailStatus());
        OAXApplication.getInstance().setUserGoogleState(userCenterData.getData().getUsercenter().getGoogleStatus());
        OAXApplication.getInstance().setUserPhoneState(userCenterData.getData().getUsercenter().getPhoneStatus());
    }

    /**
     * 登陆失效 获取请求返回错误初始化页面
     */
    public void tokenInitializeData() {
        mineLoginUserData.setVisibility(View.GONE);
        llUnlogin.setVisibility(View.VISIBLE);
        tvUserName.setText("");
        tvAuthentication.setText("");
        rlCertification.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.border_focused_withd_mine_certification));
        tvUserUid.setText("");
        tvGrade.setText("");
        userCenterBean = null;
    }

    @Override
    public void setUserCenterFailure(String msg) {
        tokenInitializeData();
    }

    @Override
    public void onResume() {
        super.onResume();
        UMManager.onResume(mContext, UMConstant.AssetsFragment);
    }

    @Override
    public void onPause() {
        super.onPause();
        UMManager.onPause(mContext, UMConstant.AssetsFragment);
    }

    @Override
    public void IidentityResultsuccessful(IdentityResultBean httpBaseBean) {
        switch (httpBaseBean.getData().getCheckStatus()) {
            case -1://未通过
                bundle.putInt("STATE", 2);
                bundle.putSerializable("UserCenterBean", userCenterBean);
                bundle.putString("Reason", httpBaseBean.getData().getReason());
                openActivity(AuthenticationStateActivity.class, bundle);
                break;
            case 0://未认证
                openActivity(AuthenticateActivity.class, bundle);
                break;
            case 1://待审核
                bundle.putInt("STATE", 0);
                bundle.putString("Reason", "");
                bundle.putSerializable("UserCenterBean", userCenterBean);
                openActivity(AuthenticationStateActivity.class, bundle);
                break;
            case 2://审核通过
                bundle.putInt("STATE", 1);
                bundle.putString("Reason", "");
                bundle.putSerializable("UserCenterBean", userCenterBean);
                openActivity(AuthenticationStateActivity.class, bundle);
                break;
        }
    }

    @Override
    public void IidentityResultfailure(String msg) {
        ToastUtil.ShowToast(msg);
    }
}
