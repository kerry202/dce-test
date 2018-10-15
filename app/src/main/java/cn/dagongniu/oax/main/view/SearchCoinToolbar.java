package cn.dagongniu.oax.main.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.dagongniu.oax.R;

/**
 * 搜索头部 自定义
 */
public class SearchCoinToolbar extends Toolbar implements View.OnClickListener {


    private EditText mSearch;
    private SearchCoinListener mListener;
    private OnFocusChangeListener OnActionListener;

    public SearchCoinToolbar(Context context) {
        this(context, null);
    }

    public SearchCoinToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchCoinToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View v = LayoutInflater.from(context).inflate(R.layout.toolbar_searchcoin, this, false);
        addView(v);
        mSearch = v.findViewById(R.id.et_search);
        TextView cancel = v.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(this);
        initTextChangeListener();
    }

    private void initTextChangeListener() {
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mListener.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mListener.onTextChanged(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                mListener.afterTextChanged(s);
            }
        });

        mSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                OnActionListener.onFocusChange(v,hasFocus);
            }
        });

    }


    @Override
    public void onClick(View v) {
        mListener.onClickCancel();
    }

    public interface OnFocusChangeListener {
        void  onFocusChange(View v, boolean hasFocus);
    }

    public interface SearchCoinListener {
        void onClickCancel();

        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);
    }

    public void setOnFocusChangeListener(OnFocusChangeListener OnActionListener) {
        this.OnActionListener = OnActionListener;
    }

    public void setOnListener(SearchCoinListener listener) {
        this.mListener = listener;
    }
}
