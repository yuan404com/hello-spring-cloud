package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther shkstart
 * @create 2022-01-26-22:31
 * 去提供者找 对外暴露的接口（举例一个根据id查找的接口）
 * 也可以直接在提供者的控制类controller里面找
 */
@Component
@FeignClient(value = "cloud-provider-payment")
public interface PaymentFeignService {
    //读操作
    @GetMapping(value = "/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);
     //在消费者这里 需要用CommonResult 毕竟是返回给前端的

     @GetMapping(value = "/payment/lb")
      String getPaymentLB();//这里也行 并没有在service接口中定义的

    //服务提供方8001故意写暂停程序
    @GetMapping(value = "/payment/feign/timeout")
     String paymentFeignTimeOut();
}
