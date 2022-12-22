package com.zhu.service;

import com.zhu.spring.MiniSpringApplicationContext;

/**
 * @author by zhuhcong
 * @descr
 * @date 2022/12/21 21:08
 */
public class Test {
    public static void main(String[] args) {
        MiniSpringApplicationContext miniSpringApplicationContext = new MiniSpringApplicationContext(AppConfig.class);

        System.out.println(miniSpringApplicationContext.getBean("userService"));
        System.out.println(miniSpringApplicationContext.getBean("userService"));
        System.out.println(miniSpringApplicationContext.getBean("userService"));
        System.out.println(miniSpringApplicationContext.getBean("userService"));
        System.out.println(miniSpringApplicationContext.getBean("userService"));
    }
}