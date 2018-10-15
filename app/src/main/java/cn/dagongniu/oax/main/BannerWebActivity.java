package cn.dagongniu.oax.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.customview.MyTradingToolbar;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.main.bean.NoticeCenterReadDetailBean;
import cn.dagongniu.oax.main.presenter.NoticeCenterReadDetailPresenter;
import cn.dagongniu.oax.main.view.INoticeCenterReadDetailView;
import cn.dagongniu.oax.utils.ToastUtil;


/**
 * 首页banner WebView
 */
public class BannerWebActivity extends BaseActivity {

    private static final String TAG = "WebActivity";

    @BindView(R.id.web_toolbar)
    MyTradingToolbar tradingToolbar;

    private WebView webview;
    private WebSettings mWebSettings;
    private View mErrorView;

    LinearLayout webParentView = null;
    Intent intent;
    Bundle bundle;
    String BannerUrl = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        super.initView();
        webview = (WebView) findViewById(R.id.main_webview);
        webParentView = (LinearLayout) webview.getParent();
        initToolber();
    }

    @Override
    protected void initData() {
        super.initData();
        bundle = new Bundle();
        intent = this.getIntent();
        BannerUrl = intent.getStringExtra("BannerUrl");
        setUpView(BannerUrl);
    }

    /**
     * 设置头部
     */
    private void initToolber() {
        tradingToolbar.setTitleNameText("");
        tradingToolbar.setRightImgVisibility(true);
        tradingToolbar.setRightTvVisibility(true);
        tradingToolbar.setRightTvColor(getContext().getResources().getColor(R.color.df_font));
        tradingToolbar.setLeftMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setUpView(String url) {
        //加载需要显示的网页
        webview.loadUrl(url);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        mWebSettings = webview.getSettings();
        mWebSettings.setJavaScriptEnabled(true);  //允许加载javascript
        mWebSettings.setSupportZoom(false);     //允许缩放
        mWebSettings.setBuiltInZoomControls(false); //原网页基础上缩放
        mWebSettings.setUseWideViewPort(false);   //任意比例缩放
        webview.setWebViewClient(webClient); //设置Web视图
        webview.setWebChromeClient(webChromeClient);
    }

    WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            tradingToolbar.setTitleNameText(title);
        }

    };

    /***
     * 设置Web视图的方法
     */
    WebViewClient webClient = new WebViewClient() {//处理网页加载失败时
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            showErrorPage();//显示错误页面
        }
    };
    boolean mIsErrorPage;

    protected void showErrorPage() {

        initErrorPage();//初始化自定义页面
        while (webParentView.getChildCount() > 1) {
            webParentView.removeViewAt(0);
        }
        @SuppressWarnings("deprecation")
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.FILL_PARENT);
        webParentView.addView(mErrorView, 0, lp);
        mIsErrorPage = true;
    }

    /****
     * 把系统自身请求失败时的网页隐藏
     */
    protected void hideErrorPage() {
        mIsErrorPage = false;
        while (webParentView.getChildCount() > 1) {
            webParentView.removeViewAt(0);
        }
    }

    /***
     * 显示加载失败时自定义的网页
     */
    protected void initErrorPage() {
        if (mErrorView == null) {
            mErrorView = View.inflate(this, R.layout.activity_error, null);
            RelativeLayout layout = (RelativeLayout) mErrorView.findViewById(R.id.online_error_btn_retry);
            layout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                }
            });
            mErrorView.setOnClickListener(null);
        }
    }

}
