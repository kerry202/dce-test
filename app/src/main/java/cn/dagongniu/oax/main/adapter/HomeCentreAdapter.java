package cn.dagongniu.oax.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.autolayout.AutoRelativeLayout;

import org.bouncycastle.asn1.x509.Holder;

import java.util.List;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.kline.bean.CommitteeBean;
import cn.dagongniu.oax.utils.ToastUtil;

/**
 * 中间指标显示适配器 (自适应)
 */
public class HomeCentreAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    Context context;//声明适配器中引用的上下文
    //将需要引用的图片和文字分别封装成数组
    private List<String> mDatas;
    private int screenWidth;//屏幕宽度

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    //通过构造方法初始化上下文
    public HomeCentreAdapter(Context context, List<String> list) {
        this.mDatas = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //获取屏幕高度
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int itemWidth = (dm.heightPixels) / 3;

        ViewHolder viewHolder = null;
        if (convertView == null) {
            mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.home_centre_item_layout, parent,
                    false);

            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) convertView
                    .findViewById(R.id.tv_home1_2);

            viewHolder.rl_main = convertView.findViewById(R.id.rl_main);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LinearLayout.LayoutParams gallery_lp = new LinearLayout.LayoutParams(itemWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
        viewHolder.rl_main.setLayoutParams(gallery_lp);


        viewHolder.mTextView.setText(mDatas.get(position));
        Log.d("getView", "getView: " + position);

        return convertView;
    }

    private final class ViewHolder {
        TextView mTextView;
        RelativeLayout rl_main;
    }


}

