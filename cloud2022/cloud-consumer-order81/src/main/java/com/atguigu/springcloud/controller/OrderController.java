package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * @auther shkstart
 * @create 2022-01-26-11:33
 */
@Slf4j
@RestController
public class OrderController {

//public static final String PAYMENT_URL = "http://localhost:8001";
public static final String PAYMENT_URL = "http://cloud-provider-payment";
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;//springframework
    @Resource
    private LoadBalancer loadBalancer;//手写轮询算法

    @GetMapping("/consumer/payment/creat")
    public CommonResult<Payment> creat(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/creat", payment, CommonResult.class);
        //return restTemplate.postForEntity(PAYMENT_URL+"/payment/creat", payment, CommonResult.class).getBody();
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable ("id")Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
        //返回对象为响应体中数据转化成的对象，基本上可以理解为Json
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable ("id")Long id){

        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        //返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头、响应状态码、响应体等
        if (entity.getStatusCode().is2xxSuccessful()){
           log.info("***************entity"+entity.getStatusCode().toString());
            return entity.getBody();
        }else{
            return new CommonResult<>(444,"操作失败");
        }

    }

//手写轮询算法。（需要关闭自定义类的lb）
    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB()
    {
        //选择服务名称
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-provider-payment");

        //这个不是有效的服务
        if(instances == null || instances.size()<=0) {
            return null;
        }

        //有效的服务--- 经过轮询计算获得脚标为index的服务名
        ServiceInstance serviceInstance = loadBalancer.instances(instances);

        URI uri = serviceInstance.getUri();

        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }


}
