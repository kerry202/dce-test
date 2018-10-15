package cn.dagongniu.oax.main.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.kline.bean.CommitteeBean;
import cn.dagongniu.oax.main.bean.NoticeCenterMoreBean;
import cn.dagongniu.oax.main.module.NoticeCenterReadMoreModule;
import cn.dagongniu.oax.utils.DateUtils;

/**
 * 委托 fragment适配器
 */
public class AnnouncementMoreAdapter extends BaseQuickAdapter<NoticeCenterMoreBean.DataBean.ListBean, BaseViewHolder> {

    public AnnouncementMoreAdapter() {
        super(R.layout.announcenment_more_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeCenterMoreBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getName());

        String tiem = DateUtils.formatDate(item.getReleaseTime());
        helper.setText(R.id.tv_releasetime, tiem);
    }
}
