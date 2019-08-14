package com.shouyin.shouyin;

import net.sf.json.JSONArray;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Objects;
import java.util.Random;

/**
 * @author ssddp
 * @ClassNameHttpPrxy2
 * @Description: a
 * @date 2019/5/15 14:09
 * @Version 1.0
 **/
public class HttpPrxyNetIn {
    private String api_domain ="http://api-sandbox.test.shouqianba.com";
    private String vendor_sn = "91800129";
    private String vendor_key = "bf6a1021f1e788e8c9affd1f4ae0e982";
    private String vendor_app_id = "2017110600000001";
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
     *
     * @param name : 商户名
     * @param contact_name : 联系人
     * @param contact_cellphone : 联系电话
     * @param area : 地区
     * @param street_address : 详细地址
     * @param account_type : 账户类型
     * @param bank_card : 银行卡号
     * @param bank_card_image : 银行卡照片
     * @param bank_name : 开户银行
     * @param bank_area : 开户地区
     * @param branch_name : 开户支行
     * @param holder : 开户姓名
     * @param legal_person_name : 法人姓名
     * @param business_license_photo : 营业执照
     * @param tax_payer_id : 工商注册号
     * @param id_type : 证件类型
     * @param identity : 身份证号
     * @param holder_id_front_photo : 身份证正面照
     * @param holder_id_back_photo : 身份证反面照
     * @param brand_photo : 门头照片
     * @param indoor_photo : 室内照片
     * @param outdoor_photo : 室外照片
     * @return
     */
    public JSONObject create(String name, String contact_name
            , String contact_cellphone, String area, String street_address
            , Integer account_type, String bank_card,String bank_card_image
            , String bank_name, String bank_area, String branch_name, String holder
            , String legal_person_name, String business_license_photo, String tax_payer_id
            , String id_type,String identity,String holder_id_front_photo,String holder_id_back_photo
            , String brand_photo, String indoor_photo, String outdoor_photo){
        String url = api_domain + "/v2/merchant/create";
        JSONObject params = new JSONObject();

        try{
            params.put("name",name); //商户名
            params.put("contact_name",contact_name); //联系人
            params.put("contact_cellphone",contact_cellphone); //联系电话
            params.put("area",area);	 //地区
            params.put("street_address",street_address);	 //详细地址
            params.put("account_type",account_type);	 //账户类型Integer
            params.put("bank_card",bank_card);	 //银行卡号
            params.put("bank_card_image",bank_card_image);	 //银行卡照片
            params.put("bank_name",bank_name);	 //开户银行
            params.put("bank_area",bank_area);	 //开户地区
            params.put("branch_name",branch_name);	 //开户支行
            params.put("holder",holder);	 //开户姓名
            params.put("legal_person_name",legal_person_name);	 //法人姓名，企业账户需要传
            params.put("business_license_photo",business_license_photo);	 //营业执照	，企业账户需要传
            params.put("tax_payer_id",tax_payer_id);	 //工商注册号，企业账户需要传
            params.put("id_type",id_type);	 //证件类型
            params.put("identity",identity);	 //身份证号
            params.put("holder_id_front_photo",holder_id_front_photo);	 //身份证正面照
            params.put("holder_id_back_photo",holder_id_back_photo);	 //身份证反面照
            params.put("brand_photo",brand_photo);	 //门头照片
            params.put("indoor_photo",indoor_photo);	 //室内照片
            params.put("outdoor_photo",outdoor_photo);	 //室外照片
            params.put("vendor_app_id",vendor_app_id);	 //服务商appid，收钱吧提供的服务商appid
            params.put("vendor_sn",vendor_sn);	 //服务商sn，收钱吧提供的服务商sn
            String sign = getSign(params.toString() + vendor_key);
            String result = HttpUtil.httpPost(url, params.toString(),sign,vendor_sn);
            return  new JSONObject(result);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 开户银行
     * @param bank_card 银行卡号
     * @return
     */
    public  String banks(String bank_card){
        String url = api_domain + "/v2/merchant/banks";
        JSONObject params = new JSONObject();
        try{
            params.put("bank_card",bank_card);           //银行卡号
            String sign = getSign(params.toString() + vendor_key);
            String result = HttpUtil.httpPost(url, params.toString(),sign,vendor_sn);
            return  result;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 开户支行
     * @param bank_name 开户银行名
     * @param bank_area 开户地区编号
     * @return
     */
    public  String branches(String bank_name,String bank_area){
        String url = api_domain + "/v2/merchant/branches";
        JSONObject params = new JSONObject();
        try{
            params.put("bank_name",bank_name);           //开户银行名
            params.put("bank_area",bank_area);           //开户地区编号
            String sign = getSign(params.toString() + vendor_key);
            String result = HttpUtil.httpPost(url, params.toString(),sign,vendor_sn);
            return  result;
        }catch (Exception e){
            return null;
        }
    }

    public  String upload(String file){
        String url = api_domain + "/v2/merchant/upload";
        JSONObject params = new JSONObject();
        try{
            params.put("file","aaa");           //图片
            String sign = getSign(params.toString() + vendor_key);
            String result = HttpUtil.httpPost(url, params.toString(),sign,vendor_sn);
            return  result;
        }catch (Exception e){
            return null;
        }
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
        if(imgStr == null){
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try{
            //解密
            byte[] b = decoder.decodeBuffer(imgStr);
            //处理数据
            for (int i = 0;i<b.length;++i){
                if(b[i]<0){
                    b[i]+=256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    public  String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println("encoder.encode(data):"+encoder.encode(Objects.requireNonNull(data)));
        String result = encoder.encode(data);
        return result;
    }
}

