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
    void postProcessBeforeInitialization(Object bean, String beanName);

    /**
     * do something after init
     */
    void postProcessAfterInitialization(Object bean, String beanName);
}