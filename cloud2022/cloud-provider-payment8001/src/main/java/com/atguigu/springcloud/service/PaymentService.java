package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @auther shkstart
 * @create 2022-01-26-10:40
 */
public interface PaymentService {

    //新增
     int creat(Payment payment);
    //读操作
     Payment getPaymentById(@Param("id") Long id);


}
