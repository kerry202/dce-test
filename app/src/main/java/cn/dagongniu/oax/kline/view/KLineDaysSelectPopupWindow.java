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

public class KLineDaysSelectPopupWindow extends PopupWindow implements View.OnClickListener {
    Activity mActivity;
    private View view;
    private KLineDaysSelectPopupWindowClickListener mListener;
    private TextView mOneDay;
    private TextView mSevenDay;

    public KLineDaysSelectPopupWindow(Activity activity) {
        this.mActivity = activity;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.popuwindow_kline_days, null);
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

        mOneDay = (TextView) view.findViewById(R.id.tv_one_day);
        mSevenDay = (TextView) view.findViewById(R.id.tv_seven_day);

        mOneDay.setOnClickListener(this);
        mSevenDay.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        switch (v.getId()) {
            case R.id.tv_one_day:
                mListener.oneDay();
                break;
            case R.id.tv_seven_day:
                mListener.sevenDay();
                break;
            default:
                break;
        }
    }

    public interface KLineDaysSelectPopupWindowClickListener {
        void oneDay();

        void sevenDay();


    }

    public void setOnItemClickListener(KLineDaysSelectPopupWindowClickListener clickListener) {
        this.mListener = clickListener;
    }
}
