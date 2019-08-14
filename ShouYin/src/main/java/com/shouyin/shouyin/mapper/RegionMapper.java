package com.shouyin.shouyin.mapper;

import com.shouyin.shouyin.model.City;
import com.shouyin.shouyin.model.Region;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ssddp
 * @ClassNameRegionMapper
 * @Description: 区Mapper
 * @date 2019/6/18 15:27
 * @Version 1.0
 **/
@Mapper
public interface RegionMapper {

        //根据市查出相应区
        List<Region> regionSelect(City city);
}
