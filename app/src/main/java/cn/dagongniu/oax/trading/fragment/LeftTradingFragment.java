package cn.dagongniu.oax.trading.fragment;

import android.view.Gravity;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.base.BaseDialogFragment;
import cn.dagongniu.oax.base.BaseFragment;

/**
 * 交易侧滑更多
 */
public class LeftTradingFragment extends BaseDialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.TOP | Gravity.START);
        mWindow.setWindowAnimations(R.style.LeftDialog);
        mWindow.setLayout(mWidth * 2 / 3, mHeight);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.left_trading_fragment;
    }

}
