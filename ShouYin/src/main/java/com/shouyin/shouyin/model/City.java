package com.shouyin.shouyin.model;

/**
 * @author ssddp
 * @ClassNamecity
 * @Description: 市
 * @date 2019/6/18 15:09
 * @Version 1.0
 **/
public class City {

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
        return "City{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
