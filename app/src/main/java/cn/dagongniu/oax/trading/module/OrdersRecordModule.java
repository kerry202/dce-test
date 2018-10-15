package cn.dagongniu.oax.trading.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.oax.assets.bean.PropertyShowBean;
import cn.dagongniu.oax.base.BaseModule;
import cn.dagongniu.oax.https.Http;
import cn.dagongniu.oax.https.HttpUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.trading.bean.OrdersRecordBean;
import cn.dagongniu.oax.utils.DebugUtils;

/**
 * 成交订单 module
 */
public class OrdersRecordModule extends BaseModule<String, OrdersRecordBean> {

    public OrdersRecordModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<OrdersRecordBean> onBaseDataListener, String... parm) {
    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<OrdersRecordBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String beginDate = parm[0];
        String endDate = parm[1];
        String marketId = parm[2];
        String type = parm[3];
        String pageNo = parm[4];
        String pageSize = parm[5];

        hashMap.put("beginDate", beginDate);                  //根据时间查询的开始日期 必须是‘yyyy-M
        hashMap.put("endDate", endDate);                      //根据时间查询的结束 必须是‘yyyy-MM-d
        hashMap.put("marketId", marketId);                    //交易对id
        hashMap.put("type", type);                            //1买入 2 卖出
        hashMap.put("pageNo", pageNo);                        //第N页 表示页码
        hashMap.put("pageSize", pageSize);                    //每页显示数据数条


        HttpUtils.getInstance().postLangIdToken(Http.ORDERSRECORD_FINDTRADEORDERLISTBYPAGE, hashMap, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    OrdersRecordBean ordersRecordBean = new Gson().fromJson(data, OrdersRecordBean.class);
                    onBaseDataListener.onNewData(ordersRecordBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String code) {
                onBaseDataListener.onError(code);
            }
        }, state);


    }
}
