package com.shouyin.shouyin.service.impl;

import com.shouyin.shouyin.mapper.RegionMapper;
import com.shouyin.shouyin.model.City;
import com.shouyin.shouyin.model.Region;
import com.shouyin.shouyin.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ssddp
 * @ClassNameRegionServiceImpl
 * @Description: 区ServiceImpl
 * @date 2019/6/18 16:41
 * @Version 1.0
 **/
@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionMapper regionMapper;

    //根据市查出相应区
    @Override
    public List<Region> regionSelect(City city){
        return  regionMapper.regionSelect(city);
    }
}
