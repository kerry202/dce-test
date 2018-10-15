package cn.dagongniu.oax.account;

import android.content.pm.PackageManager;
import android.widget.TextView;

import butterknife.BindView;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.customview.CommonToolbar;

/**
 * 版本
 */
public class VersionActivity extends BaseActivity {
    @BindView(R.id.commontoolbar)
    CommonToolbar commontoolbar;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_version;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();
        initVersion();
    }

    private void initVersion() {
        // 获取到当前版本号
        String versionName = "V ";
        try {
            versionName = versionName + OAXApplication.getInstance().getPackageManager().getPackageInfo
                    (OAXApplication.getInstance().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = " ";
            e.printStackTrace();
        }
        tvVersion.setText(versionName);
    }

    private void initToolbar() {
        commontoolbar.setTitleText(getResources().getString(R.string.about_version));
        commontoolbar.setOnCommonToolbarLeftClickListener(new CommonToolbar.CommonToolbarLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }


}
