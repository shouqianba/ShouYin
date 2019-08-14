package com.shouyin.shouyin;

import com.shouyin.shouyin.RSA.RSASignature;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author ssddp
 * @ClassNameHttpProxyPos
 * @Description: a
 * @date 2019/7/12 13:37
 * @Version 1.0
 **/
public class HttpProxyPos {

    private String api_domain ="https://vapi.shouqianba.com/";
    private String appid ="28lpm0000002";
    private String store_sn = "CS1";
    private String brand_code = "999888";
    private String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCe/j8SCVJgkuq3VF20TFCiuU1z1rd+VX\n" +
            "lfF0dkaBKxykl+MxaVQsdGQeldPMWDt7GTEAaTgcbAaaxT1S1sUStz9TNl02jgutiyZ+55iEr464l4ZTVV\n" +
            "BQk0KlDfqAb9Si6oWw2Env20bEjtgZWpgiybW6vY64SItLtj7G5etCFJ4M4qGOANpx9UEN2fQuDFTSPbGQ\n" +
            "Ki0F983eaTBdjrzREr5PWz4L4M7jU6SQSgEse9mihh9vwWt+d9Cffu+onUxPAvW14Je6kW3WA7S0vyyTsA\n" +
            "UceysgqvOKZQhoEs+Leg9DRD7UGwinuTc3A9qSYIGAb8V2nb0r0P5XsgmUTEmVZnAgMBAAECggEAf2T45F\n" +
            "rTxs3xhDP1YSJE+h3AEbFaFcAnICpm6ez6DbsoaBZHYhG/2mu1sR+go4nsnwmYO1khB8ukaQfG/aOMTb2L\n" +
            "EWvz/R25xDnCu16ZtBoGbJGryhPe7A17/7mC4DEwcXWDv2AlqQua47ORV+EWHOW3LHiKGX+3JrmrNfbfNX\n" +
            "HFwdQ7egHImCbaeVgfUBY3gUPB0Ynyf3e2ufr3QvV5Q57NUCx5YvYiP7zjj5/uFk76cGDC9CkH0LtZ2uYM\n" +
            "yMtx5iITmQhO29F7IY7EmOwETYS9aW7sd6RIMU6PJ/QcUez4iTILqNqF6ejZx2aWSH8z+fgFSVbn6Ao5dU\n" +
            "LrCphlAQKBgQDldzCi7lrsOT4w/g3xv7sM+fej8e4qSQzr+5Gv2DoGR/GqvrDd/BiR/VtYtSDpJe3SPOEY\n" +
            "ExRyeeWlWhuaa2AiC49ULwVJWlJqnfTiSL4gqUwFpfjFMP6+5KHfRaMYxibNSB9FfOBAgXcF+D4IITEVsQ\n" +
            "c1TCTmKB5ZGpjecRh6hQKBgQCxYOETZnK4NFUAZu1dRoTKyrBoui+4qqPxJSx4pdezt/8RAgJuk6UYcfkz\n" +
            "HGFsIl+oXH1hzmIcltxalc6sag4/75hGt9mNlMKySORYT3sgTK6nxa62aCXvvYcHWfr2V7cK/XgVB/RCiW\n" +
            "lxXjiVU4f0SqF8eMPoYf7Rh1hiNEk++wKBgQC9hcks+TMtokXkjyETR6mFmTvZM+vjDvzWN6znkO6z214W\n" +
            "CXPplNryUVDOHqP1DTe1CkVb7f5YYqey/46G5yK6W9Pg0wlJwYkKuDXXY/9s2IeKrr+els4A+rNbxpdj0d\n" +
            "2gdW4mpXJOtN+KlbMeYdO5t8JdWeusEPyn2ZjjOIPgRQKBgGIH5NzP9f8QDRpXyD+Qxbs+Ihj/LXil9k1D\n" +
            "+jwDjB7rRbCkp6ttNgU4mD1DJiSZKrzlwPXZFiguyEHYIYzwYEe9py8OVNIGsUPPPUQBSU8kkjJu8owlKz\n" +
            "JAUOwjMqwK9kLAqykUaE6NmxToueTtcWn2BSHBrKQ15Jrwbkx4ETMZAoGAM+/clMBth6NclFf5LIGc8JXd\n" +
            "47gI2Mbgm8a07I9aVw7FrpOPyyJ9YZwH3QXYoQ9Mn3alcoeU8BVs9FW2+8M3DJ0busvkxmF9AAt+5qFsFj\n" +
            "3mi2HW5T2VWv+0ElFT6Uer7k20WmL4bFHc1dqM0v5z4/U0pvIpCBT9lv004yJbIbw=";

    private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhH2ulxWxAIa1C2bvrBWNmqapGhDde47MmNAGdq\n" +
            "vtXEUjYUU0MhcdhCI/ALQ0Jsb4MvGsX5UjTlCJQ2twjpc7mcXY9ixyRUJWqH6gnKJZDR76ld63ODu9lMtg\n" +
            "IPktFkFWC5xIg5w6ElKCsCi1f3aMp2SodH2zJb6WqDslBG435a1dE3aJlJk6m5czL0Lv2JelBo3Ze0SjC4\n" +
            "I2XUQKnrAKhwCbkBWoDQVEOG1F3cX9nR9u2zDEhj5/Yp4StWMcyDrUfOLQs4FM5ZfaE5vl4Qf9nJkdESNF\n" +
            "64bW9zBVRoX7WBCcNyz1jpo932Qt2Wg/La2tx036XvZRzv7UKXOJ2ajSlQIDAQAB";
    private final static java.lang.String CHARSET_UTF8 = "utf8";

    /**
     * 计算字符串的MD5值
     *
     * @param signStr:签名字符串
     * @return
     */
    public java.lang.String getSign(java.lang.String signStr) {
        try{
            java.lang.String md5 = MD5Util.encryptMd5(signStr);
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
    //获取时间
    public String date(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        String sales_time1 = formatter.format(date);
        return sales_time1;
    }

    /**
     *
     * @param store_name 商户门店名称
     * @param workstation_sn 门店收银编号 如果没有传入0
     * @param check_sn 商户订单号 在商户系统中唯一
     * @param amount 订单价格 精确到分
     * @param currency 币种 如“156”for CNY
     * @param subject 订单主题
     * @param description 订单描述
     * @param operator 操作员
     * @param customer 客户信息
     * @param industry_code 行业代码 0:零售 1:酒店 2:餐饮 4:教育
     * @param pos_info 本接口对接的对端信息
     * @param resolution 是否支持拆单 1:不支持 2:支持
     *  @param request_id
     * @return
     */
        public String pos (String store_name,String workstation_sn,
                           String check_sn,String amount,String currency,
                           String subject,String description,String operator ,String customer,String industry_code,
                           String pos_info,String resolution,String request_id,String reflect) throws JSONException {
            String url = api_domain+"api/lite-pos/v1/sales/purchase";
            try {
            //head
            JSONObject headJson = new JSONObject();
            headJson.put("version","2.0.0");
            headJson.put("appid",appid);
            headJson.put("request_time",date());
            headJson.put("reserve","{}");

            //body
            JSONObject bodyJson = new JSONObject();
            bodyJson.put("brand_code",brand_code);
            bodyJson.put("store_sn",store_sn);
            bodyJson.put("store_name",store_name);
            bodyJson.put("workstation_sn",workstation_sn);
            bodyJson.put("check_sn",getClient_Sn(16));
            bodyJson.put("sales_time",date());
            bodyJson.put("amount",amount);
            bodyJson.put("currency",currency);
            bodyJson.put("subject",subject);
            bodyJson.put("description",description);
            bodyJson.put("operator",operator);
            bodyJson.put("customer",customer);
            bodyJson.put("industry_code",industry_code);
            bodyJson.put("pos_info",pos_info);
            bodyJson.put("resolution",resolution);
            bodyJson.put("request_id",getClient_Sn(10));
            bodyJson.put("reflect",reflect);
            //request
            JSONObject requestJson = new JSONObject();
            requestJson.put("head",headJson);
            requestJson.put("body",bodyJson);

            //全部
            JSONObject request = new JSONObject();
            request.put("request",requestJson);

            String sign = RSASignature.sign(requestJson.toString(),privateKey,"UTF-8");//使用私钥签名，并转成BASE64编码
            request.put("signature",sign);

            String result = HttpUtil.httpPostPos(url,request.toString(),sign); //请求
            System.out.println("result："+result);

             // 此处引入的是 com.alibaba.fastjson.JSONObject; 对象
           com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(result);
                // 获取到 key 对应的值

            String content = jsonObject.get("response").toString();

               /* String sign2     =  RSASignature.sign(content,publicKey,"UTF-8");
             boolean a =    RSASignature.doCheck(content,sign2,publicKey,"UTF-8");*/


            /* System.out.println("a"+a);*/
            return  result;
            } catch (Exception e) {
                return null;
            }
        }

    /**
     * 订单取消
     * @param original_workstation_sn 原始门店收银编号，如果没有请传入0
     * @param original_check_sn 商户订单号
     * @param original_order_sn    本系统为该订单生成的订单序列号
     * @param reflect   反射参数，可以在订单结果通知中返回
     * @return
     */
    public  String Void(String original_workstation_sn,String original_check_sn,
                            String original_order_sn,String reflect) throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String url = api_domain + "/api/lite-pos/v1/sales/void";
        //head
        JSONObject headJson = new JSONObject();
        headJson.put("version","2.0.0");
        headJson.put("appid",appid);
        headJson.put("request_time",date());
        headJson.put("reserve","{}");

        //body
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("brand_code",brand_code);
        bodyJson.put("original_workstation_sn",original_workstation_sn);
        bodyJson.put("original_store_sn",store_sn);
        bodyJson.put("original_check_sn",original_check_sn);
        bodyJson.put("original_order_sn",original_order_sn);
        bodyJson.put("reflect",reflect);
        bodyJson.put("request_id",getClient_Sn(10));

        //request
        JSONObject requestJson = new JSONObject();
        requestJson.put("head",headJson);
        requestJson.put("body",bodyJson);

        //全部
        JSONObject request = new JSONObject();
        request.put("request",requestJson);

        String sign = RSASignature.sign(requestJson.toString(),privateKey,"UTF-8");//使用私钥签名，并转成BASE64编码
        request.put("signature",sign);

        String result = HttpUtil.httpPostPos(url,request.toString(),sign); //请求
        return  result;
    }

    /**
     *
     * @param workstation_sn 门店收银机编号，如果没有请传入“0”
     * @param check_sn 商户订单号
     * @param order_sn 本系统为该订单生成的订单序列号
     * @return
     */
    public String query(String workstation_sn,String check_sn,String order_sn) throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String url = api_domain + "/api/lite-pos/v1/sales/query";

        //head
        JSONObject headJson = new JSONObject();
        headJson.put("version","2.0.0");
        headJson.put("appid",appid);
        headJson.put("request_time",date());
        headJson.put("reserve","{}");

        //body
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("brand_code",brand_code);
        bodyJson.put("store_sn",store_sn);
        bodyJson.put("workstation_sn",workstation_sn);
        bodyJson.put("check_sn",check_sn);
        bodyJson.put("order_sn",order_sn);

        //request
        JSONObject requestJson = new JSONObject();
        requestJson.put("head",headJson);
        requestJson.put("body",bodyJson);

        //全部
        JSONObject request = new JSONObject();
        request.put("request",requestJson);

        String sign = RSASignature.sign(requestJson.toString(),privateKey,"UTF-8");//使用私钥签名，并转成BASE64编码
        request.put("signature",sign);

        String result = HttpUtil.httpPostPos(url,request.toString(),sign); //请求


        return  result;
    }
}

