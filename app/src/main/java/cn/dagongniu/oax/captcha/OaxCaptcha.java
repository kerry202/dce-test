package cn.dagongniu.oax.captcha;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.language.MultiLanguageUtil;

/**
 * 网易滑块
 */
public class OaxCaptcha {

    Captcha mCaptcha = null;

    public Captcha getmCaptcha() {
        return mCaptcha;
    }

    public OaxCaptcha(Context context, CaptchaListener captchaListener) {
        mCaptcha = new Captcha(context);
        mCaptcha.setCaptchaId(Constant.CaptchId);
        mCaptcha.setCaListener(captchaListener);
        //可选:设置验证码语言为英文，默认为中文

        String languageStringType = MultiLanguageUtil.getInstance().getLanguageStringType();
        if(!languageStringType.equals("cn")){
            mCaptcha.setEnglishLanguage();
        }

        //可选：开启debug
        mCaptcha.setDebug(false);
        //可选：设置超时时间
        mCaptcha.setTimeout(10000);
        //设置验证码弹框的坐标位置: 只能设置left，top和宽度，高度为自动计算。默认无须设置为窗口居中。
        //mCaptcha.setPosition(1, 200, 1040, -1);
        //设置弹框时背景页面是否模糊，默认无须设置，默认显示弹框时背景页面模糊，Android默认风格。
        //mCaptcha.setBackgroundDimEnabled(false);
        //设置弹框时点击对话框之外区域是否自动消失，默认为消失。如果设置不自动消失请设置为false。
        //mCaptcha.setCanceledOnTouchOutside(false);
        //登陆操作
        //必填：初始化 captcha框架
    }

    public void Start(CaptchaTask captchaTask) {
        //必填：初始化 captcha框架
        mCaptcha.start();
        //关闭mLoginTask任务可以放在myCaptchaListener的onCancel接口中处理
        captchaTask.execute();
        //可直接调用验证函数Validate()，本demo采取在异步任务中调用（见UserLoginTask类中）
        //mCaptcha.Validate();
    }

    /***
     * ************************   举例
     */
    CaptchaListener myCaptchaListener = new CaptchaListener() {

        @Override
        public void onValidate(String result, String validate, String message) {
            //验证结果，valiadte，可以根据返回的三个值进行用户自定义二次验证
            if (validate.length() > 0) {
                //("验证成功，validate = " + validate);
            } else {
                //("验证失败：result = " + result + ", validate = " + validate + ", message = " + message);

            }
        }

        @Override
        public void closeWindow() {
            //请求关闭页面
            //("关闭页面");
        }

        @Override
        public void onError(String errormsg) {
            //出错
            //("错误信息：" + errormsg);
        }

        @Override
        public void onCancel() {
            //("取消线程");
            //用户取消加载或者用户取消验证，关闭异步任务，也可根据情况在其他地方添加关闭异步任务接口
//            if (mLoginTask != null) {
//                if (mLoginTask.getStatus() == AsyncTask.Status.RUNNING) {
//                    Log.i(TAG, "stop mLoginTask");
//                    mLoginTask.cancel(true);
//                }
//            }
        }

        @Override
        public void onReady(boolean ret) {
            //该为调试接口，ret为true表示加载Sdk完成
            if (ret) {
                //toastMsg("验证码sdk加载成功");
            }
        }
    };

}
