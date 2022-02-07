package com.atguigu.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @auther shkstart
 * @create 2022-01-27-8:18
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    /**
     * 正常访问，一切OK
     * @param id
     * @return
     */
    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池:"+Thread.currentThread().getName()+
                "  paymentInfo_OK,id: "+id+"\t"+"O(∩_∩)O";
    }

    /**
     * 超时访问，演示降级  模拟代码业务大
     * @param id
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="6000")
    })
    //commandProperties 就是正常的超时是在3秒钟之内，  可是现在是5秒钟
    //设置自身调用超时时间的峰值，峰值内可以正常运行，
    //超过了需要有兜底的方法处理，作服务降级fallback
    //兜底方法 fallbackMethod = "paymentInfo_TimeOutHandler"


    //单独的线程池 线程池:HystrixTimer-1 系统繁忙或者运行报错，请稍后再试,id: 3 /(ㄒoㄒ)/~~
    public String paymentInfo_TimeOut(Integer id) {
        int timeNumber = 4;
         //int age = 10 / 0;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) { e.printStackTrace(); }
        return "线程池:"+Thread.currentThread().getName()+
                "  paymentInfo_TimeOut,id: "+id+"\t"+"O(∩_∩)O，耗费"+timeNumber+"秒";
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池:"+Thread.currentThread().getName()+
                "  8001系统繁忙或者运行报错，请稍后再试,id: "+id+"\t"+"/(ㄒoㄒ)/~~";
    }

    /**
     * 上面故意制造两个异常：
     *    1  int age = 10/0; 计算异常
     *    2  我们能接受3秒钟，它运行5秒钟，超时异常。
     *
     *    当前服务不可 用了，做服务降级，兜底的方案都是paymentInfo_TimeOutHandler
     */



    //========服务熔断  服务的降级->进而熔断->恢复调用链路（半开状态）
    /**
     * 熔断机制概述
     * 熔断机制是应对雪崩效应的一种微服务链路保护机制。当扇出链路的某个微服务出错不可用或者响应时间太长时，
     * 会进行服务的降级，进而熔断该节点微服务的调用，快速返回错误的响应信息。
     * 当检测到该节点微服务调用响应正常后，恢复调用链路。
     *
     * 在Spring Cloud框架里，熔断机制通过Hystrix实现。Hystrix会监控微服务间调用的状况，
     * 当失败的调用到一定阈值，缺省是5秒内20次调用失败，就会启动熔断机制。熔断机制的注解是@HystrixCommand。
     */

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//失败率达到多少后跳闸
    })//在这十秒钟中，10次请求中，如果失败率达到60% 那么就跳闸拉
    /**
     * 到达以上阀值，断路器将会开启,当开启的时候，所有请求都不会进行转发
     *
     * 一段时间之后（默认是5秒），这个时候断路器是半开状态，会让其中一个请求进行转发。
     * 如果成功，断路器会关闭，若失败，继续开启。重复上面操作
     */
    public String paymentCircuitBreaker(@PathVariable("id") Integer id)
    {
        if(id < 0)
        {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID(); //相当于UUID.randomUUID().toString()
        //hutool.cn  一个工具包（在cloud-api-commons 引入了）
        //生成一个不带-号的唯一流水号

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }

    //这是先服务降级
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id)
    {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }


}
