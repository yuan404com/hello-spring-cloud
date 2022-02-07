package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @auther shkstart
 * @create 2022-01-26-11:36
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
   @LoadBalanced //赋予了restTemplate负载均衡的能力
    //手写了轮询算法，就要关闭lb
    public RestTemplate getRestTemplate(){
        return  new RestTemplate();
    }
}
