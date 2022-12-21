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

        UserService userService = (UserService) miniSpringApplicationContext.getBean("userService");
    }
}