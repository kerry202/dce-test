package cn.dagongniu.oax.assets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gjiazhe.wavesidebar.WaveSideBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.adapter.ContactsAdapter;
import cn.dagongniu.oax.assets.bean.CoinListBean;
import cn.dagongniu.oax.assets.bean.WTSearchBean;
import cn.dagongniu.oax.assets.presenter.CoinListPresenter;
import cn.dagongniu.oax.assets.view.ICoinListView;
import cn.dagongniu.oax.base.BaseActivity;
import cn.dagongniu.oax.customview.ClassicsHeader;
import cn.dagongniu.oax.customview.MyTradingToolbar;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.language.LanguageUtils;
import cn.dagongniu.oax.utils.AppConstants;
import cn.dagongniu.oax.utils.SortingUtils;
import cn.dagongniu.oax.utils.ToastUtil;


/**
 * 充值 提现搜索
 */
public class WithdTopSearchActivity extends BaseActivity implements ICoinListView, ContactsAdapter.MyClickListener, OnRefreshListener {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.search_toolbar)
    MyTradingToolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.side_bar)
    WaveSideBar sideBar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    ClassicsHeader mClassicsHeader;

    Intent intent;
    String stringExtra;
    Bundle bundle;
    ContactsAdapter contactsAdapter;
    CoinListPresenter coinListPresenter;
    private List<CoinListBean.DataBean> contacts_default = new ArrayList<>();
    private List<CoinListBean.DataBean> contacts = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withd_top_search;
    }

    @Override
    protected void initView() {
        super.initView();
        bundle = new Bundle();
        coinListPresenter = new CoinListPresenter(this, RequestState.STATE_REFRESH);
        coinListPresenter.getCoinListModule();
        intent = getIntent();
        stringExtra = intent.getStringExtra(AppConstants.SEARCH);
        if (stringExtra != null) {
            if (stringExtra.equals(AppConstants.TOPUP)) {//充值
                initToobar(R.string.assets_topup);
            } else if (stringExtra.equals(AppConstants.WITHDRAWAL)) {//提现
                initToobar(R.string.assets_withdrawal);
            }
        }
        initSmartRefresh();
        initEvent();
        initRecyc();
    }

    private void initSmartRefresh() {
        /**
         * 事件处理
         */
        mClassicsHeader = (ClassicsHeader) mRefreshLayout.getRefreshHeader();
        LanguageUtils.setHeaderLanguage(mClassicsHeader, this);
        mRefreshLayout.setOnRefreshListener(this);
    }

    private void initEvent() {
        etSearch.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                //s:变化后的所有字符
                // 输入框输入字段不为空
                if (!TextUtils.isEmpty(s)) {
                    String text = s.toString().trim();
                    if (!TextUtils.isEmpty(text)) {
                        if (contacts != null && contacts.size() > 0) {
                            // mListSearch 模糊搜索结果集合
                            contacts.clear();
                        }
                        // mList 需要模糊搜索的集合
                        for (CoinListBean.DataBean bean : contacts_default) {
                            // 判断javabean中是否包含搜索字段
                            if (bean.getCoinName().toLowerCase().contains(text.toLowerCase())) {
                                // 若包含，添加
                                contacts.add(bean);
                            }
                        }
                        // 给RecycleView设置搜索结果
                        setSearchDate(contacts);
                    }
                } else {
                    // 输入框输入字段为空
                    s.clear();
                    contacts.clear();
                    for (CoinListBean.DataBean bean : contacts_default) {
                        contacts.add(bean);
                    }
                    setSearchDate(contacts);
                }
            }


            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //S：变化后的所有字符；start：字符起始的位置；before: 变化之前的总字节数；count:变化后的字节数
            }

        });
    }

    public void setSearchDate(List<CoinListBean.DataBean> list) {
        contactsAdapter = new ContactsAdapter(list, R.layout.item_contacts, this);
        mRecyclerView.setAdapter(contactsAdapter);
    }

    private void initToobar(int titleName) {
        toolbar.setRightImgVisibility(true);
        toolbar.setTvLeftVisibility(true);
        toolbar.setSjVisibility(true);
        toolbar.setRightNameText(R.string.assets_record);
        toolbar.setTitleNameText(titleName);
        toolbar.setRightTvColor(getContext().getResources().getColor(R.color.df_gray_666));
        toolbar.setRightTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //历史记录
                toOtherActivity(WithTopRecordActivity.class);
            }
        });
        toolbar.setLeftMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 数据适配器
     */
    private void initRecyc() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i = 0; i < contacts.size(); i++) {

                    if (contacts.get(i).getCoinName().substring(0, 1).equals(index)) {
                        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    /**
     * 回调
     *
     * @param coinListBean
     */
    @Override
    public void setCoinListBeanData(CoinListBean coinListBean) {
        mRefreshLayout.finishRefresh();
        contacts = SortingUtils.AZSorting(coinListBean.getData());
        contacts_default = new ArrayList<>(contacts);
        contactsAdapter = new ContactsAdapter(contacts, R.layout.item_contacts, this);
        mRecyclerView.setAdapter(contactsAdapter);

    }

    @Override
    public void refreshErrer() {
        if (mRefreshLayout == null) {
            return;
        }
        mRefreshLayout.finishRefresh();
    }

    /**
     * 接口方法，响应ListView按钮点击事件
     */
    @Override
    public void clickListener(View v, int position) {
        if (stringExtra != null) {
            if (stringExtra.equals(AppConstants.TOPUP)) {//充值
                bundle.putString("MarketId", contacts.get(position).getCoinId() + "");
                bundle.putString("MarketName", contacts.get(position).getCoinName() + "");
                openActivity(TopupActivity.class, bundle);

            } else if (stringExtra.equals(AppConstants.WITHDRAWAL)) {//提现
                bundle.putString("MarketId", contacts.get(position).getCoinId() + "");
                bundle.putString("MarketName", contacts.get(position).getCoinName() + "");
                openActivity(WithdrawalActivity.class, bundle);
            }
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        contacts.clear();
        etSearch.setText("");
        coinListPresenter.getCoinListModule();
    }
}
