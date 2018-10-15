package cn.dagongniu.oax.utils;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.dagongniu.oax.R;

public class PhoneCodeUtils {

    private static PhoneCodeUtils sUtils;

    public static PhoneCodeUtils getInstance() {
        if (sUtils == null) {
            sUtils = new PhoneCodeUtils();
        }
        return sUtils;
    }


    /**
     * 倒计时
     *
     * @param time
     */
    public void setDownTime(int time, final RelativeLayout rl, final TextView tvCode) {

        time = time - 1;
        final int time1 = time;
        rl.setEnabled(false);

        if (time1 != 0) {
            tvCode.setText("" + time1 + "S");
            rl.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
        } else {
            rl.setEnabled(true);
            tvCode.setText(R.string.registered_cx_get_cede);
            rl.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
        }

        rl.postDelayed(new Runnable() {            @Override
            public void run() {
                if (time1 != 0) {
                    setDownTime(time1, rl, tvCode);
                }
            }
        }, 1000);
    }


    public void setBg(){

    }

    /**
     * 倒计时
     *
     * @param time
     */
    public void setDialogDownTime(int time, final RelativeLayout rl, final TextView tvCode) {

        time = time - 1;
        final int time1 = time;
        rl.setEnabled(false);

        if (time1 != 0) {
            tvCode.setText("" + time1 + "S");
            rl.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
        } else {
            rl.setEnabled(true);
            tvCode.setText(R.string.registered_cx_get_cede);
            rl.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
        }

        rl.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (time1 != 0) {
                    setDialogDownTime(time1, rl, tvCode);
                }
            }
        }, 1000);
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String regex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(number);
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num) || m.matches();
        }
    }

}
