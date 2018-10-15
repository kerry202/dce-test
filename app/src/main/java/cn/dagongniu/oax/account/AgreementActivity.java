package cn.dagongniu.oax.account;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import java.util.HashMap;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.HelpDetailInfoBean;
import cn.dagongniu.oax.account.presenter.HelpDetailPresenter;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.base.OAXIViewBean;
import cn.dagongniu.oax.customview.CommonToolbar;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.RequestState;

/**
 * 用户协议
 */
public class AgreementActivity extends BaseActivity implements OAXIViewBean<HelpDetailInfoBean> {

    @BindView(R.id.commontoolbar)
    CommonToolbar commontoolbar;

    private WebView webview;
    private WebSettings mWebSettings;
    LinearLayout webParentView = null;
    HelpDetailPresenter helpDetailPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void initView() {
        super.initView();
        commontoolbar.setTitleText(getResources().getString(R.string.user_agreement));
        commontoolbar.setOnCommonToolbarLeftClickListener(new CommonToolbar.CommonToolbarLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
        webview = (WebView) findViewById(R.id.main_webview);
        webParentView = (LinearLayout) webview.getParent();

        helpDetailPresenter = new HelpDetailPresenter(this);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 1);
        helpDetailPresenter.getData(map, RequestState.STATE_DIALOG);
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

        }
    };
    boolean mIsErrorPage;


    @Override
    public void setData(CommonJsonToBean<HelpDetailInfoBean> data) {
        if (data.getSuccess()) {
            setUpView(data.getData().getContent());
        }
    }

    @Override
    public void setRefresh(CommonJsonToBean data) {

    }

    @Override
    public void setLoadMore(CommonJsonToBean data) {

    }

    @Override
    public void showToast(String msg) {

    }
}
