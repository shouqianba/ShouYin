package com.shouyin.shouyin.service;

import com.shouyin.shouyin.mapper.ProvinceMapper;
import com.shouyin.shouyin.model.Province;

import java.util.List;

/**
 * @author ssddp
 * @ClassNameProvinceService
 * @Description: 省Service
 * @date 2019/6/18 16:29
 * @Version 1.0
 **/
public interface ProvinceService{

    //查全部省
     List<Province> provinceSelectAll();
}
