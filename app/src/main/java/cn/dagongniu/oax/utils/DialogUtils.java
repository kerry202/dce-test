package cn.dagongniu.oax.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.math.BigDecimal;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.constant.SPConstant;

/**
 * dialog工具类
 */
public class DialogUtils {


    /**
     * 方法一：
     * setCanceledOnTouchOutside(false);调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
     * 方法二：
     * setCanceleable(false);调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
     */

    public interface onCommitListener {
        void onCommit(String phoneCode, String emailCode, String googleCode);
    }

    public interface onClickGetEmailListener {
        void onClickGetEmail();
    }

    public interface onClickGetPhoneListener {
        void onClickPhone();
    }

    public interface OnSureListener {
        void onSure();
    }


    /**
     * 验证
     *
     * @param context
     * @param isEmail
     * @param isPhone
     * @param isGoogle
     * @param email
     * @param phone
     * @param onCommitListener
     * @param clickGetEmailListener
     * @param clickGetPhoneListener
     * @return
     */
    public static Dialog getValidationDialog(Context context, boolean isEmail, boolean isPhone, boolean isGoogle,
                                             String email, String phone,
                                             final onCommitListener onCommitListener, final onClickGetEmailListener clickGetEmailListener,
                                             final onClickGetPhoneListener clickGetPhoneListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.validation_layout_dialog, null);

        RelativeLayout rlEmail = view.findViewById(R.id.rl_email);
        RelativeLayout rlPhone = view.findViewById(R.id.rl_phone);
        RelativeLayout rlGoogle = view.findViewById(R.id.rl_google);

        //隐藏显示
        rlEmail.setVisibility(isEmail ? View.GONE : View.VISIBLE);
        rlPhone.setVisibility(isPhone ? View.GONE : View.VISIBLE);
        rlGoogle.setVisibility(isGoogle ? View.GONE : View.VISIBLE);

        TextView tvEmail = view.findViewById(R.id.tv_email);
        tvEmail.setText(email);
        TextView tvPhone = view.findViewById(R.id.tv_phone);
        tvPhone.setText(phone);

        final EditText etEmail = view.findViewById(R.id.et_email);
        final EditText etPhone = view.findViewById(R.id.et_phone);
        final EditText etGoogle = view.findViewById(R.id.et_google);

        final TextView tv_email_get_code = view.findViewById(R.id.tv_email_get_code);
        final TextView tv_phone_get_code = view.findViewById(R.id.tv_phone_get_code);

        final Dialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();

        //提交
        Button commit = view.findViewById(R.id.bt_commit);
        commit.setClickable(false);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommitListener.onCommit(etPhone.getText().toString(), etEmail.getText().toString(), etGoogle.getText().toString());
            }
        });
        //邮箱
        final RelativeLayout rlGetEamil = view.findViewById(R.id.rl_get_email);
        rlGetEamil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneCodeUtils.getInstance().setDialogDownTime(60, rlGetEamil, tv_email_get_code);
                clickGetEmailListener.onClickGetEmail();
            }
        });
        //手机号码
        final RelativeLayout rlGetPhone = view.findViewById(R.id.rl_get_phone);
        rlGetPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneCodeUtils.getInstance().setDialogDownTime(60, rlGetPhone, tv_phone_get_code);
                clickGetPhoneListener.onClickPhone();
            }
        });
        commit.setEnabled(false);
        //提交按钮有效
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (CheckComitClickable(isEmail, isPhone, isGoogle, etEmail, etPhone, etGoogle)) {
                    commit.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                    commit.setEnabled(true);
                } else {
                    commit.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                    commit.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (CheckComitClickable(isEmail, isPhone, isGoogle, etEmail, etPhone, etGoogle)) {
                    commit.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                    commit.setEnabled(true);
                } else {
                    commit.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                    commit.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etGoogle.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (CheckComitClickable(isEmail, isPhone, isGoogle, etEmail, etPhone, etGoogle)) {
                    commit.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
                    commit.setEnabled(true);
                } else {
                    commit.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
                    commit.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    /**
     * 检测是否有效。 验证码 commit按钮是否可点击
     *
     * @param isEmail
     * @param isPhone
     * @param isGoogle
     * @param etEmail
     * @param etPhone
     * @param etGoogle
     * @return
     */
    public static boolean CheckComitClickable(boolean isEmail, boolean isPhone, boolean isGoogle, EditText etEmail, EditText etPhone, EditText etGoogle) {
        boolean isCheckCommit = false;

        String emailStr = etEmail.getText().toString();
        String phoneStr = etPhone.getText().toString();
        String googleStr = etGoogle.getText().toString();

        //三种存在的情况
        if (!isEmail && !isPhone && !isGoogle) {
            if (emailStr.length() == 6 && phoneStr.length() == 6 && googleStr.length() == 6) {
                return true;
            } else {
                return false;
            }
        } else
            //邮箱跟手机
            if (!isEmail && !isPhone) {
                if (emailStr.length() == 6 && phoneStr.length() == 6) {
                    return true;
                } else {
                    return false;
                }
            } else
                //手机跟google
                if (!isPhone && !isGoogle) {
                    if (phoneStr.length() == 6 && googleStr.length() == 6) {
                        return true;
                    } else {
                        return false;
                    }
                } else
                    //google跟邮箱
                    if (!isGoogle && !isEmail) {
                        if (googleStr.length() == 6 && emailStr.length() == 6) {
                            return true;
                        } else {
                            return false;
                        }
                    } else
                        //邮箱
                        if (!isEmail) {
                            if (emailStr.length() == 6) {
                                return true;
                            } else {
                                return false;
                            }
                        } else
                            //手机
                            if (!isPhone) {
                                if (phoneStr.length() == 6) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } else
                                //google
                                if (!isGoogle) {
                                    if (googleStr.length() == 6) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }

        return isCheckCommit;
    }

    /**
     * 验证Touch ID
     *
     * @param context
     * @return
     */
    public static Dialog getVerifyTouchIDDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_verify_touchid, null);
        final Dialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();
        TextView cancel = view.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }


    /**
     * 获取 确认/取消 dialog
     *
     * @param context
     * @param des
     * @param onSureListener
     * @return
     */
    public static Dialog getSureAndCancelDialog(Context context, int des, final OnSureListener onSureListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.sure_and_cancel_dialog, null);
        final Dialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();
        TextView cancel = view.findViewById(R.id.tv_cancel);
        TextView sure = view.findViewById(R.id.tv_sure);
        TextView content = view.findViewById(R.id.tv_content);
        RelativeLayout rl_cancel = view.findViewById(R.id.rl_cancel);
        RelativeLayout rl_sure = view.findViewById(R.id.rl_sure);
        content.setText(content.getResources().getString(des));

        rl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        rl_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSureListener.onSure();
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    public static Dialog getRemindDialog(Context context, int title, int des, int sureDes) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_remind, null);
        final Dialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView sure = view.findViewById(R.id.tv_sure);
        TextView content = view.findViewById(R.id.tv_content);

        tvTitle.setText(context.getResources().getString(title));
        content.setText(content.getResources().getString(des));
        content.setText(context.getResources().getString(des));

        sure.setText(context.getResources().getString(sureDes));
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }


    public interface onClickCancelListener {
        void onCancel();
    }

    public interface onClickToListener {
        void onTo();
    }

    /**
     * 去登陆弹框 （邮箱）
     *
     * @param context
     * @param titleName
     * @param content           内容
     * @param onClickToListener
     * @return
     */
    public static Dialog getToLoginDialog(Context context, int titleName, int content,
                                          final onClickToListener onClickToListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_login_dialog_layout, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvContent = view.findViewById(R.id.tv_content);

        tvTitle.setText(titleName);
        tvContent.setText(content);

        RelativeLayout rlCancel = view.findViewById(R.id.rl_cancel);
        RelativeLayout rlGo = view.findViewById(R.id.rl_go);

        final Dialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();

        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        rlGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToListener.onTo();
            }
        });

        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }


    /**
     * 第三方 弹框 img加text
     *
     * @param context
     * @param img
     * @param showStirng
     */
    public static void showDialog(Context context, int img, String showStirng) {
        final KProgressHUD hud;
        ImageView imageView = new ImageView(context);
        imageView.setBackgroundResource(img);//R.drawable.correct

        hud = KProgressHUD.create(context)
                .setCustomView(imageView)
                .setAnimationSpeed(200)
                .setLabel(showStirng)//R.string.assets_withdrawal_successful
                .show();
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hud.dismiss();
            }
        }, 1500);

    }

    /**
     * 第三方 弹框
     *
     * @param context
     * @param showStirng
     */
    public static void showTextDialog(Context context, String showStirng) {
        final KProgressHUD hud;
        ImageView imageView = new ImageView(context);

        hud = KProgressHUD.create(context)
                .setCustomView(imageView)
                .setAnimationSpeed(200)
                .setLabel(showStirng)//R.string.assets_withdrawal_successful
                .show();
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hud.dismiss();
            }
        }, 1500);

    }


    /**
     * 等级介绍
     *
     * @param context
     * @return
     */
    public static Dialog getLvDialog(Context context) {
        String lv1 = (String) SPUtils.getParam(context, SPConstant.LEVEL1_BTC, "0");
        String lv2 = (String) SPUtils.getParam(context, SPConstant.LEVEL2_BTC, "0");

        View view = LayoutInflater.from(context).inflate(R.layout.lv_layout_dialog, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);

        TextView tvLv1 = view.findViewById(R.id.tv_lv1);
        TextView tvLv2 = view.findViewById(R.id.tv_lv2);

        String tvLv1Str = String.format(context.getResources().getString(R.string.lv_1), lv1);
        String tvLv2Str = String.format(context.getResources().getString(R.string.lv_2), lv2);

        tvLv1.setText(tvLv1Str);
        tvLv2.setText(tvLv2Str);

        RelativeLayout rlGo = view.findViewById(R.id.rl_go);

        final Dialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();


        rlGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }
}
