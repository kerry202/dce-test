package cn.dagongniu.oax.utils.events;

import cn.dagongniu.oax.trading.bean.EntrustInfoBean;

public class UpdateEnstrustInfoEvent {
    public EntrustInfoBean bean;

    public UpdateEnstrustInfoEvent(EntrustInfoBean bean) {
        this.bean = bean;
    }
}
