package com.shouyin.shouyin.controller;

import com.shouyin.shouyin.HttpProxy;
import com.shouyin.shouyin.model.ys;
import com.sun.jersey.core.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author ssddp
 * @ClassNameWoSaiController
 * @Description:
 * @date 2019/4/10 10:38
 * @Version 1.0
 **/
@Controller
@RequestMapping("/Shouyin")
public class WoSaiController {

    HttpProxy httpProxy = new HttpProxy();


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
        JSONObject jsonObject=httpProxy.activate(vendor_sn,vendor_key,appid,code);

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
        JSONObject jsonObject = httpProxy.checkin(terminal_sn,terminal_key);

        //获取新的终端号，终端密钥保存到内存中
        ys.getInstance().setZdh(jsonObject.getJSONObject("biz_response").get("terminal_sn").toString());
       ys.getInstance().setMy(jsonObject.getJSONObject("biz_response").get("terminal_key").toString());

        System.out.println("checkin.MyAndZdh:"+ys.getInstance().getMy()+"终端号:"+ys.getInstance().getZdh());
        System.out.println("checkin.toString():"+jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * 付款
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @param  total_amount:交易总金额
     * @param  dynamic_id:条码内容
     * @return
     */
    @RequestMapping("/pay")
    @ResponseBody
    public  String pay(String terminal_sn,String terminal_key,String total_amount,String dynamic_id,String hb_fq_seller_percent,String hb_fq_num){
        //获取保存在内存中的终端号，终端密钥
        terminal_sn = ys.getInstance().getZdh();
        terminal_key = ys.getInstance().getMy();

        System.out.println("pay:"+"terminal_sn:"+terminal_sn+"terminal_key:"+terminal_key+"total_amount:"+total_amount+"dynamic_id:"+dynamic_id);
        String result = httpProxy.pay(terminal_sn,terminal_key,total_amount,dynamic_id,hb_fq_seller_percent,hb_fq_num);
        System.out.println("pay.MyAndZdh:"+ys.getInstance().getMy()+"终端号:"+ys.getInstance().getZdh());
        System.out.println("pay.toString():"+result.toString());
        return result.toString();
    }

    /**
     * 预下单
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @param  total_amount:交易总金额
     * @return
     */
    @RequestMapping("/precreate")
    @ResponseBody
    public  String precreate(String terminal_sn,String terminal_key,String payway,String total_amount,String hb_fq_seller_percent,String hb_fq_num,HttpServletRequest request) throws JSONException {
        //获取保存在内存中的终端号，终端密钥
        terminal_sn = ys.getInstance().getZdh();
        terminal_key = ys.getInstance().getMy();

        System.out.println("precreate:"+"terminal_sn"+terminal_sn+"terminal_key"+terminal_key+"total_amount"+total_amount);
        String result = httpProxy.precreate(terminal_sn,terminal_key,payway,total_amount,hb_fq_seller_percent,hb_fq_num,request);
        System.out.println("precreate.MyAndZdh:"+ys.getInstance().getMy()+"终端号:"+ys.getInstance().getZdh());
        System.out.println("precreate.toString():"+result.toString());

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
        String result = httpProxy.refund(terminal_sn,terminal_key,sn,client_sn,refund_amount,operator,refund_request_no);
        System.out.println("refund.MyAndZdh:"+ys.getInstance().getMy()+"终端号:"+ys.getInstance().getZdh());
        System.out.println("refund.toString():"+result.toString());
        return result;
    }

    /**
     * 查询
     * @param terminal_sn:终端号
     * @param terminal_key:终端密钥
     * @param  sn:收钱吧唯一订单号
     * @param  client_sn:商户订单号
     * @param
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public  String query(String terminal_sn,String terminal_key,String sn,String client_sn){
        //获取保存在内存中的终端号，终端密钥
        terminal_sn = ys.getInstance().getZdh();
        terminal_key = ys.getInstance().getMy();

        System.out.println("query:"+"terminal_sn"+terminal_sn+"terminal_key"+terminal_key);
        String result = httpProxy.query(terminal_sn,terminal_key,sn,client_sn);
        System.out.println("query.MyAndZdh:"+ys.getInstance().getMy()+"终端号:"+ys.getInstance().getZdh());
        System.out.println("query.toString():"+result.toString());
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
        String result = httpProxy.cancel(terminal_sn,terminal_key,sn,client_sn);
        System.out.println("cancel.MyAndZdh:"+ys.getInstance().getMy()+"终端号:"+ys.getInstance().getZdh());
        System.out.println("cancel.toString()"+result.toString());
        return result;
    }

    /**
     * WAP API PRO支付接口
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @param  total_amount:交易总金额
     * @return
     */
    @RequestMapping("/gateway")
    @ResponseBody
    public String wapapipro(String terminal_sn, String terminal_key, String total_amount,String hb_fq_seller_percent,String hb_fq_num) throws IOException, JSONException {
        //获取保存在内存中的终端号，终端密钥
        terminal_sn = ys.getInstance().getZdh();
        terminal_key = ys.getInstance().getMy();

        System.out.println("wapapipro:"+"terminal_sn"+terminal_sn+"terminal_key"+terminal_key);
        String result = httpProxy.wapapipro(terminal_sn,terminal_key,total_amount,hb_fq_seller_percent,hb_fq_num);
        System.out.println("wapapipro.MyAndZdh:"+ys.getInstance().getMy()+"终端号:"+ys.getInstance().getZdh());
        System.out.println("wapapipro.toString()"+result.toString());
        return result.toString();
    }
    @RequestMapping("/a")

    public String a(){
        System.out.println("a");
        return "forward:/Shouyin/b";
    }

    @RequestMapping("/b")
    @ResponseBody
    public String b(){
        System.out.println("b");
        return  "index.html";
    }
}
