package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @auther shkstart
 * @create 2022-01-26-11:30
 * 手写了轮询算法（IRule）
 * 取消eureka的自我保护机制
 * 自定义选择myrule算法
 */
@SpringBootApplication
@EnableEurekaClient
//@RibbonClient(name = "cloud-provider-payment",configuration= MySelfRule.class)
//自定义选择myrule算法
public class OrderMain81 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain81.class,args);
    }
}
