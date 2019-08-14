package com.shouyin.shouyin.controller;

import com.shouyin.shouyin.HttpProxyPos;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author ssddp
 * @ClassNameWoSaiControllerPos
 * @Description: a
 * @date 2019/7/12 13:36
 * @Version 1.0
 **/
@Controller
@RequestMapping("/POS")
public class WoSaiControllerPos {

    HttpProxyPos pos = new HttpProxyPos();

    /**
     * @param store_name     商户门店名称
     * @param workstation_sn 门店收银编号 如果没有传入0
     * @param check_sn       商户订单号 在商户系统中唯一
     * @param amount         订单价格 精确到分
     * @param currency       币种 如“156”for CNY
     * @param subject        订单主题
     * @param description    订单描述
     * @param operator       操作员
     * @param customer       客户信息
     * @param industry_code  行业代码 0:零售 1:酒店 2:餐饮 4:教育
     * @param pos_info       本接口对接的对端信息
     * @param resolution     是否支持拆单 1:不支持 2:支持
     * @param request_id
     * @return
     */
    @RequestMapping("/pos")
    @ResponseBody
    public String pos(String store_name, String workstation_sn,
                      String check_sn, String sales_time, String amount, String currency,
                      String subject, String description, String operator, String customer, String industry_code,
                      String pos_info, String resolution, String reflect, String request_id) throws JSONException {
        store_name = "像猫扑向你";
        workstation_sn = "0";
        amount = "100";
        currency = "156";
        subject = "llp";
        description = "llp";
        operator = "llp";
        customer = "llp";
        industry_code = "0";
        pos_info = "0";
        resolution = "1";
        reflect = "1";

        String result = pos.pos(store_name, workstation_sn, check_sn,
                amount, currency, subject, description, operator, customer, industry_code, pos_info, resolution, request_id, reflect);
        return result;
    }

    /**
     * 订单取消
     *
     * @param original_workstation_sn 原始门店收银编号，如果没有请传入0
     * @param original_check_sn       商户订单号
     * @param original_order_sn       本系统为该订单生成的订单序列号
     * @param reflect                 反射参数，可以在订单结果通知中返回
     * @return
     */
    @RequestMapping("/void")
    @ResponseBody
    public String Void(String original_workstation_sn, String original_check_sn,
                       String original_order_sn, String reflect) throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        original_workstation_sn = "0";
        original_check_sn = "74371875921119004333567833890722";
        //original_order_sn = "190805999888000014";
        //reflect = "1";
        String result = pos.Void(original_workstation_sn, original_check_sn, original_order_sn, reflect);
        return result;
    }


    /**
     * @param workstation_sn 门店收银机编号，如果没有请传入“0”
     * @param check_sn       商户订单号
     * @param order_sn       本系统为该订单生成的订单序列号
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public String query(String workstation_sn, String check_sn, String order_sn) throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        workstation_sn = "0";
        check_sn = "1";
        order_sn = "190805999888000014";
        String result = pos.query(workstation_sn, check_sn, order_sn);
        return result;

    }

}