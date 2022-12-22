package com.zhu.service;

import com.zhu.spring.BeanPostProcessor;
import com.zhu.spring.Component;

/**
 * @author by zhuhcong
 * @descr
 * @date 2022/12/23 02:57
 */
@Component
public class SelfTestBeanPostProcessor implements BeanPostProcessor {
    @Override
    public void postProcessBeforeInitialization(Object bean, String beanName) {
        if(beanName.equals("userService")){
            System.out.println("before init bean processor");
        }

    }

    @Override
    public void postProcessAfterInitialization(Object bean, String beanName) {
        if(beanName.equals("userService")){
            System.out.println("after init bean processor");
        }
    }
}