package com.shouyin.shouyin.mapper;

import com.shouyin.shouyin.model.City;
import com.shouyin.shouyin.model.Province;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ssddp
 * @ClassNameCityMapper
 * @Description: 市Mapper
 * @date 2019/6/18 15:16
 * @Version 1.0
 **/
@Mapper
public interface CityMapper {

        //根据省查出相应市
        List<City> citySelect(Province province);

}
