package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @auther shkstart
 * @create 2022-01-26-10:43
 */
@Slf4j
@RestController
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;



//返回前端的数据
    @PostMapping("/payment/creat")
    public CommonResult creat(@RequestBody Payment payment){
        int result = paymentService.creat(payment);
        log.info("******插入结果="+result);
        if (result >0){
            return new CommonResult(200, "插入数据成功,serverPort:"+serverPort, result);
        }else {
            return new CommonResult(444, "插入数据失败");
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("******查询结果="+payment);
        if (payment !=null){
            return new CommonResult(200, "查询数据成功,serverPort:"+serverPort, payment);
        }else {
            return new CommonResult(444, "查询数据失败，查询id："+id);
        }
    }


    //向81 自己暴露自己信息
    @GetMapping("/payment/discovery")
    public Object discovert(){
        List<String> services = discoveryClient.getServices();
        for (String element :services){
            log.info("******element:"+element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PROVIDER-PAYMENT");
        for (ServiceInstance instance:instances){
            log.info(instance.getInstanceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB()
    {
        return serverPort;
    }

    //服务提供方8001故意写暂停程序
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeOut()
    {
        System.out.println("*****paymentFeignTimeOut from port: "+serverPort);
        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        return serverPort;
    }
}
