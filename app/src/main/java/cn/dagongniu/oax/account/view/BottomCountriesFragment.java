package cn.dagongniu.oax.account.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.account.adapter.CountriesListAdapter;
import cn.dagongniu.oax.account.bean.CountryCodeBean;
import cn.dagongniu.oax.assets.bean.CoinAddressListBean;
import cn.dagongniu.oax.assets.view.BottomAddressFragment;
import cn.dagongniu.oax.base.BaseDialogFragment;

/**
 * 选中国家
 */
public class BottomCountriesFragment extends BaseDialogFragment {


    @BindView(R.id.add_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_close)
    AutoRelativeLayout rlClose;
    Unbinder unbinder;

    CountriesListAdapter countriesListAdapter;
    List<CountryCodeBean.DataBean> countryList;
    BottomCountriesFragment.MyDialogFragment_Listener myDialogFragment_Listener;

    public void setCountriesListBean(List<CountryCodeBean.DataBean> countryList) {
        this.countryList = countryList;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.bottom_countries_fragment_layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.BOTTOM);
        mWindow.setWindowAnimations(R.style.BottomDialog);
        mWindow.setLayout(mWidth, mHeight / 3);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            myDialogFragment_Listener = (BottomCountriesFragment.MyDialogFragment_Listener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implementon MyDialogFragment_Listener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        countriesListAdapter = new CountriesListAdapter(getActivity());
        countriesListAdapter.setNewData(countryList);
        mRecyclerView.setAdapter(countriesListAdapter);

        countriesListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CountryCodeBean.DataBean listitem = (CountryCodeBean.DataBean) adapter.getData().get(position);
                myDialogFragment_Listener.getDataFrom_DialogFragment(listitem.getCnName(),listitem.getShortName(),listitem.getEnName());
                BottomCountriesFragment.this.dismiss();
            }
        });
    }

    // 回调接口，用于传递数据给Activity -------
    public interface MyDialogFragment_Listener {
        void getDataFrom_DialogFragment(String add,String shortName,String enName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.rl_close)
    public void onClick() {
        this.dismiss();
    }
}
