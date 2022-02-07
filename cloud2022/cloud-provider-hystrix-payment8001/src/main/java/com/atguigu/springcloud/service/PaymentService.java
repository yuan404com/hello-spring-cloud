package com.atguigu.springcloud.service;

import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther shkstart
 * @create 2022-01-27-8:17
 */
public interface PaymentService {

    /**
     * 正常访问，一切OK
     * @param id
     * @return
     */
    String paymentInfo_OK(Integer id);

    /**
     * 超时访问，演示降级
     * @param id
     * @return
     */
     String paymentInfo_TimeOut(Integer id);


     //===服务熔断
      String paymentCircuitBreaker(@PathVariable("id") Integer id);

     String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id);
}
