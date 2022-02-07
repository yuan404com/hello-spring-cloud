package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther shkstart
 * @create 2022-01-26-21:22
 * Ribbon之手写轮询算法
 *CAS+自旋锁
 */
@Component
public class MyLB implements LoadBalancer {

    //RoundRobinRule 模仿原码
    //原子类
    private AtomicInteger atomicInteger = new AtomicInteger(0);


    //获取是第几次访问次数（1 2 3 4 5 6）
    //使用了自旋锁
    public final int getAndIncrement()
    {
        int current;
        int next;
        do
        {
            current = this.atomicInteger.get();//0
            next = current >= 2147483647 ? 0 : current + 1;
            /**
             *  next   用户当一直访问，访问次数的增加超过int的范围就为0
             *  否则就为current+1（1）
             */
        } while(!this.atomicInteger.compareAndSet(current, next));
        //compareAndSet（原先值和期望值相等为true）   抢数字 担心其他线程修改了
        //next为次数第1 2 3 4 5 6 7次访问。。。
        System.out.println("*****（第几次访问，次数next）next: "+next);
        return next;
    }


    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances)
            //传入参数-----该服务名的总台数
    {
        //实际调用服务器位置的下标= rest接口第几次访问数 % 服务器集群数量（8001，8002）那么就为2
        int index = getAndIncrement() % serviceInstances.size();

        return serviceInstances.get(index);
    }
}
