package cn.dagongniu.oax.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.main.adapter.BTCorETHMarketAdapter;

/**
 * BTC 市场
 */
public class BTCMarketFragmnet extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    BTCorETHMarketAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oax_home_btc_frament;
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyc();
    }

    /**
     * 适配器
     */
    private void initRecyc() {
        //屏蔽滑动事件
        //TODO NestedScrollView嵌套RecyclerView时滑动不流畅问题的解决办法
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setSmoothScrollbarEnabled(true);
//        layoutManager.setAutoMeasureEnabled(true);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setNestedScrollingEnabled(iv_false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BTCorETHMarketAdapter(getData(), OAXApplication.getContext());
        mRecyclerView.setAdapter(adapter);
        //adapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.market_item_header_layout, null));
        //adapter.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.item_footer_layout, null));
    }


    public List<String> getData() {
        List list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("10");list.add("10");
        list.add("10");
        list.add("10");
        list.add("10");
        list.add("10");

        return list;
    }
}
