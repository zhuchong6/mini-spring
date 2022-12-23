package com.zhu.spring;

/**
 * @author by zhuhcong
 * @descr
 * @date 2022/12/23 02:54
 */
public interface BeanPostProcessor {
    /**
     * do something before init
     */
    Object postProcessBeforeInitialization(Object bean, String beanName);

    /**
     * do something after init
     */
    Object postProcessAfterInitialization(Object bean, String beanName);
}