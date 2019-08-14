package com.shouyin.shouyin.model;

/**
 * @author ssddp
 * @ClassNameregion
 * @Description: 区
 * @date 2019/6/18 15:10
 * @Version 1.0
 **/
public class Region {

        private String area_code;
        private String space_name;
        private String province_code; //省code
        private String city_code; //市code

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getSpace_name() {
        return space_name;
    }

    public void setSpace_name(String space_name) {
        this.space_name = space_name;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    @Override
    public String toString() {
        return "Region{" +
                "area_code='" + area_code + '\'' +
                ", space_name='" + space_name + '\'' +
                ", province_code='" + province_code + '\'' +
                ", city_code='" + city_code + '\'' +
                '}';
    }
}
