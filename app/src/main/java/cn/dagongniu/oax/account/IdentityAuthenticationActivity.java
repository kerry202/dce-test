package cn.dagongniu.oax.account;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anlia.photofactory.factory.PhotoFactory;
import com.anlia.photofactory.result.ResultData;
import com.bumptech.glide.Glide;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.bean.CountryCodeBean;
import cn.dagongniu.oax.account.presenter.CountryCodePresenter;
import cn.dagongniu.oax.account.presenter.FileUpdoadPresenter;
import cn.dagongniu.oax.account.presenter.UserIdentityAuthenPresenter;
import cn.dagongniu.oax.account.view.BottomCertificateFragment;
import cn.dagongniu.oax.account.view.BottomCountriesFragment;
import cn.dagongniu.oax.account.view.IFileUpdoadView;
import cn.dagongniu.oax.account.view.IUserIdentityAuthenView;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.customview.CommonToolbar;
import cn.dagongniu.oax.customview.LQRPhotoSelectUtils;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.AppManager;
import cn.dagongniu.oax.utils.BitmapCompressionUtils;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.ToastUtil;
import cn.dagongniu.oax.utils.events.MyEvents;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import me.leefeng.promptlibrary.PromptButton;
import me.leefeng.promptlibrary.PromptButtonListener;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * 身份证上传
 */
public class IdentityAuthenticationActivity extends BaseActivity implements IView, BottomCountriesFragment.MyDialogFragment_Listener,
        BottomCertificateFragment.MyDialogCertificateFragmentListener, IFileUpdoadView, IUserIdentityAuthenView {
    private static final String TAG = "IdentityAuthenticationA";

    @BindView(R.id.commontoolbar)
    CommonToolbar commontoolbar;
    @BindView(R.id.nationality_container)
    AutoRelativeLayout nationalityContainer;
    @BindView(R.id.real_name_container)
    AutoRelativeLayout realNameContainer;
    @BindView(R.id.certificate_type_container)
    AutoRelativeLayout certificateTypeContainer;
    @BindView(R.id.certificate_number_container)
    AutoRelativeLayout certificateNumberContainer;
    @BindView(R.id.tv_Identity_Recognition)
    TextView tvIdentityRecognition;
    @BindView(R.id.full_face_container)
    AutoLinearLayout fullFaceContainer;
    @BindView(R.id.reverse_face_container)
    AutoLinearLayout reverseFaceContainer;
    @BindView(R.id.photo_container)
    AutoLinearLayout photoContainer;
    @BindView(R.id.tv_remind)
    TextView tvRemind;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.rl_countrycode)
    RelativeLayout rlCountryCode;
    @BindView(R.id.tv_countrycode_name)
    TextView tvCountTryCodeName;
    @BindView(R.id.tv_certificate_type)
    TextView tvCertificateType;
    @BindView(R.id.rl_certificate)
    RelativeLayout rlCertificate;
    @BindView(R.id.im_full_face_container)
    ImageView imFullFaceContainer;
    @BindView(R.id.im_reverse_face_container)
    ImageView imReverseFaceContainer;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.tv_number)
    EditText tvNumber;

    String shortName = "CN";
    CountryCodeBean countryCodeBean;
    CountryCodePresenter countryCodePresenter;
    UserIdentityAuthenPresenter userIdentityAuthenPresenter;
    BottomCountriesFragment bottomCountriesFragment;
    BottomCertificateFragment bottomCertificateFragment;
    FileUpdoadPresenter fileUpdoadPresenter;
    PhotoFactory photoFactory;
    int type = -1;
    boolean isFullFace = false;
    boolean isReverseFace = false;
    String fullFaceData = "";
    String ReverseFaceData = "";
    Uri outputUri1 = null;//上传照片路径地址

    @Override
    protected int getLayoutId() {
        return R.layout.activity_identity_authentication;
    }

    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;

    private void init() {
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                if (type == 0) {
                    outputUri1 = outputUri;
                    try {
                        FileInputStream fis = new FileInputStream(outputFile);
                        Bitmap imFullFaceContainerbm = BitmapFactory.decodeStream(fis);

                        File file = BitmapCompressionUtils.compressImage(imFullFaceContainerbm);

                        ArrayList<File> arrayList = new ArrayList<>();
                        arrayList.add(file);
                        fileUpdoadPresenter.UploadImg(arrayList, outputFile.getName(), "image", 0);
                    } catch (Exception e) {
                    }
                } else if (type == 1) {
                    outputUri1 = outputUri;
                    try {
                        FileInputStream fis = new FileInputStream(outputFile);
                        Bitmap imReverseFaceContainerbm = BitmapFactory.decodeStream(fis);

                        File file = BitmapCompressionUtils.compressImage(imReverseFaceContainerbm);

                        ArrayList<File> arrayList = new ArrayList<>();
                        arrayList.add(file);
                        fileUpdoadPresenter.UploadImg(arrayList, outputFile.getName(), "image", 1);
                    } catch (Exception e) {
                    }
                }

            }
        }, false);//true裁剪，false不裁剪

    }

    @Override
    protected void initView() {
        super.initView();
        initEnvent();
        initToolbar();
        photoFactory = new PhotoFactory(this);//(Context context)
        bottomCountriesFragment = new BottomCountriesFragment();
        bottomCertificateFragment = new BottomCertificateFragment();
        fileUpdoadPresenter = new FileUpdoadPresenter(this, RequestState.STATE_DIALOG);
        countryCodePresenter = new CountryCodePresenter(this, RequestState.STATE_REFRESH);
        countryCodePresenter.getCountryCodeModule();
        userIdentityAuthenPresenter = new UserIdentityAuthenPresenter(this, RequestState.STATE_DIALOG);

        setEditTextInhibitInputSpeChat(tvName);
    }

    private void initToolbar() {
        commontoolbar.setTitleText(getResources().getString(R.string.identity_authentication));
        commontoolbar.setOnCommonToolbarLeftClickListener(new CommonToolbar.CommonToolbarLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }


    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText
     */
    public static String setEditTextInhibitInputSpeChat(EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = editText.getText().toString();
                String str = stringFilter(editable.toString());
                if (!editable.equals(str)) {
                    editText.setText(str);
                    //设置新的光标所在位置
                    editText.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return null;
    }

    public static String stringFilter(String str11) {
        // 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str11);
        return m.replaceAll("").trim();
    }


    public void initEnvent() {
        tvSubmit.setClickable(false);
        tvName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkLegal(tvNumber.getText().toString(), s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        tvNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkLegal(tvName.getText().toString(), s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 效验合法性
     *
     * @param name
     * @param number
     */
    public void checkLegal(String name, String number) {
        if (isFullFace && isReverseFace && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
            tvSubmit.setBackgroundResource(R.drawable.login_btu_cancel_orange_bg);
            tvSubmit.setClickable(true);
        } else {
            tvSubmit.setBackgroundResource(R.drawable.login_btu_cancel_grey_bg);
            tvSubmit.setClickable(false);
        }
    }


    @OnClick({R.id.nationality_container, R.id.reverse_face_container, R.id.full_face_container, R.id.certificate_type_container, R.id.tv_submit, R.id.rl_countrycode, R.id.rl_certificate})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_countrycode:
                if (countryCodeBean != null) {
                    bottomCountriesFragment.setCountriesListBean(countryCodeBean.getData());
                    bottomCountriesFragment.show(getSupportFragmentManager(), "BottomCountriesFragment");
                }
                break;
            case R.id.rl_certificate:
                List<String> certificateList = new ArrayList<>();
                certificateList.add(this.getResources().getString(R.string.crad));
                certificateList.add(this.getResources().getString(R.string.passport));
                bottomCertificateFragment.setCertificateListBean(certificateList);
                bottomCertificateFragment.show(getSupportFragmentManager(), "BottomCertificateFragment");
                break;
            case R.id.tv_submit:
                userIdentityAuthenPresenter.getUserIdentityAuthenModule();
                break;
            case R.id.reverse_face_container:
                type = 1;
                choosePhotos(imReverseFaceContainer);
                break;
            case R.id.full_face_container://选择照片
                type = 0;
                choosePhotos(imFullFaceContainer);
                break;
        }
    }


    /**
     * 从照片中选取
     *
     * @param imageView
     */
    public void choosePhotos(ImageView imageView) {
        init();
        /********************底部AlertSheet*************/
        PromptButton cancle = new PromptButton(this.getResources().getString(R.string.cancel), null);
        cancle.setTextColor(Color.parseColor("#F8BE13"));
        cancle.setTextSize(getResources().getDimension(R.dimen.d20));
        PromptDialog promptDialog = new PromptDialog(this);
        promptDialog.showAlertSheet("", true, cancle,
                new PromptButton(this.getResources().getString(R.string.pictures), new PromptButtonListener() {
                    @Override
                    public void onClick(PromptButton promptButton) {
                        PermissionGen.with(IdentityAuthenticationActivity.this)
                                .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA
                                ).request();
                    }
                }), new PromptButton(this.getResources().getString(R.string.choose_photos), new PromptButtonListener() {
                    @Override
                    public void onClick(PromptButton promptButton) {
                        photoFactory.FromGallery()
                                .StartForResult(new PhotoFactory.OnResultListener() {
                                    @Override
                                    public void OnCancel() {
                                        Log.e(TAG, "取消从相册选择");
                                    }

                                    @Override
                                    public void OnSuccess(ResultData resultData) {
                                        if (imageView == imFullFaceContainer) {
                                            try {
                                                Bitmap imFullFaceContainerbm = resultData.GetBitmap();
                                                File file = BitmapCompressionUtils.compressImage(imFullFaceContainerbm);
                                                ArrayList<File> arrayList = new ArrayList<>();
                                                arrayList.add(file);
                                                fileUpdoadPresenter.UploadImg(arrayList, String.valueOf(System.currentTimeMillis()) + ".jpg", "image", 0);
                                            } catch (Exception e) {
                                            }

                                        } else if (imageView == imReverseFaceContainer) {
                                            try {
                                                Bitmap imReverseFaceContainerbm = resultData.GetBitmap();
                                                File file = BitmapCompressionUtils.compressImage(imReverseFaceContainerbm);
                                                ArrayList<File> arrayList = new ArrayList<>();
                                                arrayList.add(file);
                                                fileUpdoadPresenter.UploadImg(arrayList, String.valueOf(System.currentTimeMillis()) + ".jpg", "image", 1);
                                            } catch (Exception e) {
                                            }
                                        }
                                        Uri uri = resultData.GetUri();
                                        outputUri1 = uri;
                                    }
                                });
                    }
                }));
        promptDialog.getDefaultBuilder().backAlpha(150);
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);
        countryCodeBean = (CountryCodeBean) obj;
    }

    @Override
    public void setRefresh(Object obj) {
        super.setRefresh(obj);
        countryCodeBean = (CountryCodeBean) obj;
    }


    /**
     * 选中国家回调
     *
     * @param add
     */
    @Override
    public void getDataFrom_DialogFragment(String add, String shortName, String enName) {
        tvCountTryCodeName.setText(add + "(" + enName + ")");
        this.shortName = shortName;
    }

    @Override
    public void getDataFroCertificateFragment(String add) {
        tvCertificateType.setText(add);
    }

    /**
     * ------------------------------
     */

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void takePhoto() {
        mLqrPhotoSelectUtils.takePhoto();
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void selectPhoto() {
        mLqrPhotoSelectUtils.selectPhoto();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void showTip1() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void showTip2() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
        mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
    }

    public void showDialog() {
        //创建对话框创建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框显示小图标
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置标题
        builder.setTitle(this.getResources().getString(R.string.permissions));
        //设置正文
        builder.setMessage(this.getResources().getString(R.string.permissions_show));

        //添加确定按钮点击事件
        builder.setPositiveButton(this.getResources().getString(R.string.go_setting_hint), new DialogInterface.OnClickListener() {//点击完确定后，触发这个事件

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里用来跳到手机设置页，方便用户开启权限
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + IdentityAuthenticationActivity.this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //添加取消按钮点击事件
        builder.setNegativeButton(this.getResources().getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //使用构建器创建出对话框对象
        AlertDialog dialog = builder.create();
        dialog.show();//显示对话框
    }


    /**
     * 上传正面成功
     *
     * @param data
     */
    @Override
    public void setFullFaceContainerSuccess(String data) {
        Glide.with(IdentityAuthenticationActivity.this).load(outputUri1).into(imFullFaceContainer);
        isFullFace = true;
        checkLegal(tvName.getText().toString(), tvNumber.getText().toString());
        fullFaceData = data;

    }

    /**
     * 上传反面成功
     *
     * @param data
     */
    @Override
    public void setReverseFaceContainerSuccess(String data) {
        Glide.with(IdentityAuthenticationActivity.this).load(outputUri1).into(imReverseFaceContainer);
        isReverseFace = true;
        checkLegal(tvName.getText().toString(), tvNumber.getText().toString());
        ReverseFaceData = data;
    }

    /**
     * 上传正面失败
     *
     * @param data
     */
    @Override
    public void setFullFaceContainerfailure(String data) {
        isFullFace = false;
        checkLegal(tvName.getText().toString(), tvNumber.getText().toString());
        ToastUtil.ShowToast(data);
    }

    /**
     * 上传反面失败
     *
     * @param data
     */
    @Override
    public void setReverseContainerfailure(String data) {
        isReverseFace = false;
        checkLegal(tvName.getText().toString(), tvNumber.getText().toString());
        ToastUtil.ShowToast(data);
    }

    /**
     * 上传身份证需要 ***************************
     *
     * @return
     */
    @Override
    public String getIdName() {
        return tvName.getText().toString();
    }

    @Override
    public String getCardType() {
        String s = tvCertificateType.getText().toString();
        String crad = this.getResources().getString(R.string.crad);
        String passport = this.getResources().getString(R.string.passport);
        if (s.equals(passport)) {
            return "2";
        } else {
            return "1";
        }
    }

    @Override
    public String getCardNo() {
        return tvNumber.getText().toString();
    }

    @Override
    public String getIdImageA() {
        return fullFaceData;
    }

    @Override
    public String getIdImageB() {
        return ReverseFaceData;
    }

    @Override
    public String getCountry() {
        return shortName;
    }

    @Override
    public void isSueecss() {
        ToastUtil.ShowToast(this.getResources().getString(R.string.success));

        //发送身份证资料上传成功通知
        myEvents.status = MyEvents.status_ok;
        myEvents.status_type = MyEvents.User_Identity_Authen;
        myEvents.errmsg = this.getResources().getString(R.string.user_identity_authen_success);
        eventBus.post(myEvents);
        Logger.e(TAG, "发送身份证资料上传成功通知!");

        AppManager.remove(AuthenticateActivity.class.getSimpleName());
        AppManager.remove(AuthenticationStateActivity.class.getSimpleName());
        finish();

    }

    @Override
    public void isfailure(String msg) {
        ToastUtil.ShowToast(this.getResources().getString(R.string.success));

    }
}
