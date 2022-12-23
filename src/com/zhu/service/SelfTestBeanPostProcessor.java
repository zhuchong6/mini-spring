package com.zhu.service;

import com.zhu.spring.BeanPostProcessor;
import com.zhu.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author by zhuhcong
 * @descr
 * @date 2022/12/23 02:57
 */
@Component
public class SelfTestBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if(beanName.equals("userService")){
            System.out.println("before init bean processor");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if(beanName.equals("userService")){
            System.out.println("after init bean processor");
        }

        if(beanName.equals("userService")){
            return Proxy.newProxyInstance(SelfTestBeanPostProcessor.class.getClassLoader(),
                    bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("aop logic-----");
                    return method.invoke(bean, args);
                }
            });
        }


        return bean;
    }
}