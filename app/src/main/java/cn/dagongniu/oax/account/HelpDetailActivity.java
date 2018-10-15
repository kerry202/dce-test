package cn.dagongniu.oax.account;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;

import java.util.HashMap;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.HelpDetailInfoBean;
import cn.dagongniu.oax.account.presenter.HelpDetailPresenter;
import cn.dagongniu.oax.base.OAXBaseActivity;
import cn.dagongniu.oax.base.OAXIViewBean;
import cn.dagongniu.oax.customview.CommonToolbar;
import cn.dagongniu.oax.customview.LoadingState;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.UrlParams;
import cn.dagongniu.oax.utils.ToastUtil;

/**
 * 帮助中心 详情
 */
public class HelpDetailActivity extends OAXBaseActivity implements OAXIViewBean<HelpDetailInfoBean> {
    @BindView(R.id.common_toolbar)
    CommonToolbar commonToolbar;
    @BindView(R.id.web_view)
    WebView webView;
    private HelpDetailPresenter mPresenter;
    private int mId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_helpdetail;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent.hasExtra(UrlParams.id)) {
            mId = intent.getIntExtra(UrlParams.id, -1);
        }
        initToolbar();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new HelpDetailPresenter(this);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", mId);
        mPresenter.getData(map, RequestState.STATE_DIALOG);
    }

    private void loadWebView(String content) {
        if (webView == null) {
            return;
        }
        content = content.replaceAll("<img", "<img style='max-width:100%;height:auto;'");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
    }

    private void initToolbar() {
        commonToolbar.setTitleText(getResources().getString(R.string.help_detail));
        commonToolbar.setOnCommonToolbarLeftClickListener(new CommonToolbar.CommonToolbarLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    @Override
    public void showToast(String str) {
        ToastUtil.ShowToast(str, this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setData(CommonJsonToBean<HelpDetailInfoBean> data) {
        String content = data.getData().getContent();
        loadWebView(content);
    }

    @Override
    public void setXState(LoadingState xState, String msg) {
        ToastUtil.ShowToast(msg, this);
    }

    @Override
    public void setRefresh(CommonJsonToBean<HelpDetailInfoBean> obj) {

    }

    @Override
    public void setLoadMore(CommonJsonToBean<HelpDetailInfoBean> obj) {

    }

    @Override
    protected void onDestroy() {
        if( webView!=null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }

            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();

        }
        super.onDestroy();
    }
}
