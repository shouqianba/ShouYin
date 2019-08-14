package com.shouyin.shouyin.mapper;

import com.shouyin.shouyin.model.Province;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ssddp
 * @ClassNameChinaMapper
 * @Description: 省Mapper
 * @date 2019/6/14 15:28
 * @Version 1.0
 **/
@Mapper
public interface ProvinceMapper {

    //查全部省
    List<Province> provinceSelectAll();

}
