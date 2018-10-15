package cn.dagongniu.oax.account;

import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.customview.CommonToolbar;
import cn.dagongniu.oax.utils.SkipActivityUtil;

/**
 * 实名认证
 */
public class AuthenticateActivity extends BaseActivity {
    @BindView(R.id.commontoolbar)
    CommonToolbar commontoolbar;
    @BindView(R.id.authenticate_title)
    TextView authenticateTitle;
    @BindView(R.id.authenticate_des)
    TextView authenticateDes;
    @BindView(R.id.start_authenticate)
    TextView startAuthenticate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authenticate;
    }

    @Override
    protected void initView() {
        super.initView();
        commontoolbar.setTitleText(getResources().getString(R.string.mine_authentication));
        commontoolbar.setOnCommonToolbarLeftClickListener(new CommonToolbar.CommonToolbarLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    @OnClick(R.id.start_authenticate)
    public void onClicked() {
        SkipActivityUtil.skipAnotherActivity(mContext, IdentityAuthenticationActivity.class);
    }
}
