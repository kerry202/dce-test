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
import cn.dagongniu.oax.account.adapter.CertificateListAdapter;
import cn.dagongniu.oax.account.adapter.CountriesListAdapter;
import cn.dagongniu.oax.account.bean.CountryCodeBean;
import cn.dagongniu.oax.assets.bean.CoinAddressListBean;
import cn.dagongniu.oax.base.BaseDialogFragment;

/**
 * 证件类型
 */
public class BottomCertificateFragment extends BaseDialogFragment {


    @BindView(R.id.add_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_close)
    AutoRelativeLayout rlClose;
    Unbinder unbinder;

    CertificateListAdapter certificateListAdapter;
    List<String> countryList;
    BottomCertificateFragment.MyDialogCertificateFragmentListener myDialogFragment_Listener;

    public void setCertificateListBean(List<String> countryList) {
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
        mWindow.setLayout(mWidth, mHeight / 4);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            myDialogFragment_Listener = (BottomCertificateFragment.MyDialogCertificateFragmentListener) activity;
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
        certificateListAdapter = new CertificateListAdapter(getActivity());
        certificateListAdapter.setNewData(countryList);
        mRecyclerView.setAdapter(certificateListAdapter);

        certificateListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String listitem = (String) adapter.getData().get(position);
                myDialogFragment_Listener.getDataFroCertificateFragment(listitem);
                BottomCertificateFragment.this.dismiss();
            }
        });
    }

    // 回调接口，用于传递数据给Activity -------
    public interface MyDialogCertificateFragmentListener {
        void getDataFroCertificateFragment(String add);
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
