package cn.dagongniu.oax.assets.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.assets.bean.UserCoinTopBean;
import cn.dagongniu.oax.assets.bean.UserCoinWithdrawalBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.utils.DebugUtils;
import cn.dagongniu.oax.utils.Logs;

/**
 * 提现 module
 */
public class UserCoinWithdrawalModule extends BaseModule<String, UserCoinWithdrawalBean> {

    public UserCoinWithdrawalModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<UserCoinWithdrawalBean> onBaseDataListener, String... parm) {
    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<UserCoinWithdrawalBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String coinId = parm[0];
        hashMap.put("coinId", coinId);                  //币种id

        HttpUtils.getInstance().getLangIdToKen(Http.USERCOIN_QUERYCOININFO + "/" + coinId, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                Logs.s("  提现数据:  "+data);
                DebugUtils.prinlnLog(data);
                try {
                    UserCoinWithdrawalBean userCoinWithdrawalBean = new Gson().fromJson(data, UserCoinWithdrawalBean.class);

                    Logs.s("  提现数据22:  "+userCoinWithdrawalBean);
                    onBaseDataListener.onNewData(userCoinWithdrawalBean);
                } catch (Exception e) {
                    Logs.s("  提现数据 Exception:  ");
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String code) {
                Logs.s("  提现数据 onError:  "+code);
                onBaseDataListener.onError(code);
            }
        }, state);

    }
}
