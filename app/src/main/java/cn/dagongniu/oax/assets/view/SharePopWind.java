package cn.dagongniu.oax.assets.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.OaxRedEnvelopeDetailsActivity;
import cn.dagongniu.oax.assets.RedEnvelopeActivity;
import cn.dagongniu.oax.customview.popwindow.BasePopupWindow;
import cn.dagongniu.oax.utils.DensityUtils;
import cn.dagongniu.oax.utils.PageToolUtils;

/**
 * 分享
 */
public class SharePopWind extends BasePopupWindow {

    Context context;
    private UMShareListener mShareListener;

    public SharePopWind(Context context, OnShareWxClickListener onShareWxClickListener,
                        OnSharePyqClickListener onSharePyqClickListener,
                        OnShareQQClickListener onShareQQClickListener,
                        OnShareCopyClickListener onShareCopyClickListener) {
        super(context);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_share_layout, null);

        setContentView(view);
        RelativeLayout rl_wx = view.findViewById(R.id.rl_wx);
        RelativeLayout rl_pyq = view.findViewById(R.id.rl_pyq);
        RelativeLayout rl_qq = view.findViewById(R.id.rl_qq);
        RelativeLayout rl_dd = view.findViewById(R.id.rl_copy);

        int[] wg = DensityUtils.getWidthHeight(context);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ButterKnife.bind(this, view);

        rl_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareWxClickListener.OnShareWx();
            }
        });

        rl_pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSharePyqClickListener.OnSharePyq();
            }
        });
        rl_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareQQClickListener.OnShareQQ();
            }
        });
        rl_dd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareCopyClickListener.OnShareCopy();
            }
        });
    }

    public SharePopWind(Context context, OnShareClickListener onShareClickListener, OnShareCopyClickListener onShareCopyClickListener) {
        super(context);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_share_layout, null);
        this.mShareListener = mShareListener;
        setContentView(view);
        RelativeLayout rl_wx = view.findViewById(R.id.rl_wx);
        RelativeLayout rl_pyq = view.findViewById(R.id.rl_pyq);
        RelativeLayout rl_qq = view.findViewById(R.id.rl_qq);
        RelativeLayout rl_dd = view.findViewById(R.id.rl_copy);

        int[] wg = DensityUtils.getWidthHeight(context);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ButterKnife.bind(this, view);

        rl_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareGo(SHARE_MEDIA.WEIXIN, onShareClickListener);
            }
        });

        rl_pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareGo(SHARE_MEDIA.WEIXIN_CIRCLE, onShareClickListener);
            }
        });
        rl_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareGo(SHARE_MEDIA.QQ, onShareClickListener);
            }
        });
        rl_dd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareCopyClickListener.OnShareCopy();
            }
        });
    }

    public void shareGo(SHARE_MEDIA var1, OnShareClickListener onShareClickListener) {
        onShareClickListener.OnShare(var1);
    }

    public void showPop(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
    }

    @OnClick({R.id.rl_cancel})
    public void onClicked(View view) {
        this.dismiss();
    }

    /**
     * 微信好友
     */
    public interface OnShareWxClickListener {
        public void OnShareWx();
    }

    /**
     * 微信朋友圈
     */
    public interface OnSharePyqClickListener {
        public void OnSharePyq();
    }

    /**
     * QQ
     */
    public interface OnShareQQClickListener {
        public void OnShareQQ();
    }

    /**
     * 钉钉
     */
    public interface OnShareCopyClickListener {
        public void OnShareCopy();
    }

    /**
     * 面板分享
     */
    public interface OnShareClickListener {
        public void OnShare(SHARE_MEDIA var1);
    }
}
