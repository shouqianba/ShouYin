package com.shouyin.shouyin;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @author ssddp
 * @ClassNameHttpPrxy2
 * @Description: a
 * @date 2019/5/15 14:09
 * @Version 1.0
 **/
public class HttpPrxy2 {
    private String api_domain ="https://api.shouqianba.com";
    private final static String CHARSET_UTF8 = "utf8";

    /**
     * 计算字符串的MD5值
     * @param  signStr:签名字符串
     * @return
     */
    public String getSign(String signStr) {
        try {
            String md5 = MD5Util.encryptMd5(signStr);
            return md5;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
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
            try {
                params.put("appid", appid);                                   //appid，必填
                params.put("code", code);                                     //激活码，必填
                params.put("device_id", "CNHM0001POS01");                     //客户方收银终端序列号，需保证同一appid下唯一，必填。为方便识别，建议格式为“品牌名+门店编号+‘POS’+POS编号“
            /*params.put("client_sn","POS01");                             //客户方终端编号，一般客户方或系统给收银终端的编号，必填
            params.put("name","1号款台");                                 //客户方终端名称，必填
            params.put("os_info","Mac OS");
            params.put("sdk_version","Java SDK v1.0");	 //SDK版本*/
                String sign = getSign(params.toString() + vendor_key);
                String result = HttpUtil.httpPost(url, params.toString(), sign, vendor_sn);
                return new JSONObject(result);
            } catch (Exception e) {
                return null;
            }
        }
    /**
     * 终端签到
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @return  {terminal_sn:"$终端号",terminal_key:"$终端密钥"}
     */
    public JSONObject checkin(String terminal_sn, String terminal_key){
        String url = api_domain + "/terminal/checkin";
        JSONObject params = new JSONObject();
        try{
            params.put("terminal_sn",terminal_sn);
            params.put("device_id","CNHM0001POS01");
            params.put("os_info","Mac OS"); //当前系统信息，如: Android5.0
            params.put("sdk_version","Java SDK v1.0");	 //SDK版本
            String sign = getSign(params.toString() + terminal_key);
            String result = HttpUtil.httpPost(url, params.toString(),sign,terminal_sn);
            return  new JSONObject(result);
        }catch (Exception e){
            return null;
        }
    }

    /**
     *  预授权冻结
     * @param  terminal_sn:终端号
     * @param  terminal_key:终端密钥
     * @param  total_amount:交易总金额
     * @param  dynamic_id:条码内容
     * @return
     */
    public  String freeze (String terminal_sn,String terminal_key,String total_amount,String dynamic_id){
        String url = api_domain + "/upay/v2/deposit/freeze";
        JSONObject params = new JSONObject();
        try{
            params.put("terminal_sn",terminal_sn);           //终端号
            params.put("client_sn",getClient_Sn(16));  //商户系统订单号,必须在商户系统内唯一；且长度不超过64字节
            params.put("total_amount",total_amount);               //交易总金额,以分为单位
            //params.put("payway","1");	                     //支付方式,1:支付宝 3:微信 4:百付宝 5:京东钱包
            params.put("dynamic_id",dynamic_id);	 //条码内容
            params.put("subject","Pizza");	                 //交易简介
            params.put("operator","kay");	                 //门店操作员

            String sign = getSign(params.toString() + terminal_key);
            String result = HttpUtil.httpPost(url, params.toString(),sign,terminal_sn);
            return  result;
        }catch (Exception e){
            return null;
        }
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
    public  String consume(String terminal_sn,String terminal_key,String client_sn,String sn,String operator,String consume_amount){
        String url = api_domain + "/upay/v2/deposit/consume";
        JSONObject params = new JSONObject();
        try{
            params.put("terminal_sn",terminal_sn);           //终端号
            params.put("client_sn",client_sn);  //商户系统订单号,必须在商户系统内唯一；且长度不超过64字节
            params.put("operator","kay");               //操作员
            params.put("consume_amount",consume_amount);	 //消费金额

            String sign = getSign(params.toString() + terminal_key);
            String result = HttpUtil.httpPost(url, params.toString(),sign,terminal_sn);
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
            params.put("terminal_sn",terminal_sn);            //收钱吧终端ID
            params.put("sn",sn);              //收钱吧系统内部唯一订单号
            params.put("client_sn",client_sn);   //商户系统订单号,必须在商户系统内唯一；且长度不超过64字节
            params.put("refund_amount",refund_amount);               //退款金额
            params.put("refund_request_no",refund_request_no);	      //商户退款所需序列号,表明是第几次退款
            params.put("operator",operator);	                  //门店操作员

            String sign = getSign(params.toString() + terminal_key);
            String result = HttpUtil.httpPost(url, params.toString(),sign,terminal_sn);

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
        String url = api_domain + "/upay/v2/deposit/cancel";
        JSONObject params = new JSONObject();
        try{
            params.put("terminal_sn",terminal_sn);           //终端号
            params.put("sn",sn);             //收钱吧系统内部唯一订单号
            params.put("client_sn",client_sn);  //商户系统订单号,必须在商户系统内唯一；且长度不超过64字节

            String sign = getSign(params.toString() + terminal_key);
            String result = HttpUtil.httpPost(url, params.toString(),sign,terminal_sn);
            return  result;
        }catch (Exception e){
            return null;
        }
    }




}

