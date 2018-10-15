package cn.dagongniu.oax.account.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.dagongniu.oax.R;

public class MineFragmentItemView extends RelativeLayout {

    private Context mContext;
    private View mView;

    public MineFragmentItemView(Context context) {
        this(context, null);
    }

    public MineFragmentItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MineFragmentItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.MineFragmentItemView);
        String itemDes = mTypedArray.getString(R.styleable.MineFragmentItemView_mine_item_des);
        int itemIcon = mTypedArray.getResourceId(R.styleable.MineFragmentItemView_mine_item_icon, -1);
        boolean isShowSplitLine = mTypedArray.getBoolean(R.styleable.MineFragmentItemView_mine_is_show_split_line, true);
        mView = View.inflate(context, R.layout.minefragment_itemview, null);
        addView(mView);

        //图标
        ImageView ivItemIcon = mView.findViewById(R.id.iv_item_icon);
        if (itemIcon != -1) {
            Drawable itemIconDrawable = getResources().getDrawable(itemIcon);
            ivItemIcon.setImageDrawable(itemIconDrawable);
        }

        //描述
        TextView tvItemDes = mView.findViewById(R.id.iv_item_des);
        if (tvItemDes != null) {
            tvItemDes.setText(itemDes);
        }

        //分割线
        View splitLine = mView.findViewById(R.id.split_line);
        if (isShowSplitLine) {
            splitLine.setVisibility(VISIBLE);
        } else {
            splitLine.setVisibility(GONE);
        }

        mTypedArray.recycle();
    }
}
