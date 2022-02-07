package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @auther shkstart
 * @create 2022-01-27-9:04
 */
@SpringBootApplication
@EnableFeignClients //frign调用
@EnableHystrix //hystrix 服务降级fallback
public class OrderHystrixMain81 {
    public static void main(String[] args) {
        SpringApplication.run(OrderHystrixMain81.class, args);

    }
}
