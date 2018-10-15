package cn.dagongniu.oax.captcha;

import android.content.Context;
import android.os.AsyncTask;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.LoginActivity;
import cn.dagongniu.oax.utils.ToastUtil;


/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class CaptchaTask extends AsyncTask<Void, Void, Boolean> {

    Captcha mCaptcha;
    CaptchaTaskBack captchaTaskBack;
    Context context;

    public CaptchaTask(Context context, Captcha mCaptcha, CaptchaTaskBack captchaTaskBack) {
        this.mCaptcha = mCaptcha;
        this.captchaTaskBack = captchaTaskBack;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        //可选：简单验证DeviceId、CaptchaId、Listener值
        return mCaptcha.checkParams();
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (success) {
            //必填：开始验证
            mCaptcha.Validate();

        } else {
            ToastUtil.ShowToast(context.getResources().getString(R.string.captcha_sdk_errer));
        }
    }

    @Override
    protected void onCancelled() {
        captchaTaskBack.onCaptchaTashBackCancelled();

    }

    public interface CaptchaTaskBack {
        public void onCaptchaTashBackCancelled();
    }

}
