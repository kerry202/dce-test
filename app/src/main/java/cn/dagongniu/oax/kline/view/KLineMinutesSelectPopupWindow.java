package cn.dagongniu.oax.kline.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.dagongniu.oax.R;


/**
 * Created by ink on 2017/11/14.
 */

public class KLineMinutesSelectPopupWindow extends PopupWindow implements View.OnClickListener {
    Activity mActivity;
    private View view;
    private KLineMinutesSelectPopupWindowClickListener mListener;
    private TextView mOneMin;
    private TextView mFiveMin;
    private TextView mTenMin;
    private TextView mFifteenMin;
    private TextView mThirtyMin;

    public KLineMinutesSelectPopupWindow(Activity activity) {
        this.mActivity = activity;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.popuwindow_fullscreenminutesselect, null);
        this.setContentView(view);
        initView();
        // 设置SelectPicPopupWindow弹出窗体可否点击
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        // 刷新状态
        this.update();
    }

    private void initView() {

        mOneMin = (TextView) view.findViewById(R.id.tv_one_minute);
        mFiveMin = (TextView) view.findViewById(R.id.tv_five_minute);
        mTenMin = (TextView) view.findViewById(R.id.tv_ten_minute);
        mFifteenMin = (TextView) view.findViewById(R.id.tv_fifteen_minute);
        mThirtyMin = (TextView) view.findViewById(R.id.tv_thirty_minute);
        mOneMin.setOnClickListener(this);
        mFiveMin.setOnClickListener(this);
        mTenMin.setOnClickListener(this);
        mFifteenMin.setOnClickListener(this);
        mThirtyMin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        switch (v.getId()) {
            case R.id.tv_one_minute:
                mListener.oneMin();
                break;
            case R.id.tv_five_minute:
                mListener.fiveMin();
                break;
            case R.id.tv_ten_minute:
                mListener.tenMin();
                break;
            case R.id.tv_fifteen_minute:
                mListener.fifteenMin();
                break;
            case R.id.tv_thirty_minute:
                mListener.thirtyMin();
                break;
            default:
                break;
        }
    }

    public interface KLineMinutesSelectPopupWindowClickListener {
        void oneMin();

        void fiveMin();

        void tenMin();

        void fifteenMin();

        void thirtyMin();
    }

    public void setOnItemClickListener(KLineMinutesSelectPopupWindowClickListener clickListener) {
        this.mListener = clickListener;
    }
}
