package com.shouyin.shouyin.model;

/**
 * @author ssddp
 * @ClassNameNotifyResult
 * @Description: a
 * @date 2019/8/21 11:53
 * @Version 1.0
 **/
public class NotifyResult {
    private   String  sn;//订单号
    private   String  client_sn;//商户订单号
    private   String  order_status;//订单最终状态

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getClient_sn(){return  client_sn;}
    public void setClient_sn(String client_sn){this.client_sn = client_sn;}

    public String getOrder_status(){return order_status;}
    public void setOrder_status(String order_status){this.order_status = order_status;}

    // 私有构造
    private NotifyResult() {}

    private static NotifyResult single = null;

    public static NotifyResult getInstance() {

        // 等同于 synchronized public static Singleton3 getInstance()
        synchronized(NotifyResult.class){
            // 注意：里面的判断是一定要加的，否则出现线程安全问题
            if(single == null){
                single = new NotifyResult();
            }
        }
        return single;
    }
}
