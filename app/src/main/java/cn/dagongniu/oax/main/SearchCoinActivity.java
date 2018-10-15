package cn.dagongniu.oax.main;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dagongniu.oax.OAXApplication;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.constant.Constant;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.main.adapter.SearchCoinAdapter;
import cn.dagongniu.oax.main.adapter.SearchCoinHistoryAdapter;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.view.SearchCoinToolbar;
import cn.dagongniu.oax.utils.Logger;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.ToastUtil;

/**
 * 搜索
 */
public class SearchCoinActivity extends BaseActivity {

    private static final String TAG = "SearchCoinActivity";

    @BindView(R.id.commontoolbar)
    SearchCoinToolbar commontoolbar;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    @BindView(R.id.ll_historic_container)
    AutoLinearLayout llHistoricContainer;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.history_recyclerview)
    RecyclerView mHistoryRecyclerView;
    @BindView(R.id.rl_history)
    RelativeLayout rlHistory;

    List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> marketListBeans = new ArrayList<>();
    List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> marketListDefault;
    List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> marketListHistory;

    SearchCoinHistoryAdapter searchCoinHistoryAdapter;
    SearchCoinAdapter searchCoinAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_searchcoin;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();

        marketListDefault = OAXApplication.toMapCoinsInfoList();
        marketListBeans = OAXApplication.toMapCoinsInfoList();
        marketListHistory = OAXApplication.getCoinsInfoHistory();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchCoinAdapter = new SearchCoinAdapter(getContext());
        mHistoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        searchCoinHistoryAdapter = new SearchCoinHistoryAdapter(this);

        searchCoinHistoryAdapter.setNewData(marketListHistory);
        mHistoryRecyclerView.setAdapter(searchCoinHistoryAdapter);

        searchCoinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IndexPageBean.DataBean.AllMaketListBean.MarketListBean marketListBean = marketListBeans.get(position);
                OAXApplication.setCoinsInfoHistory(marketListBean);
                Logger.e(TAG, "添加缓存数据 ID为=" + marketListBean.getMarketId());
                SkipActivityUtil.skipToKLineActivity(marketListBean.getMarketId(), Constant.KLINE_MINTYPE_1, SearchCoinActivity.this);

            }
        });
        searchCoinHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IndexPageBean.DataBean.AllMaketListBean.MarketListBean marketListBean = marketListHistory.get(position);
                SkipActivityUtil.skipToKLineActivity(marketListBean.getMarketId(), Constant.KLINE_MINTYPE_1, SearchCoinActivity.this);

            }
        });
    }

    private void initToolbar() {
        commontoolbar.setOnFocusChangeListener(new SearchCoinToolbar.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    searchCoinAdapter.setNewData(marketListDefault);
                    mRecyclerView.setAdapter(searchCoinAdapter);
                    mHistoryRecyclerView.setVisibility(View.GONE);
                    llHistoricContainer.setVisibility(View.GONE);
                } else {
                    // 此处为失去焦点时的处理内容
                }

            }
        });
        commontoolbar.setOnListener(new SearchCoinToolbar.SearchCoinListener() {
            @Override
            public void onClickCancel() {
                finish();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //s:变化后的所有字符
                // 输入框输入字段不为空
                if (!TextUtils.isEmpty(s)) {
                    String text = s.toString().trim();
                    if (!TextUtils.isEmpty(text)) {
                        if (marketListBeans != null && marketListBeans.size() > 0) {
                            // mListSearch 模糊搜索结果集合
                            marketListBeans.clear();
                        }
                        // mList 需要模糊搜索的集合
                        for (IndexPageBean.DataBean.AllMaketListBean.MarketListBean bean : marketListDefault) {
                            // 判断javabean中是否包含搜索字段
                            if (bean.getCoinName().toLowerCase().contains(text.toLowerCase())) {
                                // 若包含，添加
                                marketListBeans.add(bean);
                            }
                        }
                        // 给RecycleView设置搜索结果
                        setSearchDate(marketListBeans);
                    }
                } else {
                    // 输入框输入字段为空
                    s.clear();
                    marketListBeans.clear();
                    for (IndexPageBean.DataBean.AllMaketListBean.MarketListBean bean : marketListDefault) {
                        marketListBeans.add(bean);
                    }
                    setSearchDate(marketListBeans);
                }
            }
        });
    }

    public void setSearchDate(List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> list) {
        searchCoinAdapter.setNewData(list);
        mRecyclerView.setAdapter(searchCoinAdapter);
    }

    @OnClick(R.id.iv_delete)
    public void onClicked() {
        SPUtils.remove(this, SPConstant.COINSINFO_HISTORY_LIST);
        ToastUtil.ShowToast(this.getResources().getString(R.string.clean_successful));
        marketListHistory = OAXApplication.getCoinsInfoHistory();
        searchCoinHistoryAdapter.setNewData(marketListHistory);
        searchCoinHistoryAdapter.notifyDataSetChanged();
    }


}
