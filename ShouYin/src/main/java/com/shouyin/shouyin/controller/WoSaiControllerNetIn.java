package com.shouyin.shouyin.controller;

import com.shouyin.shouyin.HttpPrxy2;
import com.shouyin.shouyin.HttpPrxyNetIn;
import com.shouyin.shouyin.mapper.CityMapper;
import com.shouyin.shouyin.mapper.RegionMapper;
import com.shouyin.shouyin.model.City;
import com.shouyin.shouyin.model.Province;
import com.shouyin.shouyin.model.Region;
import com.shouyin.shouyin.service.ProvinceService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * @author ssddp
 * @ClassNameWoSaiControllerNetIn
 * @Description: 商户入网接口
 * @date 2019/5/22 16:37
 * @Version 1.0
 **/
@Controller
@RequestMapping("/NetIn")
public class WoSaiControllerNetIn  {

    HttpPrxyNetIn httpPrxy2 = new HttpPrxyNetIn();

    Province province = new Province();

    City city = new City();

    Region region = new Region();

    @Autowired
    ProvinceService provinceService;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    RegionMapper regionMapper;

    @RequestMapping("/netin")
    public String netin(Model model){

        List<Province> provinceList =  provinceService.provinceSelectAll();
        province.setCode(provinceList.get(0).getCode());
        List<City> cityList =  cityMapper.citySelect(province);
        city.setCode(cityList.get(0).getCode());
        List<Region> regionList =  regionMapper.regionSelect(city);

        model.addAttribute("provinceList",provinceList);
        model.addAttribute("cityList",cityList);
        model.addAttribute("regionList",regionList);

        System.out.println("所有省:"+provinceService.provinceSelectAll());
        System.out.println("北京市市:"+cityMapper.citySelect(province));
        System.out.println("北京区:"+regionMapper.regionSelect(city));
        return "netin";
    }

    /**
     * 下拉框省市区
     * @param model
     * @return
     */
    @RequestMapping("/Pcr")
    @ResponseBody
    public Map Pcr(Model model, String  Province){
        province.setCode(Province);
        List<City> cityList =  cityMapper.citySelect(province);
        city.setCode(cityList.get(0).getCode());
        List<Region> regionList =  regionMapper.regionSelect(city);

        System.out.println("下拉框市："+cityMapper.citySelect(province));
        System.out.println("下拉框区："+regionMapper.regionSelect(city));

       /* model.addAttribute("cityList",cityList);
        model.addAttribute("regionList",regionList);*/
        Map<String,Object> map = new HashMap<>();
        map.put("cityList",cityList);
        map.put("regionList",regionList);

        return map;
    }

    /**
     * 下拉框省市区2
     * @param model
     * @return
     */
    @RequestMapping("/Pcr2")
    @ResponseBody
    public Map Pcr2(Model model, String City){
        city.setCode(City);
        List<Region> regionList =  regionMapper.regionSelect(city);

        System.out.println("下拉框区："+regionMapper.regionSelect(city));

        Map<String,Object> map = new HashMap<>();
        map.put("regionList",regionList);
        return map;
    }


    @RequestMapping("/create")
    public JSONObject create(String name, String contact_name
            , String contact_cellphone, String area, String street_address
            , Integer account_type, String bank_card, String bank_card_image
            , String bank_name, String bank_area, String branch_name, String holder
            , String legal_person_name, String business_license_photo, String tax_payer_id
            , String id_type, String identity, String holder_id_front_photo, String holder_id_back_photo
            , String brand_photo, String indoor_photo, String outdoor_photo){

        System.out.println("aaa:"+bank_card_image);
        return null;
    }


    /**
     * 开户银行
     * @param bank_card
     * @return
     */
    @RequestMapping("/banks")
    @ResponseBody
    public  String banks(String bank_card){
        String result = httpPrxy2.banks(bank_card);
        System.out.println("banks.toString():"+result.toString());
        return result;
    }

    /**
     * 开户支行
     * @param bank_name 开户银行名
     * @param bank_area 开户地区编号
     * @return
     */
    @RequestMapping("/branches")
    @ResponseBody
    public  Object[] branches(String bank_name,String bank_area){
        String result = httpPrxy2.branches(bank_name,bank_area);
        System.out.println("branches.toString():"+result.toString());

        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(result);//把转为json对象
        String arry = jsonObject.getJSONObject("biz_response").get("data").toString();//获取biz_response.data的值
        JSONArray jsonArray = JSONArray.fromObject(arry);//把biz_response.data的值转为json数组对象
        Object[] strs = jsonArray.toArray();//json转为数组
        return strs;
    }

    /**
     * 文件上传
     * @param file 	图片base64编码
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public  String upload(String file){
        String result = httpPrxy2.upload("");
        System.out.println("upload.toString():"+result.toString());
        return result;
    }

    /**
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     * @return
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
     */
    public  boolean generateImage(String imgStr, String path) {
            return true;
    }

    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    @RequestMapping("/ImageStr")
    @ResponseBody
    public  String getImageStr(String imgFile) {

        String result = httpPrxy2.getImageStr(imgFile);
        System.out.println("imgFile:"+imgFile);
       // System.out.println("result:"+result);
        return result;
    }
}
