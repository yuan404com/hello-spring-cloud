package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @auther shkstart
 * @create 2022-01-26-19:14
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderConsulMain81 {
    public static void main(String[] args) {
        SpringApplication.run(OrderConsulMain81.class, args);

    }
}
