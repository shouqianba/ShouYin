package com.shouyin.shouyin.model;

/**
 * @author ssddp
 * @ClassNameProvince
 * @Description: 省
 * @date 2019/6/18 15:02
 * @Version 1.0
 **/
public class Province {

        private  String code;
        private  String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Province{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
