package com.qf.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qf.entity.Orders;
import com.qf.service.IOrderService;
import com.qf.util.AliPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/alipay")
    public void aliPay(Integer oid, HttpServletResponse response){

        Orders orders = orderService.queryById(oid);
        System.out.println("orderjkjk：" + orders);

        AlipayClient alipayClient = AliPayUtil.getAlipayClient();

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl("http://localhost:16666/order/list");
        alipayRequest.setNotifyUrl("http://2749n096f0.wicp.vip/pay/payCallback");

        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + orders.getOrderid() + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + orders.getAllprice().doubleValue() + "," +
                "    \"subject\":\"" + orders.getOrderid() + "\"," +
                "    \"body\":\"" + orders.getOrderid() + "\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/payCallback")
    @ResponseBody
    public String payCallback(
            String charset,
            String out_trade_no,
            String trade_status,
            String sign_type, HttpServletRequest request){

        Map<String, String> map = new HashMap<>();

        Map<String, String[]> parameterMap = request.getParameterMap();
        for(Map.Entry<String, String[]> entry : parameterMap.entrySet()){

            map.put(entry.getKey(), entry.getValue()[0]);
        }


        try {
            boolean flag = AlipaySignature.rsaCheckV1(
                    map,
                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB",
                    charset,
                    sign_type);
            if(flag){
                if(trade_status.equals("TRADE_SUCCESS") || trade_status.equals("TRADE_FINISHED")){
                    System.out.println("123456799ali");
                    int asd = orderService.updateOrderState(out_trade_no, 1);
                    System.out.println(asd);
                    return "success";
                }
            } else {
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "failure";
    }
}
