package cn.dagongniu.oax.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.UserCenterBean;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.customview.CommonToolbar;
import cn.dagongniu.oax.language.MultiLanguageUtil;
import cn.dagongniu.oax.utils.SkipActivityUtil;

/**
 * 认证状态
 */
public class AuthenticationStateActivity extends BaseActivity {
    @BindView(R.id.commontoolbar)
    CommonToolbar commontoolbar;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.iv_state)
    ImageView ivState;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.ll_state_success)
    AutoLinearLayout llStateSuccess;
    @BindView(R.id.ll_state_fail)
    AutoLinearLayout llStateFail;
    @BindView(R.id.tv_state_reviewing_des)
    TextView tvStateReviewingDes;
    @BindView(R.id.tv_retry)
    TextView tvRetry;
    @BindView(R.id.tv_reason)
    TextView tvReason;

    UserCenterBean userCenterBean = null;
    int state = -1;
    Intent intent;
    String Reason = "";

    @Override
    protected int getLayoutId() {
        return R.layout.authentication_state_activity;
    }

    @OnClick(R.id.tv_retry)
    public void onClicked() {
        SkipActivityUtil.skipAnotherActivity(this, IdentityAuthenticationActivity.class, true);
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();
        intent = this.getIntent();
        state = intent.getIntExtra("STATE", -1);
        Bundle bundle = intent.getExtras();
        userCenterBean = (UserCenterBean) bundle.getSerializable("UserCenterBean");
        Reason = bundle.getString("Reason");
        tvReason.setText(Reason);
        setState(state);
    }

    private void initToolbar() {
        commontoolbar.setTitleText(getResources().getString(R.string.identity_authentication));
        commontoolbar.setOnCommonToolbarLeftClickListener(new CommonToolbar.CommonToolbarLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    private void setState(int state) {
        switch (state) {
            case 0:
                if (MultiLanguageUtil.getInstance().getLanguageStringType().equals("en")) {
                    ivState.setImageDrawable(getResources().getDrawable(R.drawable.identity_reviewwing_en));
                } else {
                    ivState.setImageDrawable(getResources().getDrawable(R.drawable.identity_reviewing));
                }
                //审核中
                tvState.setText(getResources().getString(R.string.authentication_reviewing));

                tvStateReviewingDes.setVisibility(View.VISIBLE);
                llStateSuccess.setVisibility(View.GONE);
                llStateFail.setVisibility(View.GONE);
                tvRetry.setVisibility(View.GONE);
                break;
            case 1:
                //成功
                String userName = "";
                if (userCenterBean.getData().getUsercenter().getIdName() != null) {
                    if (userCenterBean.getData().getUsercenter().getIdNo().length() > 1) {
                        userName = "*" + userCenterBean.getData().getUsercenter().getIdName().substring(userCenterBean.getData().getUsercenter().getIdName().length() - 1, userCenterBean.getData().getUsercenter().getIdName().length());
                    } else {
                        userName = userCenterBean.getData().getUsercenter().getIdName();
                    }
                }
                tvName.setText(userName);

                String userCridId = "";
                if (userCenterBean.getData().getUsercenter().getIdNo() != null) {
                    if (userCenterBean.getData().getUsercenter().getIdNo().length() > 6) {
                        userCridId = "****" + userCenterBean.getData().getUsercenter().getIdNo()
                                .substring(userCenterBean.getData().getUsercenter().getIdNo().length() - 6, userCenterBean.getData().getUsercenter().getIdNo().length());
                    } else {
                        userCridId = userCenterBean.getData().getUsercenter().getIdNo();
                    }
                }
                tvNumber.setText(userCridId);


                tvState.setText(getResources().getString(R.string.authentication_success));
                ivState.setImageDrawable(getResources().getDrawable(R.drawable.identity_success));
                tvStateReviewingDes.setVisibility(View.GONE);
                llStateSuccess.setVisibility(View.VISIBLE);
                llStateFail.setVisibility(View.GONE);
                tvRetry.setVisibility(View.GONE);
                break;
            case 2:
                //失败
                tvState.setText(getResources().getString(R.string.authentication_fail));
                ivState.setImageDrawable(getResources().getDrawable(R.drawable.identity_fail));
                tvStateReviewingDes.setVisibility(View.GONE);
                llStateSuccess.setVisibility(View.GONE);
                llStateFail.setVisibility(View.VISIBLE);
                tvRetry.setVisibility(View.VISIBLE);
                break;
        }
    }
}
