package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther shkstart
 * @create 2022-01-25-17:51
 * 传给前端的一个类
 */
@Data
@AllArgsConstructor//全参
@NoArgsConstructor//空参
public class CommonResult<T> {
   private Integer code;//传给前端的一个编码 例如404
   private String message;//传给前端的一个消息
   private T data;
   public CommonResult(Integer code,String message){
         this(code,message,null);
   }
}
