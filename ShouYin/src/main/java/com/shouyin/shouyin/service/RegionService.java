package com.shouyin.shouyin.service;

import com.shouyin.shouyin.model.City;
import com.shouyin.shouyin.model.Region;

import java.util.List;

/**
 * @author ssddp
 * @ClassNameRegionService
 * @Description: 区Service
 * @date 2019/6/18 16:32
 * @Version 1.0
 **/
public interface RegionService {

    //根据市查出相应区
    List<Region> regionSelect(City city);
}
