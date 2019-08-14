package com.shouyin.shouyin.controller;

import com.shouyin.shouyin.HttpProxy;
import com.shouyin.shouyin.HttpPrxy2;
import com.shouyin.shouyin.model.ys;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ssddp
 * @ClassNameWoSaiController2
 * @Description: 预授权接口
 * @date 2019/5/15 14:23
 * @Version 1.0
 **/
@Controller
@RequestMapping("/Shouyin2")
public class WoSaiController2 {

   HttpPrxy2 httpPrxy2 = new HttpPrxy2();

    /**
     * 终端激活
     * @param  code:激活码
     * @param  vendor_sn:服务商序列号
     * @param  vendor_key:服务商密钥
     * @param  appid:应用编号
     * @return  {terminal_sn:"$终端号",terminal_key:"$终端密钥"}
     */
    @RequestMapping(value = "/activate")
    @ResponseBody
    public  String activate(String vendor_sn,String vendor_key,String appid,String code) throws JSONException {
        System.out.println("activate:"+"vendor_sn"+vendor_sn+"vendor_key"+vendor_key+"appid"+appid+"code"+code);
        JSONObject jsonObject=httpPrxy2.activate(vendor_sn,vendor_key,appid,code);

        //获取终端号，终端密钥保存到内存中
        ys.getInstance().setZdh(jsonObject.getJSONObject("biz_response").get("terminal_sn").toString());
        ys.getInstance().setMy(jsonObject.getJSONObject("biz_response").get("terminal_key").toString());

        System.out.println("activate.MyAndZdh:"+ys.getInstance().getMy()+"终端号:"+ys.getInstance().getZdh());
        System.out.println("activate.toString():"+jsonObject.toString());
        return  jsonObject.toString();
    }

    /**
     * 终端签到
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @return
     */
    @RequestMapping("/checkin")
    @ResponseBody
    public  String checkin(String terminal_sn,String terminal_key) throws JSONException {
        //获取保存在内存中的终端号，终端密钥
        terminal_sn = ys.getInstance().getZdh();
        terminal_key = ys.getInstance().getMy();

        System.out.println("checkin:"+"terminal_sn"+terminal_sn+"terminal_key"+terminal_key);
        JSONObject jsonObject = httpPrxy2.checkin(terminal_sn,terminal_key);

        //获取新的终端号，终端密钥保存到内存中
        ys.getInstance().setZdh(jsonObject.getJSONObject("biz_response").get("terminal_sn").toString());
        ys.getInstance().setMy(jsonObject.getJSONObject("biz_response").get("terminal_key").toString());

        System.out.println("checkin.MyAndZdh:"+ys.getInstance().getMy()+"终端号:"+ys.getInstance().getZdh());
        System.out.println("checkin.toString():"+jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     *  预授权冻结
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @param  total_amount:交易总金额
     * @param  dynamic_id:条码内容
     * @return
     */
    @RequestMapping("/freeze")
    @ResponseBody
    public  String freeze (String terminal_sn,String terminal_key,String total_amount,String dynamic_id){
        //获取保存在内存中的终端号，终端密钥
        terminal_sn = ys.getInstance().getZdh();
        terminal_key = ys.getInstance().getMy();

        System.out.println("freeze:"+"terminal_sn:"+terminal_sn+"terminal_key:"+terminal_key+"total_amount:"+total_amount+"dynamic_id:"+dynamic_id);
        String result = httpPrxy2.freeze(terminal_sn,terminal_key,total_amount,dynamic_id);
        System.out.println("freeze.MyAndZdh:"+ys.getInstance().getMy()+"终端号:"+ys.getInstance().getZdh());
        System.out.println("freeze.toString():"+result.toString());
        return result.toString();
    }

    /**
     * 预授权完成
     *@param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @param  client_sn:商户系统订单号
     * @param  operator:操作员
     *@param  consume_amount:消费金额
     * @param  sn:收钱吧系统订单号
     * @return
     */
    @RequestMapping("/consume")
    @ResponseBody
    public  String consume(String terminal_sn,String terminal_key,String client_sn,String sn,String operator,String consume_amount){
        //获取保存在内存中的终端号，终端密钥
        terminal_sn = ys.getInstance().getZdh();
        terminal_key = ys.getInstance().getMy();
        System.out.println
                ("consume:"+"terminal_sn:"+terminal_sn+"terminal_key:"+terminal_key+"client_sn:"+client_sn+"operator:"+operator+"consume_amount:"+consume_amount);
        String result = httpPrxy2.consume(terminal_sn,terminal_key,client_sn,sn,operator,consume_amount);
        System.out.println("consume.MyAndZdh:"+ys.getInstance().getMy()+"终端号:"+ys.getInstance().getZdh());
        System.out.println("consume.toString():"+result.toString());
        return result.toString();
    }

    /**
     * 退款
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @param  sn:收钱吧唯一订单号
     * @param  client_sn:商户订单号
     * @param  refund_amount:退款金额
     * @param  operator:操作员
     * @param  refund_request_no:退款序列号
     * @return
     */
    @RequestMapping("/refund")
    @ResponseBody
    public  String refund(String terminal_sn,String terminal_key,String sn,String client_sn,String refund_amount,String operator,String refund_request_no){
        //获取保存在内存中的终端号，终端密钥
        terminal_sn = ys.getInstance().getZdh();
        terminal_key = ys.getInstance().getMy();

        System.out.println("refund:"+"terminal_sn"+terminal_sn+"terminal_key"+terminal_key);
        String result = httpPrxy2.refund(terminal_sn,terminal_key,sn,client_sn,refund_amount,operator,refund_request_no);
        System.out.println("refund.MyAndZdh:"+ys.getInstance().getMy()+"终端号:"+ys.getInstance().getZdh());
        System.out.println("refund.toString():"+result.toString());
        return result;
    }

    /**
     * 自动撤单
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥*
     * @param  sn:收钱吧唯一订单号
     * @param  client_sn:商户订单号
     * @return
     */
    @RequestMapping("/cancel")
    @ResponseBody
    public  String cancel(String terminal_sn,String terminal_key,String sn,String client_sn){
        //获取保存在内存中的终端号，终端密钥
        terminal_sn = ys.getInstance().getZdh();
        terminal_key = ys.getInstance().getMy();

        System.out.println("cancel:"+"terminal_sn"+terminal_sn+"terminal_key"+terminal_key+"client_sn:"+client_sn);
        String result = httpPrxy2.cancel(terminal_sn,terminal_key,sn,client_sn);
        System.out.println("cancel.MyAndZdh:"+ys.getInstance().getMy()+"终端号:"+ys.getInstance().getZdh());
        System.out.println("cancel.toString()"+result.toString());
        return result;
    }
}
