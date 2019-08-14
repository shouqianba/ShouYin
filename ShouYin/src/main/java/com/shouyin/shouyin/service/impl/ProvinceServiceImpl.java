package com.shouyin.shouyin.service.impl;

import com.shouyin.shouyin.mapper.ProvinceMapper;
import com.shouyin.shouyin.model.Province;
import com.shouyin.shouyin.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ssddp
 * @ClassNameProvinceServiceImpl
 * @Description: 省ServiceImpl
 * @date 2019/6/18 16:33
 * @Version 1.0
 **/
@Service
public class ProvinceServiceImpl  implements ProvinceService {

    @Autowired
    private ProvinceMapper provinceMapper;

    //查全部省
    @Override
    public List<Province> provinceSelectAll() {
        return provinceMapper.provinceSelectAll();
    }
}
