package cn.dagongniu.oax.main.fragment;

import android.app.Activity;
import android.content.Context;

import butterknife.OnClick;
import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseFragment;
import cn.dagongniu.oax.constant.SPConstant;
import cn.dagongniu.oax.constant.UMConstant;
import cn.dagongniu.oax.main.MainActivity;
import cn.dagongniu.oax.utils.SPUtils;
import cn.dagongniu.oax.utils.SkipActivityUtil;
import cn.dagongniu.oax.utils.um.UMManager;

public class GuidanceThirdFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guidance_third;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @OnClick(R.id.tv_skip)
    public void onClicked() {
        UMManager.onEvent(mContext, UMConstant.GuidanceActivity, UMConstant.GuidanceActivity_third_jump);
        SkipActivityUtil.guidanceSkipToMain(getActivity(), mContext);
    }
}
