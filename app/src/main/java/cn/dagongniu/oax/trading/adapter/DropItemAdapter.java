package cn.dagongniu.oax.trading.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.kline.bean.CommitteeBean;
import cn.dagongniu.oax.main.adapter.HomeCentreAdapter;

/**
 * DropEditText 适配器
 */
public class DropItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    Context context;//声明适配器中引用的上下文
    private List<String> mDatas;
    private int screenWidth;//屏幕宽度

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    //通过构造方法初始化上下文
    public DropItemAdapter(Context context, List<String> list) {
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

        DropItemAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.dropedit_item_layout, parent,
                    false);
            viewHolder = new DropItemAdapter.ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.mTextView = convertView.findViewById(R.id.tv_name);
        } else {
            viewHolder = (DropItemAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.mTextView.setText(mDatas.get(position));
        Log.d("getView", "getView: " + position);

        return convertView;
    }

    private final class ViewHolder {
        TextView mTextView;
    }

}
