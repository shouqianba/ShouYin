package com.shouyin.shouyin.controller;

import com.google.gson.Gson;
import com.shouyin.shouyin.model.NotifyResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ssddp
 * @ClassNameNotify
 * @Description: a
 * @date 2019/8/14 14:20
 * @Version 1.0
 **/
@RestController
@RequestMapping("/shouqianba/callback")
@Slf4j
public class Notify {

    @RequestMapping("/orderdetail")
    public String proback(HttpServletRequest request, Model model) {
        try {
            String sign = request.getHeader("Authorization");
            log.info("【回调sign】Authorization={}",sign);
            int contentLength = request.getContentLength();
            if (contentLength < 0) {
                return null;
            }
            byte[] buffer = new byte[contentLength];
            for (int i = 0; i < contentLength;) {
                int readlen = request.getInputStream().read(buffer, i, contentLength - i);
                if (readlen == -1) {
                    break;
                }
                i += readlen;
            }
            String charEncoding = request.getCharacterEncoding();
            if (charEncoding == null) {
                charEncoding = "UTF-8";
            }
            String backData = new String(buffer, charEncoding);
            log.info("【回调body】backData={}",backData);

            Gson gson = new Gson();
            Map<String,Object> map = new HashMap<String,Object>();
            map = gson.fromJson(backData,map.getClass());


            log.info("【收钱吧订单号】：sn={}",map.get("sn"));
            log.info("【收钱吧商户内部订单号】：client_sn={}",map.get("client_sn"));
            log.info("【收钱吧订单状态】：order_status={}",map.get("order_status"));

            if(map.get("order_status") != null){
                NotifyResult.getInstance().setSn(map.get("sn").toString());
                NotifyResult.getInstance().setClient_sn(map.get("client_sn").toString());
                NotifyResult.getInstance().setOrder_status(map.get("order_status").toString());
            }
            return "success";
        } catch (IOException e) {
            log.error("【回调异常】IOException={}",e.getMessage());
            return "file";
        }
    }
}
