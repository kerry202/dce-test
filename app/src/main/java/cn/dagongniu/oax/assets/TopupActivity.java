package cn.dagongniu.oax.assets;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.UserCoinTopBean;
import cn.dagongniu.oax.assets.presenter.UserCoinTopPresenter;
import cn.dagongniu.oax.assets.view.IUserCoinTopView;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.customview.MyTradingToolbar;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.ImgUtils;
import cn.dagongniu.oax.utils.PageToolUtils;
import cn.dagongniu.oax.utils.QRCode;
import cn.dagongniu.oax.utils.ToastUtil;

/**
 * 充值
 */
public class TopupActivity extends BaseActivity implements IUserCoinTopView {

    @BindView(R.id.topup_toolbar)
    MyTradingToolbar toolbar;
    @BindView(R.id.im_code)
    ImageView imCode;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.ll_tag)
    LinearLayout llTag;
    @BindView(R.id.rl_save_img)
    RelativeLayout rlSaveImg;
    @BindView(R.id.tv_name)
    TextView tvName;

    UserCoinTopPresenter userCoinTopPresenter;
    Intent intent;
    String MarketId = null;
    String MarketName = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topup;
    }

    @Override
    protected void initView() {
        super.initView();
        intent = this.getIntent();
        MarketId = intent.getStringExtra("MarketId");
        MarketName = intent.getStringExtra("MarketName");
        tvName.setText(MarketName);
        initToobar();
        initCode();
        userCoinTopPresenter = new UserCoinTopPresenter(this, RequestState.STATE_DIALOG);
        userCoinTopPresenter.getUserCoinTopModule();
    }

    private void initCode() {
        //imView.setImageBitmap(QRCode.createQRCode("http://www.tmtpost.com/2536837.html"));
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private void initToobar() {
        toolbar.setRightImgVisibility(true);
        toolbar.setTvLeftVisibility(true);
        toolbar.setSjVisibility(true);
        toolbar.setRightNameText(R.string.assets_record);
        toolbar.setTitleNameText(R.string.assets_topup);
        toolbar.setRightTvColor(getContext().getResources().getColor(R.color.df_gray_666));
        toolbar.setRightTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //历史记录
                toOtherActivity(WithTopRecordActivity.class);
            }
        });
        toolbar.setLeftMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 回调
     *
     * @param userCoinTopBean
     */
    @Override
    public void setUserCoinTopData(UserCoinTopBean userCoinTopBean) {
        tvAddress.setText(userCoinTopBean.getData().getAddress());
        if (userCoinTopBean.getData().getTag() != null) {
            tvLabel.setText(userCoinTopBean.getData().getTag());
            llTag.setVisibility(View.VISIBLE);
        } else {
            llTag.setVisibility(View.GONE);
        }
        imCode.setImageBitmap(QRCode.createQRCode(userCoinTopBean.getData().getAddress()));
    }

    @Override
    public String getCoinId() {
        return MarketId;
    }

    /**
     * 参数有误-去登陆
     *
     * @param msg
     */
    @Override
    public void goLogin(String msg) {
        ToastUtil.ShowToast(msg);
    }

    @OnClick({R.id.rl_copy_add, R.id.rl_copy_tag, R.id.rl_save_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_copy_add://复制地址
                PageToolUtils.CopyText(this, tvAddress);
                break;
            case R.id.rl_copy_tag://复制Tag
                PageToolUtils.CopyText(this, tvLabel);
                break;
            case R.id.rl_save_img://保存图片
                if (imCode.getDrawable() != null) {
                    Bitmap bm = ((BitmapDrawable) ((ImageView) imCode).getDrawable()).getBitmap();
                    ImgUtils.requestPermission(this);
                    if (ImgUtils.saveImageToGallery(this, bm)) {
                        ToastUtil.ShowToast(this.getResources().getString(R.string.save_success));
                    } else {
                        ToastUtil.ShowToast(this.getResources().getString(R.string.save_failure));
                    }
                }
                break;
        }
    }
}
