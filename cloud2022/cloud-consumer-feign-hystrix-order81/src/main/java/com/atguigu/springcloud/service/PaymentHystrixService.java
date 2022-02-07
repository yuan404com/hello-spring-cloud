package com.atguigu.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther shkstart
 * @create 2022-01-27-9:05
 * 只有这里的方法 才能调用到8001 服务名为cloud-provider-hystrix-payment的方法
 *面对的异常：1.运行时异常 2.超时 3.宕机
 */
@Component
@FeignClient(value = "cloud-provider-hystrix-payment",
        fallback = PaymentFallbackService.class)
public interface PaymentHystrixService {

    @GetMapping("/payment/hystrix/ok/{id}")
     String paymentInfo_OK(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
     String paymentInfo_TimeOut(@PathVariable("id") Integer id);

}
