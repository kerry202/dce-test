package cn.dagongniu.oax.customview;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dagongniu.oax.R;


/**
 * Created by 输入框加减
 */

public class EditTextView extends FrameLayout {

    private static final String TAG = "EditTextView";
    @BindView(R.id.tv_jian)
    TextView tvJian;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_jia)
    TextView tvJia;

    private Context mContext;

    public EditTextView(Context context) {
        super(context);
        mContext = context;
    }

    public EditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public EditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        etCode.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });
    }
}
