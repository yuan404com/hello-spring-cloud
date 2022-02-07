package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @auther shkstart
 * @create 2022-01-26-10:16
 */
@Mapper
public interface  PaymentDao {
    //新增
    public int creat(Payment payment);
    //读操作
    public Payment getPaymentById(@Param("id") Long id);

}
