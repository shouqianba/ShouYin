package com.shouyin.shouyin.service.impl;

import com.shouyin.shouyin.mapper.CityMapper;
import com.shouyin.shouyin.model.City;
import com.shouyin.shouyin.model.Province;
import com.shouyin.shouyin.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ssddp
 * @ClassNameCityServiceImpl
 * @Description: 市ServiceImpl
 * @date 2019/6/18 16:38
 * @Version 1.0
 **/
@Service
public class CityServiceImpl implements CityService {

    @Autowired
   private CityMapper cityMapper;

    ///根据省查出相应市
    @Override
    public List<City> citySelect(Province province){

        return  cityMapper.citySelect(province);
    }

}
