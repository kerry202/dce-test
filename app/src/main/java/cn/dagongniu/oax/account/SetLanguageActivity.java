package cn.dagongniu.oax.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.customview.MyTradingToolbar;
import cn.dagongniu.oax.language.LanguageType;
import cn.dagongniu.oax.language.MultiLanguageUtil;
import cn.dagongniu.oax.main.MainActivity;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.MyEvents;

/**
 * 设置语言页面
 */
public class SetLanguageActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.mytrading_toolbar)
    MyTradingToolbar mytradingToolbar;
    private RelativeLayout rl_simplified_chinese;
    private RelativeLayout rl_english;
    private ImageView iv_english;
    private ImageView iv_simplified_chinese;
    private int savedLanguageType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_language;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(newBase));
    }

    @Override
    protected void initView() {
        super.initView();
        mytradingToolbar.setTitleNameText(R.string.setting_language_title);
        mytradingToolbar.setLeftMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mytradingToolbar.setRightImgVisibility(true);
        mytradingToolbar.setRightTvVisibility(true);
        initViews();
    }

    private void initViews() {
        rl_simplified_chinese = findViewById(R.id.rl_simplified_chinese);
        rl_english = findViewById(R.id.rl_english);
        iv_english = findViewById(R.id.iv_english);
        iv_simplified_chinese = findViewById(R.id.iv_simplified_chinese);
        rl_simplified_chinese.setOnClickListener(this);
        rl_english.setOnClickListener(this);
        savedLanguageType = MultiLanguageUtil.getInstance().getLanguageType();
        if (savedLanguageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            setFollowSytemVisible();
        } else if (savedLanguageType == LanguageType.LANGUAGE_EN) {
            setEnglishVisible();
        } else if (savedLanguageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            setSimplifiedVisible();
        } else {
            setSimplifiedVisible();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        int selectedLanguage = 0;
        switch (id) {
            case R.id.rl_simplified_chinese:
                if (isCover(iv_simplified_chinese)) {
                    setSimplifiedVisible();
                    selectedLanguage = LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
                    startMain(selectedLanguage);
                    SPUtils.setParam(this, SPConstant.LANGUAGE, "CN");
                }
                break;
            case R.id.rl_english:
                if (isCover(iv_english)) {
                    setEnglishVisible();
                    selectedLanguage = LanguageType.LANGUAGE_EN;
                    startMain(selectedLanguage);
                    SPUtils.setParam(this, SPConstant.LANGUAGE, "EN");
                }
                break;
        }


    }

    private void startMain(int selectedLanguage) {
        MultiLanguageUtil.getInstance().updateLanguage(selectedLanguage);
        Intent intent = new Intent(SetLanguageActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        bundle.putBoolean("Language", true);
        intent.putExtras(bundle);
        startActivity(intent);
        if (selectedLanguage == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
//            System.exit(0);
        }
    }

    /**
     * 检测制定View是否被遮住显示不全
     *
     * @return
     */
    protected boolean isCover(View view) {
        boolean cover = false;
        Rect rect = new Rect();
        cover = view.getGlobalVisibleRect(rect);
        if (cover) {
            if (rect.width() >= view.getMeasuredWidth() && rect.height() >= view.getMeasuredHeight()) {
                return !cover;
            }
        }
        return true;
    }

    private void setSimplifiedVisible() {
        iv_english.setVisibility(View.GONE);
        iv_simplified_chinese.setVisibility(View.VISIBLE);
    }

    private void setEnglishVisible() {
        iv_english.setVisibility(View.VISIBLE);
        iv_simplified_chinese.setVisibility(View.GONE);
    }

    private void setFollowSytemVisible() {
        iv_english.setVisibility(View.GONE);
        iv_simplified_chinese.setVisibility(View.GONE);
    }

    private void refreshname() {
        mytradingToolbar.setTitleNameText(R.string.setting_language_title);
    }

}
