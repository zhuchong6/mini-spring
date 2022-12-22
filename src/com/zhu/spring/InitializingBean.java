package com.zhu.spring;

/**
 * @author by zhuhcong
 * @descr
 * @date 2022/12/23 02:24
 */
public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
