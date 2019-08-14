package com.shouyin.shouyin.service;

import com.shouyin.shouyin.model.City;
import com.shouyin.shouyin.model.Province;

import java.util.List;

/**
 * @author ssddp
 * @ClassNameCityService
 * @Description: 市Service
 * @date 2019/6/18 16:32
 * @Version 1.0
 **/
public interface CityService {

    //根据省查出相应市
    List<City> citySelect(Province province);

}
