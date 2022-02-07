package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @auther shkstart
 * @create 2022-01-27-10:46
 *在这里 的ok 无论在哪里的方法，都没有出现HystrixCommand这个注解标签
 * 当8001宕机了， 访问ok访问， 便会有服务降级。
 * 解决的代码的冗余， 和全局降级。
 * （yml、接口、主程序类、和这个类）
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "----------PaymentFallbackService fall back-paymentInfo_OK  /(ㄒoㄒ)/~~";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "----------PaymentFallbackService fall back-paymentInfo_TimeOut  /(ㄒoㄒ)/~~";
    }
}
