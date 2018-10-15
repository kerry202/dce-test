package cn.dagongniu.oax.assets;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.OaxRegisteredActivity;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.customview.MyTradingToolbar;
import cn.dagongniu.oax.utils.AppManager;

/**
 * 分享成功
 */
public class ShareSuccessActivity extends BaseActivity {

    @BindView(R.id.assets_toolbar)
    MyTradingToolbar myTradingToolbar;
    @BindView(R.id.rl_my_assets)
    RelativeLayout rlMyAssets;
    @BindView(R.id.tv_success)
    TextView tv_success;
    Intent intent;
    int redType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_success;
    }

    @Override
    protected void initView() {
        super.initView();
        intent = getIntent();
        redType = intent.getIntExtra("SuccessType", -1);
        initToobar();
    }

    private void initToobar() {

        myTradingToolbar.setRightImgVisibility(true);
        myTradingToolbar.setTvLeftVisibility(true);
        myTradingToolbar.setSjVisibility(true);
        myTradingToolbar.setRightTvVisibility(true);
        myTradingToolbar.setLeftImgVisibility(true);
        if (redType == 1) {
            myTradingToolbar.setTitleNameText(R.string.copy_success);
            tv_success.setText("红包已封好，快去发红包吧！");
        } else {
            myTradingToolbar.setTitleNameText(R.string.red_oax_share_success);
            tv_success.setText("分享成功");
        }
    }

    //屏蔽返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.rl_my_assets)
    public void onClick() {
        AppManager.remove(OaxRedEnvelopeDetailsActivity.class.getSimpleName());
        AppManager.remove(ShareSuccessActivity.class.getSimpleName());
        AppManager.remove(RedEnvelopeActivity.class.getSimpleName());
    }
}
