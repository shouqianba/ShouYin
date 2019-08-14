package com.shouyin.shouyin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class HttpProxy {
    private String api_domain ="https://api.shouqianba.com";
    private final static String CHARSET_UTF8 = "utf8";

    /**
     * 计算字符串的MD5值
     * @param  signStr:签名字符串
     * @return
     */
    public String getSign(String signStr) {
        try{
            String md5 = MD5Util.encryptMd5(signStr);
            return md5;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return  null ;
        }
    }

    public String getClient_Sn(int codeLenth){
        while (true) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < codeLenth; i++) {
                if (i == 0)
                    sb.append(new Random().nextInt(9) + 1); // first field will not start with 0.
                else
                    sb.append(new Random().nextInt(10));
            }
            return sb.toString();
        }
    }


    /**
     * 终端激活
     * @param  code:激活码
     * @param  vendor_sn:服务商序列号
     * @param  vendor_key:服务商密钥
     * @param  appid:应用编号
     * @return  {terminal_sn:"$终端号",terminal_key:"$终端密钥"}
     */
    public  JSONObject activate(String vendor_sn,String vendor_key,String appid,String code){
        String url = api_domain + "/terminal/activate";
        JSONObject params = new JSONObject();
        try{
            params.put("appid","2019070200001725");                                   //appid，必填
            params.put("code",code);                                     //激活码，必填
          //  params.put("device_id","30332-01");                     //客户方收银终端序列号，需保证同一appid下唯一，必填。为方便识别，建议格式为“品牌名+门店编号+‘POS’+POS编号“
         /*   params.put("client_sn","POS01");                             //客户方终端编号，一般客户方或系统给收银终端的编号，必填
            params.put("name","1hao");                                 //客户方终端名称，必填
            params.put("os_info","Mac OS");
            params.put("sdk_version","Java SDK v1.0");	 //SDK版本*/

            System.out.println("aa"+params.toString());
            String sign = getSign(params.toString() + vendor_key);
           System.out.println(sign);
            String result = HttpUtil.httpPost(url, params.toString(),sign,vendor_sn);
            System.out.println(result.toString());
            return new JSONObject(result);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 终端签到
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @return  {terminal_sn:"$终端号",terminal_key:"$终端密钥"}
     */
    public  JSONObject checkin(String terminal_sn,String terminal_key){
        String url = api_domain + "/terminal/checkin";
        JSONObject params = new JSONObject();
        try{
            params.put("terminal_sn",terminal_sn);                       
            params.put("device_id","00001asfsadf2");
            params.put("os_info","Mac OS"); //当前系统信息，如: Android5.0
            params.put("sdk_version","Java SDK v1.0");	 //SDK版本
            String sign = getSign(params.toString() + terminal_key);
            System.out.println(sign);
            String result = HttpUtil.httpPost(url, params.toString(),sign,terminal_sn);
            return  new JSONObject(result);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 预下单
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @param  total_amount:交易总金额
     * @return
     */
    public  String precreate(String terminal_sn,String terminal_key,String total_amount,String hb_fq_seller_percent,String hb_fq_num) throws JSONException {
        String url = api_domain + "/upay/v2/precreate";
        JSONObject params = new JSONObject();

        JSONObject map = new JSONObject();
        map.put("hb_fq_seller_percent",hb_fq_seller_percent);
        map.put("hb_fq_num",hb_fq_num);
        JSONObject map2 = new JSONObject();
        map2.put("extend_params",map);

        try{
            params.put("terminal_sn","100003640007866849");           //收钱吧终端ID
            params.put("client_sn",getClient_Sn(16));  //商户系统订单号,必须在商户系统内唯一；且长度不超过32字节
            params.put("total_amount",total_amount);               //交易总金额
            params.put("payway","1");	                     //支付方式
            //params.put("sub_payway","3");
           // params.put("payer_uid","okSzXt6LaRSqxwYDoa9LIRSYWoEY");
            params.put("subject","Pizza");	                 //交易简介
            params.put("operator","Kay");
            //params.put("extended",map2);
            params.put("notify_url","http://llp-love-llx.com/shouqianba/callback/orderdetail"); // 	服务器异步回调 url//门店操作员
            //params.put("sub_payway","2");	                 //二级支付方式
            System.out.println("params.toString()"+params.toString());
            String sign = getSign(params.toString() + "ed8ded88da8e35bee0282694faabdf5a");
            String result = HttpUtil.httpPost(url, params.toString(),sign,"100003640007866849");
            System.out.println("result:"+result);
            return  result;
        }catch (Exception e){
            return null;
        }
    }


    /**
     * 付款
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @param  total_amount:交易总金额
     * @param  dynamic_id:条码内容
     * @return
     */
    public  String pay(String terminal_sn,String terminal_key,String total_amount,String dynamic_id,String hb_fq_seller_percent,String hb_fq_num){
        String url = api_domain + "/upay/v2/pay";
        JSONObject params = new JSONObject();

        Map<String,String> map = new HashMap<>();
        map.put("hb_fq_seller_percent",hb_fq_seller_percent);
        map.put("hb_fq_num",hb_fq_num);
        Map<String,Object> map2 = new HashMap<>();
        map2.put("extend_params",map);
        try{
            params.put("terminal_sn","100003640007866849");           //终端号
            params.put("client_sn",getClient_Sn(16));  //商户系统订单号,必须在商户系统内唯一；且长度不超过64字节
            params.put("total_amount",total_amount);               //交易总金额,以分为单位
            //params.put("payway","1");	                     //支付方式,1:支付宝 3:微信 4:百付宝 5:京东钱包
            params.put("dynamic_id",dynamic_id);	 //条码内容
            params.put("subject","Pizza");	                 //交易简介
            params.put("operator","kay");	                 //门店操作员
           // params.put("extended",map2);
            String sign = getSign(params.toString() + "ed8ded88da8e35bee0282694faabdf5a");
            String result = HttpUtil.httpPost(url, params.toString(),sign,"100003640007866849");
            return  result;
        }catch (Exception e){
            return null;
        }
    }
    /**
     * 退款
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @param  sn:收钱吧唯一订单号
     * @param  client_sn:商户订单号
     * @param  refund_amount:退款金额
     * @param  operator:操作员
     * @return
     */
    public  String refund(String terminal_sn,String terminal_key,String sn,String client_sn,String refund_amount,String operator,String refund_request_no){
        String url = api_domain + "/upay/v2/refund";
        JSONObject params = new JSONObject();
        try{
            params.put("terminal_sn","100003640007866849");            //收钱吧终端ID
            params.put("sn",sn);              //收钱吧系统内部唯一订单号
            //params.put("client_sn","15621404524380120689");   //商户系统订单号,必须在商户系统内唯一；且长度不超过64字节
            params.put("refund_amount",refund_amount);               //退款金额
            params.put("refund_request_no",refund_request_no);	      //商户退款所需序列号,表明是第几次退款
            params.put("operator",operator);	                  //门店操作员
            //params.put("client_tsn","");

            String sign = getSign(params.toString() + "ed8ded88da8e35bee0282694faabdf5a");
            String result = HttpUtil.httpPost(url, params.toString(),sign,"100003640007866849");
            return  result;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 查询
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @return
     */
    public  String query(String terminal_sn,String terminal_key,String sn,String client_sn){
        String url = api_domain + "/upay/v2/query";
        JSONObject params = new JSONObject();
        try{
            params.put("terminal_sn","100003640007866849");           //终端号
            params.put("sn",sn);             //收钱吧系统内部唯一订单号
            params.put("client_sn",client_sn);  //商户系统订单号,必须在商户系统内唯一；且长度不超过64字节
            String sign = getSign(params.toString() + "ed8ded88da8e35bee0282694faabdf5a");
            String result = HttpUtil.httpPost(url, params.toString(),sign,"100003640007866849");
            return  result;
        }catch (Exception e){
            return null;
        }
    }
    /**
     * 自动撤单
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @return
     */
    public  String cancel(String terminal_sn,String terminal_key,String sn,String client_sn){
        String url = api_domain + "/upay/v2/cancel";
        JSONObject params = new JSONObject();
        try{
            params.put("terminal_sn","100003640007866849");           //终端号
            params.put("sn",sn);             //收钱吧系统内部唯一订单号
            params.put("client_sn",client_sn);  //商户系统订单号,必须在商户系统内唯一；且长度不超过64字节

            String sign = getSign(params.toString() + "ed8ded88da8e35bee0282694faabdf5a");
            String result = HttpUtil.httpPost(url, params.toString(),sign,"100003640007866849");

            return  result;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 排序
     * @param  params:签名字符串
     * @return
     */
    public static List<Map.Entry<String, String>> sortMap(final Map<String, String> params) {
        final List<Map.Entry<String, String>> infos = new ArrayList<Map.Entry<String, String>>(params.entrySet());

        Collections.sort(infos, new Comparator<Map.Entry<String, String>>()
                    {
            public int compare(final Map.Entry<String, String> o1, final Map.Entry<String, String> o2) {
                return (o1.getKey().toString().compareTo(o2.getKey()));
            }
        });
        return infos;
    }
    /**
     * WAP API PRO支付接口
     * @param  terminal_sn :终端号
     * @param  terminal_key :终端密钥
     * @param  total_amount :交易总金额
     * @return
     */
    public String wapapipro(String terminal_sn, String terminal_key, String total_amount,String hb_fq_seller_percent,String hb_fq_num) throws JSONException {
        final Map<String, String> params = new HashMap<String, String>();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hb_fq_seller_percent","0");
        jsonObject.put("hb_fq_num","12");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("extend_params",jsonObject);

        params.put("description", "WWWwww");  //商品详情
        params.put("client_sn", getClient_Sn(16));
        params.put("notify_url","localhost:9500/Shouyin/callback"); // 	服务器异步回调 url
        params.put("total_amount",total_amount);  //交易总金额
        params.put("return_url","http://llp-love-llx.com");  //页面跳转同步通知页面路径
        params.put("terminal_sn","100003640007866849");
        //params.put("payway","2");
        params.put("subject","aaa");  //交易概述
        params.put("operator","kay");  //门店操作员
        params.put("extended",jsonObject1.toString());
        final List<Map.Entry<String, String>> list = sortMap(params);
        StringBuffer str = new StringBuffer();

        //拼接参数字符串
        for (final Map.Entry<String, String> m : list) {
            String ss = m.getKey() + "=" + m.getValue()+"&";
            str.append(ss);
        }
        // 拼接密钥
        String psign = str.toString()+"key="+"ed8ded88da8e35bee0282694faabdf5a";
        String sign =getSign(psign).toUpperCase();        //加密转大写   getSign为获取加密后的内容
        StringBuffer newStr = new StringBuffer("https://qr.shouqianba.com/gateway?");
        newStr.append(str+"sign="+sign);
        System.out.println("newStr.toString():"+newStr.toString());
        return  newStr.toString();
    }


    /**
     * 支行列表接口
     * @param bank_card
     * @param terminal_sn
     * @param terminal_key
     * @return
     */
    public  String banks(String bank_card,String terminal_sn,String terminal_key) {
        String url = "http://api-sandbox.test.shouqianba.com/v2/merchant/banks";
        JSONObject params = new JSONObject();
        try{
            params.put("bank_card","6214852114497459");                                   //appid，必填
            String sign = getSign(params.toString() + "bf6a1021f1e788e8c9affd1f4ae0e982");
            System.out.println(sign);
            String result = HttpUtil.httpPost(url, params.toString(),sign,"91800129");
            System.out.println(result);
            return result;
        }catch (Exception e){
            return null;
        }
    }
}
