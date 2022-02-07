package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther shkstart
 * @create 2022-01-25-17:48
 */
@Data
@AllArgsConstructor//全参
@NoArgsConstructor//空参
public class Payment implements Serializable {
    private Long id;
    private String serial;
}
