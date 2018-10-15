package cn.dagongniu.oax.account;

import android.app.Dialog;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.customview.CommonToolbar;
import cn.dagongniu.oax.utils.DialogUtils;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.MyEvents;

public class SettingActivity extends BaseActivity {
    private static final String TAG = "SettingActivity";

    @BindView(R.id.commontoolbar)
    CommonToolbar commontoolbar;
    @BindView(R.id.touch_id)
    Switch touchId;
    @BindView(R.id.rl_set_language)
    AutoRelativeLayout rlSetLanguage;
    @BindView(R.id.rl_set_version)
    AutoRelativeLayout rlSetVersion;
    @BindView(R.id.tv_logout)
    TextView tvLogin;

    private KProgressHUD hud;
    private Dialog sureDialog;
    private Dialog validationDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();
        //暂时不实现指纹登录功能
        touchId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    validationDialog = DialogUtils.getValidationDialog(mContext, false, false, true, "", "",
                            new DialogUtils.onCommitListener() {
                                @Override
                                public void onCommit(String phoneCode, String emailCode, String googleCode) {
                                    //ViewUtils.showKProgressHUD(mContext, R.drawable.correct, R.string.touch_id_open_success);
                                    validationDialog.dismiss();
                                }
                            },
                            new DialogUtils.onClickGetEmailListener() {
                                @Override
                                public void onClickGetEmail() {

                                }
                            },
                            new DialogUtils.onClickGetPhoneListener() {
                                @Override
                                public void onClickPhone() {

                                }
                            });
                } else {
                    DialogUtils.getVerifyTouchIDDialog(mContext);
                }
            }
        });
        setLoginState();
    }

    private void setLoginState() {
        //判断是否登录
        boolean isLoginState = (boolean) SPUtils.getParam(this, SPConstant.LOGIN_STATE, false);
        if (isLoginState) {
            tvLogin.setVisibility(View.VISIBLE);
        } else {
            tvLogin.setVisibility(View.GONE);
        }
    }

    private void initToolbar() {
        commontoolbar.setTitleText(getResources().getString(R.string.mine_setting));
        commontoolbar.setOnCommonToolbarLeftClickListener(new CommonToolbar.CommonToolbarLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    @OnClick({R.id.rl_set_language, R.id.rl_set_version, R.id.tv_logout})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_set_language://国际化
                SkipActivityUtil.skipAnotherActivity(mContext, SetLanguageActivity.class);
                break;
            case R.id.rl_set_version://关于大公牛
                SkipActivityUtil.skipAnotherActivity(mContext, VersionActivity.class);
                break;
            case R.id.tv_logout:
                sureDialog = DialogUtils.getSureAndCancelDialog(mContext, R.string.exit_app_des, new DialogUtils.OnSureListener() {
                    @Override
                    public void onSure() {
                        SPUtils.remove(SettingActivity.this, SPConstant.LOGIN_STATE);
                        SPUtils.remove(SettingActivity.this, SPConstant.USER_ID);
                        SPUtils.remove(SettingActivity.this, SPConstant.USER_TOKEN);
                        SPUtils.remove(SettingActivity.this, SPConstant.USER_ACCOUNT);
                        SPUtils.remove(SettingActivity.this, SPConstant.USER_CHOOSECOUNTRIES);
                        OAXApplication.collectCoinsMap.clear();
                        ToastUtil.ShowToast(SettingActivity.this.getString(R.string.esc_login_success));
                        sureDialog.dismiss();
                        //发送退出登录通知
                        myEvents.status = MyEvents.status_ok;
                        myEvents.status_type = MyEvents.LoginEsc;
                        myEvents.errmsg = SettingActivity.this.getString(R.string.esc_login_success);
                        eventBus.post(myEvents);
                        Logger.e(TAG, "发送登录成功通知!");
                        finish();
                    }
                });
                break;
        }
    }
}
