package cn.dagongniu.oax.account;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.GoogleCodeBean;
import cn.dagongniu.oax.account.presenter.BindGoogleCodePresenter;
import cn.dagongniu.oax.account.presenter.GoogleCodePresenter;
import cn.dagongniu.oax.account.view.IGoogleCodeView;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.customview.CommonToolbar;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.PageToolUtils;
import cn.dagongniu.oax.utils.events.MyEvents;

/**
 * 谷歌验证
 */
public class GoogleVerifyActivity extends BaseActivity implements IGoogleCodeView {

    private static final String TAG = "GoogleVerifyActivity";

    @BindView(R.id.commontoolbar)
    CommonToolbar commontoolbar;
    @BindView(R.id.tv_input_google_key)
    TextView tvInputGoogleKey;
    @BindView(R.id.et_input_key)
    EditText etInputKey;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.tv_google_key)
    TextView tvGoogleKey;
    @BindView(R.id.im_google)
    ImageView imGoogle;
    @BindView(R.id.rv_click_coyp)
    TextView tvCoyp;
    private Dialog validationDialog;

    GoogleCodePresenter googleCodePresenter;
    BindGoogleCodePresenter bindGoogleCodePresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_google_verify;
    }

    @Override
    protected void initView() {
        super.initView();
        tvOpen.setEnabled(false);
        initToolbar();
        initTextWatcher();
    }

    @Override
    protected void initData() {
        super.initData();
        googleCodePresenter = new GoogleCodePresenter(this, RequestState.STATE_DIALOG);
        googleCodePresenter.getGoogleCodeModule();
        bindGoogleCodePresenter = new BindGoogleCodePresenter(this, RequestState.STATE_DIALOG);
    }

    private void initToolbar() {
        commontoolbar.setTitleText(getResources().getString(R.string.google_verify));
        commontoolbar.setOnCommonToolbarLeftClickListener(new CommonToolbar.CommonToolbarLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    private void initTextWatcher() {
        etInputKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = s.toString().trim();
                if (content.length() == 6) {
                    tvOpen.setEnabled(true);
                    tvOpen.setBackgroundColor(getResources().getColor(R.color.df_F5BE23));
                } else {
                    tvOpen.setBackgroundColor(getResources().getColor(R.color.df_cccccc));
                    tvOpen.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.tv_open)
    public void onClicked() {
        bindGoogleCodePresenter.getBindGoogleCodeModule();
    }

    /**
     * google请求回调
     *
     * @param googleCodeData
     */
    @Override
    public void setGoogleCodeData(GoogleCodeBean googleCodeData) {
        tvGoogleKey.setText(googleCodeData.getData().getSecret());

        Glide.with(this)
                .load("http://qr.liantu.com/api.php?&w=200&text=" + googleCodeData.getData().getQRBarcodeUrl())
                .apply(new RequestOptions().placeholder(R.drawable.banner_df).error(R.drawable.banner_df).dontAnimate().centerCrop())
                .into(imGoogle);

    }

    @Override
    public void bindSuccess() {
        //发送绑定google成功 刷新
        myEvents.status = MyEvents.status_ok;
        myEvents.status_type = MyEvents.Bind_Google_Success;
        myEvents.errmsg = this.getResources().getString(R.string.bind_google_seuccss);
        eventBus.post(myEvents);
        Logger.e(TAG, "发送绑定google成功消息!");
        //回调之前页面刷新
        this.setResult(400);
        this.finish();
    }

    @Override
    public String getGoogleKey() {
        return tvGoogleKey.getText().toString();
    }

    @Override
    public String getGoogleCode() {
        return etInputKey.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.rv_click_coyp)
    public void onClick() {
        PageToolUtils.CopyText(this, tvGoogleKey);
    }
}
