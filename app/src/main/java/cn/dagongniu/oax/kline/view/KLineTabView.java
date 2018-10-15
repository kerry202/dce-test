package cn.dagongniu.oax.kline.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.dagongniu.oax.R;

public class KLineTabView extends RelativeLayout {

    private Context mContext;
    private View mView;
    private TextView mTvName;
    private View mTabLine;
    private int mSelectColor;
    private int mUnSelectColor;

    public KLineTabView(Context context) {
        this(context, null);
    }

    public KLineTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KLineTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.KLineTabView);
        String name = mTypedArray.getString(R.styleable.KLineTabView_kline_tab_name);
        boolean select = mTypedArray.getBoolean(R.styleable.KLineTabView_kline_tab_select, false);
        mSelectColor = mTypedArray.getColor(R.styleable.KLineTabView_kline_tab_select_color, getResources().getColor(R.color.kline_current_selected));
        mUnSelectColor = mTypedArray.getColor(R.styleable.KLineTabView_kline_tab_select_color, getResources().getColor(R.color.kline_tab_un_select));
        mView = View.inflate(context, R.layout.kline_tabview, null);
        addView(mView);
        //Tab描述
        mTvName = mView.findViewById(R.id.tv_name);
        mTabLine = mView.findViewById(R.id.tab_line);
        setSelectState(select, name);

        mTypedArray.recycle();
    }

    public void setSelectState(boolean select, String name) {
        if (select) {
            mTabLine.setVisibility(VISIBLE);
            mTvName.setTextColor(mSelectColor);
        } else {
            mTvName.setTextColor(mUnSelectColor);
            mTabLine.setVisibility(INVISIBLE);
        }
        if (mTvName != null && !TextUtils.isEmpty(name)) {
            mTvName.setText(name);
        }
    }
}
