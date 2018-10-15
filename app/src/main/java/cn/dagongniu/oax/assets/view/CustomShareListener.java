package cn.dagongniu.oax.assets.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import cn.dagongniu.oax.assets.OaxRedEnvelopeDetailsActivity;
import cn.dagongniu.oax.assets.RedEnvelopeActivity;
import cn.dagongniu.oax.assets.ShareSuccessActivity;
import cn.dagongniu.oax.utils.AppManager;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.events.MyEvents;

/**
 * 分享回调
 */
public class CustomShareListener implements UMShareListener {

    private static final String TAG = "CustomShareListener";

    private WeakReference<RedEnvelopeActivity> mActivity;
    Bundle bundle = new Bundle();
    MyEvents myEvents;
    EventBus eventBus;

    public CustomShareListener(Activity activity, MyEvents myEvents, EventBus eventBus) {
        mActivity = new WeakReference(activity);
        this.myEvents = myEvents;
        this.eventBus = eventBus;
    }

    @Override
    public void onStart(SHARE_MEDIA platform) {

    }

    @Override
    public void onResult(SHARE_MEDIA platform) {

        if (platform.name().equals("WEIXIN_FAVORITE")) {
            Toast.makeText(mActivity.get(), " 收藏成功啦", Toast.LENGTH_SHORT).show();
        } else {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                //Toast.makeText(mActivity.get(),  " 分享成功啦", Toast.LENGTH_SHORT).show();

                AppManager.remove(OaxRedEnvelopeDetailsActivity.class.getSimpleName());
                AppManager.remove(ShareSuccessActivity.class.getSimpleName());
                AppManager.remove(RedEnvelopeActivity.class.getSimpleName());
                //红包分享成功啦
                myEvents.status = MyEvents.status_ok;
                myEvents.status_type = MyEvents.ShareSuccess;
                myEvents.errmsg = "红包分享成功啦";
                eventBus.post(myEvents);
                Logger.e(TAG, "发送红包分享成功啦通知!");
//
//                Intent intent = new Intent(mActivity.get(), ShareSuccessActivity.class);
//                intent.putExtra("SuccessType", 0);
//                mActivity.get().startActivity(intent);
            }
        }
    }

    @Override
    public void onError(SHARE_MEDIA platform, Throwable t) {
        if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                && platform != SHARE_MEDIA.EMAIL
                && platform != SHARE_MEDIA.FLICKR
                && platform != SHARE_MEDIA.FOURSQUARE
                && platform != SHARE_MEDIA.TUMBLR
                && platform != SHARE_MEDIA.POCKET
                && platform != SHARE_MEDIA.PINTEREST

                && platform != SHARE_MEDIA.INSTAGRAM
                && platform != SHARE_MEDIA.GOOGLEPLUS
                && platform != SHARE_MEDIA.YNOTE
                && platform != SHARE_MEDIA.EVERNOTE) {
            //Toast.makeText(mActivity.get(), " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCancel(SHARE_MEDIA platform) {
        //Toast.makeText(mActivity.get(), " 分享取消了", Toast.LENGTH_SHORT).show();
    }

}
