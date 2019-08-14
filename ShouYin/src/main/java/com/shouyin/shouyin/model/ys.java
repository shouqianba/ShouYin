package com.shouyin.shouyin.model;

/**
 * @author ssddp
 * @ClassNameys
 * @Description: a
 * @date 2019/4/17 20:23
 * @Version 1.0
 **/
public class ys {

    private   String  zdh;//终端号
    private   String  my;//终端密钥

    public String getZdh() {
        return zdh;
    }

    public void setZdh(String zdh) {
        this.zdh = zdh;
    }

    public String getMy() {
        return my;
    }

    public void setMy(String my) {
        this.my = my;
    }

    // 私有构造
    private ys() {}

    private static ys single = null;

    public static ys getInstance() {

        // 等同于 synchronized public static Singleton3 getInstance()
        synchronized(ys.class){
            // 注意：里面的判断是一定要加的，否则出现线程安全问题
            if(single == null){
                single = new ys();
            }
        }
        return single;
    }
}
