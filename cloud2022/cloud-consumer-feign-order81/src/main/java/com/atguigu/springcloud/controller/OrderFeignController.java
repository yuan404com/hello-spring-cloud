package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther shkstart
 * @create 2022-01-26-22:37
 */
@Slf4j
@RestController
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/consumer/payment/get/openfeign/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/openfeign/lb")
    public String getPaymentLB()
    {
        return paymentFeignService.getPaymentLB();
    }

    //服务提供方8001故意写暂停程序
    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeOut(){
        //openfeign-ribbon,客户端一般默认等待1秒。  然而我故意让它等待3秒（故意报错）
        //服务方自己访问没问题等待三秒，  客户端这边超过一秒，得不到结果就报错500
        return paymentFeignService.paymentFeignTimeOut();
    }
}
