package cn.dagongniu.oax.assets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.constant.UMConstant;
import cn.dagongniu.oax.customview.MyTradingToolbar;
import cn.dagongniu.oax.utils.um.UMManager;

/**
 * 红包帮助 遇到问题
 */
public class RedHelpActivity extends BaseActivity {

    @BindView(R.id.assets_toolbar)
    MyTradingToolbar myTradingToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_red_help;
    }

    @Override
    protected void initView() {
        super.initView();
        initToobar();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private void initToobar() {

        myTradingToolbar.setRightImgVisibility(true);
        myTradingToolbar.setTvLeftVisibility(true);
        myTradingToolbar.setSjVisibility(true);
        myTradingToolbar.setRightTvVisibility(true);
        myTradingToolbar.setTitleNameText(R.string.red_help);
        myTradingToolbar.setRightTvColor(getContext().getResources().getColor(R.color.df_333333));
        myTradingToolbar.setLeftMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}


