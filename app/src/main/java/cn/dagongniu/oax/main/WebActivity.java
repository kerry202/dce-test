package cn.dagongniu.oax.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.customview.MyTradingToolbar;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.main.bean.NoticeCenterReadDetailBean;
import cn.dagongniu.oax.main.presenter.NoticeCenterReadDetailPresenter;
import cn.dagongniu.oax.main.view.INoticeCenterReadDetailView;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.ToastUtil;


/**
 * webview 公共
 */
public class WebActivity extends BaseActivity implements INoticeCenterReadDetailView {

    private static final String TAG = "WebActivity";

    @BindView(R.id.web_toolbar)
    MyTradingToolbar tradingToolbar;

    private WebView webview;
    private WebSettings mWebSettings;
    private View mErrorView;

    LinearLayout webParentView = null;
    Intent intent;
    String ArticleID = "";
    String ArticleType = "";
    NoticeCenterReadDetailPresenter noticeCenterReadDetailPresenter;
    Bundle bundle;
    boolean More = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        super.initView();
        webview = (WebView) findViewById(R.id.main_webview);
        webParentView = (LinearLayout) webview.getParent();

    }

    @Override
    protected void initData() {
        super.initData();
        bundle = new Bundle();
        intent = this.getIntent();
        More = intent.getBooleanExtra("More", false);
        ArticleID = intent.getStringExtra("ArticleID");
        ArticleType = intent.getStringExtra("ArticleType");
        initToolber();
        noticeCenterReadDetailPresenter = new NoticeCenterReadDetailPresenter(this, RequestState.STATE_DIALOG);
        if (ArticleID != null && !ArticleID.equals("")) {
            noticeCenterReadDetailPresenter.getNoticeCenterReadDetailModule();
        } else {
            ToastUtil.ShowToast(this.getResources().getString(R.string.announcement_content_no_id));
        }

    }

    /**
     * 设置头部
     */
    private void initToolber() {
        tradingToolbar.setTitleNameText(R.string.announcement);
        tradingToolbar.setRightImgVisibility(true);
        if(More){
            tradingToolbar.setRightNameText(R.string.announcement_more);
        }else{
            tradingToolbar.setRightTvVisibility(true);
        }

        tradingToolbar.setRightTvColor(getContext().getResources().getColor(R.color.df_font));
        tradingToolbar.setLeftMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tradingToolbar.setRightTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("ArticleType", ArticleType);
                openActivity(AnnouncementMoreActivity.class, bundle);
            }
        });
    }

    private void setUpView(String url) {
        url = url.replaceAll("<img", "<img style='max-width:100%;height:auto;'");
        //加载需要显示的网页
        webview.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        mWebSettings = webview.getSettings();
        mWebSettings.setJavaScriptEnabled(true);  //允许加载javascript
        mWebSettings.setSupportZoom(false);     //允许缩放
        mWebSettings.setBuiltInZoomControls(false); //原网页基础上缩放
        mWebSettings.setUseWideViewPort(false);   //任意比例缩放
        webview.setWebViewClient(webClient); //设置Web视图
    }

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

    @Override
    public String getReadDetail() {
        return ArticleID;
    }

    @Override
    public void setNoticeCenterReadDetailData(NoticeCenterReadDetailBean noticeCenterReadDetailData) {
        setUpView(noticeCenterReadDetailData.getData().getContent());
    }
}
