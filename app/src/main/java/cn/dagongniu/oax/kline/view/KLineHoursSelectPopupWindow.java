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

public class KLineHoursSelectPopupWindow extends PopupWindow implements View.OnClickListener {
    Activity mActivity;
    private View view;
    private KLineHoursSelectPopupWindowClickListener mListener;
    private TextView mOneHour;
    private TextView mFourHour;
    private TextView mEightHour;

    public KLineHoursSelectPopupWindow(Activity activity) {
        this.mActivity = activity;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.popuwindow_kline_hours, null);
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

        mOneHour = (TextView) view.findViewById(R.id.tv_one_hour);
        mFourHour = (TextView) view.findViewById(R.id.tv_four_hour);
        mEightHour = (TextView) view.findViewById(R.id.tv_eight_hour);

        mOneHour.setOnClickListener(this);
        mFourHour.setOnClickListener(this);
        mEightHour.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        switch (v.getId()) {
            case R.id.tv_one_hour:
                mListener.oneHour();
                break;
            case R.id.tv_four_hour:
                mListener.fourHour();
                break;
            case R.id.tv_eight_hour:
                mListener.eightHour();
                break;
            default:
                break;
        }
    }

    public interface KLineHoursSelectPopupWindowClickListener {
        void oneHour();

        void fourHour();

        void eightHour();

    }

    public void setOnItemClickListener(KLineHoursSelectPopupWindowClickListener clickListener) {
        this.mListener = clickListener;
    }
}
