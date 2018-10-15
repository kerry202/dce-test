package cn.dagongniu.oax.assets.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.CoinListBean;

/**
 * 充值提现搜索  适配器
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> implements View.OnClickListener {

    private List<CoinListBean.DataBean> contacts;
    private int layoutId;
    private MyClickListener mListener;

    public ContactsAdapter(List<CoinListBean.DataBean> contacts, int layoutId, MyClickListener listener) {
        this.contacts = contacts;
        this.layoutId = layoutId;
        this.mListener = listener;
    }

    //响应按钮点击事件,调用子定义接口，并传入View
    @Override
    public void onClick(View v) {
        mListener.clickListener(v, (int) v.getTag());
    }


    //自定义接口，用于回调按钮点击事件到Activity
    public interface MyClickListener {
        public void clickListener(View v, int position);
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        CoinListBean.DataBean contact = contacts.get(position);
        if (position == 0 || !contacts.get(position - 1).getCoinName().substring(0, 1).equals(contacts.get(position).getCoinName().substring(0, 1))) {
            holder.tvIndex.setVisibility(View.VISIBLE);
            holder.tvIndex.setText(contacts.get(position).getCoinName().substring(0, 1));
        } else {
            holder.tvIndex.setVisibility(View.GONE);
        }

        holder.rlClick.setOnClickListener(this);
        holder.rlClick.setTag(position);

        holder.tvName.setText(contact.getCoinName());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIndex;
        public TextView tvName;
        public RelativeLayout rlClick;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            tvIndex = (TextView) itemView.findViewById(R.id.tv_index);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            rlClick = (RelativeLayout) itemView.findViewById(R.id.rl_click);
        }
    }
}